package by.test.guz.dicontainer.primitive.samples;

import by.test.guz.dicontainer.primitive.annotations.Bean;
import by.test.guz.dicontainer.primitive.annotations.Inject;

@Bean
public class NoBindingForConstructorArgumentSample implements Sample {

    private SecondarySample sample;

    @Inject
    public NoBindingForConstructorArgumentSample(SecondarySample sample) {
        this.sample = sample;
    }
}
