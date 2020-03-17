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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    EditText email,pass,confpass;
    Button reg;
    TextView log;
    ProgressBar progressBar;
    FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        email= findViewById(R.id.reg_email);
        pass=findViewById(R.id.reg_pass);
        confpass=findViewById(R.id.reg_confirmpass);
        reg=findViewById(R.id.reg_button);
        fauth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.probar);
        log=findViewById(R.id.reg_lo);

        if(fauth.getCurrentUser() !=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignInActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email=email.getText().toString().trim();
                String Pass=pass.getText().toString().trim();
                String copass=confpass.getText().toString().trim();

                if(TextUtils.isEmpty(Email)){
                    email.setError("Email is required");
                    return;
                }
                if(android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()== false){
                    email.setError("Not Valid email");
                    return;
                }
                if(TextUtils.isEmpty(Pass)){
                    pass.setError("Password is required");
                    return;
                }
                if(Pass.length()<6){
                    pass.setError("Password must contain more than 6 characters");
                    return;
                }


                progressBar.setVisibility(View.VISIBLE);

                fauth.createUserWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignInActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            finish();
                        }
                        else{
                            Toast.makeText(SignInActivity.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();}
                    }
                });



            }
        });



    }
}
