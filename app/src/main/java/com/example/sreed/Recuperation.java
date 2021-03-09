package com.example.sreed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.sreed.MyRequest.MyRequest;

public class Recuperation extends AppCompatActivity {
    private EditText Email, Code;
    private Button Valider;
    private RequestQueue queue;
    private MyRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperation);
        Email = findViewById(R.id.Email);
        Code = findViewById(R.id.Code);
        Valider = findViewById(R.id.Valider);

        queue = VolleySingleton.getInstance(this).getRequestQueue();
        request = new MyRequest(this, queue);

        Valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Email.getText().toString().length() > 0)
                {
                    Code.setVisibility(View.VISIBLE);

                    request.Recuperation(Email.getText().toString(), Code.getText().toString(), new MyRequest.RecuperationCallBack() {
                        @Override
                        public void onSuccess(String message) {
                            Toast.makeText(Recuperation.this, message, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(String message) {
                            Toast.makeText(Recuperation.this, message, Toast.LENGTH_SHORT).show();
                        }
                    });

                    Valider.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            request.Recuperation(Email.getText().toString(), Code.getText().toString(), new MyRequest.RecuperationCallBack() {
                                @Override
                                public void onSuccess(String message) {
                                    Toast.makeText(Recuperation.this, message, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), NewPassword.class);
                                    intent.putExtra("Email", Email.getText().toString());
                                    startActivity(intent);
                                    finish();
                                }

                                @Override
                                public void onError(String message) {
                                    Toast.makeText(Recuperation.this, message, Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    });
                }
                else 
                {
                    Toast.makeText(Recuperation.this, "Indiquez votre Email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
