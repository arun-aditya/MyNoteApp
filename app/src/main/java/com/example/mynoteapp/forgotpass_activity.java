package com.example.mynoteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotpass_activity extends AppCompatActivity {
    EditText ema;
    Button res;
    FirebaseAuth fauth;
    ProgressBar pbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpass_activity);

        ema=findViewById(R.id.passreset_email);
        res=findViewById(R.id.passreset_button);
        pbar=findViewById(R.id.passreset_probar);
        fauth=FirebaseAuth.getInstance();

        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email= ema.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    ema.setError("Email is required");
                    return;
                }
                pbar.setVisibility(View.VISIBLE);
                fauth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(forgotpass_activity.this, "Password reset link sent to email", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                            finish();
                        }
                        else{
                            Toast.makeText(forgotpass_activity.this, "Error!"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            pbar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });
    }
}
