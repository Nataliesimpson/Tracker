package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.Food;

/**
 * Created by user on 22/08/2016.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    //instance variable//
    private final ArrayList<Food> foodList = new ArrayList<>();

    //constructor//
    public DatabaseHandler(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);

    }

    //where table is created - SQL command string gets passed in//
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY, " + Constants.FOOD_NAME +
                " TEXT, " + Constants.FOOD_CALORIES_NAME + " INT, " + Constants.DATE_NAME + " LONG);";

        db.execSQL(CREATE_TABLE);
    }

    //dropping table before each session//
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);

        //when table is dropped, create a new one//
        onCreate(db);
    }

    //Get total items saved//
    public int getTotalItems() {

        int totalItems = 0;

        //selecting all from our table//
        String query = "SELECT * FROM " + Constants.TABLE_NAME;
        //SQLite db instance - so we are able to use our db//
        SQLiteDatabase dba = this.getReadableDatabase();
        //wanting everything returned from table//
        //cursor holds all of the database rows//
        //The purpose of a cursor is to point to a single row of the result fetched by the query//
        //We load the row pointed by the cursor object//
        Cursor cursor = dba.rawQuery(query, null);
        //rawQuery() directly accepts an SQL select statement as input//
        //The method returns 'Cursor' object which points to one row of the query result//

        //cursor holds all of the databse rows//
        totalItems = cursor.getCount();

        cursor.close();

        return totalItems;
    }

    //get total calories consumed//
    public int totalCalories() {
        int cals = 0;

        SQLiteDatabase dba = this.getReadableDatabase();

        //getting sum of calories column//
        String query = "SELECT SUM( " + Constants.FOOD_CALORIES_NAME + " ) " +
                "FROM " + Constants.TABLE_NAME;

        Cursor cursor = dba.rawQuery(query, null);

        //if there is something there in the cursor//
        //we are going to get the id at index 0//
        if (cursor.moveToFirst()) {
            cals = cursor.getInt(0);
        }

        //close rows and db//
        cursor.close();
        dba.close();

        return cals;
    }


    //delete food item//
    //passes in id to target which item we want to delete//
    public void deleteFood(int id) {

        SQLiteDatabase dba = this.getWritableDatabase();
        dba.delete(Constants.TABLE_NAME, Constants.KEY_ID + " = ?",
                //convert id int into string - as putting into string array//
                new String[]{String.valueOf(id)});

        dba.close();
    }


    //add food to db//
    //passing in food item//
    public void addFood(Food food) {

        //this method writes to db//
        SQLiteDatabase dba = this.getWritableDatabase();

        //key value pair object//
        ContentValues values = new ContentValues();
        values.put(Constants.FOOD_NAME, food.getFoodName());
        values.put(Constants.FOOD_CALORIES_NAME, food.getCalories());
        //ask Android OS to give date//
        values.put(Constants.DATE_NAME, System.currentTimeMillis());

        //inserting food into our table//
        dba.insert(Constants.TABLE_NAME, null, values);

        //checking if food object has been added to the db//
        Log.v("Added Food item", "It worked!!");

        //close db//
        dba.close();
    }

    //Get all foods - items in our db and retrieve everything//
    //returning an ArrayList of food objects//
    //will be easier to put in CustomListView is ArrayList//
    public ArrayList<Food> getFoods(){

        //clear our array we created before we add anything so nothing from previous query//
        foodList.clear();

        //reading from database//
        SQLiteDatabase dba = this.getReadableDatabase();

        //create new string array of everything want to get from the db//
        Cursor cursor = dba.query(Constants.TABLE_NAME,
                new String[]{Constants.KEY_ID, Constants.FOOD_NAME, Constants.FOOD_CALORIES_NAME,
                        Constants.DATE_NAME}, null, null, null, null, Constants.DATE_NAME + " DESC ");

        //loop through cursor, now it has all items from db//
        //Cursor is an object that can iterate on the result rows of your query - cursor can move to each row//
        //move to first moves cursor to the first result//
        if (cursor.moveToFirst()) {
            do {

                //create instance of our food item//
                Food food = new Food();
                food.setFoodName(cursor.getString(cursor.getColumnIndex(Constants.FOOD_NAME)));
                food.setCalories(cursor.getInt(cursor.getColumnIndex(Constants.FOOD_CALORIES_NAME)));
                food.setFoodId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID)));

                DateFormat dateFormat = DateFormat.getDateInstance();
                String date = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.DATE_NAME))).getTime());

                food.setRecordDate(date);

                foodList.add(food);

            }while (cursor.moveToNext());
        }

        cursor.close();
        dba.close();

        return foodList;
    }


}

