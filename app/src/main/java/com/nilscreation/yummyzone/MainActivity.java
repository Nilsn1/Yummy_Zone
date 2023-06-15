package com.nilscreation.yummyzone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseError;
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
import com.nilscreation.yummyzone.Models.PopularModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerviewCategory, recyclerviewPopular;
    ArrayList<CategoryModel> categorylist;
    ArrayList<PopularModel> popularlist;
    FloatingActionButton cartFab;
    TextView wlcm;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerviewCategory = findViewById(R.id.recyclerviewCategory);
        recyclerviewPopular = findViewById(R.id.recyclerviewPopular);
        cartFab = findViewById(R.id.cartFab);
        wlcm = findViewById(R.id.wlcm);

        cartFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser != null) {

            String userId = firebaseUser.getUid();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User DB");
            databaseReference.child(userId).child("username").addListenerForSingleValueEvent(new ValueEventListener() {
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

        recyclerviewCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerviewPopular.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        categorylist = new ArrayList<>();
        categorylist.add(new CategoryModel(R.drawable.cat_pizza, "Pizza"));
        categorylist.add(new CategoryModel(R.drawable.cat_burger, "Burger"));
        categorylist.add(new CategoryModel(R.drawable.cat_hotdog, "Hotdog"));
        categorylist.add(new CategoryModel(R.drawable.cat_donut, "Donut"));
        categorylist.add(new CategoryModel(R.drawable.cat_biryani, "Biryani"));

        CategoryAdapter categoryAdapter = new CategoryAdapter(categorylist, this);
        recyclerviewCategory.setAdapter(categoryAdapter);

        popularlist = new ArrayList<>();
        popularlist.add(new PopularModel("Pepperoni Pizza", R.drawable.pepperoni_pizza, "100", "A classic medley of zesty pepperoni and gooey cheese, harmonizing atop a crispy pizza crust."));
        popularlist.add(new PopularModel("Cheese Burger", R.drawable.cheese_burger, "150", "Juicy beef patty nestled in melted cheese, embraced by a soft bun."));
        popularlist.add(new PopularModel("Meat Pizza", R.drawable.meat_pizza, "250", "A carnivore's delight, the meat pizza boasts a savory symphony of hearty toppings, satisfying cravings with each mouthwatering slice."));
        popularlist.add(new PopularModel("Spicy Hot Dog", R.drawable.hotdog, "120", "Fiery and flavorful, the spicy hot dog ignites taste buds with its tantalizing heat, delivering a sizzling kick in every bite."));

        PopularAdapter popularAdapter = new PopularAdapter(this, popularlist);
        recyclerviewPopular.setAdapter(popularAdapter);

    }
}