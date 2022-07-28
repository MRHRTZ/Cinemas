package co.gararetech.cinemas.controller;

import co.gararetech.cinemas.model.DashboardModel;
import co.gararetech.cinemas.model.ProfileModel;
import co.gararetech.cinemas.utils.FileChooser;
import co.gararetech.cinemas.view.DashboardView;
import co.gararetech.cinemas.view.ProfileView;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.filechooser.FileNameExtensionFilter;
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
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
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
        dashboardView.setVisible(true);
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
        String image = userData.getString("image");

        String urlParameters = "post_type=" + post_type + "&user_id=" + user_id + "&city_id=" + city_id + "&email=" + email + "&password=" + password + "&image=" + image;

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
            } else if (pass_new.equals("")) {
                JOptionPane.showMessageDialog(view, "Field masih kosong");
            } else {
                model.getUserData().put("city_id", city_name);
                model.getUserData().put("email", email);
                model.getUserData().put("password", pass_new);
                System.out.println("Update user all type");
                new Thread() {
                    public void run() {
                        try {
                            postUpdate("all");
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
                        JOptionPane.showMessageDialog(view, "Berhasil update profile!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }.start();
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

    public void chooseImage(ProfileView view) throws IOException {
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

            Path fileBuffer = Paths.get(path);
            String base64 = fileToBase64(fileBuffer);

            model.getUserData().put("image", base64);
            ImageIcon image = new ImageIcon(path);
            Image img;
            if (image.getIconWidth() > image.getIconHeight()) {
                img = image.getImage().getScaledInstance(100, -1, Image.SCALE_SMOOTH);
            } else {
                img = image.getImage().getScaledInstance(-1, 100, Image.SCALE_SMOOTH);
            }
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

    public void loading(JButton button, Boolean status) {
        if (status) {
            button.setIcon(new ImageIcon(getClass().getResource("../view/images/loading-25.gif")));
        } else {
            button.setIcon(null);
        }
    }

    public void exitButton(ProfileView view) {
        if (JOptionPane.showConfirmDialog(view, "Apakah Anda Mau Keluar ?", "Cinemas",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public void minimizeButton(ProfileView view) {
        view.setState(view.ICONIFIED);
    }
}
