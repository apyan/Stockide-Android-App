package com.example.spectrus.stockide;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TitleScreen extends Activity {

    // Variable needed for Activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_screen);
    }

    // Proceed to the Menu Fragment Screen
    public void onProceed(View v){
        Intent eIntent = new Intent(this, MenuFragmentScreen.class);
        startActivity(eIntent);
    }
}
