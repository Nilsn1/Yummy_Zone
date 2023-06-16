package com.nilscreation.yummyzone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nilscreation.yummyzone.Models.FoodModel;

public class DetailActivity extends AppCompatActivity {

    TextView title, price, description, qty;
    Button cartBtn;
    ImageView plusBtn, minusBtn, productImg;

    String mCategory, mtitle, mdescription, mimageUrl, mstatus;
    int qtyNumber = 1;
    int mprice, mdeliveryCharges, finalprice, orderId;

    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        price = findViewById(R.id.price);
        plusBtn = findViewById(R.id.plusBtn);
        minusBtn = findViewById(R.id.minusBtn);
        productImg = findViewById(R.id.productImg);
        qty = findViewById(R.id.qty);
        cartBtn = findViewById(R.id.cartBtn);

        Bundle bundle = getIntent().getExtras();
        mCategory = bundle.getString("Category");
        mtitle = bundle.getString("Title");
        mprice = bundle.getInt("Price", 0);
        mdescription = bundle.getString("Description");
        mimageUrl = bundle.getString("ImageUrl");
        mdeliveryCharges = bundle.getInt("Delivery");

        title.setText(mtitle);
        description.setText(mdescription);
        price.setText(String.valueOf(mprice));
        Glide.with(this).load(mimageUrl).into(productImg);

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qtyNumber = qtyNumber + 1;
                qty.setText(String.valueOf(qtyNumber));
                finalprice = mprice * qtyNumber;
                price.setText(String.valueOf(finalprice));
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qtyNumber > 1) {
                    qtyNumber = qtyNumber - 1;
                }
                qty.setText(String.valueOf(qtyNumber));
                finalprice = mprice * qtyNumber;
                price.setText(String.valueOf(finalprice));
            }
        });

        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                orderId = (int) System.currentTimeMillis();
                FoodModel foodModel = new FoodModel(mtitle, mCategory, mimageUrl, mprice, finalprice, mdeliveryCharges, qtyNumber);

                FirebaseUser firebaseUser = auth.getCurrentUser();
                if (firebaseUser != null) {
                    String userId = firebaseUser.getUid();
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User DB");
                    databaseReference.child(userId).child("Order Details").child("Cart").child(mtitle).setValue(foodModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(DetailActivity.this, "added to cart", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(DetailActivity.this, " " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

    }
}