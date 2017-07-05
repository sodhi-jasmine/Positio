package com.jasminesodhi.positio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(getApplicationContext(), ApiCallService.class));

        Intent intent = new Intent(this, LandingActivity.class);
        startActivity(intent);
    }
}