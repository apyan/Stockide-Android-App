package com.example.spectrus.stockide;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.spectrus.stockide.AppFunction.AppGraphics;

public class MenuFragmentScreen extends FragmentActivity {

    // Variables
    AppGraphics fragMGraphics;
    //AppStorage fileReader;
    public Context context;
    Button button_00, button_01, button_02;
    public ViewGroup.LayoutParams param_00;

    public long buttonClickTime = 0;
    public long TIME_THRESHOLD = 250;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_fragment_screen);

        // Set up graphic variables on the screen
        context = MenuFragmentScreen.this;
        fragMGraphics = new AppGraphics(context);

        button_00 = (Button)findViewById(R.id.button_00);
        button_01 = (Button)findViewById(R.id.button_01);
        button_02 = (Button)findViewById(R.id.button_02);

        String fontPath = "fonts/Titillium-Bold.ttf";
        //String fontPath = "fonts/Sansation-Bold.ttf";
        //Typeface font = Typeface.createFromAsset(getApplicationContext().getAssets(), fontPath);

        // Resizing buttons
        //int widthDivision = fragMGraphics.getFullWidth() / 4;

        // Button 0
        param_00 = button_00.getLayoutParams();
        //param_00.width = widthDivision;
        button_00.setLayoutParams(param_00);
        //button_00.setTypeface(font);
        button_00.setTextColor(Color.WHITE);
        // Button 1
        param_00 = button_01.getLayoutParams();
        //param_00.width = widthDivision;
        button_01.setLayoutParams(param_00);
        //button_01.setTypeface(font);
        button_01.setTextColor(Color.WHITE);
        // Button 2
        param_00 = button_02.getLayoutParams();
        //param_00.width = widthDivision;
        button_02.setLayoutParams(param_00);
        //button_02.setTypeface(font);
        button_02.setTextColor(Color.WHITE);

        // Stock Viewing Fragment as Default
        getFragmentManager().beginTransaction().add(R.id.fragment_place, new StockViewFragment()).commit();
    }

    // To Stock Viewing Fragment
    public void onStockViewing(View v) {
        // Prevent multi-clicking, threshold of 250 ms
        if (SystemClock.elapsedRealtime() - buttonClickTime < TIME_THRESHOLD){
            return;
        }
        buttonClickTime = SystemClock.elapsedRealtime();

        getFragmentManager().beginTransaction().replace(R.id.fragment_place, new StockViewFragment()).commit();
    }

    // Currency Converting Fragment
    public void onCurrencyConverter(View v) {
        // Prevent multi-clicking, threshold of 250 ms
        if (SystemClock.elapsedRealtime() - buttonClickTime < TIME_THRESHOLD){
            return;
        }
        buttonClickTime = SystemClock.elapsedRealtime();

        getFragmentManager().beginTransaction().replace(R.id.fragment_place, new CurrencyConvertFragment()).commit();
    }

    // To Options Fragment
    public void onOptions(View v) {
        // Prevent multi-clicking, threshold of 250 ms
        if (SystemClock.elapsedRealtime() - buttonClickTime < TIME_THRESHOLD){
            return;
        }
        buttonClickTime = SystemClock.elapsedRealtime();

        getFragmentManager().beginTransaction().replace(R.id.fragment_place, new OptionsFragment()).commit();
    }

}
