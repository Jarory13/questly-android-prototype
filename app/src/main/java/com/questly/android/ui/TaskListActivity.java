package com.questly.android.ui;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.questly.android.Config;
import com.questly.android.R;
import com.questly.android.commons.BaseActivity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class TaskListActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        getFragmentManager().beginTransaction()
                .add(getContainerId(), TaskListFragment.newInstance()).commit();
    }

    public static class TaskListFragment extends Fragment {

        public static TaskListFragment newInstance() {
            return new TaskListFragment();
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            setHasOptionsMenu(true);
            View view = inflater.inflate(R.layout.fragment_task_list, container, false);
            Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(
                    true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Tasks List");
            ListView listView = (ListView) view.findViewById(R.id.listview);
            TaskAdapter adapter = new TaskAdapter();
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            Firebase task = new Firebase(Config.QUESTLY_FIREBASE_URL + "/quests");
            task.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Toast.makeText(getActivity(), dataSnapshot.toString(), Toast.LENGTH_SHORT)
                            .show();
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

            return view;
        }


        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    getActivity().finish();
                    break;
            }
            return true;
        }
    }

    public static class TaskAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
                convertView = layoutInflater
                        .inflate(R.layout.list_item_task, parent, false);
            }
            return convertView;
        }


    }


}
