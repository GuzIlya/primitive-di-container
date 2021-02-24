package by.test.guz.dicontainer.primitive;

import by.test.guz.dicontainer.primitive.samples.EventService;

public class Main {
    public static void main(String[] args) {
        Injector injector = Application.run("by.test.guz.dicontainer.primitive.sample");
        EventService eventService = injector.getProvider(EventService.class).getInstance();
    }
}
