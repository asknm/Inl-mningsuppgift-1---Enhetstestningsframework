import se.umu.cs.unittest.TestClass;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Consumer;

public class Model {

    private Consumer<String> consumer;
    MyView myView;

    private Class c;
    private Object obj;

    private int successes = 0;
    private int fails = 0;
    private int exceptions = 0;

    public Model(Consumer<String> consumer, MyView myView) {
        this.consumer = consumer;
        this.myView = myView;
    }

    public void runTests() {
        getClassAndObject();
        callMethods();
    }


    /**
     * Gets the wanted class and object from the name of the class entered in the view
     */
    public void getClassAndObject() {
        // Method(Consumer)
        // new Model(this::publish)

        try {
            c = Class.forName(myView.getTestText());
            if (TestClass.class.isAssignableFrom(c)) {
                obj = c.getDeclaredConstructor().newInstance();
            }
            else {
                consumer.accept("Class does not implement TestClass \n");
            }
        } catch (ClassNotFoundException e) {
            consumer.accept("Class not found \n");
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            consumer.accept("Could not create instance of class \n");
        }
    }

    /**
     * Finds and calls the setup, teardown and test methods and executes each test method
     * with setup executed before and teardown executed after each one
     */
    public void callMethods() {
        Method setup = null;
        try {
            setup = c.getDeclaredMethod("setUp");
        } catch (NoSuchMethodException e) {
            consumer.accept("Could not find setup method \n");
            return;
        }
        Method teardown = null;
        try {
            teardown = c.getDeclaredMethod("tearDown");
        } catch (NoSuchMethodException e) {
            consumer.accept("Could not find teardown method \n");
            return;
        }
        Method[] methods = c.getMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("test") && method.getParameterCount() == 0 && (method.getReturnType() == boolean.class)) {
                consumer.accept(method.getName() + ": ");
                try {
                    setup.invoke(obj);
                    boolean ret = (boolean) method.invoke(obj);
                    teardown.invoke(obj);
                    if (ret) {
                        consumer.accept("SUCCESS \n");
                        successes++;
                    }
                    else {
                        consumer.accept("FAIL \n");
                        fails++;
                    }
                } catch (Exception e) {
                    //Catches exception if one is thrown by the invoked test method
                    consumer.accept("FAIL Generated a " + e.getCause().toString() + "\n");
                    exceptions++;
                }
            }
        }
    }

    public int getSuccesses() {
        return successes;
    }

    public int getFails() {
        return fails;
    }

    public int getExceptions() {
        return exceptions;
    }
}
