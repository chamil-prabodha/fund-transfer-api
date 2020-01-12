package com.personal.dao;

import java.util.HashMap;
import java.util.Map;

public class MapDataSource<R, T> {
    private Map<R, T> source;

    public MapDataSource() {
        source = new HashMap<>();
    }

    public Map<R, T> getSource() {
        return source;
    }
}
