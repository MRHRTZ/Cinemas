package co.gararetech.cinemas.controller;

import co.gararetech.cinemas.model.DashboardModel;
import co.gararetech.cinemas.view.DashboardView;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JTextArea;
import org.json.JSONArray;
import org.json.JSONObject;

public class DetailTheaterController {
        private DashboardModel model;

    public DashboardModel getModel() {
        return model;
    }

    public void setModel(DashboardModel model) {
        this.model = model;
    }
    
    public void showDetail(DashboardView view, String theaterId) {
        System.out.println("Detail Theater ID : " + theaterId);
        JSONObject movieData;
        
        JSONArray theaterList = model.getCinemaList();
        for (int i = 0; i < theaterList.length(); i++) {
            JSONObject rowData = (JSONObject) theaterList.get(i);
            if (rowData.getString("id").equals(theaterId)) {
                final JDialog frame = new JDialog(view, rowData.getString("name"), true);
                
                JTextArea sinopsis = new JTextArea();
                sinopsis.setPreferredSize(new Dimension(400, 150));
                sinopsis.setText(rowData.toString());
                sinopsis.setWrapStyleWord(true);
                sinopsis.setLineWrap(true);
                
                frame.getContentPane().add(sinopsis);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        }
    }
}
