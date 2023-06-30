package com.nilscreation.yummyzone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class OrderDetailActivity extends AppCompatActivity {

    TextView orderId, orderTime, orderAddress, itemTotalPrice, deliveryCharges, totalCharges;

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

    }
}