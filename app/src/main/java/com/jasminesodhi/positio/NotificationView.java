package com.jasminesodhi.positio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by jasminesodhi on 15/06/17.
 */

public class NotificationView extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
