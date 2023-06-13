package com.nilscreation.yummyzone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    TextView title, price, description, qty;
    Button cartBtn;
    ImageView plusBtn, minusBtn, productImg;

    String mCategory, mtitle, mdescription, mimageUrl;
    int qtyNumber = 1;
    int mprice, mdeliveryCharges, finalprice;

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


    }
}