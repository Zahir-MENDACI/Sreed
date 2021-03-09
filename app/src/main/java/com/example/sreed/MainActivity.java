package com.example.sreed;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.sreed.MyRequest.MyRequest;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout Pseudo, Mdp;
    private Button Login, Register, AdminLog;
    private Session session;
    private RequestQueue queue;
    private MyRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue = VolleySingleton.getInstance(this).getRequestQueue();
        request = new MyRequest(this, queue);

        session = new Session(this);
        if (session.isLogged())
        {
            if(session.getEmail() != null)
            {
                Intent intent = new Intent(getApplicationContext(), Admin.class);
                startActivity(intent);
                finish();
            }
            else
            {
                Intent intent = new Intent(getApplicationContext(), ComptePublic.class);
                startActivity(intent);
                finish();
            }

        }

        Pseudo = findViewById(R.id.pseudo);
        Mdp = findViewById(R.id.mdp);
        Login = findViewById(R.id.login);


        /*final String PseudoText = Pseudo.getEditText().getText().toString().trim();
        final String MdpText = Mdp.getEditText().getText().toString().trim();*/

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request.ConnexionP(Pseudo.getEditText().getText().toString().trim(), Mdp.getEditText().getText().toString().trim(), new MyRequest.LoginPCallBack() {
                    @Override
                    public void onSuccess(String message) {
                        session.inserPseudo(Pseudo.getEditText().getText().toString().trim());
                        Intent intent = new Intent(getApplicationContext(), ComptePublic.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.item:
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
