package com.sirosh.project.dao.impl;

import com.sirosh.project.dao.DishTypeDao;
import com.sirosh.project.dao.impl.utils.Wrapper;
import com.sirosh.project.entity.Dish;
import com.sirosh.project.entity.DishType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Illya on 4/11/17.
 */

@Repository
public class JdbcDishTypeDao implements DishTypeDao {

    private static final String SELECT_ALL_TYPES = "SELECT * FROM dish_types";
    private static final String INSERT_DISH_TYPE = "INSERT INTO dish_types (name) VALUES (?)";
    private static final String UPDATE_DISH_TYPE = "UPDATE dish_types SET name=?";
    private static final String DELETE_DISH_TYPE = "DELETE FROM dish_types WHERE id=? OR name=?";
    private static final String SELECT_DISH_TYPE_NAMES = "SELECT name FROM dish_types";
    private static final String SELECT_DISH_TYPE_NAMES_WITH_PREFIX = "SELECT name FROM dish_types WHERE name LIKE ?";
    private static final String SELECT_DISH_TYPE_BY_ID = "SELECT * FROM dish_types WHERE id=?";
    private static final String SELECT_DISH_TYPE_BY_NAME = "SELECT * FROM dish_types WHERE name=?";
    private static final String SELECT_DISH_TYPE_BY_NAMES = "SELECT * FROM dish_types WHERE name IN (?)";
    private static final String SELECT_DISH_TYPES_BY_DISH = "SELECT * FROM dish_types WHERE id IN ( " +
                                                                "SELECT type_id " +
                                                                "FROM dishes_types_join "+
                                                                "WHERE dish_id=?" +
                                                            ")";
    private static final String CHECK_IF_EXISTS_NAME = "SELECT EXISTS (SELECT * FROM dish_types WHERE name=?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<DishType> getAllDishTypes() {
        return jdbcTemplate.query(SELECT_ALL_TYPES,mapper);
    }

    public DishType addDishType(DishType type) {
        return jdbcTemplate.queryForObject(INSERT_DISH_TYPE,mapper,type.getName());
    }

    public void saveDishType(DishType type) {
        jdbcTemplate.update(UPDATE_DISH_TYPE,type.getName());
    }

    public void deleteDishType(DishType type) {
        jdbcTemplate.update(DELETE_DISH_TYPE,type.getId(),type.getName());
    }

    public List<String> getDishTypeNames() {
        return jdbcTemplate.queryForList(SELECT_DISH_TYPE_NAMES,String.class);
    }

    public List<String> getDishTypeNamesWithPrefix(String namePrefix) {
        return jdbcTemplate.queryForList(SELECT_DISH_TYPE_NAMES_WITH_PREFIX,String.class,namePrefix+"%");
    }

    public DishType getDishTypeById(Integer id) {
        return jdbcTemplate.queryForObject(SELECT_DISH_TYPE_BY_ID,mapper,id);
    }

    public DishType getDishTypeByName(String name) {
        return jdbcTemplate.queryForObject(SELECT_DISH_TYPE_BY_NAME,mapper,name);
    }

    public List<DishType> getDishTypesByNames(List<String> names) {
        return jdbcTemplate.query(SELECT_DISH_TYPE_BY_NAMES,mapper, Wrapper.getSequenceStringFromList(names));
    }

    public List<DishType> getDishTypesByDish(Dish dish) {
        return jdbcTemplate.query(SELECT_DISH_TYPES_BY_DISH,mapper,dish.getId());
    }

    public List<Integer> getDishTypeIdsByDish(Dish dish) {
        return null;
    }

    public Boolean isNameExists(String name) {
        return jdbcTemplate.queryForObject(CHECK_IF_EXISTS_NAME,Boolean.class,name);
    }


    private RowMapper<DishType> mapper = new RowMapper<DishType>() {
        public DishType mapRow(ResultSet rs, int i) throws SQLException {
            DishType type = new DishType();
            type.setId(rs.getInt("id"));
            type.setName(rs.getString("name"));
            return type;
        }
    };
}
