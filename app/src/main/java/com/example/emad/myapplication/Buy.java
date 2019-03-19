package com.example.emad.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by EMAD on 3/10/2018.
 */

public class Buy extends AppCompatActivity {
Button btby;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        btby= (Button) findViewById(R.id.submit);
        btby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(Buy.this,"You exactly ordered this product Successfully",Toast.LENGTH_LONG).show();

            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent in =new Intent(Buy.this,NewsAcc.class);
        startActivity(in);

    }
}
