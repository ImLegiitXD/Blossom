package nevalackin.homoBus.bus.impl;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;
import nevalackin.homoBus.bus.Bus;

public final class EventBus<Event> implements Bus<Event> {
    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();

    private final Map<Type, List<CallSite<Event>>> callSiteMap = new HashMap<>();
    private final Map<Type, List<Listener<Event>>> listenerCache = new HashMap<>();

    public void subscribe(Object subscriber) {
        for (Field field : subscriber.getClass().getDeclaredFields()) {
            EventLink annotation = field.getAnnotation(EventLink.class);
            if (annotation != null) {
                Type eventType = ((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0];

                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }

                try {
                    Listener<Event> listener = (Listener<Event>)LOOKUP.unreflectGetter(field).invokeWithArguments(subscriber);
                    byte priority = annotation.value();

                    CallSite<Event> callSite = new CallSite<>(subscriber, listener, priority);

                    if (this.callSiteMap.containsKey(eventType)) {
                        List<CallSite<Event>> callSites = this.callSiteMap.get(eventType);
                        callSites.add(callSite);
                        callSites.sort((o1, o2) -> o2.priority - o1.priority);
                    } else {
                        List<CallSite<Event>> callSites = new ArrayList<>(1);
                        callSites.add(callSite);
                        this.callSiteMap.put(eventType, callSites);
                    }
                } catch (Throwable throwable) {
                }
            }
        }

        populateListenerCache();
    }

    private void populateListenerCache() {
        Map<Type, List<CallSite<Event>>> callSiteMap = this.callSiteMap;
        Map<Type, List<Listener<Event>>> listenerCache = this.listenerCache;

        for (Type type : callSiteMap.keySet()) {
            List<CallSite<Event>> callSites = callSiteMap.get(type);
            int size = callSites.size();
            List<Listener<Event>> listeners = new ArrayList<>(size);

            for (int i = 0; i < size; i++) {
                listeners.add(callSites.get(i).listener);
            }
            listenerCache.put(type, listeners);
        }
    }

    public void unsubscribe(Object subscriber) {
        for (List<CallSite<Event>> callSites : this.callSiteMap.values()) {
            callSites.removeIf(eventCallSite -> eventCallSite.owner == subscriber);
        }

        populateListenerCache();
    }

    public void post(Event event) {
        List<Listener<Event>> listeners = this.listenerCache.getOrDefault(
                event.getClass(), Collections.emptyList());

        int i = 0;
        int listenersSize = listeners.size();

        while (i < listenersSize) {
            listeners.get(i++).call(event);
        }
    }

    private static class CallSite<Event> {
        private final Object owner;
        private final Listener<Event> listener;
        private final byte priority;

        public CallSite(Object owner, Listener<Event> listener, byte priority) {
            this.owner = owner;
            this.listener = listener;
            this.priority = priority;
        }
    }
}