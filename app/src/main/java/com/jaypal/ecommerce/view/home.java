package com.jaypal.ecommerce.view;

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
import com.jaypal.ecommerce.R;
import com.jaypal.ecommerce.show_cart;

public class home extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView=findViewById(R.id.bottom_nav);



        if (savedInstanceState==null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new shops()).commit();
           // bottomNavigationView.setCheckedItem(R.id.nav_shops);

        }
        bottomNavigationView.setItemIconSize(80);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selected_fragment=null;
                switch(item.getItemId())
                {
                    case R.id.nav_shops:
                        selected_fragment=new shops();
                        break;
                    case R.id.nav_products:
                        selected_fragment=new products();
                        break;
                    case R.id.nav_preorders:
                        selected_fragment=new previous_orders();
                        break;
                    case R.id.nav_profile:
                        selected_fragment=new profile();
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,selected_fragment).commit();
                return true;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.tool_cart:
                Intent intent=new Intent(home.this, show_cart.class);
                startActivity(intent);
                //Toast.makeText(this, "You clicked about", Toast.LENGTH_SHORT).show();
                break;

        }
        return true;
    } @Override
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