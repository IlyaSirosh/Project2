package com.sirosh.project.config;



import com.sirosh.project.utils.JsonViewResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Illya on 4/10/17.
 */

@Configuration
@ComponentScan(basePackages = "com.sirosh.project")
@PropertySource("classpath:db.properties")
@EnableWebMvc
public class WebAppConfig  extends WebMvcConfigurerAdapter{

    @Autowired
    Environment env;


    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(env.getProperty("db.driver"));
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setUsername(env.getProperty("db.username"));
        dataSource.setPassword(env.getProperty("db.password"));
//        dataSource.setSchema(env.getProperty("db.schema"));

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(){
        return new NamedParameterJdbcTemplate(dataSource());
    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/*.html").addResourceLocations("/main/web/");
//    }
//
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/in‌​dex.html");
    }




//    @Bean
//    public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
//        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
//
//        resolver.setContentNegotiationManager(manager);
//
//        List<ViewResolver> resolvers = new ArrayList<ViewResolver>();
//
//        resolvers.add(jsonViewResolver());
////        resolvers.add(jspViewResolver());
//
//        resolver.setViewResolvers(resolvers);
//
//        return resolver;
//    }

    @Bean
    public ViewResolver jsonViewResolver() {
        return new JsonViewResolver();
    }

//    @Bean
//    public ViewResolver jspViewResolver() {
//        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//        viewResolver.setViewClass(JstlView.class);
//        viewResolver.setPrefix("/presentation/views/");
//        viewResolver.setSuffix(".jsp");
//        return viewResolver;
//    }


    @Bean
    public Md5PasswordEncoder passwordEncoder(){
        return new Md5PasswordEncoder();
    }
}
