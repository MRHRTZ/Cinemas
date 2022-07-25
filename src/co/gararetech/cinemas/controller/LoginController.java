package co.gararetech.cinemas.controller;

import co.gararetech.cinemas.model.LoginModel;
import co.gararetech.cinemas.view.DashboardView;
import co.gararetech.cinemas.view.LoginView;
import co.gararetech.cinemas.view.RegisterView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONObject;

public class LoginController {

    private LoginModel model;

    public LoginModel getModel() {
        return model;
    }

    public void setModel(LoginModel model) {
        this.model = model;
    }

    public JSONArray getUserList() throws MalformedURLException, IOException {
        URL usersUrl = model.getUsersEndpoint();

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

        return new JSONArray(responseContent.toString());
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

    public void submit(LoginView login, DashboardView dashboard) {
        String email = login.getTxtEmail().getText();
        String password = login.getTxtPassword().getText();
        JSONObject userData = null;
        
        try {
            JSONArray userList = getUserList();
            for (int i = 0; i < userList.length(); i++) {
                JSONObject row = userList.getJSONObject(i);
                
                if (row.getString("email").equals(email)) {
                    userData = row;
                }
            }
            
            if (email.equals("") && password.equals("")) {
                JOptionPane.showMessageDialog(login, "Data email dan password tidak boleh kosong");
            } else if (userData == null) {
                JOptionPane.showMessageDialog(login, "Email atau Password salah!");
            } else {
                String md5Password = MD5(password);
                if (md5Password.equals(userData.getString("password"))) {
                    model.setUserData(userData);
                    JOptionPane.showMessageDialog(login, "Login berhasil");
                    login.dispose();
                    dashboard.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(login, "Email atau Password salah");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void viewRegister(LoginView login, RegisterView register) {
        login.dispose();
        register.setVisible(true);
    }
}
