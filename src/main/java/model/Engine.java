package model;

public class Engine {
    ControlInterface UI;
    private int userId;

    public Engine(ControlInterface UI) {
        this.UI = UI;
    }

    public void start() {
        userId = UI.handleLogin();

        UI.handleGetData(userId);

        UI.displayOptions();
    }
}

// test email "Katti.Matikainen@katti.org"
