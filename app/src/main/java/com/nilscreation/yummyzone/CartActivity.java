package com.nilscreation.yummyzone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nilscreation.yummyzone.Adapters.CartAdapter;
import com.nilscreation.yummyzone.Models.FoodModel;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerviewCart;
    ArrayList<FoodModel> cartlist;
    CartAdapter cartAdapter;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerviewCart = findViewById(R.id.recyclerviewCart);
        recyclerviewCart.setLayoutManager(new LinearLayoutManager(this));
        cartlist = new ArrayList<>();

        cartAdapter = new CartAdapter(CartActivity.this, cartlist);
        recyclerviewCart.setAdapter(cartAdapter);

        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser != null) {
            String userId = firebaseUser.getUid();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User DB");
            databaseReference.child(userId).child("Order Details").child("Cart").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    cartlist.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        FoodModel model = dataSnapshot.getValue(FoodModel.class);
                        cartlist.add(model);
                    }
                    cartAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


    }
}