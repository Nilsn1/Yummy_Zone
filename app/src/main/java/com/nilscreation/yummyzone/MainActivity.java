package com.nilscreation.yummyzone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
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
import com.nilscreation.yummyzone.Models.FoodModel;
import com.nilscreation.yummyzone.Models.PopularModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton cartFab;

    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cartFab = findViewById(R.id.cartFab);
        bottomNavigation = findViewById(R.id.bottomNavigation);

        loadFragment(new HomeFragment());

        cartFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.home) {
                    loadFragment(new HomeFragment());
                } else if (id == R.id.orders) {
                    loadFragment(new OrdersFragment());
                } else if (id == R.id.support) {
                    loadFragment(new ContactFragment());
                } else {
                    loadFragment(new AccountFragment());
                }
                return true;
            }
        });
    }

    private void loadFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder exitdialog = new AlertDialog.Builder(this);
        exitdialog.setTitle("Exit").setIcon(R.drawable.ic_info).setMessage("Do you really want to exit?");
        exitdialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finishAffinity();
            }
        });
        exitdialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        exitdialog.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        exitdialog.show();
    }
}