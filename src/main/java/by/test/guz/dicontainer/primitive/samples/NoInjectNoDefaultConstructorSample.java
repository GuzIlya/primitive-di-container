package by.test.guz.dicontainer.primitive.samples;

import by.test.guz.dicontainer.primitive.annotations.Bean;

@Bean
public class NoInjectNoDefaultConstructorSample implements Sample {
    public NoInjectNoDefaultConstructorSample(String s) {
    }
}
