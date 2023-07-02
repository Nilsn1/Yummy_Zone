package com.nilscreation.yummyzone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nilscreation.yummyzone.Adapters.OrderDetailAdapter;
import com.nilscreation.yummyzone.Models.FoodModel;

import java.util.ArrayList;

public class OrderDetailActivity extends AppCompatActivity {

    TextView orderId, orderTime, orderAddress, itemTotalPrice, deliveryCharges, totalCharges;

    RecyclerView recyclerviewCart;
    ArrayList<FoodModel> foodlist;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        orderId = findViewById(R.id.orderId);
        orderTime = findViewById(R.id.orderTime);
        orderAddress = findViewById(R.id.orderAddress);
        itemTotalPrice = findViewById(R.id.itemTotalPrice);
        deliveryCharges = findViewById(R.id.deliveryCharges);
        totalCharges = findViewById(R.id.totalCharges);

        recyclerviewCart = findViewById(R.id.recyclerviewCart);
        recyclerviewCart.setLayoutManager(new LinearLayoutManager(this));
        foodlist = new ArrayList<>();
        OrderDetailAdapter adapter = new OrderDetailAdapter(this, foodlist);
        recyclerviewCart.setAdapter(adapter);

        Bundle bundle = getIntent().getExtras();
        String OrderId = bundle.getString("orderId");
        String OrderTime = bundle.getString("orderTime");
        String OrderAddress = bundle.getString("orderAddress");
        int OrderItemtotal = bundle.getInt("itemTotal");
        int OrderDelivery = bundle.getInt("delivery");
        int OrderPrice = bundle.getInt("orderPrice");

        orderId.setText("Order Id: " + OrderId);
        orderTime.setText("Time: " + OrderTime);
        orderAddress.setText("Address: " + OrderAddress);
        itemTotalPrice.setText("" + OrderItemtotal);
        deliveryCharges.setText("" + OrderDelivery);
        totalCharges.setText("" + OrderPrice);

        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser != null) {
            String userId = firebaseUser.getUid();

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User DB");
            databaseReference.child(userId).child("Order Details").child("Completed").child(OrderId).child("products").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    foodlist.clear();

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        FoodModel foodModel = dataSnapshot.getValue(FoodModel.class);
                        foodlist.add(foodModel);
                    }
                    adapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(OrderDetailActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }

    }
}