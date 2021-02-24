package by.test.guz.dicontainer.primitive;

import by.test.guz.dicontainer.primitive.exceptions.ConstructorNotFoundException;
import by.test.guz.dicontainer.primitive.exceptions.TooManyConstructorsException;
import by.test.guz.dicontainer.primitive.samples.ManyInjectsSample;
import by.test.guz.dicontainer.primitive.samples.NoInjectNoDefaultConstructorSample;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ObjectFactoryTest {

    private Injector injector;
    private ObjectFactory factory;

    @BeforeEach
    public void init() {
        injector = new InjectorImpl();
        factory = new ObjectFactory(injector);
        injector.setFactory(factory);
    }

    @Test
    void testCreateObject_NoInjectNoDefaultConstructor_ShouldThrowConstructorNotFoundException() {
        assertThrows(ConstructorNotFoundException.class, () -> factory.createObject(NoInjectNoDefaultConstructorSample.class));
    }

    @Test
    void testCreateObject_ManyInjects_ShouldThrowTooManyConstructorsException() {
        assertThrows(TooManyConstructorsException.class, () -> factory.createObject(ManyInjectsSample.class));
    }
}