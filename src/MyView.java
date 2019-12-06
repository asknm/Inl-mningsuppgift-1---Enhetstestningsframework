import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Holds the Swing components shown to the user
 * @author ens19amn - Ask Norheim Morken
 */
public class MyView {

    private static MyView myView;
    private JTextField testTextField;
    private JTextArea textArea;
    private JButton runButton;
    private JButton clearButton;

    public MyView() {
        //The UI element are setup here in the constructor
        JFrame frame = new JFrame("MyUnitTester");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 768);

        JPanel northPanel = new JPanel();
        testTextField = new JTextField(50);
        runButton = new JButton("Run tests");
        northPanel.add(testTextField);
        northPanel.add(runButton);

        JPanel southPanel = new JPanel();
        clearButton = new JButton("Clear");
        southPanel.add(clearButton);

        // Text Area at the Center
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);

        frame.getContentPane().add(BorderLayout.NORTH, northPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, southPanel);
        frame.getContentPane().add(BorderLayout.CENTER, scrollPane);
        frame.setVisible(true);

    }

    /**
     * Adds a listener to runButton
     * @param listener the listener
     */
    public void newRunButtonListener(ActionListener listener) {
        runButton.addActionListener(listener);
    }

    /**
     * Adds a listener to clearButton
     * @param listener the listener
     */
    public void newClearButtonListener(ActionListener listener) {
        clearButton.addActionListener(listener);
    }

    /**
     * Clears the text area
     */
    public void clearTextArea() {
        textArea.setText("");
    }

    /**
     * returns the TestClass entered into the testTextField
     * @return the name of the TestClass entered
     */
    public String getTestText() {
        return testTextField.getText();
    }

    /**
     * Appends text to the text area
     * @param string the string to append
     */
    public void appendToTextArea(String string) {
        textArea.append(string);
    }
}
