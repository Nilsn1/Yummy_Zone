package com.nilscreation.yummyzone;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nilscreation.yummyzone.Adapters.CategoryAdapter;
import com.nilscreation.yummyzone.Adapters.PopularAdapter;
import com.nilscreation.yummyzone.Models.CategoryModel;
import com.nilscreation.yummyzone.Models.FoodModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView recyclerviewCategory, recyclerviewPopular;
    ArrayList<CategoryModel> categorylist;
    ArrayList<FoodModel> popularlist;
    TextView wlcm;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    PopularAdapter popularAdapter;

    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        recyclerviewCategory = view.findViewById(R.id.recyclerviewCategory);
        recyclerviewPopular = view.findViewById(R.id.recyclerviewPopular);
        wlcm = view.findViewById(R.id.wlcm);


        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser != null) {

            String userId = firebaseUser.getUid();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User DB");
            databaseReference.child(userId).child("User Details").child("username").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String value = snapshot.getValue(String.class);
                    wlcm.setText("Hello, " + value + "!");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        recyclerviewCategory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerviewPopular.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        categorylist = new ArrayList<>();
        categorylist.add(new CategoryModel(R.drawable.cat_pizza, "Pizza"));
        categorylist.add(new CategoryModel(R.drawable.cat_burger, "Burger"));
        categorylist.add(new CategoryModel(R.drawable.cat_hotdog, "Hotdog"));
        categorylist.add(new CategoryModel(R.drawable.cat_donut, "Donut"));
        categorylist.add(new CategoryModel(R.drawable.cat_biryani, "Biryani"));

        CategoryAdapter categoryAdapter = new CategoryAdapter(categorylist, getContext());
        recyclerviewCategory.setAdapter(categoryAdapter);

        popularlist = new ArrayList<>();

        popularAdapter = new PopularAdapter(getContext(), popularlist);
        recyclerviewPopular.setAdapter(popularAdapter);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Popular Food");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                popularlist.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    FoodModel model = dataSnapshot.getValue(FoodModel.class);
                    popularlist.add(model);
                }
                popularAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}