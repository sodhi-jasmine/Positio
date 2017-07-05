package com.jasminesodhi.positio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by jasminesodhi on 30/06/17.
 */

public class LandingActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_landing);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}