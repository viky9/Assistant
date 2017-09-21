package com.example.prashant.assistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MapPoint extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_point);
        textView=(TextView)findViewById(R.id.textView);
        Intent intent = getIntent();

        String fName = intent.getStringExtra("items");
        textView.setText(fName);
    }
}
