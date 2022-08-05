package co.gararetech.cinemas.model;

import javax.swing.JToggleButton;

public class CheckoutTicketModel {
    private JToggleButton selectedTime;
    private String movieId;
    
    public CheckoutTicketModel() {
        
    }

    public JToggleButton getSelectedTime() {
        return selectedTime;
    }

    public void setSelectedTime(JToggleButton selectedTime) {
        this.selectedTime = selectedTime;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }
    
    
}
