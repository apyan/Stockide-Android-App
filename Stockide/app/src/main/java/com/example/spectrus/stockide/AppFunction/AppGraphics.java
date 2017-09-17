package com.example.spectrus.stockide.AppFunction;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * This class is used for graphics aiding.
 */

public class AppGraphics {

    // Graphic Variables
    int screenHeight;
    int screenWidth;
    Context context;

    // Constructor
    public AppGraphics (Context eContext) {
        context = eContext;
        // Obtains the screen dimensions of width and height
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        screenHeight = metrics.heightPixels;
        screenWidth = metrics.widthPixels;
    }

    // Obtains the screen dimensions of width and height
    public void getScreenSize(){
        // Obtains the screen dimensions of width and height
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        screenHeight = metrics.heightPixels;
        screenWidth = metrics.widthPixels;
    }

    // For Popup Window Background Dimming
    public static void dimPopUpBackground(PopupWindow popupWindow) {
        View container;
        if (popupWindow.getBackground() == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                container = (View) popupWindow.getContentView().getParent();
            } else {
                container = popupWindow.getContentView();
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                container = (View) popupWindow.getContentView().getParent().getParent();
            } else {
                container = (View) popupWindow.getContentView().getParent();
            }
        }
        Context context = popupWindow.getContentView().getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.75f;
        wm.updateViewLayout(container, p);
    }

    public int getFullHeight(){
        return screenHeight;
    }
    public int getFullWidth(){
        return screenWidth;
    }

}
