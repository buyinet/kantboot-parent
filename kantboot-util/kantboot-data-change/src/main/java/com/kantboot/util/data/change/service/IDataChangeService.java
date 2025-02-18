package com.kantboot.util.data.change.service;

public interface IDataChangeService {

    void dataChange(String key);

    void dataChanges(String[] keys);

    String getUuidByKey(String key);

}
