package com.personal.dao;

import java.util.Hashtable;
import java.util.Map;

public class MapDataSource<R, T> {
    private Map<R, T> source;

    public MapDataSource() {
        source = new Hashtable<>();
    }

    public Map<R, T> getSource() {
        return source;
    }
}
