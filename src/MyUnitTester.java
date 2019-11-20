import unittest.TestClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;

public class MyUnitTester implements ActionListener {

    private JTextField testTextField;
    private JTextArea textArea;

    public MyUnitTester() {
        //The UI element are setup here in the constructor
        JFrame frame = new JFrame("MyUnitTester");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 768);

        JPanel northPanel = new JPanel();
        testTextField = new JTextField(50);
        JButton runButton = new JButton("Run tests");
        runButton.addActionListener(this);
        northPanel.add(testTextField);
        northPanel.add(runButton);

        JPanel southPanel = new JPanel();
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(this);
        southPanel.add(clearButton);

        // Text Area at the Center
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);

        frame.getContentPane().add(BorderLayout.NORTH, northPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, southPanel);
        frame.getContentPane().add(BorderLayout.CENTER, scrollPane);
        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //This method is called when a button is clicked
        //Tests to determine which button was clicked
        String action = actionEvent.getActionCommand();
        if (action.equals("Run tests")) {
            textArea.setText("");
            runTests();
        }
        else if (action.equals("Clear")) {
            textArea.setText("");
        }
    }

    private void runTests() {

        SwingWorker sw1 = new SwingWorker()
        {

            Integer successes = 0;
            Integer fails = 0;
            Integer exceptions = 0;

            @Override
            protected String doInBackground() throws Exception
            {
                //What is done when the SwingWorker is executed
                Class class;
                Object obj;
                try {
                    class = Class.forName(testTextField.getText());
                    if (TestClass.class.isAssignableFrom(class)) {
                        obj = class.getDeclaredConstructor().newInstance();
                    }
                    else {
                        textArea.append("Class does not implement TestClass \n");
                        return "Fail";
                    }
                } catch (ClassNotFoundException e) {
                    textArea.append("Class not found \n");
                    return "Fail";
                }

                Method setup = class.getDeclaredMethod("setUp");
                Method teardown = class.getDeclaredMethod("tearDown");
                Method[] methods = class.getMethods();
                for (Method method : methods) {
                    if (method.getName().startsWith("test") && method.getParameterCount() == 0 && (method.getReturnType() == boolean.class)) {
                        textArea.append(method.getName() + ": ");
                        try {
                            setup.invoke(obj);
                            boolean ret = (boolean) method.invoke(obj);
                            teardown.invoke(obj);
                            if (ret) {
                                textArea.append("SUCCESS \n");
                                successes++;
                            }
                            else {
                                textArea.append("FAIL \n");
                                fails++;
                            }
                        } catch (Exception e) {
                            //Catches exception if one is thrown by the invoked test method
                            textArea.append("FAIL Generated a " + e.getCause().toString() + "\n");
                            exceptions++;
                        }
                    }
                }
                return "";
            }

            @Override
            protected void done()
            {
                //What is done when the SwingWorker is done
                textArea.append("\n");
                textArea.append(String.valueOf(successes) + " tests succeeded \n");
                textArea.append(String.valueOf(fails) + " tests failed \n");
                textArea.append(String.valueOf(exceptions) + " tests failed because of an exception \n");
            }

        };

        //Executes the SwingWorker defined above
        sw1.execute();
    }

    public static void main(String args[]){
        new MyUnitTester();
    }

}
