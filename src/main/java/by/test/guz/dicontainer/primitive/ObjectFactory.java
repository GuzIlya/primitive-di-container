package by.test.guz.dicontainer.primitive;

import by.test.guz.dicontainer.primitive.annotations.Inject;
import by.test.guz.dicontainer.primitive.exceptions.ConstructorNotFoundException;
import by.test.guz.dicontainer.primitive.exceptions.TooManyConstructorsException;

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
        Constructor<?>[] cons = implClass.getConstructors();

        for (Constructor<?> constructor : cons) {
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



        List<Object> parameterObjects = new ArrayList<>();
        for (Class<?> parameterType : target.getParameterTypes()) {
            parameterObjects.add(injector.getProvider(parameterType).getInstance());
        }

        T t = null;

        try {
            t = (T) target.newInstance(parameterObjects.toArray());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return t;
    }
}
