package com.dennismwebia.angar.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;

import com.dennismwebia.angar.R;

/**
 * Created by dennis on 2/22/18.
 */

public class Utils {
    private Activity context;
    private AppCompatActivity appCompatActivity;

    public Utils(Activity activity, AppCompatActivity appCompatActivity) {
        this.context = activity;
        this.appCompatActivity = appCompatActivity;
    }

    private void centerToolbarTitle(@NonNull final Toolbar toolbar) {
        final CharSequence title = toolbar.getTitle();
        final ArrayList<View> outViews = new ArrayList<>(1);
        toolbar.findViewsWithText(outViews, title, View.FIND_VIEWS_WITH_TEXT);
        if (!outViews.isEmpty()) {
            final TextView titleView = (TextView) outViews.get(0);
            titleView.setGravity(Gravity.LEFT);
            titleView.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf"));
            final Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) titleView.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            toolbar.requestLayout();
        }
    }

    public void initToolbar(Toolbar toolbar, String title, final Class<?> destination_class){
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(context.getResources().getDrawable(R.drawable.ic_keyboard_arrow_left_white_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, destination_class);
                context.startActivity(intent);
                context.finish();
            }
        });
        centerToolbarTitle(toolbar);
        appCompatActivity.setSupportActionBar(toolbar);
    }

    public void startActivity(Activity activity, Class<?> destination_class) {
        Intent intent = new Intent(activity, destination_class);
        activity.startActivity(intent);
    }

    public void hideKeyPad(){
        context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /**
     * Show SnackBar
     */
    public void showSnackBar(View container, final String text) {
        Snackbar snackbar = Snackbar.make(container, text, Snackbar.LENGTH_LONG);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Bold.ttf");
        textView.setTextColor(context.getResources().getColor(R.color.colorWhite));
        textView.setTypeface(typeface);
        snackbar.show();
    }

    public void showErrorToast(String message) {
        StyleableToast styleableToast = new StyleableToast
                .Builder(context)
                .duration(Toast.LENGTH_LONG)
                .text(message)
                .textColor(Color.WHITE)
                .typeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf"))
                .backgroundColor(Color.RED)
                .build();

        if (styleableToast != null) {
            styleableToast.show();
            styleableToast = null;
        }
    }
}
