package com.example.sreed;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.sreed.MyRequest.MyRequest;

import java.util.Map;

public class Inscription extends AppCompatActivity {

    private TextInputLayout TIL_Email, TIL_Mdp, TIL_Cmdp;
    private Button Btn_Reg;
    private RequestQueue queue;
    private MyRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        TIL_Email = findViewById(R.id.email);
        TIL_Mdp = findViewById(R.id.mdp);
        TIL_Cmdp = findViewById(R.id.cmdp);
        Btn_Reg = findViewById(R.id.btn_register);
        
        queue = VolleySingleton.getInstance(this).getRequestQueue();
        request = new MyRequest(this, queue);
        
        Btn_Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = TIL_Email.getEditText().getText().toString().trim();
                String Mdp = TIL_Mdp.getEditText().getText().toString().trim();
                String Cmdp = TIL_Cmdp.getEditText().getText().toString().trim();
                
                if (Email.length() > 0 && Mdp.length() > 0 && Cmdp.length() > 0)
                {
                    request.register(Email, Mdp, Cmdp, new MyRequest.RegisterCallBack() {
                        @Override
                        public void onSuccess(String message) {
                            Intent intent = new Intent(getApplicationContext(), Login.class);
                            intent.putExtra("REGISTER", message);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void inputErrors(Map<String, String> errors) {
                            if (errors.get("Email") != null)
                            {
                                TIL_Email.setError(errors.get("Email"));
                            }
                            else 
                            {
                                TIL_Email.setErrorEnabled(false);
                            }
                            if (errors.get("Mdp") != null)
                            {
                                TIL_Mdp.setError(errors.get("Mdp"));
                            }
                            else
                            {
                                TIL_Mdp.setErrorEnabled(false);
                            }
                        }

                        @Override
                        public void onError(String message) {
                            Toast.makeText(Inscription.this, message, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    Toast.makeText(Inscription.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
