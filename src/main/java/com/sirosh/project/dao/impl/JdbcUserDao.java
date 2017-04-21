package com.sirosh.project.dao.impl;

import com.sirosh.project.dao.UserDao;
import com.sirosh.project.dao.impl.utils.Wrapper;
import com.sirosh.project.entity.User;
import com.sirosh.project.pojo.Email;
import com.sirosh.project.pojo.Pageable;
import com.sirosh.project.pojo.Password;
import com.sirosh.project.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Illya on 4/10/17.
 */

@Repository
public class JdbcUserDao implements UserDao{

    private final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id=?";
    private final String SELECT_USER_BY_EMAIL = "SELECT * FROM users WHERE email=?";
    private final String SELECT_USER_BY_NICKNAME = "SELECT * FROM users WHERE nickname=?";
    private final String SELECT_ALL_USERS = "SELECT * FROM users";
    private final String SELECT_USERS_AMOUNT = "SELECT COUNT(*) FROM users";
    private final String INSERT_USER = "INSERT INTO users (nickname,email,password,role) VALUES (?,?,?,?)";
    private final String UPDATE_USER = "UPDATE users SET nickname=?,email=?,password=?,role=? WHERE id=?";
    private final String DELETE_USER = "DELETE FROM users WHERE id=?";
    private final String SELECT_USER_NICKNAMES_AND_DISH_AMOUNT_RATING = "SELECT nickname, (SELECT COUNT(name) " +
                                                                                          "FROM dishes" +
                                                                                          "WHERE u.id = author " +
                                                                                          "GROUP BY author " +
                                                                        ") AS amount " +
                                                                        "FROM users u " +
                                                                        "WHERE u.id IN (SELECT (DISTINCT author) " +
                                                                                       "FROM dishes" +
                                                                         ") " +
                                                                        "ORDER BY amount DESC " +
                                                                        "LIMIT ?,?";
    private final String CHECK_USER_EXISTS = "SELECT EXISTS (SELECT * FROM users WHERE nickname=? OR email=?)";
    private final String CHECK_NICKNAME_EXISTS = "SELECT EXISTS (SELECT * FROM users WHERE nickname=?)";
    private final String CHECK_EMAIL_EXISTS = "SELECT EXISTS (SELECT * FROM users WHERE email=?)";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User getUserById(Integer id) {
        return jdbcTemplate.queryForObject(SELECT_USER_BY_ID,mapper,id);
    }

    public User getUserByEmail(String email) {
        return jdbcTemplate.queryForObject(SELECT_USER_BY_EMAIL,mapper,email);
    }

    public User getUserByNickname(String nickname) {
        return jdbcTemplate.queryForObject(SELECT_USER_BY_NICKNAME,mapper,nickname);
    }

    public List<User> getAllUsers() {
        return jdbcTemplate.query(SELECT_ALL_USERS,mapper);
    }

    public Long getUsersAmount() {
        return jdbcTemplate.queryForObject(SELECT_USERS_AMOUNT,Long.class);
    }

    public void addUser(User user) {

        jdbcTemplate.update(INSERT_USER,user.getNickname(),user.getEmail(),user.getPassword(),user.getRole());

    }

    public void saveUser(User user) {
        jdbcTemplate.update(UPDATE_USER,user.getNickname(),user.getEmail(),user.getPassword(),user.getRole(),user.getId());
    }

    public void deleteUser(Integer id) {
        jdbcTemplate.update(DELETE_USER,id);
    }

    public Boolean isExists(User user) {
        return jdbcTemplate.queryForObject(CHECK_USER_EXISTS,Boolean.class,user.getNickname(),user.getEmail());
    }

    public Boolean isEmailExists(String email) {
        return jdbcTemplate.queryForObject(CHECK_EMAIL_EXISTS,Boolean.class,email);
    }

    public Boolean isNicknameExists(String nickname) {
        return jdbcTemplate.queryForObject(CHECK_NICKNAME_EXISTS,Boolean.class,nickname);
    }

    public Map<String, Integer> getAuthorsRatingByDishAmountAndNickname(Pageable p) {
        List<Wrapper> list = jdbcTemplate.query(SELECT_USER_NICKNAMES_AND_DISH_AMOUNT_RATING,wrapMapper,p.getPageNumber()-1,p.getPageSize());

        Map<String,Integer> map = new HashMap<String, Integer>();

        for(Wrapper x: list){
            map.put(x.<String>getKey(), x.<Integer>getValue());
        }

        return map;
    }

    private RowMapper<User> mapper = new RowMapper<User>() {
        public User mapRow(ResultSet rs, int i) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setNickname(rs.getString("nickname"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setRole(rs.getString("role"));
            return user;
        }
    };


    private RowMapper<Wrapper> wrapMapper = new Wrapper().mapper;
}
