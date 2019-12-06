import se.umu.cs.unittest.TestClass;

public class MyTest implements TestClass {

    String string;

    public void setUp() {
        string = "Test";
    }

    public void tearDown() {
        string = null;
    }

    public boolean testStringLength() {
        return string.length() == 4;
    }

    public boolean testTrue() {
        return true;
    }

    public boolean testFalse() {
        return false;
    }

}
