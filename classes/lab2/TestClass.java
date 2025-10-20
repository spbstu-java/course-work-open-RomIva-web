package classes.lab2;

public class TestClass {
    public void publicMethod1(String text) {
        System.out.println("Публичный метод 1: " + text);
    }
    public int publicMethod2(int a, int b) {
        int result = a + b;
        System.out.println("Публичный метод 2: " + a + " + " + b + " = " + result);
        return result;
    }
    
    @Repeat(3)
    protected void protectedMethod1(String name, int age) {
        System.out.println("Защищенный метод 1: " + name + ", возраст " + age);
    }
    protected String protectedMethod2() {
        System.out.println("Защищенный метод 2 без параметров (без аннотации)");
        return "protected";
    }
    @Repeat(2)
    protected void protectedMethod3(double number, boolean flag) {
        System.out.println("Защищенный метод 3: число=" + number + ", флаг=" + flag);
    }
    
    @Repeat(4)
    private void privateMethod1(double number) {
        System.out.println("Приватный метод 1: число " + number);
    }
    private boolean privateMethod2(String text, int count, boolean flag) {
        System.out.println("Приватный метод 2: текст='" + text + "', count=" + count + ", flag=" + flag);
        return !flag;
    }
    @Repeat(1)
    private int privateMethod3(int x, int y) {
        int result = x * y;
        System.out.println("Приватный метод 3: " + x + " * " + y + " = " + result);
        return result;
    }
    private void privateMethod4() {
        System.out.println("Приватный метод 4 без параметров (без аннотации)");
    }
}