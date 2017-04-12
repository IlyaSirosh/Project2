package com.sirosh.project.dao.impl;

import com.sirosh.project.dao.IngredientTypeDao;
import com.sirosh.project.dao.impl.utils.Wrapper;
import com.sirosh.project.entity.Dish;
import com.sirosh.project.entity.Ingredient;
import com.sirosh.project.entity.IngredientType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Illya on 4/10/17.
 */

@Repository
public class JdbcIngredientTypeDao implements IngredientTypeDao {

    private static final String INSERT_INGREDIENT_TYPE = "INSERT INTO ingredient_types (name) VALUES (?)";
    private static final String UPDATE_INGEDIENT_TYPE = "UPDATE ingredient_types SET name=? WHERE id=?";
    private static final String DELETE_INGREDIENT_TYPE = "DELETE FROM ingredient_types WHERE id=? OR name=?";
    private static final String SELECT_INGREDIENT_TYPE_BY_ID = "SELECT * FROM ingredient_types WHERE id=?";
    private static final String SELECT_INGREDIENT_TYPE_BY_NAME = "SELECT * FROM ingredient_types WHERE name=?";
    private static final String SELECT_INGREDIENT_TYPE_NAMES = "SELECT name FROM ingredient_types";
    private static final String SELECT_INGREDIENT_TYPE_NAMES_USING_PREFIX = "SELECT name FROM ingredient_types WHERE name LIKE ?";
    private static final String SELECT_INGREDIENT_TYPES_BY_NAMES = "SELECT * FROM ingredient_types WHERE name IN (?)";
    private static final String SELECT_INGREDIENT_TYPES_BY_INGREDIENT = "SELECT * " +
                                                                        "FROM ingredient_types  " +
                                                                        "WHERE id IN (SELECT type_id " +
                                                                                     "FROM ingredients_types_join " +
                                                                                     "WHERE ingredient_id=?" +
                                                                        ")";
    private static final String SELECT_INGREDIENT_TYPES_BY_INGREDIENT_NAME = "SELECT * " +
                                                                            "FROM ingredient_types  " +
                                                                            "WHERE id IN (SELECT type_id " +
                                                                                        "FROM ingredients_types_join " +
                                                                                        "WHERE ingredient_id IN (" +
                                                                                             "SELECT id FROM ingredients WHERE name=?" +
                                                                                        ")" +
                                                                            ")";
    private static final String SELECT_INGREDIENT_TYPES_BY_INGREDIENTS = "SELECT * " +
                                                                         "FROM ingredient_types  " +
                                                                         "WHERE id IN (SELECT type_id " +
                                                                                        "FROM ingredients_types_join " +
                                                                                        "WHERE ingredient_id IN (?)" +
                                                                         ")";

    private static final String SELECT_INGREDIENT_TYPES_BY_DISH = "SELECT * " +
                                                                    "FROM ingredient_types  " +
                                                                    "WHERE id IN (SELECT T.type_id " +
                                                                                "FROM ingredients_types_join T" +
                                                                                "WHERE T.ingredient_id IN (SELECT I.ingredient_id" +
                                                                                                            "FROM dishes_ingredients_join I" +
                                                                                                            "WHERE I.dish_id=?"+
                                                                                ")" +
                                                                    ")";
    private static final String CHECK_IF_EXISTS = "SELECT EXISTS (SELECT * FROM ingredient_types WHERE name=?)";
    @Autowired
    private JdbcTemplate jdbcTemplate;



    public IngredientType addIngredientType(IngredientType type) {
        return jdbcTemplate.queryForObject(INSERT_INGREDIENT_TYPE,mapper,type.getName());
    }

    public void saveIngredientType(IngredientType type) {
        jdbcTemplate.update(UPDATE_INGEDIENT_TYPE,type.getName(),type.getId());
    }

    public void deleteIngredientType(IngredientType type) {
        jdbcTemplate.update(DELETE_INGREDIENT_TYPE,type.getId(),type.getName());
    }

    public List<String> getIngredientTypesNameWithPrefix(String namePrefix) {
        return jdbcTemplate.queryForList(SELECT_INGREDIENT_TYPE_NAMES_USING_PREFIX,String.class,namePrefix+"%");
    }

    public List<String> getIngredientTypesName() {
        return jdbcTemplate.queryForList(SELECT_INGREDIENT_TYPE_NAMES,String.class);
    }

    public IngredientType getIngredientTypeById(Integer id) {
        return jdbcTemplate.queryForObject(SELECT_INGREDIENT_TYPE_BY_ID,mapper,id);
    }

    public IngredientType getIngredientTypeByName(String name) {
        return jdbcTemplate.queryForObject(SELECT_INGREDIENT_TYPE_BY_NAME,mapper,name);
    }

    public List<IngredientType> getIngredientTypesByNames(List<String> names) {
        return jdbcTemplate.query(SELECT_INGREDIENT_TYPES_BY_NAMES,mapper, Wrapper.getSequenceStringFromList(names));
    }

    public List<IngredientType> getIngredientTypesByIngredient(Ingredient ingredient) {
        return jdbcTemplate.query(SELECT_INGREDIENT_TYPES_BY_INGREDIENT,mapper,ingredient.getId());
    }

    public List<IngredientType> getIngredientTypesByIngredient(String ingredient) {
        return jdbcTemplate.query(SELECT_INGREDIENT_TYPES_BY_INGREDIENT_NAME,mapper,ingredient);
    }

    public List<IngredientType> getIngredientTypesByIngredient(List<Integer> ingredientIds) {

        return jdbcTemplate.query(SELECT_INGREDIENT_TYPES_BY_INGREDIENTS,mapper,Wrapper.getSequenceStringFromList(ingredientIds));
    }

    public List<IngredientType> getIngredientTypesByDish(Dish dish) {
        return jdbcTemplate.query(SELECT_INGREDIENT_TYPES_BY_DISH,mapper,dish.getId());
    }

    public Boolean isNameExists(String name) {
        return jdbcTemplate.queryForObject(CHECK_IF_EXISTS,Boolean.class,name);
    }

    private RowMapper<IngredientType> mapper = new RowMapper<IngredientType>() {
        public IngredientType mapRow(ResultSet rs, int i) throws SQLException {
            IngredientType type = new IngredientType();

            type.setId(rs.getInt("id"));
            type.setName(rs.getString("name"));

            return type;
        }
    };
}
