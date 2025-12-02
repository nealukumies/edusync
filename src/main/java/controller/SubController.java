package controller;

/**
 * Abstract base class for sub-controllers in the application.
 */
public abstract class SubController {
    /** The main controller of the application. */
    private MainController mainController;

    /** Set the main controller for this sub-controller.
     * @param mainController The main controller to set.
     */
    public void setMainViewController(final MainController mainController) {
        this.mainController = mainController;
    }
    /** Initialize the controller fully after setting the main controller. */
    public abstract void initializeFully();
    public MainController getMainController() { return this.mainController; }
}
