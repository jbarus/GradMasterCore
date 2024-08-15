package com.github.jbarus.structures;

import java.util.HashMap;

public class CorrelationMap<T> {
    private HashMap<T, T> map = new HashMap<>();

    public void addRelation(T obj1, T obj2) {
        map.put(obj1, obj2);
        map.put(obj2, obj1);
    }

    public void removeRelation(T obj1, T obj2) {
        map.remove(obj1);
        map.remove(obj2);
    }

    public boolean containsRelation(T obj1, T obj2) {
        return map.get(obj1).equals(obj2);
    }

    public T getRelation(T obj) {
        return map.get(obj);
    }
}
