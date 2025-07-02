package moshi.blossom.event;

import moshi.blossom.Blossom;

public class Event

{
    public boolean canceled = false;

    public Event call() {
        Blossom.INSTANCE.getEventBus().post(this);

        return this;

    }

    public void cancelEvent() {
        setCanceled(true);

    }

    public boolean isCanceled() {
        return this.canceled;

    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;

    }

}
