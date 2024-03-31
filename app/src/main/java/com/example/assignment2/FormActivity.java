package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;

public class FormActivity extends AppCompatActivity {

    EditText name;
    EditText location;
    EditText phone;
    EditText description;
    EditText rating;

    Button save;
    Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        name = findViewById(R.id.name);
        location = findViewById(R.id.location);
        phone = findViewById(R.id.phone);
        description = findViewById(R.id.description);
        rating = findViewById(R.id.rating);

        save = findViewById(R.id.save);
        cancel = findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass data back using intent
                Intent resultIntent = new Intent();
                resultIntent.putExtra("name", name.getText().toString());
                resultIntent.putExtra("location", location.getText().toString());
                resultIntent.putExtra("phone", phone.getText().toString());
                resultIntent.putExtra("description", description.getText().toString());
                resultIntent.putExtra("rating", rating.getText().toString());
                setResult(RESULT_OK, resultIntent);

                finish();
            }
        });
    }
}