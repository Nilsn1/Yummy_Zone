package com.nilscreation.yummyzone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nilscreation.yummyzone.Adapters.FoodAdapter;
import com.nilscreation.yummyzone.Models.FoodModel;

import java.util.ArrayList;
import java.util.Locale;

public class FoodListActivity extends AppCompatActivity {

    RecyclerView recyclerview;
    ArrayList<FoodModel> foodlist;
    ArrayList<FoodModel> filterData;
    FoodAdapter adapter;
    DatabaseReference databaseReference;
    TextView categoryTitle;

    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        categoryTitle = findViewById(R.id.categoryTitle);
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        recyclerview = findViewById(R.id.recyclerviewFood);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        foodlist = new ArrayList<>();
        filterData = new ArrayList<>();
        adapter = new FoodAdapter(this, foodlist);
        recyclerview.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Food DB");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                foodlist.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    FoodModel foodModel = dataSnapshot.getValue(FoodModel.class);

                    String category = foodModel.getCategory();

                    Intent intent = getIntent();
//                    String Category = intent.getStringExtra("Category");
                    String Query = intent.getStringExtra("Query");
//                    categoryTitle.setText(Category);

                    foodlist.add(foodModel);
                    filterData(Query);
                    searchView.setQuery(Query, true);

//                    if (Category.equals(category)) {
//                        foodlist.add(foodModel);
//                    } else if (Category.equals("All Items")) {
//                        foodlist.add(foodModel);
//                        filterData(Query);
//                        searchView.setQuery(Query, true);
////                        Toast.makeText(FoodListActivity.this, "trigger", Toast.LENGTH_SHORT).show();
//                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FoodListActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterData(newText);
                return true;
            }
        });
    }

    private void filterData(String query) {
        filterData.clear();
        for (FoodModel foodModel : foodlist) {
            if (foodModel.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filterData.add(foodModel);
            }
        }
        adapter = new FoodAdapter(this, filterData);
        recyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}