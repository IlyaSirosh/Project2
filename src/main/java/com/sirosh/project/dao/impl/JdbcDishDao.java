package com.sirosh.project.dao.impl;

import com.sirosh.project.dao.*;
import com.sirosh.project.dao.impl.utils.Wrapper;
import com.sirosh.project.entity.*;
import com.sirosh.project.pojo.Amount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
            "(name,carbohydrates,proteins,fats,calories,image,author,description) " +
            " VALUES (?,?,?,?,?,?,?,?)";
    private static final String INSERT_DISH_INGREDIENT = "INSERT INTO dishes_ingredients_join (dish_id,ingredient_id,amount,unity) VALUES (?,?,?,?) ";
    private static final String INSERT_DISH_TYPES = "INSERT INTO dishes_types_join (dish_id,type_id) VALUES (?,?)";
    private static final String UPDATE_DISH = "UPDATE dishes SET name=?, image=?, author=?, description=?, " +
            "carbohydrates=?, proteins=?, fats=?, calories=?";
    private static final String UPDATE_DISH_INGREDIENTS = "UPDATE dishes_ingredients_join SET amount=?,unity=? WHERE dish_id=? AND ingredient_id=?";
    private static final String DELETE_DISH = "DELETE FROM dishes WHERE id=? OR name=?";
    private static final String DELETE_DISH_INGREDIENTS = "DELETE FROM dishes_ingredients_join WHERE dish_id=? ";
//    private static final String DELETE_DISH_REDUNDANT_INGREDIENTS = "DELETE FROM dishes_ingredients_join WHERE dish_id=? AND ingredient_id NOT IN (?)";
    private static final String DELETE_DISH_TYPES = "DELETE FROM dishes_types_join WHERE dish_id=? ";
    private static final String DELETE_REDUNDANT_DISH_TYPES = "DELETE FROM dishes_types_join WHERE dish_id=? AND type_id NOT IN (?)";
    private static final String SELECT_DISH_BY_NAME = "SELECT * FROM dishes WHERE name=?";
    private static final String SELECT_DISH_BY_ID = "SELECT * FROM dishes WHERE id=?";
    private static final String SELECT_DISHES_BY_NAMES = "SELECT * FROM dishes WHERE name IN (?)";
    private static final String SELECT_DISH_NAMES_WITH_PREFIX = "SELECT name FROM dishes WHERE name LIKE ?";
    private static final String SELECT_ALL_DISHES = "SELECT * FROM dishes LIMIT ?,?";
    private static final String SELECT_DISHES_BY_AUTHOR = "SELECT * FROM dishes WHERE author=? LIMIT ?,?";
    private static final String SELECT_DISHES_BY_DISH_TYPES = "SELECT * FROM dishes WHERE id IN (" +
                                                                    "SELECT dish_id"+
                                                                    "FROM dishes_types_join AS T1"+
                                                                    "WHERE NOT EXISTS (" +
                                                                        "SELECT * " +
                                                                        "FROM dishes " +
                                                                        "WHERE NOT EXISTS (" +
                                                                            "SELECT * " +
                                                                            "FROM dishes_types_join AS T2 " +
                                                                            "WHERE T1.dish_id = T2.dish_id " +
                                                                            "AND T2.type_id IN (" +
                                                                                "SELECT id FROM dish_types WHERE name IN (?) " +
                                                                            ")" +
                                                                        ")" +
                                                                    ")"+
                                                              ") LIMIT ?,?";
    private static final String SELECT_DISHES_BY_INGREDIENTS = "SELECT * FROM dishes WHERE id IN (" +
                                                                    "SELECT dish_id"+
                                                                    "FROM dishes_ingredients_join AS T1"+
                                                                    "WHERE NOT EXISTS (" +
                                                                        "SELECT * " +
                                                                        "FROM dishes " +
                                                                        "WHERE NOT EXISTS (" +
                                                                            "SELECT * " +
                                                                            "FROM dishes_ingredients_join AS T2 " +
                                                                            "WHERE T1.dish_id = T2.dish_id " +
                                                                            "AND T2.ingredient_id IN (" +
                                                                                "SELECT id FROM ingredients WHERE name IN (?) " +
                                                                            ")" +
                                                                        ")" +
                                                                    ")"+
                                                                ") LIMIT ?,?";

    private static final String SELECT_DISHES_BY_INGREDIENT_TYPES = "SELECT * FROM dishes WHERE id IN (" +
                                                                        "SELECT dish_id"+
                                                                        "FROM dishes_ingredients_join AS T1"+
                                                                        "WHERE NOT EXISTS (" +
                                                                            "SELECT * " +
                                                                            "FROM dishes " +
                                                                            "WHERE NOT EXISTS (" +
                                                                                "SELECT * " +
                                                                                "FROM dishes_ingredients_join AS T2 " +
                                                                                "WHERE T1.dish_id = T2.dish_id " +
                                                                                "AND T2.ingredient_id IN (" +
                                                                                    "SELECT (DISTINCT ingredient_id) " +
                                                                                    "FROM ingredients_types_join " +
                                                                                    "WHERE type_id IN (?) " +
                                                                                ")" +
                                                                            ")" +
                                                                        ")"+
                                                                    ") LIMIT ?,?";


//    private static final String SELECT_DISH_VARIETIES = "SELECT * FROM dish_varieties WHERE main=?";
//    private static final String COUNT_DISH_VARIETIES = "SELECT COUNT(*) FROM dish_varieties WHERE main=?";

    private static final String COUNT_ALL_DISHES = "SELECT COUNT(*) FROM dishes";
    private static final String COUNT_DISHES_BY_AUTHOR = "SELECT COUNT(*) FROM dishes WHERE author=?";
    private static final String COUNT_DISHES_BY_DISH_TYPES = "SELECT COUNT(*) FROM dishes WHERE id IN (" +
            "SELECT dish_id"+
            "FROM dishes_types_join AS T1"+
            "WHERE NOT EXISTS (" +
            "SELECT * " +
            "FROM dishes " +
            "WHERE NOT EXISTS (" +
            "SELECT * " +
            "FROM dishes_types_join AS T2 " +
            "WHERE T1.dish_id = T2.dish_id " +
            "AND T2.type_id IN (" +
            "SELECT id FROM dish_types WHERE name IN (?) " +
            ")" +
            ")" +
            ")"+
            ")";
    private static final String COUNT_DISHES_BY_INGREDIENTS = "SELECT COUNT(*) FROM dishes WHERE id IN (" +
            "SELECT dish_id"+
            "FROM dishes_ingredients_join AS T1"+
            "WHERE NOT EXISTS (" +
            "SELECT * " +
            "FROM dishes " +
            "WHERE NOT EXISTS (" +
            "SELECT * " +
            "FROM dishes_ingredients_join AS T2 " +
            "WHERE T1.dish_id = T2.dish_id " +
            "AND T2.ingredient_id IN (" +
            "SELECT id FROM ingredients WHERE name IN (?) " +
            ")" +
            ")" +
            ")"+
            ")";

    private static final String CHECK_IF_NAME_EXISTS = "SELECT EXISTS (SELECT * FROM dishes WHERE name=?)";
    private static final String CHECK_IF_DISH_TYPE_EXISTS = "SELECT EXISTS (SELECT * FROM dishes_types_join WHERE dish_id=? AND type_id=?)";

    @Autowired
    private UserDao userDao;

    @Autowired
    private IngredientDao ingredientDao;

    @Autowired
    private UnityDao unityDao;

    @Autowired
    private DishTypeDao dishTypeDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public Dish addDish(Dish d) {

        Dish r = jdbcTemplate.queryForObject(INSERT_DISH,mapper,d.getName(),
                d.getCarbohydrates(),d.getProteins(),d.getFats(),d.getCalories(),d.getImage().toString(),
                d.getAuthor().getId(),d.getDescription());

        Wrapper wrapper;
        Map<Ingredient, Amount> map = new HashMap<Ingredient, Amount>();
        for (Ingredient x : d.getIngredients().keySet()) {
            wrapper = jdbcTemplate.queryForObject(INSERT_DISH_INGREDIENT, ingredientDishMapper,
                    r.getId(), x.getId(), d.getIngredients().get(x).getValue(),
                    d.getIngredients().get(x).getUnity().getId());
            map.put((Ingredient) wrapper.getKey(), (Amount) wrapper.getValue());
        }
        r.setIngredients(map);

        List<DishType> list = new ArrayList<DishType>();
        for (DishType x : d.getTypes()) {

            list.add(jdbcTemplate.queryForObject(INSERT_DISH_TYPES, dishTypeMapper, r.getId(), x.getId()));

        }
        r.setTypes(list);

         return r;
    }

    public void saveDish(Dish dish) {

        for(Ingredient x:dish.getIngredients().keySet()) {
            jdbcTemplate.update(UPDATE_DISH_INGREDIENTS, dish.getIngredients().get(x).getValue(),
                    dish.getIngredients().get(x).getUnity().getId(),dish.getId(),x.getId());
        }

        List<Integer> types = new ArrayList<Integer>();
        for(DishType x:dish.getTypes()){

            if(!isDishTypeExists(dish,x))
                jdbcTemplate.update(INSERT_DISH_TYPES,dish.getId(),x.getId());

            types.add(x.getId());
        }

        jdbcTemplate.update(DELETE_REDUNDANT_DISH_TYPES,dish.getId(),Wrapper.getSequenceStringFromList(types));

        if(!types.isEmpty())
            jdbcTemplate.update(UPDATE_DISH,dish.getName(),dish.getImage().toString(),dish.getAuthor().getId(),
                dish.getDescription(), dish.getCarbohydrates(), dish.getProteins(), dish.getFats(), dish.getCalories());
    }

    public void deleteDish(Dish dish) {

        jdbcTemplate.update(DELETE_DISH_TYPES,dish.getId());

        jdbcTemplate.update(DELETE_DISH_INGREDIENTS,dish.getId());

        jdbcTemplate.update(DELETE_DISH,dish.getId());
    }

    public Dish getDishByName(String name) {
        return jdbcTemplate.queryForObject(SELECT_DISH_BY_NAME,mapper,name);
    }

    public Dish getDishById(Integer id) {
        return jdbcTemplate.queryForObject(SELECT_DISH_BY_ID,mapper,id);
    }

    public List<Dish> getDishByName(List<String> name) {
        return jdbcTemplate.query(SELECT_DISHES_BY_NAMES,mapper, Wrapper.getSequenceStringFromList(name));
    }

    public List<String> getDishNamesWithPrefix(String namePrefix) {
        return jdbcTemplate.queryForList(SELECT_DISH_NAMES_WITH_PREFIX,String.class,namePrefix+"%");
    }

    public List<Dish> getAllDishes(Pageable page) {
        return jdbcTemplate.query(SELECT_ALL_DISHES,mapper,page.getPageNumber(),page.getPageSize());
    }

    public List<Dish> getDishesByAuthor(User author, Pageable page) {
        return jdbcTemplate.query(SELECT_DISHES_BY_AUTHOR,mapper,author.getId(),page.getPageNumber(),page.getPageSize());
    }

    public List<Dish> getDishesByTypes(List<String> typeIds, Pageable page) {
        return jdbcTemplate.query(SELECT_DISHES_BY_DISH_TYPES,mapper,Wrapper.getSequenceStringFromList(typeIds));
    }

    public List<Dish> getDishesByIngredients(List<String> ingredients, Pageable page) {
        return jdbcTemplate.query(SELECT_DISHES_BY_INGREDIENTS,mapper,Wrapper.getSequenceStringFromList(ingredients));
    }

    public List<Dish> getDishesByIngredientTypes(List<String> types, Pageable page) {
        return jdbcTemplate.query(SELECT_DISHES_BY_INGREDIENT_TYPES,mapper,Wrapper.getSequenceStringFromList(types));
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
        return jdbcTemplate.queryForObject(COUNT_DISHES_BY_DISH_TYPES,Integer.class,Wrapper.getSequenceStringFromList(types));
    }

    public int getDishesByIngredientsCount(List<String> ingredients) {
        return jdbcTemplate.queryForObject(COUNT_DISHES_BY_INGREDIENTS,Integer.class,Wrapper.getSequenceStringFromList(ingredients));
    }

    public Boolean isNameExists(String name) {
        return jdbcTemplate.queryForObject(CHECK_IF_NAME_EXISTS,Boolean.class,name);
    }

    private Boolean isDishTypeExists(Dish dish,DishType type){

        return jdbcTemplate.queryForObject(CHECK_IF_DISH_TYPE_EXISTS,Boolean.class,dish.getId(),type.getId());
    }

//    public int getDishesByIngredientTypesCount(List<IngredientType> types) {
//        return 0;
//    }


    private RowMapper<Dish> mapper = new RowMapper<Dish>() {
        public Dish mapRow(ResultSet rs, int i) throws SQLException {
            Dish d = new Dish();

            d.setId(rs.getInt("id"));
            d.setName(rs.getString("name"));

            User author = userDao.getUserById(rs.getLong("author"));
            d.setAuthor(author);

            Nutrients n = new Nutrients();

            n.setCalories(rs.getDouble("calories"));
            n.setCarbohydrates(rs.getDouble("carbohydrates"));
            n.setFats(rs.getDouble("fats"));
            n.setProteins(rs.getDouble("proteins"));

            URL image = Dish.defaultImage;

            try {
                image = new URL(rs.getString("image"));
            }catch (Exception e){}

            d.setImage(image);

            d.setDescription(rs.getString("description"));

            return d;
        }
    };

    private  RowMapper<Wrapper> ingredientDishMapper = new RowMapper<Wrapper>() {
        public Wrapper mapRow(ResultSet rs, int i) throws SQLException {
            Wrapper n = new Wrapper();

            Ingredient d = ingredientDao.getIngredientById(rs.getInt("ingredient_id"));
            n.setKey(d);
            Amount a = new Amount();
            a.setValue(rs.getDouble("amount"));
            Unity u = unityDao.getUnityById(rs.getInt("unity"));
            a.setUnity(u);
            n.setValue(a);
            return n;
        }
    };

    private RowMapper<DishType> dishTypeMapper = new RowMapper<DishType>() {
        public DishType mapRow(ResultSet rs, int i) throws SQLException {
            return dishTypeDao.getDishTypeById(rs.getInt("type_id"));
        }
    };

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
