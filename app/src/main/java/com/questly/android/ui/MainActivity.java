package com.questly.android.ui;

import com.questly.android.R;
import com.questly.android.commons.BaseActivity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends BaseActivity {

    private CreateTaskDialogFragment createTaskDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_acticity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (createTaskDialogFragment == null) {
                    createTaskDialogFragment = new CreateTaskDialogFragment();
                }
                createTaskDialogFragment.show(getFragmentManager(),
                        CreateTaskDialogFragment.class.getSimpleName());
            }
        });
    }

}
