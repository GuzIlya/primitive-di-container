package by.test.guz.dicontainer.primitive;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Bean {
    String scope() default "Singleton";
}
