package com.jaypal.ecommerce;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class shop_signup extends AppCompatActivity {
    private static final String TAG = "shop signup";
    Button register;
    EditText username,password,fname,lname,contact;
    String user,pass,f,l,con,url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_signup);
        register=findViewById(R.id.btn_register);
        username=findViewById(R.id.et_username);
        password=findViewById(R.id.et_password);
        lname=findViewById(R.id.et_lastname);
        fname=findViewById(R.id.et_firstname);
        contact=findViewById(R.id.et_contact);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        url="http://192.168.43.89/Ecommerce/shop_signup.php";
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String msg= String.valueOf(jsonObject.get("success"));
                            if(msg.equals("1"))
                            {
                                Toast.makeText(shop_signup.this,"success"+response,Toast.LENGTH_SHORT).show();

                            }else
                                Toast.makeText(shop_signup.this,response,Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(shop_signup.this, MainActivity.class);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
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
                        user= String.valueOf(username.getText());
                        pass= String.valueOf(password.getText());
                        f= String.valueOf(fname.getText());
                        l= String.valueOf(lname.getText());
                        con= String.valueOf(contact.getText());
                        Log.d(TAG, "getParams: "+con);
                        Map<String,String>map=new HashMap<>();
                        map.put("uname",user);
                        map.put("lname",l);
                        map.put("fname",f);
                        map.put("contact",con);
                        map.put("pass",pass);
                        return map;
                    }
                };
                RequestQueue requestQueue= Volley.newRequestQueue(shop_signup.this);
                requestQueue.add(stringRequest);



            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}