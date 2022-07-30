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
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.http.*;
import java.net.http.HttpResponse.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingWorker;

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
        String password = String.valueOf(login.getTxtPassword().getPassword());

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
                dashboard.getModel().setInvalidMessage("Data email dan password tidak boleh kosong");
                login.dispose();
            } else if (userData == null) {
                dashboard.getModel().setInvalidMessage("Email atau Password salah!");
                login.dispose();
                System.out.println("Failed login invalid email " + email + ":" + password);
            } else {
                String md5Password = MD5(password);
                if (md5Password.equals(userData.getString("password"))) {
                    dashboard.getModel().setUserData(userData);
                    System.out.println("Success login " + email + ":" + password);
                    login.dispose();
                    dashboard.setVisible(true);
                } else {
                    System.out.println("Failed login wrong password " + email + ":" + password);
                    dashboard.getModel().setInvalidMessage("Email atau Password salah!");
                    login.dispose();
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

    public void loading(JButton button, Boolean status) {
        if (status) {
            button.setIcon(new ImageIcon(getClass().getResource("/co/gararetech/cinemas/view/images/loading-25.gif")));
        } else {
            button.setIcon(null);
        }
    }

    public void exitButton(LoginView view) {
        if (JOptionPane.showConfirmDialog(view, "Apakah Anda Mau Keluar ?", "Cinemas",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public void minimizeButton(LoginView view) {
        view.setState(LoginView.ICONIFIED);
    }
}
