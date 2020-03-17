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

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {
    EditText em,pa;
    TextView forgotpass;
    Button Login;
    TextView sign;
    ProgressBar probar;
    FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        em=findViewById(R.id.login_email);
        pa=findViewById(R.id.login_password);
        forgotpass=findViewById(R.id.login_forgot);
        Login=findViewById(R.id.login_button);
        sign=findViewById(R.id.login_signup);
        probar=findViewById(R.id.login_probar);

        fauth=FirebaseAuth.getInstance();

        if(fauth.getCurrentUser()!=null){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }


        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),forgotpass_activity.class));

            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignInActivity.class));
                finish();
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = em.getText().toString().trim();
                String pass = pa.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    em.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    pa.setError("Password is required");
                    return;
                }
                 probar.setVisibility(View.VISIBLE);


                fauth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            finish();
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Error!"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            probar.setVisibility(View.INVISIBLE);
                        }
                    }
                });

            }
        });

    }
}
