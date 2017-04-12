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

    public Unity add(Unity unity) {
        if(isNameExists(unity.getName()))
            throw new IllegalArgumentException();

        return unityDao.addUnity(unity);
    }

    public void save(Unity unity) {

    }

    public void delete(Unity unity) {

    }

    public List<String> getNames() {
        return null;
    }

    public Unity getByName(String name) {
        return null;
    }

    public Boolean isNameExists(String name) {
        return null;
    }
}
