package org.example;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        Driver.setUp();
        Driver.loginButtonClick();
        Driver.singIn();
        Driver.createAudioProject();
        Driver.projectSavingCheck();
        Driver.getProjectName();
        Driver.projectsPageNavigation();
        Driver.checkCreatedProject();
        Driver.browserTurnDown();
    }
}
