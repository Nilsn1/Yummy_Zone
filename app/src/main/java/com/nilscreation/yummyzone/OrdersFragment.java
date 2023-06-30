package com.nilscreation.yummyzone;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nilscreation.yummyzone.Adapters.OrderAdapter;
import com.nilscreation.yummyzone.Models.OrderDetails;

import java.util.ArrayList;


public class OrdersFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<OrderDetails> orderList;

    FirebaseAuth auth = FirebaseAuth.getInstance();

    OrderAdapter orderAdapter;


    public OrdersFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        orderList = new ArrayList<>();

        orderAdapter = new OrderAdapter(getContext(), orderList, getActivity());
        recyclerView.setAdapter(orderAdapter);

        FirebaseUser firebaseUser = auth.getCurrentUser();

        if (firebaseUser != null) {
            String userId = firebaseUser.getUid();

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User DB")
                    .child(userId).child("Order Details").child("Completed");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    orderList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        OrderDetails orderDetails = dataSnapshot.getValue(OrderDetails.class);
                        orderList.add(orderDetails);
                    }

                    orderAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        return view;
    }
}