package com.example.aditya.crud_sqlite;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by aditya on 31-05-2017.
 */

public class UsersListActivity extends AppCompatActivity {

    private AppCompatActivity activity = UsersListActivity.this;
    private AppCompatTextView textViewName;
    private RecyclerView recyclerViewUsers;
    private ArrayList<Student> listUsers;
    //private UsersRecyclerAdapter usersRecyclerAdapter;
    private DatabaseHelper databaseHelper;
    ListView lv;
    String emailFromIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        getSupportActionBar().setTitle("");
        initViews();
        initObjects();

    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        textViewName = (AppCompatTextView) findViewById(R.id.textViewName);
        //recyclerViewUsers = (RecyclerView) findViewById(R.id.recyclerViewUsers);
        lv = (ListView)findViewById(R.id.list_view);

    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        listUsers = new ArrayList<>();
        //usersRecyclerAdapter = new UsersRecyclerAdapter(listUsers);

//    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//        recyclerViewUsers.setLayoutManager(mLayoutManager);
//        recyclerViewUsers.setItemAnimator(new DefaultItemAnimator());
//        recyclerViewUsers.setHasFixedSize(true);
//        recyclerViewUsers.setAdapter(usersRecyclerAdapter);

        databaseHelper = new DatabaseHelper(activity);

        emailFromIntent = getIntent().getStringExtra("EMAIL");
        textViewName.setText(emailFromIntent);

        getDataFromSQLite();
    }

    /**
     * This method is to fetch all user records from SQLite
     */
    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listUsers.clear();
                listUsers.addAll(databaseHelper.getAllUser(emailFromIntent));
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
               // usersRecyclerAdapter.notifyDataSetChanged();
                CustomAdapter adapter = new CustomAdapter(getBaseContext(),listUsers);
                lv.setAdapter(adapter);
            }
        }.execute();
    }
}
