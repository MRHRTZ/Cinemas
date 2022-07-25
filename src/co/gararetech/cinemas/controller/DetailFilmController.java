package co.gararetech.cinemas.controller;

import co.gararetech.cinemas.model.DashboardModel;
import co.gararetech.cinemas.view.DashboardView;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import org.json.JSONArray;
import org.json.JSONObject;

public class DetailFilmController {
    private DashboardModel model;

    public DashboardModel getModel() {
        return model;
    }

    public void setModel(DashboardModel model) {
        this.model = model;
    }
    
    public void showDetail(DashboardView view, String movieId) {
        System.out.println("Detail Film ID : " + movieId);
        JSONObject movieData;
        
        JSONArray movieList = model.getPlayingList();
        for (int i = 0; i < movieList.length(); i++) {
            JSONObject rowData = (JSONObject) movieList.get(i);
            if (rowData.getString("id").equals(movieId)) {
                final JDialog frame = new JDialog(view, rowData.getString("title"), true);
                
                JTextArea sinopsis = new JTextArea();
                sinopsis.setPreferredSize(new Dimension(400, 150));
                sinopsis.setText(rowData.getString("synopsis"));
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
