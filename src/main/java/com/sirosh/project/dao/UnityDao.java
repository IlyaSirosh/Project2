package com.sirosh.project.dao;

import com.sirosh.project.entity.Unity;

import java.util.List;

/**
 * Created by Illya on 4/10/17.
 */
public interface UnityDao {



    void addUnity(Unity unity);
    void saveUnity(Unity unity);
    void deleteUnity(Unity unity);

    List<String> getUnityNames();


    Unity getUnityById(Integer id);
    Unity getUnityByName(String name);

    Boolean isUnityNameExists(String name);
}
