package by.test.guz.dicontainer.primitive;

public class ProviderImpl<T> implements Provider<T> {

    public ProviderImpl(T instance) {
        this.instance = instance;
    }

    private final T instance;

    @Override
    public T getInstance() {
        return instance;
    }
}
