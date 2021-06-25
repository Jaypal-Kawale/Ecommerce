package com.jaypal.ecommerce;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jaypal.ecommerce.view.home;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton,registerButton;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sessionManager=new SessionManager(this);
        usernameEditText = findViewById(R.id.activity_main_usernameEditText);
        passwordEditText = findViewById(R.id.activity_main_passwordEditText);
        loginButton = findViewById(R.id.activity_main_loginButton);
        registerButton = findViewById(R.id.activity_main_signupButton);
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.activity_main_loginButton:
                loginuser();
                break;
            case R.id.activity_main_signupButton:
                signup();
                break;
        }
    }

    private void loginuser() {
        String url= "http://192.168.43.89/Ecommerce/login.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                Log.d(TAG, "onResponse: "+"count"+response);
                try {
                    jsonObject = new JSONObject(response);
                    String succes = jsonObject.getString("success");

//                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                     Log.d(TAG, "onResponse: "+response);
                    if(succes.equals("1")) {
                        if (jsonObject.getInt("logincheck")==0)
                        {
                            Toast.makeText(MainActivity.this,"Logged in successfully",Toast.LENGTH_SHORT).show();
                            sessionManager.createSession(jsonObject.getString("username"),jsonObject.getString("email"), String.valueOf(jsonObject.getInt("message")),jsonObject.getString("mob"));
                            Intent intent=new Intent(MainActivity.this, home.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(MainActivity.this,"Logged in successfully",Toast.LENGTH_SHORT).show();
                            sessionManager.createSession(jsonObject.getString("username"),jsonObject.getString("password"), String.valueOf(jsonObject.getInt("message")),jsonObject.getString("mob"));
                            Intent intent=new Intent(MainActivity.this, home_shop.class);
                            startActivity(intent);
                        }

                        finish();
                    }
                    else{
                        Toast.makeText(MainActivity.this,"username or password doesnt match",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, "onResponse: "+e);
                }

                }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String u= String.valueOf(usernameEditText.getText());
                String p= String.valueOf(passwordEditText.getText());
                Map<String,String>map=new HashMap<>();
                map.put("username",u);
                map.put("password",p);
                return map;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    private void signup() {
        Intent intent=new Intent(MainActivity.this,logincheck.class);
        startActivity(intent);
    }
}