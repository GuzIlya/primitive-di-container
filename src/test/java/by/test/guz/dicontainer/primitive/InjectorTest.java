package by.test.guz.dicontainer.primitive;

import by.test.guz.dicontainer.primitive.exceptions.BindingNotFoundException;
import by.test.guz.dicontainer.primitive.exceptions.MoreThanOneImplException;
import by.test.guz.dicontainer.primitive.samples.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InjectorTest {

    private Injector injector;
    private ObjectFactory factory;

    @BeforeEach
    public void init() {
        injector = new InjectorImpl();
        factory = new ObjectFactory(injector);
        injector.setFactory(factory);
    }

    @Test
    void testGetProvider_ValidBindingBefore_ShouldPass() {
        injector.bind(Sample.class, SingletonSample.class);
        Provider<Sample> provider = injector.getProvider(Sample.class);
        assertNotNull(provider);
        assertNotNull(provider.getInstance());
        assertSame(SingletonSample.class, provider.getInstance().getClass());
    }

    @Test
    void testGetProvider_NoBindingBefore_ShouldThrowBindingNotFoundException() {
        assertThrows(BindingNotFoundException.class, () -> injector.getProvider(Sample.class));
    }

    @Test
    void testGetProvider_NoBindingForConstructorArguments_ShouldThrowBindingNotFoundException() {
        injector.bind(Sample.class, NoBindingForConstructorArgumentSample.class);
        assertThrows(BindingNotFoundException.class, () -> injector.getProvider(Sample.class));
    }

    @Test
    void testGetProvider_ValidBindingForConstructorArguments_ShouldPass() {
        injector.bind(Sample.class, NoBindingForConstructorArgumentSample.class);
        injector.bind(SecondarySample.class, SecondarySampleImpl.class);
        Provider<Sample> provider = injector.getProvider(Sample.class);
        assertNotNull(provider);
        assertNotNull(provider.getInstance());
        assertSame(NoBindingForConstructorArgumentSample.class, provider.getInstance().getClass());
    }

    @Test
    void testGetProvider_NoInject_ShouldPass() {
        injector.bind(Sample.class, NoInjectSample.class);
        Provider<Sample> provider = injector.getProvider(Sample.class);
        assertNotNull(provider);
        assertNotNull(provider.getInstance());
        assertSame(NoInjectSample.class, provider.getInstance().getClass());
    }

    @Test
    void testGetProvider_ForClassWithBinding_ShouldPass() {
        injector.bind(Sample.class, PrototypeSample.class);
        Provider<PrototypeSample> provider = injector.getProvider(PrototypeSample.class);
        assertNotNull(provider);
        assertNotNull(provider.getInstance());
        assertSame(PrototypeSample.class, provider.getInstance().getClass());
    }

    @Test
    void testGetProvider_ForClassWithoutBinding_ShouldReturnNull() {
        Provider<NotBeanAnnotatedSample> provider = injector.getProvider(NotBeanAnnotatedSample.class);
        assertNull(provider);
    }

    @Test
    void testBindSingleton_AfterCompareReturnOfGetProviderCallsForEquality_ShouldPass() {
        injector.bindSingleton(Sample.class, SingletonSample.class);
        Provider<Sample> provider1 = injector.getProvider(Sample.class);
        Provider<Sample> provider2 = injector.getProvider(Sample.class);
        assertEquals(provider1, provider2);
    }

    @Test
    void testBind_AfterCompareReturnOfGetProviderCallsForInequality_ShouldPass() {
        injector.bind(Sample.class, PrototypeSample.class);
        Provider<Sample> provider1 = injector.getProvider(Sample.class);
        Provider<Sample> provider2 = injector.getProvider(Sample.class);
        assertNotEquals(provider1, provider2);
    }

    @Test
    void testBind_BindTwoImplForOneInterface_MoreThanOneImplException() {
        injector.bind(Sample.class, PrototypeSample.class);
        assertThrows(MoreThanOneImplException.class, () -> injector.bind(Sample.class, SingletonSample.class));
    }
}