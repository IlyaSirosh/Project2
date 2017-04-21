package com.sirosh.project.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Created by Illya on 4/12/17.
 */
public class AppInitializer implements WebApplicationInitializer{


    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(WebAppConfig.class);
        servletContext.addListener(new ContextLoaderListener(context));
        context.setServletContext(servletContext);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);


        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        System.out.println("Init app");

        //servletContext.addFilter("springSecurityFilterChain", DelegatingFilterProxy.class);


    }
}
