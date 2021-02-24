package by.test.guz.dicontainer.primitive.samples;

import by.test.guz.dicontainer.primitive.annotations.Bean;
import by.test.guz.dicontainer.primitive.annotations.Inject;

@Bean(scope = "Singleton")
public class EventServiceImpl implements EventService {
    private EventDao eventDao;


//    public EventServiceImpl() {}

    @Inject
    public EventServiceImpl(EventDao eventDao) {
        this.eventDao = eventDao;
    }
}
