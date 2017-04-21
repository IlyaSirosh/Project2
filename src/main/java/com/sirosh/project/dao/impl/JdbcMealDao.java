package com.sirosh.project.dao.impl;

import com.sirosh.project.dao.DishDao;
import com.sirosh.project.dao.MealDao;
import com.sirosh.project.dao.UnityDao;
import com.sirosh.project.dao.UserDao;
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

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Illya on 4/11/17.
 */


@Repository
public class JdbcMealDao implements MealDao {

    private static final String INSERT_MEAL = "INSERT INTO meals (user,date,time) VALUES (?,?,?)";
    private static final String INSERT_DISH_AND_AMOUNT_TO_MEAL = "INSERT INTO meals_dishes_join (meal_id,dish_id,amount,unity) VALUES (?,?,?,?)";
    private static final String UPDATE_MEAL = "UPDATE meals SET user=?,date=?,time=? WHERE id=?";
    private static final String UPDATE_MEAL_DISHES = "UPDATE meals_dishes_join SET amount=?,unity=? WHERE meal_id=? AND dish_id=?";
    private static final String DELETE_MEAL = "DELETE FROM meals WHERE user=?,date=?,time=?";
    private static final String DELETE_MEAL_DISHES = "DELETE FROM meals_dishes_join WHERE meal_id=?";
    private static final String DELETE_REDUNDANT_DISHES = "DELETE FROM meals_dishes_join WHERE meal_id=:id AND dish_id NOT IN (:ids)";
    private static final String SELECT_MEALS_BY_USER = "SELECT * FROM meals WHERE user=?";
    private static final String SELECT_MEALS_BY_ID = "SELECT * FROM meals WHERE id=?";
    private static final String SELECT_DISHES_AMOUNT_BY_MEAL = "SELECT dish_id,amount,unity FROM meals_dishes_join WHERE meal_id=?";
    private static final String SELECT_NUTRIENTS_BY_USER_AND_DATE = "SELECT SUM(amount*carbohydrates) AS carbohydrates, SUM(amount*proteins) AS proteins, " +
                                                                            "SUM(amount*fats) AS fats, SUM(amount*calories) AS calories " +
                                                                    "FROM dishes INNER JOIN meals_dishes_join ON dishes.id=meals_dishes_join.dish_id " +
                                                                    "WHERE meal_id IN (SELECT id FROM meals WHERE user=? AND date=?)";
    private static final String SELECT_NUTRIENTS_BY_MEAL = "SELECT SUM(amount*carbohydrates) AS carbohydrates, SUM(amount*proteins) AS proteins, " +
                                                                "SUM(amount*fats) AS fats, SUM(amount*calories) AS calories " +
                                                                "FROM dishes INNER JOIN meals_dishes_join ON dishes.id=meals_dishes_join.dish_id " +
                                                                "WHERE meal_id = ?";
    private static final String SELECT_MEALS_BY_USER_AND_DATE = "SELECT * FROM meals WHERE user=? AND date=?";
    private static final String SELECT_MEALS_BY_USER_AND_PERIOD = "SELECT * FROM meals WHERE user=? AND (date BETWEEN ? AND ?)";
    private static final String SELECT_MEAL_BY_USER_AND_DATE_AND_TIME = "SELECT * FROM meals WHERE user=? AND date=? AND time=?";
    private static final String CHECH_IF_DISH_EXISTS = "SELECT EXISTS (SELECT * FROM meals_dishes_join WHERE meal_id=? AND dish_id=?)";

    @Autowired
    private DishDao dishDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UnityDao unityDao;


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void addMeal(Meal meal) {
        jdbcTemplate.update(INSERT_MEAL,meal.getUser(),meal.getDate(),meal.getTime());

        Meal meal2 = getMealByUserDateTime(meal.getUser(),meal.getDate(),meal.getTime());

        for(Dish x: meal.getDishes().keySet()){
            addDishAndAmountToMeal(meal2,x,meal.getDishes().get(x));

        }

    }

    private void addDishAndAmountToMeal(Meal meal, Dish dish, Amount amount) {

        jdbcTemplate.update(INSERT_DISH_AND_AMOUNT_TO_MEAL,meal.getId(),dish.getId(),amount.getValue(),amount.getUnity().getId());

    }

    public void saveMeal(Meal meal) {

        jdbcTemplate.update(UPDATE_MEAL,meal.getUser().getId(),meal.getDate(),meal.getTime(),meal.getId());

        List<Integer> l = new ArrayList<Integer>();
        for(Dish x: meal.getDishes().keySet()){
            if(isDishExists(meal,x))
                jdbcTemplate.update(UPDATE_MEAL_DISHES,meal.getDishes().get(x).getValue(),meal.getDishes().get(x).getUnity().getId(),meal.getId(),x.getId());
            else
                addDishAndAmountToMeal(meal,x,meal.getDishes().get(x));

            l.add(x.getId());
        }

        if(!l.isEmpty()) {
            MapSqlParameterSource map = new MapSqlParameterSource();
            map.addValue("ids",l);
            map.addValue("id",meal.getId());

            namedParameterJdbcTemplate.update(DELETE_REDUNDANT_DISHES, map);
        }
    }

    public List<Meal> getAllMealsByUser(User user) {

        return jdbcTemplate.query(SELECT_MEALS_BY_USER,mealMapper,user.getId());

    }

    public List<Meal> getAllMealsByUser(User user, Pageable page) {
        return jdbcTemplate.query(SELECT_MEALS_BY_USER+" LIMIT ?,?",mealMapper,user.getId(),page.getPageNumber()-1,page.getPageSize());
    }

    public Map<Dish, Amount> getDishesByMeal(Meal meal) {

        List<Wrapper> l = jdbcTemplate.query(SELECT_DISHES_AMOUNT_BY_MEAL,mealDishMapper,meal.getId());

        Map<Dish,Amount> map = new HashMap<Dish, Amount>();

        for(Wrapper x: l){
            map.put((Dish)x.getKey(),(Amount)x.getValue());
        }

        return map;
    }

    public Nutrients getNutrientsByUserAndDate(User user, Date date) {
        return jdbcTemplate.queryForObject(SELECT_NUTRIENTS_BY_USER_AND_DATE,nutrMapper,user.getId(),date.toString());
    }

    public Nutrients getNutrientsByMeal(Meal meal) {
        return jdbcTemplate.queryForObject(SELECT_NUTRIENTS_BY_MEAL,nutrMapper,meal.getId());
    }

    public List<Meal> getMealsByUserAndDate(User user, Date date) {
        return jdbcTemplate.query(SELECT_MEALS_BY_USER_AND_DATE,mealMapper,user.getId(),date.toString());
    }

    public List<Meal> getMealsByUserAndDate(User user, Date from, Date to) {
        return jdbcTemplate.query(SELECT_MEALS_BY_USER_AND_PERIOD,mealMapper,user.getId(),from.toString(),to.toString());
    }

    public Meal getMealById(Integer id) {
        return jdbcTemplate.queryForObject(SELECT_MEALS_BY_ID,mealMapper,id);
    }

    public Meal getMealByUserDateTime(User user, Date date, Time time) {
        return jdbcTemplate.queryForObject(SELECT_MEAL_BY_USER_AND_DATE_AND_TIME,mealMapper,user.getId(),date.toString(),time.toString());
    }

    public void deleteMeal(Meal meal) {
        jdbcTemplate.update(DELETE_MEAL_DISHES,meal.getId());
        jdbcTemplate.update(DELETE_MEAL,meal.getId());
    }

    public Boolean isDishExists(Meal meal,Dish dish){
        return jdbcTemplate.queryForObject(CHECH_IF_DISH_EXISTS,Boolean.class,meal.getId(),dish.getId());
    }


    private RowMapper<Meal> mealMapper = new RowMapper<Meal>() {
        public Meal mapRow(ResultSet rs, int i) throws SQLException {
            Meal meal = new Meal();
            meal.setId(rs.getLong("id"));
            User user = userDao.getUserById(rs.getInt("user"));
            meal.setUser(user);
            meal.setDate(rs.getDate("date"));
            meal.setTime(rs.getTime("time"));
            return meal;
        }
    };

    private  RowMapper<Nutrients> nutrMapper = new RowMapper<Nutrients>() {
        public Nutrients mapRow(ResultSet rs, int i) throws SQLException {
            Nutrients n = new Nutrients();
            n.setCarbohydrates(rs.getDouble("carbohydrates"));
            n.setProteins(rs.getDouble("proteins"));
            n.setFats(rs.getDouble("fats"));
            n.setCalories(rs.getDouble("calories"));
            return n;
        }
    };

    private  RowMapper<Wrapper> mealDishMapper = new RowMapper<Wrapper>() {
        public Wrapper mapRow(ResultSet rs, int i) throws SQLException {
            Wrapper n = new Wrapper();

            Dish d = dishDao.getDishById(rs.getInt("dish_id"));
            n.setKey(d);
            Amount a = new Amount();
            a.setValue(rs.getDouble("amount"));
            Unity u = unityDao.getUnityById(rs.getInt("unity"));
            a.setUnity(u);
            n.setValue(a);
            return n;
        }
    };
}
