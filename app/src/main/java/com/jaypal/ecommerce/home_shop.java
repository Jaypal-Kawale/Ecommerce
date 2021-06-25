package com.jaypal.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jaypal.ecommerce.view.home;
import com.jaypal.ecommerce.view.previous_orders;
import com.jaypal.ecommerce.view.products;
import com.jaypal.ecommerce.view.profile;
import com.jaypal.ecommerce.view.shops;

public class home_shop extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_shop);
        bottomNavigationView=findViewById(R.id.shop_bottom_nav);



        if (savedInstanceState==null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.shop_framelayout,new shop_products()).commit();
            // bottomNavigationView.setCheckedItem(R.id.nav_shops);

        }
        bottomNavigationView.setItemIconSize(80);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selected_fragment=null;
                switch(item.getItemId())
                {
                    case R.id.shop_nav_products:
                        selected_fragment=new shop_products();
                        break;
                    case R.id.shop_nav_preorders:
                        selected_fragment=new shop_orders();
                        break;
                    case R.id.shop_nav_profile:
                        selected_fragment=new shop_profile();
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.shop_framelayout,selected_fragment).commit();
                return true;
            }
        });
    }
    @Override
    public void onBackPressed() {



            if (doubleBackToExitPressedOnce) {
                //super.onBackPressed();
                finishAffinity();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "press again to exit application", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }


}