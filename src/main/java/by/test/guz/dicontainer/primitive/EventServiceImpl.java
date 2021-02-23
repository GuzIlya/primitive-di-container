package by.test.guz.dicontainer.primitive;

public class EventServiceImpl implements EventService {
    private EventDao eventDao;

    @Inject
    public EventServiceImpl(EventDao eventDao) {
        this.eventDao = eventDao;
    }
}
