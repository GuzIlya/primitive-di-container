package by.test.guz.dicontainer.primitive;

public class Main {
    public static void main(String[] args) {
        Injector injector = Application.run("by.test.guz.dicontainer.primitive");
        EventServiceImpl eventService = injector.getProvider(EventServiceImpl.class).getInstance();
    }
}
