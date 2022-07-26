package co.gararetech.cinemas.model;

import co.gararetech.cinemas.view.LoginView;
import co.gararetech.cinemas.view.SplashScreenView;

public class SplashScreenModel {
    private LoginView loginPage;
    private SplashScreenView splashView;

    public LoginView getLoginPage() {
        return loginPage;
    }
    public void setLoginPage(LoginView loginPage) {
        this.loginPage = loginPage;
    }
    public SplashScreenView getSplashView() {
        return splashView;
    }
    public void setSplashView(SplashScreenView splashView) {
        this.splashView = splashView;
    }
}
