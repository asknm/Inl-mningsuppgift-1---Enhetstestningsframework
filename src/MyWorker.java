import javax.swing.*;
import java.util.List;

/**
 * SwingWorker that finds and executed the required methods in a TestClass
 * @author ens19amn - Ask Norheim Morken
 */

public class MyWorker extends SwingWorker<Object, String> {

    private MyView myView;
    private Model model;



    public MyWorker(MyView myView) {
        this.myView = myView;
        model = new Model(this::publish, myView);
    }

    /**
     * What is done when the swing worker is executed
     * Attempts to get the class and method and executes methods if successful
     * @return null
     */
    @Override
    protected Object doInBackground()
    {
        model.runTests();
        return null;
    }

    /**
     * Receives strings from the worker thread to append to the text area in the view
     * @param chunks List of strings to append
     */
    @Override
    protected void process(List<String> chunks) {
        //super.process(chunks);
        for (String string : chunks) {
            myView.appendToTextArea(string);
        }
    }

    /**
     * What is done after the doInBackground has completed
     * Appends a summary of the results to the text area in the view
     */
    @Override
    protected void done()
    {
        //What is done when the SwingWorker is done
        myView.appendToTextArea("\n");
        myView.appendToTextArea(model.getSuccesses() + " tests succeeded \n");
        myView.appendToTextArea(model.getFails() + " tests failed \n");
        myView.appendToTextArea(model.getExceptions() + " tests failed because of an exception \n");
        myView.appendToTextArea("\n");
    }

}
