package co.gararetech.cinemas.controller;

import co.gararetech.cinemas.model.SplashScreenModel;
import co.gararetech.cinemas.view.LoginView;
import co.gararetech.cinemas.view.SplashScreenView;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UnsupportedLookAndFeelException;

public class SplashScreenController {
    private SplashScreenModel model;

    public SplashScreenModel getModel() {
        return model;
    }

    public void setModel(SplashScreenModel model) {
        this.model = model;
    }
    
    public void viewLogin() {
        try {
            // TODO add your handling code here:.
            Thread.sleep(2000);
            model.getSplashView().dispose();
            new LoginView().setVisible(true);
            System.out.println("Opened windows");
        } catch (InterruptedException ex) {
            Logger.getLogger(SplashScreenView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SplashScreenView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(SplashScreenView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(SplashScreenView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(SplashScreenView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
