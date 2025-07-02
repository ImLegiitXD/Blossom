package nevalackin.homoBus;

@FunctionalInterface
public interface Listener<Event> {
  void call(Event paramEvent);
}
