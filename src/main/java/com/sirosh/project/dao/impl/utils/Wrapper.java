package com.sirosh.project.dao.impl.utils;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Illya on 4/10/17.
 */
public class Wrapper {



    private Object key;
    private Object value;

    public <T> T getKey() {
        return (T)key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public <T> T getValue() {
        return (T)value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public  RowMapper<Wrapper> mapper = new RowMapper<Wrapper>() {
        public Wrapper mapRow(ResultSet rs, int i) throws SQLException {
            Wrapper wrapper = new Wrapper();
            wrapper.setKey(rs.getObject(1));
            wrapper.setValue(rs.getObject(2));

            return wrapper;
        }
    };

//    public static String getSequenceStringFromList(List<String> list){
//        StringBuilder sb = new StringBuilder();
//
//        for(String x: list){
//            sb.append("'");
//            sb.append(x);
//            sb.append("',");
//        }
//
//        sb.deleteCharAt(sb.length()-1);
//        sb.deleteCharAt(sb.length()-1);
//        sb.deleteCharAt(0);
//
//        return sb.toString();
//    }


    public static <T> String getSequenceStringFromList(List<T> list){
        StringBuilder sb = new StringBuilder();

        for(T x: list){
            sb.append("'");
            sb.append(x.toString());
            sb.append("',");
        }

        sb.deleteCharAt(sb.length()-1);
        sb.deleteCharAt(sb.length()-1);
        sb.deleteCharAt(0);

        return sb.toString();
    }
}
