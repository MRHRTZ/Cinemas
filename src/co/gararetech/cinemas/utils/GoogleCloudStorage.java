package co.gararetech.cinemas.utils;

import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class GoogleCloudStorage {

    final private String project = "gararetech";
    final private String bucket = "gararetech-bucket";
    private Storage storage;

    public GoogleCloudStorage() throws URISyntaxException, FileNotFoundException, IOException {
        String jsonAccountPath = getClass().getResource("/co/gararetech/cinemas/credentials/gararetech-account.json").getPath().replaceAll("%20", " ");
        StorageOptions storageOptions = StorageOptions.newBuilder()
                .setProjectId(this.project)
                .setCredentials(GoogleCredentials.fromStream(new FileInputStream(jsonAccountPath))).build();
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
        try {
            URLConnection connection = file.toURL().openConnection();
            String mimeType = connection.getContentType();
            System.out.println(mimeType);
            byte[] content = extractBytes(imagePath);

            BlobId blobId = BlobId.of(this.bucket, file.getName());
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setContentType(mimeType)
                    .build();
            
            storage.create(blobInfo, Files.readAllBytes(Paths.get(imagePath)));

            System.out.println("Uploaded filename to bucket " + this.bucket);
        } catch (IOException ex) {
            Logger.getLogger(GoogleCloudStorage.class.getName()).log(Level.SEVERE, null, ex);
        }
        String result = "https://storage.googleapis.com/" + this.bucket + "/" + file.getName();

        return result;
    }

}
