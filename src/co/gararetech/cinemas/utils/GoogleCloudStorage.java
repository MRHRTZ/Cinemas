package co.gararetech.cinemas.utils;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class GoogleCloudStorage {

    final private String project = "gararetech";
    final private String bucket = "gararetech-bucket";
    private Storage storage;

    public GoogleCloudStorage() throws URISyntaxException, FileNotFoundException, IOException {
        InputStream jsonAccountPath = this.getClass().getResourceAsStream("/co/gararetech/cinemas/credentials/gararetech-account.json");
        StorageOptions storageOptions = StorageOptions.newBuilder()
                .setProjectId(this.project)
                .setCredentials(GoogleCredentials.fromStream(jsonAccountPath)).build();
        this.storage = storageOptions.getService();
    }

    public String getProject() {
        return project;
    }

    public String getBucket() {
        return bucket;
    }

    public Storage getStorage() {
        return storage;
    }

    public String generateID(int length) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = length;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString.toUpperCase();
    }

    public byte[] extractBytes(String ImageName) throws IOException {
        // open image
        File imgPath = new File(ImageName);
        BufferedImage bufferedImage = ImageIO.read(imgPath);

        // get DataBufferBytes from Raster
        WritableRaster raster = bufferedImage.getRaster();
        DataBufferByte data = (DataBufferByte) raster.getDataBuffer();

        return (data.getData());
    }

    public String uploadFile(String imagePath) {
        File file = new File(imagePath);
        String pathFilename = generateID(30) + "-" + file.getName();

        try {
            URLConnection connection = file.toURL().openConnection();
            String mimeType = connection.getContentType();
            System.out.println(mimeType);

            BlobId blobId = BlobId.of(this.bucket, pathFilename);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setContentType(mimeType)
                    .build();

            storage.create(blobInfo, Files.readAllBytes(Paths.get(imagePath)));

            System.out.println("Uploaded filename to bucket " + this.bucket);
        } catch (IOException ex) {
            Logger.getLogger(GoogleCloudStorage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String result = "https://storage.googleapis.com/" + this.bucket + "/" + pathFilename;

        return result;
    }

}
