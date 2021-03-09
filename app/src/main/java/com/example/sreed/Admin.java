package com.example.sreed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.sreed.MyRequest.MyRequest;

import java.util.ArrayList;

public class Admin extends AppCompatActivity{

    private TextView tv;
    private Button btn, Logout, Settings;
    private Session session;
    private RequestQueue queue;
    private MyRequest request;
    private String Email;
    private EditText post;
    //private SwipeRefreshLayout Swipe;
    EditText ET;
    RecyclerView recyclerView;
    ArrayList<ModelFeed> modelFeedArrayList = new ArrayList<>();
    AdapterFeed adapterFeed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        btn = findViewById(R.id.btn);
        post = findViewById(R.id.ET);
        //Swipe = findViewById(R.id.Swipe);

        /*Swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Swipe.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Swipe.setRefreshing(false);
                    }

                }, 1000);
            }
        });*/

        ET = findViewById(R.id.ET);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapterFeed = new AdapterFeed(this, modelFeedArrayList);
        recyclerView.setAdapter(adapterFeed);

        //populateRecyclerView();

        session = new Session(this);

        queue = VolleySingleton.getInstance(this).getRequestQueue();
        request = new MyRequest(this, queue);

        if (session.isLogged())
        {
            Email = session.getEmail();
        }


                request.PostList(Email, new MyRequest.PostListCallBack() {
                    @Override
                    public void onSuccess(String Email, String Contenu, String Date) {
                        populateRecyclerView(Email, Contenu, Date);
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(Admin.this, message, Toast.LENGTH_SHORT).show();
                    }
                });




        /*Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.Logout();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Settings.class);
                startActivity(intent);

            }
        });*/

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request.Posts(Email, ET.getText().toString(), new MyRequest.PostCallBack() {
                    @Override
                    public void onSuccess(String message) {
                        Toast.makeText(Admin.this, message, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Admin.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(Admin.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.item1:
                Intent intent1 = new Intent(getApplicationContext(), Settings.class);
                startActivity(intent1);
                return true;
            case R.id.item2:
                session.Logout();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }



    public void populateRecyclerView(String Email, String Contenu, String Date) {

        ModelFeed modelFeed = new ModelFeed(2, Email, Date, Contenu, View.VISIBLE);
        modelFeedArrayList.add(modelFeed);

        adapterFeed.notifyDataSetChanged();

        /*Intent intent = getIntent();
        intent.getStringExtra("postt");*/


    }

}
