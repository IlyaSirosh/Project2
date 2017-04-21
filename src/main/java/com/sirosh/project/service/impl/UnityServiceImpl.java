package com.sirosh.project.service.impl;

import com.sirosh.project.dao.UnityDao;
import com.sirosh.project.entity.Unity;
import com.sirosh.project.service.UnityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Illya on 4/12/17.
 */
@Service
public class UnityServiceImpl implements UnityService {

    @Autowired
    private UnityDao unityDao;

    public void add(Unity unity) {
//        if(isNameExists(unity.getName()))
//            throw new IllegalArgumentException();

        unityDao.addUnity(unity);
    }

    public void save(Unity unity) {
        unityDao.saveUnity(unity);
    }

    public void delete(Unity unity) {
        unityDao.deleteUnity(unity);
    }

    public List<String> getNames() {
        return unityDao.getUnityNames();
    }

    public Unity getByName(String name) {
        return unityDao.getUnityByName(name);
    }

    public Boolean isNameExists(String name) {
        return unityDao.isUnityNameExists(name);
    }
}
