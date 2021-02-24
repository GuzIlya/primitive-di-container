package by.test.guz.dicontainer.primitive;

public class Main {
    public static void main(String[] args) {
        Injector injector = Application.run("by.test.guz.dicontainer.primitive.sample");

        /*
            All Bean annotated classes are already binded.
            Use injector bind or bindSingleton method for binding more classes (Not recommended)
            Use injector getProvider method to get instances (All instances that desired instance depends of would be automatically instantiated).
         */
    }
}
