package co.gararetech.cinemas.controller;

import co.gararetech.cinemas.model.RegisterModel;
import co.gararetech.cinemas.view.LoginView;
import co.gararetech.cinemas.view.RegisterView;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONObject;

public class RegisterController {

    private RegisterModel model;

    public RegisterModel getModel() {
        return model;
    }

    public void setModel(RegisterModel model) {
        this.model = model;
    }

    public JSONArray getUserList() throws MalformedURLException, IOException {
        URL usersUrl = model.getShowUserURL();

        model.setConnection((HttpURLConnection) usersUrl.openConnection());
        model.getConnection().setRequestMethod("GET");
        model.getConnection().setConnectTimeout(5000);
        model.getConnection().setReadTimeout(5000);

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
        System.out.println("register users : " + responseContent.toString());

        return new JSONArray(responseContent.toString());
    }

    public JSONObject postUser(String email, String password) throws MalformedURLException, IOException {
        URL usersUrl = model.getCreateUserURL();
        String urlParameters = "email=" + email + "&password=" + password;
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
        System.out.println("register post result : " + responseContent.toString());

        return new JSONObject(responseContent.toString());
    }

    public void submit(RegisterView register, LoginView login) {
        String email = register.getTxtEmail().getText();
        String password1 = register.getTxtPassword1().getText();
        String password2 = register.getTxtPassword2().getText();
        Boolean isDuplicateEmail = false;

        try {
            JSONArray userList = getUserList();
            for (int i = 0; i < userList.length(); i++) {
                JSONObject row = userList.getJSONObject(i);
                if (row.getString("email").equals(email)) {
                    isDuplicateEmail = true;
                    break;
                }
            }

            System.out.println("DupMail " + isDuplicateEmail);
            if (email.equals("") && password1.equals("") && password2.equals("")) {
                JOptionPane.showMessageDialog(register, "Email dan Password belum terisi");
            } else if (isDuplicateEmail) {
                JOptionPane.showMessageDialog(register, "Email tersebut telah terpakai");
            } else if (!password1.equals(password2)) {
                JOptionPane.showMessageDialog(register, "Password tidak sesuai");
            } else {
                System.out.println("Post user action " + email + ":" + password2);
                JSONObject result = postUser(email, password2);
                if (result.getBoolean("status")) {
                    JOptionPane.showMessageDialog(register, "Register berhasil silahkan login");
                    register.dispose();
                    login.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(register, "Gagal register!");
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void viewLogin(RegisterView register, LoginView login) {
        register.dispose();
        login.setVisible(true);
    }
    
    public void loading(JButton button, Boolean status) {
        if (status) {
            button.setIcon(new ImageIcon(getClass().getResource("../view/images/loading-25.gif")));
        } else {
            button.setIcon(null);
        }
    }
    public void exitButton(RegisterView view){
        if (JOptionPane.showConfirmDialog(view, "Apakah Anda Mau Keluar ?","Cinemas",
            JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
            System.exit(0);
    }
    public void minimizeButton(RegisterView view){
        view.setState(RegisterView.ICONIFIED);
    }
}
