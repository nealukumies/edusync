package controller;

public abstract class SubController {
    private MainController mainController;
    public void setMainViewController(final MainController mainController) {
        this.mainController = mainController;
    }
    public abstract void initializeFully();
    public MainController getMainController() { return this.mainController; }
}
