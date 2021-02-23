package by.test.guz.dicontainer.primitive;

import java.io.IOException;
import java.net.URISyntaxException;

public class Application {
    public static Injector run(String basePackage) {
        Injector injector = new InjectorImpl();

        try {
            BeanScanner.scan(basePackage, injector);
        } catch (IOException | URISyntaxException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        ObjectFactory factory = new ObjectFactory(injector);

        injector.setFactory(factory);

        return injector;
    }
}
