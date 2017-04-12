package com.sirosh.project.service;

import com.sirosh.project.entity.Unity;

import java.util.List;

/**
 * Created by Illya on 4/12/17.
 */
public interface UnityService {

    Unity add(Unity unity);
    void save(Unity unity);
    void delete(Unity unity);

    List<String> getNames();
    Unity getByName(String name);

    Boolean isNameExists(String name);

}
