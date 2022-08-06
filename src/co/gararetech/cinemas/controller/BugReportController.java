
package co.gararetech.cinemas.controller;

import co.gararetech.cinemas.view.BugReportView;
import co.gararetech.cinemas.view.DashboardView;
import co.gararetech.cinemas.view.LoginView;
import javax.swing.JOptionPane;


public class BugReportController {
    
     public void exitButton(BugReportView view) {
        view.dispose();
        
    }
     public void minimizeButton(BugReportView view) {
        view.setState(BugReportView.ICONIFIED);
    }
    
}
