package com.sirosh.project.utils;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;


import java.util.Locale;


/**
 * Created by Illya on 4/12/17.
 */
public class JsonViewResolver implements ViewResolver{


    public View resolveViewName(String viewName, Locale locale) throws Exception {
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        view.setPrettyPrint(true);
        return view;
    }

}