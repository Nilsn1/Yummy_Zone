package com.nilscreation.yummyzone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.nilscreation.yummyzone.Adapters.CartAdapter;
import com.nilscreation.yummyzone.Models.FoodModel;
import com.nilscreation.yummyzone.Models.OrderDetails;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CartActivity extends AppCompatActivity {

    TextView itemTotalPrice, deliveryCharges, totalCharges, txtAddress;
    ImageView btnAddress;
    RecyclerView recyclerviewCart;
    ArrayList<FoodModel> cartlist;
    CartAdapter cartAdapter;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    Button checkout;
    ArrayList<FoodModel> dataList;

    String mtitle, mimageUrl;
    int mprice, mdeliveryCharges, qtyNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        itemTotalPrice = findViewById(R.id.itemTotalPrice);
        deliveryCharges = findViewById(R.id.deliveryCharges);
        totalCharges = findViewById(R.id.totalCharges);
        checkout = findViewById(R.id.checkout);
        txtAddress = findViewById(R.id.txtAddress);
        btnAddress = findViewById(R.id.btnAddress);

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        CartFragment cartFragment = new CartFragment();
//        fragmentTransaction.add(R.id.container, cartFragment);
//        fragmentTransaction.commit();

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

        btnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String address = txtAddress.getText().toString();

                DateFormat df = new SimpleDateFormat("dd/MM/yyyy, HH:mm");
                String date = df.format(Calendar.getInstance().getTime());

                if (txtAddress.getText().toString().isEmpty()) {
                    txtAddress.setError("Address Cannot be empty");
                } else {

                    int totalPrice = 0;
                    int orderPrice = 0;
                    FoodModel foodModel;
                    String orderId = String.valueOf(System.currentTimeMillis());

                    for (FoodModel product : dataList) {
                        mtitle = product.getTitle();
                        mprice = product.getPrice();
                        mimageUrl = product.getImageUrl();
                        qtyNumber = product.getQty();

                        totalPrice += product.getPrice() * product.getQty();
                        mdeliveryCharges = product.getDeliveryCharges();
                        orderPrice = totalPrice + mdeliveryCharges;

                        foodModel = new FoodModel(mtitle, mimageUrl, mprice, mdeliveryCharges, qtyNumber);

                        OrderDetails orderDetails = new OrderDetails(orderId, totalPrice, mdeliveryCharges, orderPrice, address, date);

                        FirebaseUser firebaseUser = auth.getCurrentUser();
                        if (firebaseUser != null) {
                            String userId = firebaseUser.getUid();
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User DB");
                            databaseReference.child(userId).child("Order Details").child("Completed").child(orderId).child("Food").child(mtitle).setValue(foodModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(CartActivity.this, "Your Order has been Placed Successfully", Toast.LENGTH_SHORT).show();

                                        DatabaseReference deleteCart = FirebaseDatabase.getInstance().getReference("User DB");
                                        deleteCart.child(userId).child("Order Details").child("Cart").removeValue();

                                    } else {
                                        Toast.makeText(CartActivity.this, " " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("User DB");
                            databaseReference2.child(userId).child("Order Details").child("Completed").child(orderId).setValue(orderDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
//                                    Toast.makeText(CartActivity.this, "Added to cart", Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(CartActivity.this, " " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }

                }

            }
        });
    }

//    private void setData() {
//        ArrayList<FoodModel> foodlist = cartAdapter.getData();
//        int totalPrice = 0;
//        int mdeliveryCharges = 0;
//
//        for (FoodModel product : foodlist) {
//            totalPrice += product.getPrice() * product.getQty();
//            mdeliveryCharges = product.getDeliveryCharges();
//            Toast.makeText(CartActivity.this, "" + product.getFinalPrice(), Toast.LENGTH_SHORT).show();
//
//            itemTotalPrice.setText(String.valueOf(totalPrice));
//            deliveryCharges.setText(String.valueOf(mdeliveryCharges));
//            int mTotalCharges = totalPrice + mdeliveryCharges;
//            totalCharges.setText(String.valueOf(mTotalCharges));
//
//        }
//    }

    public void receiveDataFromAdapter(ArrayList<FoodModel> dataList) {

        int totalPrice = 0;
        int orderPrice;

        this.dataList = dataList;

        for (FoodModel product : dataList) {
            totalPrice += product.getPrice() * product.getQty();
            mdeliveryCharges = product.getDeliveryCharges();
//            Toast.makeText(CartActivity.this, "" + product.getPrice(), Toast.LENGTH_SHORT).show();

            itemTotalPrice.setText(String.valueOf(totalPrice));
            deliveryCharges.setText(String.valueOf(mdeliveryCharges));
            orderPrice = totalPrice + mdeliveryCharges;
            totalCharges.setText(String.valueOf(orderPrice));

        }
    }

//    public void totalPrice(int mItemTotalPrice, int mdeliveryCharges) {
//
//        itemTotalPrice.setText(String.valueOf(mItemTotalPrice));
//        deliveryCharges.setText(String.valueOf(mdeliveryCharges));
//        int mTotalCharges = mItemTotalPrice + mdeliveryCharges;
//        totalCharges.setText(String.valueOf(mTotalCharges));
//    }

}