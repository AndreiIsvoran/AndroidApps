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

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    EditText etEmail,etPassword,etConfirmPassword,etFirstName,etLastName;
    Button registerButton;
    private FirebaseAuth mAuth;
    Firebase fireBase;
    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        etFirstName = findViewById(R.id.firstName);
        etLastName = findViewById(R.id.lastName);
        etEmail = findViewById(R.id.emailEditText);
        etPassword = findViewById(R.id.passwordEditText);
        etConfirmPassword = findViewById(R.id.confirmPasswordEditText);
        registerButton = findViewById(R.id.registerButton2);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        fireBase = new Firebase("https://fir-tutorial-28089.firebaseio.com/");

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
        String password = etPassword.getText().toString();
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();

        writeNewUser(firstName,lastName);

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

        if(firstName.isEmpty())
        {
            Toast.makeText(Register.this,"First name is empty",Toast.LENGTH_LONG).show();
            etFirstName.requestFocus();
            return;
        }

        if(lastName.isEmpty())
        {
            Toast.makeText(Register.this,"Last name is empty",Toast.LENGTH_LONG).show();
            etLastName.requestFocus();
            return;
        }

        if(password.length()<6)
        {
            Toast.makeText(Register.this,"Password needs to be 6 or more characters",Toast.LENGTH_LONG).show();
            etPassword.requestFocus();
            return;
        }


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
    private void writeNewUser(String firstName, String lastName) {
        User user = new User(firstName,lastName);
        fireBase.push().setValue(user);
    }
}
