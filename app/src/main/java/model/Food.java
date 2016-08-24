package model;

import android.support.v7.app.AppCompatActivity;
import java.io.Serializable;

/**
 * Created by user on 21/08/2016.
 */

//implementing serializable saves/freezes our food object with all states & behaviours so we can use later//
    //these can then be as extras between intents with all properties intact//
    //giving it an id lets us know the serialize version//

public class Food extends AppCompatActivity implements Serializable {

    private static final long serialVersionUID = 10L;

    //instance variables//
    private String foodName;
    private int calories;
//    private String mealName;
    private int foodId;
    private String recordDate;

    //constructor//
    public Food( String food, int cals, int id, String date){
        foodName = food;
        calories = cals;
//        mealName = meal;
        foodId = id;
        recordDate = date;

    }

    //default constructor - in case we dont want to add all variables to food object//
    public Food(){
    //left empty//
    }

    //getters & setters for food object//

    //ID//
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    //Food name//
    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    //Calories//
    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    //Meal//
//    public String getMealName() {
//        return mealName;
//    }
//
//    public void setMealName(String mealName) {
//        this.mealName = mealName;
//    }

    //Food id from db//
    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    //Date//
    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }
}
