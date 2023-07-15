package com.nilscreation.yummyzone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    Button checkout, btnAddNow;
    ArrayList<FoodModel> dataList;
    String mtitle, mCategory, mimageUrl;
    int mprice, singleItemTotalPrice, mdeliveryCharges, qtyNumber;
    LinearLayout cartlayout, emptyCart;

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
        cartlayout = findViewById(R.id.cartlayout);
        emptyCart = findViewById(R.id.emptyCart);
        btnAddNow = findViewById(R.id.btnAdd);

        btnAddNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, FoodListActivity.class);
                intent.putExtra("Query", "");
                startActivity(intent);
//                finish();
            }
        });

        recyclerviewCart = findViewById(R.id.recyclerviewCart);
        recyclerviewCart.setLayoutManager(new LinearLayoutManager(this));
        cartlist = new ArrayList<>();

        cartAdapter = new CartAdapter(CartActivity.this, cartlist);
        recyclerviewCart.setAdapter(cartAdapter);

        btnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(CartActivity.this, "Getting Your Address", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CartActivity.this, MapsActivity.class));

            }
        });

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String address = txtAddress.getText().toString();

                OrderDetails orderDetails = null;
                ArrayList<FoodModel> myDatalist = new ArrayList<>();

                DateFormat df = new SimpleDateFormat("dd/MM/yyyy, HH:mm");
                String date = df.format(Calendar.getInstance().getTime());

                if (txtAddress.getText().toString().isEmpty()) {
                    txtAddress.setError("Address Cannot be empty");
                } else {

                    int totalPrice = 0;
                    int orderPrice = 0;
                    String orderId = String.valueOf(System.currentTimeMillis());

                    for (FoodModel product : dataList) {
                        mtitle = product.getTitle();
                        mCategory = product.getCategory();
                        mimageUrl = product.getImageUrl();
                        mprice = product.getPrice();
                        mdeliveryCharges = product.getDeliveryCharges();
                        qtyNumber = product.getQty();
                        singleItemTotalPrice = mprice * qtyNumber;
//                        Toast.makeText(CartActivity.this, "" + singleItemTotalPrice, Toast.LENGTH_SHORT).show();

                        totalPrice += product.getPrice() * product.getQty();
                        orderPrice = totalPrice + mdeliveryCharges;

                        myDatalist.add(new FoodModel(mtitle, mCategory, mimageUrl, mprice, singleItemTotalPrice, mdeliveryCharges, qtyNumber));

                        orderDetails = new OrderDetails(orderId, totalPrice, mdeliveryCharges, orderPrice, address, date, myDatalist);

                    }

                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    if (firebaseUser != null) {
                        String userId = firebaseUser.getUid();

                        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("User DB");
                        databaseReference2.child(userId).child("Order Details").child("Completed").child(orderId).setValue(orderDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
//                                    Toast.makeText(CartActivity.this, "Your Order has been Placed Successfully", Toast.LENGTH_SHORT).show();

                                    Dialog dialog = new Dialog(CartActivity.this);
                                    dialog.setContentView(R.layout.order_successful);
                                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                                    ImageView imageView = dialog.findViewById(R.id.imageView);
                                    Animation alpha = AnimationUtils.loadAnimation(CartActivity.this, R.anim.alpha);
                                    imageView.startAnimation(alpha);

                                    Button btn = dialog.findViewById(R.id.btn);
                                    btn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            dialog.dismiss();
                                        }
                                    });
                                    dialog.show();

                                    DatabaseReference deleteCart = FirebaseDatabase.getInstance().getReference("User DB");
                                    deleteCart.child(userId).child("Order Details").child("Cart").removeValue();

                                } else {
                                    Toast.makeText(CartActivity.this, " " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                }

            }
        });
    }

    public void receiveDataFromAdapter(ArrayList<FoodModel> dataList) {

        int totalPrice = 0;
        int orderPrice;

        this.dataList = dataList;

        for (FoodModel product : dataList) {
            totalPrice += product.getPrice() * product.getQty();
            mdeliveryCharges = product.getDeliveryCharges();

            itemTotalPrice.setText(String.valueOf(totalPrice));
            deliveryCharges.setText(String.valueOf(mdeliveryCharges));
            orderPrice = totalPrice + mdeliveryCharges;
            totalCharges.setText(String.valueOf(orderPrice));

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    public void loadData() {
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser != null) {
            String userId = firebaseUser.getUid();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User DB");
            databaseReference.child(userId).child("Order Details").child("Cart").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    cartlist.clear();

                    // Check if the data exists
                    if (snapshot.exists()) {
                        cartlayout.setVisibility(View.VISIBLE);
                        emptyCart.setVisibility(View.GONE);

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            FoodModel model = dataSnapshot.getValue(FoodModel.class);
                            cartlist.add(model);
                        }
                        cartAdapter.notifyDataSetChanged();

                    } else {

                        emptyCart.setVisibility(View.VISIBLE);
                        cartlayout.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            DatabaseReference addressReference = FirebaseDatabase.getInstance().getReference("User DB");
            addressReference.child(userId).child("User Details").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String address = snapshot.child("address").getValue(String.class);
                    txtAddress.setText(address);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }
}