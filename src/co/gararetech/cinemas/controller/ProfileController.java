package co.gararetech.cinemas.controller;

import co.gararetech.cinemas.model.ProfileModel;
import co.gararetech.cinemas.utils.FileChooser;
import co.gararetech.cinemas.utils.GoogleCloudStorage;
import co.gararetech.cinemas.view.DashboardView;
import co.gararetech.cinemas.view.ProfileView;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.filechooser.FileFilter;
import org.json.JSONObject;

/**
 *
 * @author Acer
 */
public class ProfileController {

    private ProfileModel model;

    public ProfileModel getModel() {
        return model;
    }

    public void setModel(ProfileModel model) {
        this.model = model;
    }

    public void back(ProfileView profileView, DashboardView dashboardView) {
        profileView.setVisible(false);
        DashboardController dashboardController = dashboardView.getDashboardController();
        dashboardController.getModel().setNeedRefresh(true);
        dashboardView.setLoadingUser(dashboardController.addDialogLoading(dashboardView, " Sedang memperbarui data pengguna .."));
        new Thread() {
            public void run() {
                try {
                    dashboardView.getDashboardController().refreshUserData(dashboardView, dashboardView.getNowPlayingController(), dashboardView.getUpcomingController(), dashboardView.getOrderHistoryController(), dashboardView.getCinemaListController());
                    dashboardView.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    public void selectedChangePassword(ProfileView view, ChangeEvent evt) {
        AbstractButton abstractButton = (AbstractButton) evt.getSource();
        ButtonModel buttonModel = abstractButton.getModel();
        boolean selected = buttonModel.isSelected();
        view.getTxtOldPassword().setEnabled(selected);
        view.getTxtNewPassword().setEnabled(selected);
    }

    public JSONObject postUpdate(String post_type) throws MalformedURLException, IOException {

        URL usersUrl = model.getUpdateUserURL();

        JSONObject userData = model.getUserData();
        String user_id = userData.getString("user_id");
        String city_id = userData.getString("city_id");
        String email = userData.getString("email");
        String password = userData.getString("password");

        String urlParameters;

        if (!userData.isNull("image")) {
            String image = userData.getString("image");

            if (image.equals("")) {
                urlParameters = "post_type=" + post_type + "&user_id=" + user_id + "&city_id=" + city_id + "&email=" + email + "&password=" + password;
            } else {
                urlParameters = "post_type=" + post_type + "&user_id=" + user_id + "&city_id=" + city_id + "&email=" + email + "&password=" + password + "&image=" + image;
            }

        } else {
            urlParameters = "post_type=" + post_type + "&user_id=" + user_id + "&city_id=" + city_id + "&email=" + email + "&password=" + password;
        }

        System.out.println("Update user parameter " + urlParameters);

        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;

        model.setConnection((HttpURLConnection) usersUrl.openConnection());
        model.getConnection().setRequestMethod("POST");
        model.getConnection().setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        model.getConnection().setRequestProperty("charset", "utf-8");
        model.getConnection().setRequestProperty("Content-Length", Integer.toString(postDataLength));
        model.getConnection().setConnectTimeout(5000);
        model.getConnection().setReadTimeout(5000);
        model.getConnection().setDoOutput(true);
        model.getConnection().setUseCaches(false);
        try ( DataOutputStream wr = new DataOutputStream(model.getConnection().getOutputStream())) {
            wr.write(postData);
        }

        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        int status = model.getConnection().getResponseCode();

        if (status > 299) {
            reader = new BufferedReader(new InputStreamReader(model.getConnection().getErrorStream()));
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            reader.close();
        } else {
            reader = new BufferedReader(new InputStreamReader(model.getConnection().getInputStream()));
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            reader.close();
        }
        System.out.println("Update user post result : " + responseContent.toString());

        return new JSONObject(responseContent.toString());
    }

    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateUser(ProfileView view) throws IOException {
        view.getBtnProfileSave().setEnabled(false);
        view.getBtnProfileSave().setIcon(new ImageIcon(getClass().getResource("/co/gararetech/cinemas/view/images/loading-25.gif")));

        String city_name = String.valueOf(view.getjCity().getSelectedItem());
        String email = view.getTxtEmail().getText();
        String pass_old = String.valueOf(view.getTxtOldPassword().getPassword());
        String pass_new = String.valueOf(view.getTxtNewPassword().getPassword());

        Boolean isPassChange = view.getIsChangePassword().isSelected();

        if (isPassChange) {
            String md5_pass_old = MD5(pass_old);
            String user_pass_old = model.getUserData().getString("password");

            if (!user_pass_old.equals(md5_pass_old)) {
                JOptionPane.showMessageDialog(view, "Password lama tidak sesuai!");
                view.getBtnProfileSave().setEnabled(true);

            } else if (pass_new.equals("")) {
                JOptionPane.showMessageDialog(view, "Field masih kosong");
                view.getBtnProfileSave().setEnabled(true);

            } else {
                model.getUserData().put("city_id", city_name);
                model.getUserData().put("email", email);
                model.getUserData().put("password", pass_new);
                System.out.println("Update user all type");
                new Thread() {
                    public void run() {
                        try {
                            postUpdate("all");
                            view.getBtnProfileSave().setEnabled(true);
                            view.getBtnProfileSave().setIcon(null);
                            JOptionPane.showMessageDialog(view, "Berhasil update profile!");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }.start();
            }
        } else {
            model.getUserData().put("city_id", city_name);
            model.getUserData().put("email", email);
            System.out.println("Update user no_pass type");
            new Thread() {
                public void run() {
                    try {
                        postUpdate("no_pass");
                        view.getBtnProfileSave().setEnabled(true);
                        view.getBtnProfileSave().setIcon(null);
                        JOptionPane.showMessageDialog(view, "Berhasil update profile!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }.start();
        }
    }

    public void pictureload(ProfileView view) throws MalformedURLException, IOException {
        if (!model.getUserData().isNull("image")) {
            if (model.getUserData().getString("image").equals("")) {
                BufferedImage Img = ImageIO.read(getClass().getResource("/co/gararetech/cinemas/view/images/ProfileIconBlack.png"));
                BufferedImage Images = makeRoundedCorner(Img, 1000);
                view.getProfilePicture().setIcon(new ImageIcon(Images));
            } else {
                System.out.println("Img url : " + model.getUserData().getString("image"));
                URL dataImageUrl = new URL(model.getUserData().getString("image").replaceAll(" ", "%20"));

                BufferedImage Img = ImageIO.read(dataImageUrl);
                BufferedImage roundedImage = makeRoundedCorner(Img, 8000);

                Image images = roundedImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                ImageIcon img = new ImageIcon(images);
                view.getProfilePicture().setIcon(img);
            }
        }
    }

    public String fileToBase64(Path filePath) throws IOException {
        try ( InputStream in = Files.newInputStream(filePath, StandardOpenOption.READ)) {
            final ByteArrayOutputStream bos = new ByteArrayOutputStream();

            try ( OutputStream out = Base64.getEncoder().wrap(bos)) {
                in.transferTo(out);
            }

            return bos.toString(StandardCharsets.UTF_8);
        }
    }

    public void chooseImage(ProfileView view, GoogleCloudStorage gcs) throws IOException {
        JFileChooser ch = new JFileChooser();
        FileChooser preview = new FileChooser();
        ch.setAccessory(preview);
        ch.addPropertyChangeListener(preview);
        ch.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                String name = file.getName();
                return file.isDirectory() || name.endsWith(".png") || name.endsWith(".PNG") || name.endsWith("jpg") || name.endsWith("JPG");
            }

            @Override
            public String getDescription() {
                return "png,jpg";
            }
        });
        int opt = ch.showOpenDialog(view);
        if (opt == JFileChooser.APPROVE_OPTION) {
            String path = ch.getSelectedFile().getAbsolutePath();
            System.out.println("Set profile picture " + path);

            String imageUrl = gcs.uploadFile(path);
            System.out.println("Uploaded URL : " + imageUrl);

            model.getUserData().put("image", imageUrl);
            ImageIcon image = new ImageIcon(path);
            System.out.println("icon img : " + image);
            URL dataImageUrl = new URL(model.getUserData().getString("image").replaceAll(" ", "%20"));
            BufferedImage Img = ImageIO.read(dataImageUrl);
            BufferedImage roundedImage = makeRoundedCorner(Img, 8100);
            Image img = roundedImage.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            ImageIcon profile_pic = new ImageIcon(img);
            view.getProfilePicture().setIcon(profile_pic);

            System.out.println("Update user image type");
            new Thread() {
                public void run() {
                    try {
                        postUpdate("image");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }.start();
        }
    }

    public BufferedImage makeRoundedCorner(BufferedImage image, int cornerRadius) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = output.createGraphics();

        // This is what we want, but it only does hard-clipping, i.e. aliasing
        // g2.setClip(new RoundRectangle2D ...)
        // so instead fake soft-clipping by first drawing the desired clip shape
        // in fully opaque white with antialiasing enabled...
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));

        // ... then compositing the image on top,
        // using the white shape from above as alpha source
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);

        g2.dispose();

        return output;
    }

    public void loading(JButton button, Boolean status) {
        if (status) {
            button.setIcon(new ImageIcon(getClass().getResource("/co/gararetech/cinemas/view/images/loading-25.gif")));
        } else {
            button.setIcon(null);
        }
    }

    public void exitButton(ProfileView view, DashboardView dashboard) {
        if (JOptionPane.showConfirmDialog(view, "Apakah Anda Mau Keluar ?", "Cinemas",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            System.exit(0);

        }
    }

    public void minimizeButton(ProfileView view) {
        view.setState(view.ICONIFIED);
    }
}
