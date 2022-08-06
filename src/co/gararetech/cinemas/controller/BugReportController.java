package co.gararetech.cinemas.controller;

import co.gararetech.cinemas.model.DashboardModel;
import co.gararetech.cinemas.view.BugReportView;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class BugReportController {

    private DashboardModel model;

    public BugReportController() {

    }

    public DashboardModel getModel() {
        return model;
    }

    public void setModel(DashboardModel model) {
        this.model = model;
    }
     public void minimizeButton(BugReportView view) {
        view.setState(BugReportView.ICONIFIED);
    }
    
    public void postReport(String email, String description) throws ProtocolException, IOException {
        URL usersUrl = model.getBugReportUrl();
        String urlParameters = "email=" + email + "&description=" + description;
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
        System.out.println("Report bug post result : " + responseContent.toString());

    } 

    public void exitButton(BugReportView view) {
        view.dispose();
    }
    
    public void sendReport(BugReportView view) {
        String email = view.getTxtEmail().getText();
        String description = view.getTxtBugDesc().getText();
        
        if (description.equals("")) {
            JOptionPane.showMessageDialog(view, "Deskripsikan bug aplikasi secara rinci.");
            view.getTxtBugDesc().requestFocus();
        } else {
            try {
                postReport(email, description);
                JOptionPane.showMessageDialog(view, "Pesan telah terkirim!");
                view.dispose();
            } catch (IOException ex) {
                Logger.getLogger(BugReportController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
