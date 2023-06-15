package com.nilscreation.yummyzone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nilscreation.yummyzone.Adapters.FoodAdapter;
import com.nilscreation.yummyzone.Models.FoodModel;

import java.util.ArrayList;

public class FoodListActivity extends AppCompatActivity {

    RecyclerView recyclerview;
    ArrayList<FoodModel> foodlist;
    FoodAdapter adapter;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        recyclerview = findViewById(R.id.recyclerviewFood);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        foodlist = new ArrayList<>();
        adapter = new FoodAdapter(this, foodlist);
        recyclerview.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Food DB");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                foodlist.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    FoodModel foodModel = dataSnapshot.getValue(FoodModel.class);

                    foodlist.add(foodModel);

//                    String title = foodModel.getTitle();
//                    String description = foodModel.getDescription();
//                    int price = foodModel.getPrice();
//                    Toast.makeText(FoodListActivity.this, "" + price, Toast.LENGTH_SHORT).show();
//                    foodlist.add(new FoodModel(title, description, price));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FoodListActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}