package com.nilscreation.yummyzone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.nilscreation.yummyzone.Adapters.CategoryAdapter;
import com.nilscreation.yummyzone.Adapters.PopularAdapter;
import com.nilscreation.yummyzone.Models.CategoryModel;
import com.nilscreation.yummyzone.Models.PopularModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerviewCategory, recyclerviewPopular;
    ArrayList<CategoryModel> categorylist;
    ArrayList<PopularModel> popularlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerviewCategory = findViewById(R.id.recyclerviewCategory);
        recyclerviewPopular = findViewById(R.id.recyclerviewPopular);

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