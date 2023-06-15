package com.nilscreation.yummyzone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nilscreation.yummyzone.Models.UserDetail;

public class SignupActivity extends AppCompatActivity {

    TextView signupUsername, signupEmail, signupMobile, signupPassword, signupPassword2, login;
    Button btnSignup;
    String userName, userEmail, userPassword, userPassword2, userMobile;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupUsername = findViewById(R.id.signupUsername);
        signupEmail = findViewById(R.id.signupEmail);
        signupMobile = findViewById(R.id.signupMobile);
        signupPassword = findViewById(R.id.signupPassword);
        signupPassword2 = findViewById(R.id.signupPassword2);
        login = findViewById(R.id.login);
        btnSignup = findViewById(R.id.btnSignup);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = signupUsername.getText().toString();
                userMobile = signupMobile.getText().toString();
                userEmail = signupEmail.getText().toString();
                userPassword = signupPassword.getText().toString();
                userPassword2 = signupPassword2.getText().toString();

                if (userName.isEmpty()) {
                    signupUsername.setError("Email Required");
                } else if (userMobile.isEmpty()) {
                    signupMobile.setError("Mobile Required");
                } else if (userEmail.isEmpty()) {
                    signupEmail.setError("Email Required");
                } else if (userPassword.isEmpty()) {
                    signupPassword.setError("Password Required");
                } else {
                    checkUser(userEmail);
                }
            }
        });
    }

    private void checkUser(String userEmail) {
        if (userEmail.isEmpty()) {
            signupEmail.setError("Email Required");
        } else {
            auth.fetchSignInMethodsForEmail(userEmail).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                @Override
                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                    if (task.isSuccessful()) {
                        boolean noUser = task.getResult().getSignInMethods().isEmpty();
                        if (noUser) {
                            signupNewUser();
                        } else {
                            Toast.makeText(SignupActivity.this, "Email is already Registered Please try another one", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignupActivity.this, " " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    private void signupNewUser() {

        auth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    Toast.makeText(SignupActivity.this, "Your account Created Successfully", Toast.LENGTH_SHORT).show();

                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    String userId = firebaseUser.getUid();

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User DB");
                    UserDetail userDetail = new UserDetail(userId, userName, userMobile, userEmail);
                    databaseReference.child(userId).setValue(userDetail);

                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(SignupActivity.this, " " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}