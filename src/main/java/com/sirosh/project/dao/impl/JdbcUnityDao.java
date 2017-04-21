package com.sirosh.project.dao.impl;

import com.sirosh.project.dao.UnityDao;
import com.sirosh.project.entity.Unity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Illya on 4/10/17.
 */
@Repository
public class JdbcUnityDao implements UnityDao {

    private static final String INSERT_UNITY = "INSERT INTO unities (name) VALUES (?)";
    private static final String UPDATE_UNITY = "UPDATE unities SET name=? WHERE id=?";
    private static final String DELETE_UNITY = "DELETE FROM unities WHERE id=? OR name=?";
    private static final String SELECT_UNITY_BY_ID = "SELECT * FROM unities WHERE id=?";
    private static final String SELECT_ALL_UNITY_NAMES = "SELECT name FROM unities";
    private static final String SELECT_UNITY_BY_NAME = "SELECT * FROM unities WHERE name=?";
    private static final String CHECK_IF_UNITY_NAME_EXISTS = "SELECT EXISTS (SELECT * FROM unities WHERE name=?)";


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addUnity(Unity unity) {
        jdbcTemplate.update(INSERT_UNITY,unity.getName());
    }

    public void saveUnity(Unity unity) {
        jdbcTemplate.update(UPDATE_UNITY,unity.getName(),unity.getId());
    }

    public void deleteUnity(Unity unity) {
        jdbcTemplate.update(DELETE_UNITY,unity.getId(),unity.getName());
    }

    public List<String> getUnityNames() {
        return jdbcTemplate.queryForList(SELECT_ALL_UNITY_NAMES,String.class);
    }

    public Unity getUnityById(Integer id) {
        return jdbcTemplate.queryForObject(SELECT_UNITY_BY_ID,mapper,id);
    }

    public Unity getUnityByName(String name) {
        return jdbcTemplate.queryForObject(SELECT_UNITY_BY_NAME,mapper,name);
    }

    public Boolean isUnityNameExists(String name) {
        return jdbcTemplate.queryForObject(CHECK_IF_UNITY_NAME_EXISTS,Boolean.class);
    }


    private RowMapper<Unity> mapper = new RowMapper<Unity>() {
        public Unity mapRow(ResultSet rs, int i) throws SQLException {
            Unity unity = new Unity();
            unity.setId(rs.getInt("id"));
            unity.setName(rs.getString("name"));
            return unity;
        }
    };
}
