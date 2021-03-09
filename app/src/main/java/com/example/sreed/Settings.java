package com.example.sreed;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.sreed.MyRequest.MyRequest;

public class Settings extends AppCompatActivity {

    private EditText NP, Mdp;
    private CheckBox Check;
    private Button Valider;
    private Session session;
    private RequestQueue queue;
    private MyRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        NP = findViewById(R.id.NP);
        Mdp = findViewById(R.id.mdp);
        Check = findViewById(R.id.Check);
        Valider = findViewById(R.id.btn);


        Check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    Mdp.setEnabled(false);
                }
                else
                {
                    Mdp.setEnabled(true);
                }
            }
        });

        queue = VolleySingleton.getInstance(this).getRequestQueue();
        request = new MyRequest(this, queue);

        session = new Session(this);
        final String Email = session.getEmail();

        Valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request.Settings(Email, NP.getText().toString(), Mdp.getText().toString(), Check.isChecked(), new MyRequest.SettingsCallBack() {
                    @Override
                    public void onSuccess(String message) {
                        Toast.makeText(Settings.this, "Validé", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(Settings.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        /*Intent intent = getIntent();
        Toast.makeText(this, intent.getStringExtra("post"), Toast.LENGTH_SHORT).show();

        Intent intent1 = new Intent(getApplicationContext(), Admin.class);
        intent1.putExtra("postt", "aaaaaa");
        startActivity(intent1);*/



    }
}
