package com.sirosh.project.dao.impl;

import com.sirosh.project.dao.*;
import com.sirosh.project.dao.impl.utils.Wrapper;
import com.sirosh.project.entity.*;
import com.sirosh.project.pojo.Amount;
import com.sirosh.project.pojo.Pageable;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Illya on 4/11/17.
 */
@Repository
public class JdbcDishDao implements DishDao {

    private static final String INSERT_DISH = "INSERT INTO dishes " +
            "(name, carbohydrates, proteins, fats, calories, image, author, description) " +
            " VALUES (?,?,?,?,?,?,?,?)";
    private static final String INSERT_DISH_INGREDIENT = "INSERT INTO dishes_ingredients_join (dish_id,ingredient_id) VALUES (?,?) ";
    private static final String INSERT_DISH_TYPES = "INSERT INTO dishes_types_join (dish_id,type_id) VALUES (?,?)";
    private static final String UPDATE_DISH = "UPDATE dishes SET name=?, image=?, author=?, description=?, " +
            "carbohydrates=?, proteins=?, fats=?, calories=? WHERE id=?";
    private static final String DELETE_DISH = "DELETE FROM dishes WHERE id=?";
    private static final String DELETE_DISH_INGREDIENTS = "DELETE FROM dishes_ingredients_join WHERE dish_id=? ";
    private static final String DELETE_DISH_REDUNDANT_INGREDIENTS = "DELETE FROM dishes_ingredients_join WHERE dish_id=:id AND ingredient_id NOT IN (:ids)";
    private static final String DELETE_DISH_TYPES = "DELETE FROM dishes_types_join WHERE dish_id=? ";
    private static final String DELETE_REDUNDANT_DISH_TYPES = "DELETE FROM dishes_types_join WHERE dish_id=:id AND (type_id NOT IN (:ids))";
    private static final String SELECT_DISH_BY_NAME = "SELECT * FROM dishes WHERE name=?";
    private static final String SELECT_DISH_BY_ID = "SELECT * FROM dishes WHERE id=?";
    private static final String SELECT_DISHES_BY_NAMES = "SELECT * FROM dishes WHERE name IN (:names)";
    private static final String SELECT_DISH_NAMES_WITH_PREFIX = "SELECT name FROM dishes WHERE name LIKE ?";
    private static final String SELECT_ALL_DISHES = "SELECT * FROM dishes LIMIT ?,?";
    private static final String SELECT_DISHES_BY_AUTHOR = "SELECT * FROM dishes WHERE author IN (SELECT id " +
                                                                                                "FROM users " +
                                                                                                "WHERE id=? OR nickname=?" +
                                                                                                ") LIMIT ?,?";
    private static final String SELECT_DISH_IDS_BY_TYPE_NAMES_EXACT =
                                                                    "SELECT dish_id "+
                                                                    "FROM dishes_types_join T1 "+
                                                                    "WHERE NOT EXISTS (" +
                                                                        "SELECT * " +
                                                                        "FROM dishes " +
                                                                        "WHERE NOT EXISTS (" +
                                                                            "SELECT * " +
                                                                            "FROM dishes_types_join T2 " +
                                                                            "WHERE T1.dish_id = T2.dish_id " +
                                                                            "AND T2.type_id IN (" +
                                                                                "SELECT id FROM dish_types WHERE name IN (:names) " +
                                                                            ")" +
                                                                        ")" +
                                                                    ")";



    private static final String SELECT_DISH_IDS_BY_INGREDIENT_NAMES_EXACT =
                                                                    "SELECT dish_id "+
                                                                    "FROM dishes_ingredients_join T1 "+
                                                                    "WHERE NOT EXISTS (" +
                                                                        "SELECT * " +
                                                                        "FROM dishes " +
                                                                        "WHERE NOT EXISTS (" +
                                                                            "SELECT * " +
                                                                            "FROM dishes_ingredients_join T2 " +
                                                                            "WHERE T1.dish_id = T2.dish_id " +
                                                                            "AND T2.ingredient_id IN (" +
                                                                                "SELECT id FROM ingredients WHERE name IN (:names) " +
                                                                            ")" +
                                                                        ")" +
                                                                    ")";



    private static final String SELECT_DISH_IDS_BY_INGREDIENT_TYPE_NAMES_EXACT =
                                                                        "SELECT dish_id "+
                                                                        "FROM dishes_ingredients_join T1 "+
                                                                        "WHERE NOT EXISTS (" +
                                                                            "SELECT * " +
                                                                            "FROM dishes " +
                                                                            "WHERE NOT EXISTS (" +
                                                                                "SELECT * " +
                                                                                "FROM dishes_ingredients_join T2 " +
                                                                                "WHERE T1.dish_id = T2.dish_id " +
                                                                                "AND T2.ingredient_id IN (" +
                                                                                    "SELECT ingredient_id " +
                                                                                    "FROM ingredients_types_join " +
                                                                                    "WHERE type_id IN (" +
                                                                                        "SELECT id FROM ingredient_types WHERE name IN (:names) " +
                                                                                    ") " +
                                                                                ")" +
                                                                            ")" +
                                                                        ")";

    private static final String SELECT_DISHES_BY_DISH_TYPES_EXACT = "SELECT * FROM dishes WHERE id IN (" + SELECT_DISH_IDS_BY_TYPE_NAMES_EXACT+  ") LIMIT :page,:size";
    private static final String SELECT_DISHES_BY_INGREDIENT_TYPES_EXACT = "SELECT * FROM dishes WHERE id IN (" + SELECT_DISH_IDS_BY_INGREDIENT_TYPE_NAMES_EXACT + ") LIMIT :page,:size";
    private static final String SELECT_DISHES_BY_INGREDIENTS_EXACT = "SELECT * FROM dishes WHERE id IN (" + SELECT_DISH_IDS_BY_INGREDIENT_NAMES_EXACT + ") LIMIT :page,:size";


    private static final String SELECT_DISH_IDS_BY_TYPE_NAMES = "SELECT dish_id " +
                                                                "FROM dishes_types_join " +
                                                                "WHERE type_id IN (" +
                                                                        "SELECT id FROM dish_types WHERE name IN (:type_names)" +
                                                                ") " +
                                                                "GROUP BY dish_id HAVING COUNT(type_id)=:count";

    private static final String SELECT_DISH_IDS_BY_INGREDIENT_NAMES =   "SELECT dish_id " +
                                                                        "FROM dishes_ingredients_join " +
                                                                        "WHERE ingredient_id IN (" +
                                                                            "SELECT id FROM ingredients WHERE name IN (:ingredient_names)" +
                                                                        ") " +
                                                                        "GROUP BY dish_id HAVING COUNT(ingredient_id)=:count";

    @Deprecated
    private static final String SELECT_DISH_IDS_BY_INGREDIENT_TYPE_NAMES = "SELECT dish_id " +
                                                                            "FROM dishes_ingredients_join " +
                                                                            "WHERE ingredient_id IN (" +
                                                                                "SELECT ingredient_id " +
                                                                                "FROM ingredients_types_join " +
                                                                                "WHERE type_id IN (" +
                                                                                    "SELECT id " +
                                                                                    "FROM ingredient_types " +
                                                                                    "WHERE name IN (:names)" +
                                                                                ")" +
                                                                            ")";

    private static final String SELECT_DISHES_BY_DISH_TYPES = "SELECT * FROM dishes WHERE id IN (" + SELECT_DISH_IDS_BY_TYPE_NAMES+  ") LIMIT :page,:size";
    private static final String SELECT_DISHES_BY_INGREDIENT_TYPES = "SELECT * FROM dishes WHERE id IN (" + SELECT_DISH_IDS_BY_INGREDIENT_TYPE_NAMES + ") LIMIT :page,:size";
    private static final String SELECT_DISHES_BY_INGREDIENTS = "SELECT * FROM dishes WHERE id IN (" + SELECT_DISH_IDS_BY_INGREDIENT_NAMES + ") LIMIT :page,:size";


    private static final String SELECT_DISHES_BY_TYPES_AND_INGREDIENTS = "SELECT * " +
            "FROM dishes " +
            "WHERE id IN ("+SELECT_DISH_IDS_BY_TYPE_NAMES+") AND id IN ("+SELECT_DISH_IDS_BY_INGREDIENT_NAMES+") LIMIT :page,:size";
//    private static final String SELECT_DISH_VARIETIES = "SELECT * FROM dish_varieties WHERE main=?";
//    private static final String COUNT_DISH_VARIETIES = "SELECT COUNT(*) FROM dish_varieties WHERE main=?";

    private static final String COUNT_ALL_DISHES = "SELECT COUNT(*) FROM dishes";
    private static final String COUNT_DISHES_BY_AUTHOR = "SELECT COUNT(*) FROM dishes WHERE author=?";
    private static final String COUNT_DISHES_BY_DISH_TYPES = "SELECT COUNT(*) FROM dishes WHERE id IN ("+ SELECT_DISH_IDS_BY_TYPE_NAMES+")";

    private static final String COUNT_DISHES_BY_INGREDIENTS = "SELECT COUNT(*) FROM dishes WHERE id IN (" + SELECT_DISH_IDS_BY_INGREDIENT_NAMES+")";

    private static final String CHECK_IF_NAME_EXISTS = "SELECT EXISTS (SELECT * FROM dishes WHERE name=?)";
    private static final String CHECK_IF_DISH_TYPE_EXISTS = "SELECT EXISTS (SELECT * FROM dishes_types_join WHERE dish_id=? AND type_id=?)";
    private static final String CHECK_IF_INGREDIENT_EXISTS = "SELECT EXISTS (SELECT * FROM dishes_ingredients_join WHERE dish_id=? AND ingredient_id=?)";


    @Autowired
    private UserDao userDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public void addDish(Dish d) {

        jdbcTemplate.update(INSERT_DISH,d.getName(),
                d.getCarbohydrates(),d.getProteins(),d.getFats(),d.getCalories(),d.getImage(),
                d.getAuthor().getId(),d.getDescription());

        Dish d2 = getDishByName(d.getName());


        for (Ingredient x: d.getIngredients()) {
            jdbcTemplate.update(INSERT_DISH_INGREDIENT,
                    d2.getId(), x.getId());

        }

        for (DishType x : d.getTypes()) {

            jdbcTemplate.update(INSERT_DISH_TYPES,  d2.getId(), x.getId());

        }

    }

    public void saveDish(Dish dish) {

        for(Ingredient x: dish.getIngredients()) {
            if(!isIngredientExists(dish,x))
                jdbcTemplate.update(INSERT_DISH_INGREDIENT,dish.getId(), x.getId());
        }


        for(DishType x:dish.getTypes()){

            if(!isDishTypeExists(dish,x))
                jdbcTemplate.update(INSERT_DISH_TYPES,dish.getId(),x.getId());


        }

        deleteRedundantDishTypes(dish,dish.getDishTypeIds());
        deleteRedundantIngredients(dish,dish.getIngredientIds());


        jdbcTemplate.update(UPDATE_DISH,dish.getName(),dish.getImage(),dish.getAuthor().getId(),
                dish.getDescription(), dish.getCarbohydrates(), dish.getProteins(), dish.getFats(), dish.getCalories(),dish.getId());
    }

    public void deleteDish(Dish dish) {

        jdbcTemplate.update(DELETE_DISH_TYPES,dish.getId());

        jdbcTemplate.update(DELETE_DISH_INGREDIENTS,dish.getId());

        jdbcTemplate.update(DELETE_DISH,dish.getId());
    }

    private void deleteRedundantIngredients(Dish dish, List<Integer> ids ){
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("ids",ids);
        map.addValue("id",dish.getId());

        namedParameterJdbcTemplate.update(DELETE_DISH_REDUNDANT_INGREDIENTS,map);
    }

    private void deleteRedundantDishTypes(Dish dish, List<Integer> ids){
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("ids",ids);
        map.addValue("id",dish.getId());

        namedParameterJdbcTemplate.update(DELETE_REDUNDANT_DISH_TYPES,map);
    }

    public Dish getDishByName(String name) {
        return jdbcTemplate.queryForObject(SELECT_DISH_BY_NAME,mapper,name);
    }

    public Dish getDishById(Integer id) {
        return jdbcTemplate.queryForObject(SELECT_DISH_BY_ID,mapper,id);
    }

    public List<Dish> getDishByName(List<String> names) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("names",names);

        return namedParameterJdbcTemplate.query(SELECT_DISHES_BY_NAMES,map,mapper);
    }

    public List<String> getDishNamesWithPrefix(String namePrefix) {
        return jdbcTemplate.queryForList(SELECT_DISH_NAMES_WITH_PREFIX,String.class,namePrefix+"%");
    }

    public List<Dish> getAllDishes(Pageable page) {
        return jdbcTemplate.query(SELECT_ALL_DISHES,mapper,page.getPageNumber()-1,page.getPageSize());
    }

    public List<Dish> getDishesByAuthor(User author, Pageable page) {
        return jdbcTemplate.query(SELECT_DISHES_BY_AUTHOR,mapper,author.getId(),author.getNickname(),page.getPageNumber()-1,page.getPageSize());
    }

    public List<Dish> getDishesByTypes(List<String> types, Pageable page) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("type_names",types);
        map.addValue("count",types.size());
        map.addValue("page",page.getPageNumber()-1);
        map.addValue("size",page.getPageSize());

        return namedParameterJdbcTemplate.query(SELECT_DISHES_BY_DISH_TYPES,map,mapper);
    }

    public List<Dish> getDishesByIngredients(List<String> ingredients, Pageable page) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("ingredient_names",ingredients);
        map.addValue("count",ingredients.size());
        map.addValue("page",page.getPageNumber()-1);
        map.addValue("size",page.getPageSize());
        return namedParameterJdbcTemplate.query(SELECT_DISHES_BY_INGREDIENTS,map,mapper);
    }

    public List<Dish> getDishesByIngredientTypes(List<String> types, Pageable page) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("names",types);
        map.addValue("page",page.getPageNumber()-1);
        map.addValue("size",page.getPageSize());
        return namedParameterJdbcTemplate.query(SELECT_DISHES_BY_INGREDIENT_TYPES,map,mapper);
    }

    public List<Dish> getDishesByTypesExact(List<String> types, Pageable page) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("names",types);
        map.addValue("page",page.getPageNumber()-1);
        map.addValue("size",page.getPageSize());

        return namedParameterJdbcTemplate.query(SELECT_DISHES_BY_DISH_TYPES_EXACT,map,mapper);
    }

    public List<Dish> getDishesByIngredientsExact(List<String> ingredients, Pageable page) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("names",ingredients);
        map.addValue("page",page.getPageNumber()-1);
        map.addValue("size",page.getPageSize());
        return namedParameterJdbcTemplate.query(SELECT_DISHES_BY_INGREDIENTS_EXACT,map,mapper);
    }

    public List<Dish> getDishesByIngredientTypesExact(List<String> types, Pageable page) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("names",types);
        map.addValue("page",page.getPageNumber()-1);
        map.addValue("size",page.getPageSize());
        return namedParameterJdbcTemplate.query(SELECT_DISHES_BY_INGREDIENT_TYPES_EXACT,map,mapper);
    }

    public List<Dish> getDishesByTypesAndIngredients(List<String> types, List<String> ingredients, Pageable page) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("ingredient_names",ingredients);
        map.addValue("type_names",types);
        map.addValue("page",page.getPageNumber()-1);
        map.addValue("size",page.getPageSize());

        return namedParameterJdbcTemplate.query(SELECT_DISHES_BY_TYPES_AND_INGREDIENTS,map,mapper);
    }

//    public List<Ingredient> getDishIngredients(Dish dish) {
//        return null;
//    }
//
//    public List<Dish> getDishesByIngredientTypes(List<IngredientType> types, Pageable page) {
//        return null;
//    }
//
//    public List<DishVariety> getDishVarieties(Dish dish, Pageable page) {
//        return null;
//    }
//
//    public int getDishVarietiesCount(Dish dish) {
//        return 0;
//    }

    public int getAllDishesCount() {
        return jdbcTemplate.queryForObject(COUNT_ALL_DISHES,Integer.class);
    }

    public int getDishesByAuthorCount(User author) {
        return jdbcTemplate.queryForObject(COUNT_DISHES_BY_AUTHOR,Integer.class,author.getId());
    }

    public int getDishesByTypesCount(List<String> types) {

        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("type_names",types);

        return namedParameterJdbcTemplate.queryForObject(COUNT_DISHES_BY_DISH_TYPES,map,Integer.class);
    }

    public int getDishesByIngredientsCount(List<String> ingredients) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("ingredient_names",ingredients);

        return namedParameterJdbcTemplate.queryForObject(COUNT_DISHES_BY_INGREDIENTS,map,Integer.class);
    }

    public Boolean isNameExists(String name) {

        return jdbcTemplate.queryForObject(CHECK_IF_NAME_EXISTS,Boolean.class,name);
    }

    private Boolean isDishTypeExists(Dish dish,DishType type){

        return jdbcTemplate.queryForObject(CHECK_IF_DISH_TYPE_EXISTS,Boolean.class,dish.getId(),type.getId());
    }

    private Boolean isIngredientExists(Dish dish,Ingredient ingredient){
        return jdbcTemplate.queryForObject(CHECK_IF_INGREDIENT_EXISTS,Boolean.class,dish.getId(),ingredient.getId());
    }

//    public int getDishesByIngredientTypesCount(List<IngredientType> types) {
//        return 0;
//    }


    private RowMapper<Dish> mapper = new RowMapper<Dish>() {
        public Dish mapRow(ResultSet rs, int i) throws SQLException {
            Dish d = new Dish();

            d.setId(rs.getInt("id"));
            d.setName(rs.getString("name"));

            User author = userDao.getUserById(rs.getInt("author"));
            d.setAuthor(author);

            Nutrients n = new Nutrients();

            n.setCalories(rs.getDouble("calories"));
            n.setCarbohydrates(rs.getDouble("carbohydrates"));
            n.setFats(rs.getDouble("fats"));
            n.setProteins(rs.getDouble("proteins"));

            d.setImage(rs.getString("image"));

            d.setDescription(rs.getString("description"));

            return d;
        }
    };
//
//    private  RowMapper<Wrapper> ingredientDishMapper = new RowMapper<Wrapper>() {
//        public Wrapper mapRow(ResultSet rs, int i) throws SQLException {
//            Wrapper n = new Wrapper();
//
//            Ingredient d = ingredientDao.getIngredientById(rs.getInt("ingredient_id"));
//            n.setKey(d);
//            Amount a = new Amount();
//            a.setValue(rs.getDouble("amount"));
//            Unity u = unityDao.getUnityById(rs.getInt("unity"));
//            a.setUnity(u);
//            n.setValue(a);
//            return n;
//        }
//    };
//
//    private RowMapper<DishType> dishTypeMapper = new RowMapper<DishType>() {
//        public DishType mapRow(ResultSet rs, int i) throws SQLException {
//            return dishTypeDao.getDishTypeById(rs.getInt("type_id"));
//        }
//    };

//    private RowMapper<DishVariety> mapper2 = new RowMapper<DishVariety>() {
//        public DishVariety mapRow(ResultSet rs, int i) throws SQLException {
//            DishVariety d = new DishVariety();
//
//            d.setId(rs.getInt("id"));
//            d.setName(rs.getString("name"));
//
//            User author = userDao.getUserById(rs.getLong("author"));
//            d.setAuthor(author);
//
//            Nutrients n = new Nutrients();
//
//            n.setCalories(rs.getDouble("calories"));
//            n.setCarbohydrates(rs.getDouble("carbohydrates"));
//            n.setFats(rs.getDouble("fats"));
//            n.setProteins(rs.getDouble("proteins"));
//
//            URL image = Dish.defaultImage;
//
//            try {
//                image = new URL(rs.getString("image"));
//            }catch (Exception e){}
//
//            d.setImage(image);
//
//            d.setDescription(rs.getString("description"));
//
//            Dish m = new Dish();
//            m.setId(rs.getInt("main"));
//
//            if(m.getId()!=0)
//                d.setMain(m);
//
//            return d;
//        }
//    };
}
