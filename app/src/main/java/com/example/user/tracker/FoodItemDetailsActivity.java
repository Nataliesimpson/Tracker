package com.example.user.tracker;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import data.*;
import model.*;

/**
 * Created by user on 24/08/2016.
 */
public class FoodItemDetailsActivity extends AppCompatActivity {

    private TextView foodName, calories, dateTaken;
    private int foodId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_item_details);

        foodName = (TextView) findViewById(R.id.detsFoodName);
        calories = (TextView) findViewById(R.id.detsCaloriesValue);
        dateTaken = (TextView) findViewById(R.id.detsDateText);

        //has serializable food object - all details//
        //pass in key "userObj" from CustomListViewAdaptor//
        Food food = (Food) getIntent().getSerializableExtra("userObj");

        foodName.setText(food.getFoodName());
        calories.setText(String.valueOf(food.getCalories()));
        dateTaken.setText(food.getRecordDate());

        //getting food id so we are able to delete it//
        foodId = food.getFoodId();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar//
        getMenuInflater().inflate(R.menu.menu_food_item_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       //get item id so it can be deleted//
        int id = item.getItemId();

        //refers to deleteItem from menu//
        if (id == R.id.deleteItem) {

            AlertDialog.Builder alert = new AlertDialog.Builder(FoodItemDetailsActivity.this);
            alert.setTitle("Delete?");
            alert.setMessage("Are you sure you want to delete this item?");
            alert.setNegativeButton("No", null);
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    DatabaseHandler dba = new DatabaseHandler(getApplicationContext());
                    dba.deleteFood(foodId);

                    Toast.makeText(getApplicationContext(), "Food Item Deleted!", Toast.LENGTH_LONG)
                            .show();

                    startActivity(new Intent(FoodItemDetailsActivity.this, DisplayFoodActivity.class));

                    //remove this activity from activity stack//
                    FoodItemDetailsActivity.this.finish();
                }
            });
            alert.show();
        }

        return super.onOptionsItemSelected(item);
    }
}












