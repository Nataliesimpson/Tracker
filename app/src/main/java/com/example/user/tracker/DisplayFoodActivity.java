package com.example.user.tracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import data.CustomListViewAdaptor;
import data.DatabaseHandler;
import model.Food;
import util.Utils;

/**
 * Created by user on 21/08/2016.
 */
public class DisplayFoodActivity extends AppCompatActivity{

    private DatabaseHandler dba;
    private ArrayList<Food> dbFoods = new ArrayList<>();
    private CustomListViewAdaptor foodAdapter;
    private ListView listView;

    private Food myFood;
    private TextView totalCals, totalFoods;


    //displaying total cals & total foods//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_food);

        listView = (ListView) findViewById(R.id.list_breakfast);
        totalCals = (TextView) findViewById(R.id.totalAmountTextView);
        totalFoods = (TextView) findViewById(R.id.totalItemsTextView);

        refreshData();
    }

    //populating our total food and total cals and listView//
    private void refreshData() {
        //clear ArrayList//
        dbFoods.clear();

        dba = new DatabaseHandler(getApplicationContext());

        ArrayList<Food> foodsFromDB = dba.getFoods();

        int calsValue = dba.totalCalories();
        int totalItems = dba.getTotalItems();

        //formatting the numbers//
        String formattedValue = Utils.formatNumber(calsValue);
        String formattedItems = Utils.formatNumber(totalItems);

        totalCals.setText("Total Calories: " + formattedValue);
        totalFoods.setText("Total Foods: " + formattedItems);

        //loop through all db foods//
        for (int i = 0; i < foodsFromDB.size(); i++){

            String name = foodsFromDB.get(i).getFoodName();
            String dateText = foodsFromDB.get(i).getRecordDate();
            int cals = foodsFromDB.get(i).getCalories();
            int foodId = foodsFromDB.get(i).getFoodId();

            Log.v("FOOD IDS: ", String.valueOf(foodId));

            //new instance of food//
            myFood = new Food();
            myFood.setFoodName(name);
            myFood.setRecordDate(dateText);
            myFood.setCalories(cals);
            myFood.setFoodId(foodId);

            //add myFood object to dbFoods arrayList//
            dbFoods.add(myFood);

        }
        dba.close();

        //setup adapter//
        //adaptor takes in Display Food and XML file we want to inflate - list_row & data//
        foodAdapter = new CustomListViewAdaptor(DisplayFoodActivity.this, R.layout.list_row, dbFoods);
        listView.setAdapter(foodAdapter);
        foodAdapter.notifyDataSetChanged();

    }

    //Show menu in display food//
    //takes menu view parameter//

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_main, menu);
        //we return true to tell android we have provided a menu//
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intentHomepage = new Intent(DisplayFoodActivity.this, Tracker.class);
        Intent intentDisplayFood = new Intent(DisplayFoodActivity.this, AddFoodActivity.class);

        switch (item.getItemId()) {

            case R.id.action_homepage: startActivity(intentHomepage);
                return true;

            case R.id.action_diary: startActivity(intentDisplayFood);
                return true;
        }

        return super.onOptionsItemSelected(item);

    }
}
