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

public class NewPassword extends AppCompatActivity {

    private EditText ET_Mdp, ET_Cmdp;
    private Button Valider;
    private RequestQueue queue;
    private MyRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        ET_Mdp = findViewById(R.id.Mdp);
        ET_Cmdp = findViewById(R.id.Cmdp);
        Valider = findViewById(R.id.Valider);

        queue = VolleySingleton.getInstance(this).getRequestQueue();
        request = new MyRequest(this, queue);

        Valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Mdp = ET_Mdp.getText().toString();
                String Cmdp = ET_Cmdp.getText().toString();

                    Intent intent = getIntent();
                    String Email = intent.getStringExtra("Email");
                    request.NewPassword(Email, Mdp, Cmdp, new MyRequest.NewPassCallBack() {
                        @Override
                        public void onSuccess(String message) {
                            Toast.makeText(NewPassword.this, "Votre mdp a été modifié avec succèes", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Login.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onError(String message) {
                            Toast.makeText(NewPassword.this, message, Toast.LENGTH_SHORT).show();
                        }
                    });



            }
        });


    }
}
