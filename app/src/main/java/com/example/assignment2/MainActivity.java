package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    EditText searchView;
    ImageView crossIcon;

    ArrayList<Restaurant> list = new ArrayList<>();
    ResturantAdapter adapter;

    private Integer REQUEST_CODE = 130;
    private String SHARED_PREFS_FILE_NAME = "restaurants";
    private String STRING_KEY = "restaurants_key";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get String List from Shared Preferences
        String jsonString = getString();

        if(jsonString != null) {
            // Convert String List to ArrayList
            Gson gson = new Gson();
            list = gson.fromJson(jsonString, new TypeToken<ArrayList<Restaurant>>() {}.getType());
        }

        searchView = findViewById(R.id.searchView);

        // Search When TextField Changes
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newText = s.toString();

                ArrayList<Restaurant> searchResults = new ArrayList<>();

                for (Restaurant restaurant : list) {
                    if (restaurant.getName().toLowerCase().contains(newText.toLowerCase())) {
                        searchResults.add(restaurant);
                    }
                }

                adapter.restaurants = searchResults;
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        crossIcon = findViewById(R.id.cross);

        // Clear and Set List
        crossIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setText("");

                adapter.restaurants = list;
                adapter.notifyDataSetChanged();
            }
        });

        TextView add = findViewById(R.id.add);

        // Move to Restaurant Form
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                startActivityForResult(intent, REQUEST_CODE);   // Set Request Code
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        // Set List Layout
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setHasFixedSize(true);

        // Create and Set Adapter with List
        adapter = new ResturantAdapter(list);
        recyclerView.setAdapter(adapter);
    }

    // Called When we have data from intent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check Request Code
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            // Get Intent Values
            String name = data.getStringExtra("name");
            String location = data.getStringExtra("location");
            String phone = data.getStringExtra("phone");
            String description = data.getStringExtra("description");
            String rating = data.getStringExtra("rating");

            // Create Restaurant
            Restaurant restaurant = new Restaurant(name, location, phone, description, Double.parseDouble(rating));

            list.add(restaurant);

            // Sort by rating
            list.sort((r1, r2) -> {
                // Compare ratings
                return Double.compare(r2.getRating(), r1.getRating()); // Descending order
            });

            adapter.notifyDataSetChanged();

            Gson gson = new Gson();
            // Convert model to string
            String jsonString = gson.toJson(list);

            // Save String in Shared Preferences
            saveString(jsonString);
        }
    }

    public void saveString(String stringValue) {
        SharedPreferences sharedPreferences = this.getSharedPreferences(SHARED_PREFS_FILE_NAME, this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(STRING_KEY, stringValue);
        editor.apply();
    }

    public String getString() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(SHARED_PREFS_FILE_NAME, this.MODE_PRIVATE);
        return sharedPreferences.getString(STRING_KEY, null);
    }
}