package home.firebaseapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class Register extends AppCompatActivity {

    EditText etEmail,etUsername,etPassword,etConfirmPassword;
    Button registerButton;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        etEmail = findViewById(R.id.emailEditText);
        etUsername = findViewById(R.id.usernameEditText);
        etPassword = findViewById(R.id.passwordEditText);
        etConfirmPassword = findViewById(R.id.confirmPasswordEditText);
        registerButton = findViewById(R.id.registerButton2);
        progressBar = findViewById(R.id.progressBarRegister);

        mAuth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

    }

    private void registerUser()
    {

        String email = etEmail.getText().toString();
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString();

        if (email.isEmpty())
        {
            Toast.makeText(Register.this,"Email is empty",Toast.LENGTH_LONG).show();
            etEmail.requestFocus();
            return;
        }

        if(password.isEmpty())
        {
            Toast.makeText(Register.this,"Password is empty",Toast.LENGTH_LONG).show();
            etPassword.requestFocus();
            return;
        }

        if(username.isEmpty())
        {
            Toast.makeText(Register.this,"Username is empty",Toast.LENGTH_LONG).show();
            etUsername.requestFocus();
            return;
        }

        if(password.length()<6)
        {
            Toast.makeText(Register.this,"Passowrd needs to be 6 or more characters",Toast.LENGTH_LONG).show();
            etPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(Register.this, "User register", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(Register.this,MainActivity.class));
                }
                else
                {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


    }
}
