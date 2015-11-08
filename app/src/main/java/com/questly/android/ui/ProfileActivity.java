package com.questly.android.ui;

import com.questly.android.R;
import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Mark Z.");
        FloatingActionButton avatar = (FloatingActionButton) findViewById(R.id.avatar);
        Picasso.with(this).load(R.drawable.ic_face_white_48dp).into(avatar);
        CollapsingToolbarLayout layout = (CollapsingToolbarLayout) findViewById(
                R.id.toolbar_layout);
        layout.setContentScrimColor(getResources().getColor(R.color.questly_green));
        layout.setStatusBarScrimColor(getResources().getColor(R.color.questly_green));
    }
}
