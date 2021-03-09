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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.sreed.MyRequest.MyRequest;

import java.util.ArrayList;

public class ComptePublic extends AppCompatActivity {

    private MyRequest request;
    private RequestQueue queue;
    private Session session;
    private Button Logout;
    private String Pseudo;
    RecyclerView recyclerView;
    ArrayList<ModelFeed> modelFeedArrayList = new ArrayList<>();
    AdapterFeed adapterFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte_public);

        recyclerView = findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapterFeed = new AdapterFeed(this, modelFeedArrayList);
        recyclerView.setAdapter(adapterFeed);

        queue = VolleySingleton.getInstance(this).getRequestQueue();
        request = new MyRequest(this, queue);

        session = new Session(this);

        Pseudo = session.getPseudo();

        request.PostListP(Pseudo, new MyRequest.PostListPCallBack() {
            @Override
            public void onSuccess(String Contenu, String Date) {
                populateRecyclerView(Pseudo, Contenu, Date);
            }

            @Override
            public void onError(String message) {
                Toast.makeText(ComptePublic.this, message, Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.item:
                session.Logout();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void populateRecyclerView(String Pseudo, String Contenu, String Date) {

        ModelFeed modelFeed = new ModelFeed(2, Pseudo, Date, Contenu, View.GONE);
        modelFeedArrayList.add(modelFeed);

        adapterFeed.notifyDataSetChanged();
    }

}