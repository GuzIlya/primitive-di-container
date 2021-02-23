package by.test.guz.dicontainer.primitive;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ObjectFactory {

    private final Injector injector;

    public ObjectFactory(Injector injector) {
        this.injector = injector;
    }

    public <T> T createObject(Class<T> implClass) {
        Constructor<?> target = null;

        Constructor<?>[] declaredConstructors = implClass.getDeclaredConstructors();

        for (Constructor<?> constructor : declaredConstructors) {
            if (constructor.isAnnotationPresent(Inject.class))
                if (target == null) {
                    target = constructor;
                } else throw new TooManyConstructorsException("Too many constructors with Inject annotation found.");
        }

        if (target == null) {
            try {
                target = implClass.getConstructor();
            } catch (NoSuchMethodException e) {
                throw new ConstructorNotFoundException("Constructor for " + implClass + " not found.");
            }
        }

        T t = null;

        List<Object> parameterObjects = new ArrayList<>();
        for (Class<?> parameterType : target.getParameterTypes()) {
            parameterObjects.add(injector.getProvider(parameterType).getInstance());
        }

        try {
            if (parameterObjects.isEmpty())
                t = (T) target.newInstance();
            else
                t = (T) target.newInstance(parameterObjects.toArray());

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return t;
    }
}
