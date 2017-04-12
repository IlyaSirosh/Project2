package com.sirosh.project.dao.impl;

import com.sirosh.project.dao.IngredientDao;
import com.sirosh.project.dao.IngredientTypeDao;
import com.sirosh.project.dao.UserDao;
import com.sirosh.project.dao.impl.utils.Wrapper;
import com.sirosh.project.entity.Dish;
import com.sirosh.project.entity.Ingredient;
import com.sirosh.project.entity.IngredientType;
import com.sirosh.project.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
public class JdbcIngredientDao implements IngredientDao {

    private static final String INSERT_INGREDIENT = "INSERT INTO ingredients (name,author) VALUES (?,?)";
    private static final String INSERT_ING_AND_TYPES = "INSERT INTO ingredients_types_join (ingredient_id,type_id) VALUES (?,?)";
    private static final String UPDATE_INGREDIENT = "UPDATE ingredients SET name=?, author=? WHERE id=?";
    private static final String DELETE_INGREDIENT = "DELETE FROM ingredients WHERE id=?";
    private static final String DELETE_INGREDIENT_TYPES = "DELETE FROM ingredients_types_join WHERE ingredient_id=? AND type_id IN (?)";
    private static final String DELETE_INGREDIENT_REDUNDANT_TYPES = "DELETE FROM ingredients_types_join WHERE ingredient_id=? AND type_id NOT IN (?)";
    private static final String SELECT_ALL_INGREDIENTS = "SELECT * FROM ingredients LIMIT ?,?";
    private static final String SELECT_INGREDIENT_NAMES = "SELECT name FROM ingredients";
    private static final String SELECT_INGREDIENT_NAMES_WITH_PREFIX = "SELECT name FROM ingredients WHERE name LIKE ?";
    private static final String SELECT_INGREDIENT_BY_ID = "SELECT * FROM ingredients WHERE id=?";
    private static final String SELECT_INGREDIENT_BY_NAME = "SELECT * FROM ingredients WHERE name=?";
    private static final String SELECT_INGREDIENTS_BY_NAMES = "SELECT * FROM ingredients WHERE name IN (?)";
    private static final String SELECT_INGREDIENTS_BY_TYPES = "SELECT * FROM ingredients WHERE id IN (" +
                                                                    "SELECT ingredient_id"+
                                                                    "FROM ingredients_types_join AS T1"+
                                                                    "WHERE NOT EXISTS (" +
                                                                        "SELECT * " +
                                                                        "FROM ingredients " +
                                                                        "WHERE NOT EXISTS (" +
                                                                            "SELECT * " +
                                                                            "FROM ingredients AS T2 " +
                                                                            "WHERE T1.ingredient_id = T2.ingredient_id " +
                                                                            "AND T2.type_id IN (" +
                                                                                "SELECT id FROM ingredient_types WHERE name IN (?) " +
                                                                            ")" +
                                                                        ")" +
                                                                    ")"+
                                                              ")";

    private static final String SELECT_DISH_INGREDIENTS = "SELECT * FROM ingredients WHERE id IN (" +
                                                                "SELECT ingredient_id " +
                                                                "FROM dishes_ingredients_join " +
                                                                "WHERE dish_id IN (" +
                                                                    "SELECT id FROM dishes WHERE name=?"+
                                                                ") "+
                                                          ")";
    private static final String SELECT_DISH_INGREDIENT_IDS =
            "SELECT ingredient_id " +
            "FROM dishes_ingredients_join " +
            "WHERE dish_id = ?";

    private static final String CHECH_IF_EXISTS_NAME = "SELECT EXISTS (SELECT * FROM ingredients WHERE name=?)";
    private static final String CHECH_IF_EXISTS_INGREDIENT_TYPE = "SELECT EXISTS (SELECT * FROM ingredients_types_join WHERE ingredient_id=? AND type_id=?)";

    @Autowired
    private UserDao userDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public Ingredient addIngredient(Ingredient ingredient) {

        Ingredient g = jdbcTemplate.queryForObject(INSERT_INGREDIENT,mapper,ingredient.getName(),ingredient.getAuthor().getId());


        for(IngredientType x: ingredient.getTypes()){
            if(!isExistsType(g,x))
                addIngredientAndType(g,x);
        }

        g.setTypes(ingredient.getTypes());

        return g;
    }

    private void addIngredientAndType(Ingredient ingredient, IngredientType type) {
        jdbcTemplate.update(INSERT_ING_AND_TYPES,ingredient.getId(),type.getId());
    }

    public void saveIngredient(Ingredient ingredient) {
        List<Integer> ids = new ArrayList<Integer>();
        for(IngredientType x: ingredient.getTypes()){

            ids.add(x.getId());

            if(!isExistsType(ingredient,x))
                addIngredientAndType(ingredient,x);

        }
        if(!ids.isEmpty())
            jdbcTemplate.update(DELETE_INGREDIENT_REDUNDANT_TYPES,ingredient.getId(),Wrapper.getSequenceStringFromList(ids));
        jdbcTemplate.update(UPDATE_INGREDIENT,ingredient.getName(),ingredient.getAuthor().getId(),ingredient.getId());
    }

    public void deleteIngredient(Ingredient ingredient) {
        jdbcTemplate.update(DELETE_INGREDIENT_TYPES,ingredient.getId(),Wrapper.getSequenceStringFromList(ingredient.getIngredientTypeIds()));
        jdbcTemplate.update(DELETE_INGREDIENT,ingredient.getId());
    }

    public List<Ingredient> getAllIngredients(Pageable page) {
        return jdbcTemplate.query(SELECT_ALL_INGREDIENTS,mapper,page.getPageNumber(),page.getPageSize());
    }

    public List<Ingredient> getIngredientsByTypes(List<String> types, Pageable page) {
        return jdbcTemplate.query(SELECT_INGREDIENTS_BY_TYPES+" LIMIT ?,?",mapper,Wrapper.getSequenceStringFromList(types),page.getPageNumber(),page.getPageSize());
    }

    public List<Ingredient> getIngredientsByTypes(List<String> types) {
        return jdbcTemplate.query(SELECT_INGREDIENTS_BY_TYPES,mapper,Wrapper.getSequenceStringFromList(types));
    }

    public List<String> getIngredientNames() {
        return jdbcTemplate.queryForList(SELECT_INGREDIENT_NAMES,String.class);
    }

    public List<String> getIngredientNames(Pageable page) {
        return jdbcTemplate.queryForList(SELECT_INGREDIENT_NAMES+"LIMIT ?,?",String.class,page.getPageNumber(),page.getPageSize());
    }

    public List<String> getIngredientNamesWithPrefix(String namePrefix) {
        return jdbcTemplate.queryForList(SELECT_INGREDIENT_NAMES_WITH_PREFIX,String.class,namePrefix+"%");
    }

    public List<String> getIngredientNamesWithPrefix(String namePrefix, Pageable page) {
        return jdbcTemplate.queryForList(SELECT_INGREDIENT_NAMES_WITH_PREFIX+"LIMIT ?,?",String.class,namePrefix+"%",page.getPageNumber(),page.getPageSize());
    }

    public Ingredient getIngredientById(Integer id) {
        return jdbcTemplate.queryForObject(SELECT_INGREDIENT_BY_ID,mapper,id);
    }

    public Ingredient getIngredientByName(String name) {
        return jdbcTemplate.queryForObject(SELECT_INGREDIENT_BY_NAME,mapper,name);
    }

    public List<Ingredient> getIngredientsByNames(List<String> names) {
        return jdbcTemplate.query(SELECT_INGREDIENTS_BY_NAMES,mapper, Wrapper.getSequenceStringFromList(names));
    }

    public List<Ingredient> getDishIngredients(Dish dish) {
        return jdbcTemplate.query(SELECT_DISH_INGREDIENTS,mapper,dish.getName());
    }

    public List<Integer> getDishIngredientIds(Dish dish) {
        return jdbcTemplate.queryForList(SELECT_DISH_INGREDIENT_IDS,Integer.class,dish.getId());
    }

    public Boolean isExixts(String name) {
        return jdbcTemplate.queryForObject(CHECH_IF_EXISTS_NAME,Boolean.class,name);
    }

    private Boolean isExistsType(Ingredient ingredient,IngredientType type){
        return jdbcTemplate.queryForObject(CHECH_IF_EXISTS_INGREDIENT_TYPE,Boolean.class,ingredient.getId(),type.getId());
    }

    private RowMapper<Ingredient> mapper = new RowMapper<Ingredient>() {
        public Ingredient mapRow(ResultSet rs, int i) throws SQLException {
            Ingredient ingredient = new Ingredient();
            ingredient.setId(rs.getInt("id"));
            ingredient.setName(rs.getString("name"));
            User author = userDao.getUserById(rs.getLong("author"));
            ingredient.setAuthor(author);
            return ingredient;
        }
    };
}
