package com.example.bioticclasses.actionbar;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bioticclasses.R;

public class CustomActionBar {

    public CustomActionBar(Activity activity, String s) {
        ((AppCompatActivity) activity).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((AppCompatActivity) activity).getSupportActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater inflator = LayoutInflater.from(activity);
        View v = inflator.inflate(R.layout.actionbar_layout, null);
        ((TextView) v.findViewById(R.id.title)).setText(s);
        ((AppCompatActivity) activity).getSupportActionBar().setCustomView(v);
        ((AppCompatActivity) activity).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) activity).getSupportActionBar().show();
    }
}
