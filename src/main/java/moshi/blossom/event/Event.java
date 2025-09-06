package moshi.blossom.event;

import lombok.Getter;
import lombok.Setter;
import moshi.blossom.Blossom;

@Setter
@Getter
public class Event {
    public boolean canceled = false;

    public Event call() {
        Blossom.INSTANCE.getEventBus().post(this);
        return this;
    }

    public void cancelEvent() {
        setCanceled(true);
    }
}
