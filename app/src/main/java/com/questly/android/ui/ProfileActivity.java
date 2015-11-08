package com.questly.android.ui;

import com.parse.ParseUser;
import com.questly.android.R;
import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView textView = (TextView) findViewById(R.id.user_name_textview);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Qwerty123");
        textView.setText("Qwerty123");
        FloatingActionButton avatar = (FloatingActionButton) findViewById(R.id.avatar);
        Picasso.with(this).load(R.drawable.ic_face_white_48dp).into(avatar);
        CollapsingToolbarLayout layout = (CollapsingToolbarLayout) findViewById(
                R.id.toolbar_layout);
        layout.setContentScrimColor(getResources().getColor(R.color.questly_green));
        layout.setStatusBarScrimColor(getResources().getColor(R.color.questly_green));
    }
}
