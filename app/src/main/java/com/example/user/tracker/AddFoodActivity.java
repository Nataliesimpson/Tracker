package com.example.user.tracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import data.DatabaseHandler;
import model.Food;

//intents sit between both the OS and activities//
// giving the OS actions to do and giving back information to activities//
// but also sit in between activities passing around information//
// here we are using an intent to tell the ActivityManager which activity we want to start//

/**
 * Created by user on 20/08/2016.
 */
public class AddFoodActivity extends AppCompatActivity {

    EditText foodName, foodCals;
    Button mSubmitButton;

    private DatabaseHandler dba;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Tracker:", "AddFoodActivity.onCreateCalled");
        setContentView(R.layout.activity_add_food);

        dba = new DatabaseHandler(AddFoodActivity.this);

        foodName = (EditText)findViewById(R.id.foodEditText);
        foodCals = (EditText)findViewById(R.id.caloriesEditText);
        mSubmitButton = (Button)findViewById(R.id.submitButton);

        Intent intent = getIntent();

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveDataToDB();
            }
        });
    }

    //saving user input to db//
    private void saveDataToDB(){

        Food food = new Food();
        String name = foodName.getText().toString().trim();
        String calsString = foodCals.getText().toString().trim();
        int cals = Integer.parseInt(calsString);

        if (name.equals("") || calsString.equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter the food you want to add", Toast.LENGTH_SHORT).show();
        }else{
            food.setFoodName(name);
            food.setCalories(cals);

            dba.addFood(food);
            dba.close();

            //clear the form for next session//
            foodName.setText("");
            foodCals.setText("");

            //take users to next screen (display all entered items)//
            startActivity(new Intent(AddFoodActivity.this, DisplayFoodActivity.class));

        }
    }

    //Show menu in add food//
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
        Intent intentHomepage = new Intent(AddFoodActivity.this, Tracker.class);
        Intent intentDisplayFood = new Intent(AddFoodActivity.this, DisplayFoodActivity.class);

        switch (item.getItemId()) {

            case R.id.action_homepage: startActivity(intentHomepage);
                return true;

            case R.id.action_diary: startActivity(intentDisplayFood);
                return true;
        }

        return super.onOptionsItemSelected(item);

    }

}





















