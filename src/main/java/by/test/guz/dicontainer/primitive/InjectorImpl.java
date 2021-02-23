package by.test.guz.dicontainer.primitive;

import by.test.guz.dicontainer.primitive.exceptions.BindingNotFoundException;
import by.test.guz.dicontainer.primitive.exceptions.MoreThanOneImplException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Bean
public class InjectorImpl implements Injector {

    private ObjectFactory factory;
    private Map<Class<?>, BeanInfo> beansBinded = new HashMap<>();
    private Map<Class<?>, Provider> cache = new ConcurrentHashMap<>();

    @Override
    public <T> Provider<T> getProvider(Class<T> type) {
        if (cache.containsKey(type)) {
            return cache.get(type);
        }

        Class<? extends T> implClass = type;

        if (type.isInterface()) {
            if (beansBinded.containsKey(type)) {
                implClass = beansBinded.get(type).getBeanType();
            } else throw new BindingNotFoundException("Bean for interface " + type + " not found.");
        }

        T t = factory.createObject(implClass);

        Provider<T> provider = new ProviderImpl(t);

        if (beansBinded.containsKey(type) && beansBinded.get(type).isSingleton()) {
            cache.put(type, provider);
        }

        return provider;

    }

    @Override
    public <T> void bind(Class<T> intf, Class<? extends T> impl) {
        bind(intf, impl, false);
    }

    @Override
    public <T> void bindSingleton(Class<T> intf, Class<? extends T> impl) {
        bind(intf, impl, true);
    }

    @Override
    public void setFactory(ObjectFactory factory) {
        this.factory = factory;
    }

    private <T> void bind(Class<T> intf, Class<? extends T> impl, boolean isSingleton) {
        if (beansBinded.containsKey(intf)) {
            throw new MoreThanOneImplException(intf + "has more than one impl, this feature is not supported yet");
        }

        beansBinded.put(intf, new BeanInfo(impl, isSingleton));
    }
}
