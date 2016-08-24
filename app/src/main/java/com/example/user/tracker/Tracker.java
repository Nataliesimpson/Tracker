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
import android.widget.Toast;

/**
 * Created by user on 20/08/2016.
 */
public class Tracker extends AppCompatActivity {

    //add member variables for each of the controls using the ID//
    //m stands for members: private, non-static variables//
    Button mGetStartedButton;

    @Override
    //Bundle savedInstanceState is used to restore the state of the app//
    //if it had been running before, then shut down//
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the content view to what's inside our activity_main//
        //R class gives us access to our resources//
        setContentView(R.layout.activity_main);

        //this method takes a resource id & returns a view object//
        mGetStartedButton = (Button) findViewById(R.id.get_started);

        mGetStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Tracker:", "Get started button clicked!");

                //class: argument specifies the activity class ActivityManager should start//
                //context: argument tells the Activity Manager which app package the activity class can be found in//
                Intent intent = new Intent(Tracker.this, AddFoodActivity.class);
                startActivity(intent);
            }
        });
    }

    //Show menu in Tracker activity//
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
        Intent intentAddFood = new Intent(Tracker.this, AddFoodActivity.class);
        Intent intentDisplayFood = new Intent(Tracker.this, DisplayFoodActivity.class);

        switch (item.getItemId()) {

            case R.id.action_addFood: startActivity(intentAddFood);
                return true;

            case R.id.action_diary: startActivity(intentDisplayFood);
                return true;
        }

        return super.onOptionsItemSelected(item);

    }

}
