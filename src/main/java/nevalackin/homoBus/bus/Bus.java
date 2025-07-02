package nevalackin.homoBus.bus;

public interface Bus<Event> {
  void subscribe(Object paramObject);
  
  void unsubscribe(Object paramObject);
  
  void post(Event paramEvent);
}

