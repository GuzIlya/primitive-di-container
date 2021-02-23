package by.test.guz.dicontainer.primitive;

public class BeanInfo {
    private Class beanType;
    private boolean isSingleton;

    public BeanInfo(Class beanType, boolean isSingleton) {
        this.beanType = beanType;
        this.isSingleton = isSingleton;
    }

    public boolean isSingleton() {
        return isSingleton;
    }

    public Class getBeanType() {
        return beanType;
    }
}
