package com.example.sreed;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.sreed.MyRequest.MyRequest;

public class Login extends AppCompatActivity {

    private TextInputLayout TIL_Email, TIL_Mdp;
    private Button Btn_Log, Btn_Reg;
    private TextView Recup;
    private RequestQueue queue;
    private MyRequest request;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TIL_Email = findViewById(R.id.email);
        TIL_Mdp = findViewById(R.id.mdp);
        Btn_Log = findViewById(R.id.btn_log);
        Btn_Reg = findViewById(R.id.Register);
        Recup = findViewById(R.id.Recup);
        session = new Session(this);

        queue = VolleySingleton.getInstance(this).getRequestQueue();
        request = new MyRequest(this, queue);

        final Intent intent = getIntent();
        if (intent.hasExtra("REGISTER"))
        {
            Toast.makeText(this, intent.getStringExtra("REGISTER"), Toast.LENGTH_SHORT).show();
        }

        if (session.isLogged())
        {
            Intent intent2 = new Intent(getApplicationContext(), Admin.class);
            startActivity(intent2);
            finish();
        }

        Btn_Log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = TIL_Email.getEditText().getText().toString().trim();
                String Mdp = TIL_Mdp.getEditText().getText().toString().trim();

                if (Email.length() > 0 && Mdp.length() > 0)
                {
                    request.Connection(Email, Mdp, new MyRequest.LoginCallBack() {
                        @Override
                        public void onSuccess(String Email) {
                            session.insertUser(Email);
                            Intent intent = new Intent(getApplicationContext(), Admin.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onError(String message) {
                            Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    Toast.makeText(Login.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Recup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Recuperation.class);
                startActivity(intent);
            }
        });

        Btn_Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Inscription.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
