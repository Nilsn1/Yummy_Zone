package com.nilscreation.yummyzone;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nilscreation.yummyzone.Models.OrderDetails;
import com.nilscreation.yummyzone.Models.UserDetail;

import java.util.ArrayList;

public class AccountFragment extends Fragment {

    ImageView userImage;
    TextView email;
    EditText username, mobile, address;
    Button btnSave;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser;
    String userId, userName, userMobile, userEmail, usertAddress;


    public AccountFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        userImage = view.findViewById(R.id.userImage);
//        userImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getContext(), AccountActivity.class));
//            }
//        });

        email = view.findViewById(R.id.email);
        username = view.findViewById(R.id.username);
        mobile = view.findViewById(R.id.mobile);
        address = view.findViewById(R.id.address);
        btnSave = view.findViewById(R.id.btnSave);

        firebaseUser = auth.getCurrentUser();
        if (firebaseUser != null) {
            userId = firebaseUser.getUid();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User DB");
            databaseReference.child(userId).child("User Details").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    String Username = snapshot.child("username").getValue(String.class);
                    String Mobile = snapshot.child("mobile").getValue(String.class);
                    String Address = snapshot.child("address").getValue(String.class);
                    String Email = snapshot.child("email").getValue(String.class);

                    email.setText(Email);
                    username.setText(Username);
                    mobile.setText(Mobile);
                    address.setText(Address);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = username.getText().toString();
                userMobile = mobile.getText().toString();
                usertAddress = address.getText().toString();
                userEmail = email.getText().toString();

                UserDetail userDetail = new UserDetail(userId, userName, userMobile, userEmail, usertAddress);

                firebaseUser = auth.getCurrentUser();
                if (firebaseUser != null) {
                    userId = firebaseUser.getUid();

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User DB");
                    databaseReference.child(userId).child("User Details").setValue(userDetail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Your Changes save Successfully", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getContext(), "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });


        return view;
    }
}