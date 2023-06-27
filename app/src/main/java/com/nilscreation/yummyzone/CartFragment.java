package com.nilscreation.yummyzone;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class CartFragment extends Fragment {

    RecyclerView recyclerviewCart;
    ArrayList<FoodModel> cartlist;
    CartAdapter cartAdapter;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    public CartFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

//        recyclerviewCart = view.findViewById(R.id.recyclerviewCart);
//        recyclerviewCart.setLayoutManager(new LinearLayoutManager(getContext()));
//        cartlist = new ArrayList<>();
//
//        cartAdapter = new CartAdapter(getContext(), cartlist,getActivity());
//        recyclerviewCart.setAdapter(cartAdapter);
//
//        FirebaseUser firebaseUser = auth.getCurrentUser();
//        if (firebaseUser != null) {
//            String userId = firebaseUser.getUid();
//            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User DB");
//            databaseReference.child(userId).child("Order Details").child("Cart").addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    cartlist.clear();
//                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                        FoodModel model = dataSnapshot.getValue(FoodModel.class);
//                        cartlist.add(model);
//                    }
//                    cartAdapter.notifyDataSetChanged();
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        }

        return view;
    }
}