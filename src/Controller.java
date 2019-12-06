/**
 * The controller for the application'
 * @author ens19amn - Ask Norheim Morken
 */
public class Controller {

    private MyView myView = new MyView();

    public Controller() {
        myView.newRunButtonListener(actionEvent -> run());
        myView.newClearButtonListener(actionEvent -> clear());
    }

    /**
     * Starts the swing worker
     */
    private void run() {
        myView.clearTextArea();
        MyWorker myWorker = new MyWorker(myView);
        myWorker.run();
    }

    /**
     * Clears the text area
     */
    private void clear() {
        myView.clearTextArea();
    }

}
