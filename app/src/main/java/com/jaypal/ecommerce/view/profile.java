package com.jaypal.ecommerce.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.jaypal.ecommerce.MainActivity;
import com.jaypal.ecommerce.R;
import com.jaypal.ecommerce.SessionManager;
import com.jaypal.ecommerce.adapter.cart_shops_adapter;
import com.jaypal.ecommerce.model.cart_model;
import com.jaypal.ecommerce.model.cart_shop_model;
import com.jaypal.ecommerce.shop_addproduct;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class profile extends Fragment {
    private static final String TAG = "Profile";
    private static final int RESULT_OK = 200;
    TextView username,mob,addr,fullname;
    ImageView img,profileImg;
    List<cart_shop_model> lis;

    cart_shop_model sh;
    RecyclerView recyclerView;
Button logoutbtn;
SessionManager sessionManager;
    Bitmap bitmap;
    String encodedImage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile,container,false);
        username=view.findViewById(R.id.user_name);
        mob=view.findViewById(R.id.user_email);
        addr=view.findViewById(R.id.address);
        fullname=view.findViewById(R.id.address_full_name);
        logoutbtn=view.findViewById(R.id.sign_out_btn);
        profileImg=view.findViewById(R.id.profile_image);
        img=view.findViewById(R.id.product_image);
        lis=new ArrayList<>();
        recyclerView=view.findViewById(R.id.recent_orders);
        sessionManager=new SessionManager(getContext());
        Log.d(TAG, "onCreateView: "+sessionManager.getUserDetail());
        getprofile();
         getorder();
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logout();
            }
        });
        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requirepermission();
            }
        });
        return view;
    }

    private void requirepermission() {
        Dexter.withActivity((Activity) getContext())
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {

                        Intent intent  = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(Intent.createChooser(intent,"Select Image"),1);
                    }
                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }

    private void getprofile() {
        String url= "http://192.168.43.89/Ecommerce/get_myprofile.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;

                try {
                    jsonObject = new JSONObject(response);
                    String succes = jsonObject.getString("success");
                   // JSONArray jsonArray = jsonObject.getJSONArray("data");
                    //  Log.d(TAG, "onResponse: "+response);
                    if(succes.equals("1")) {
                         JSONObject js=jsonObject.optJSONObject("data");
                         username.setText(js.getString("username"));
                         mob.setText(js.getString("contact_no"));
                         addr.setText(js.getString("password"));
                    //   Glide.with(getContext()).load("http://192.168.43.89/Ecommerce/images/"+js.getString("customer_img")).into(profileImg);
                         fullname.setText(js.getString("firstname")+" "+js.getString("lastname"));
                    }
                    else{
                       // Toast.makeText(profile.this,"username or password doesnt match",Toast.LENGTH_SHORT).show();
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
                Map<String,String>map=new HashMap<>();
                map.put("id",sessionManager.getUserDetail().get("ID"));
                return map;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);


    }
    private void addproduct() {
        String url= "http://192.168.43.89/Ecommerce/update_prof_image.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: "+response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>map=new HashMap<>();
//                map.put("name",proname.getText().toString());
//                map.put("desc",prodesc.getText().toString());
//                map.put("price",proprice.getText().toString());
//                map.put("cat",procat.getText().toString());
                map.put("img",encodedImage);
                map.put("id",sessionManager.getUserDetail().get("ID"));
                return map;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == 1 && resultCode == RESULT_OK && data!=null){

            Uri filePath = data.getData();

            try {
                InputStream inputStream = getContext().getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);

                imageStore(bitmap);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    private void imageStore(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);

        byte[] imageBytes = stream.toByteArray();

        encodedImage = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);


    }
    private void getorder() {
        String url= "http://192.168.43.89/Ecommerce/getorder.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: getorder"+response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String succes = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if(succes.equals("1")){

                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String imageurl = object.getString("shop_img");
                            int catid = object.getInt("category_id");
                            String name = object.getString("shop_name");
                            String url = "http://192.168.43.89/Ecommerce/images/"+imageurl;
                            int custmer_id=object.getInt("customer_id");
                            int shop_id=object.getInt("shop_id");
                            int quan=object.getInt("quantity");
                            int pric=object.getInt("price");
                            int status=object.getInt("status");
                            sh=new cart_shop_model(shop_id,name,catid,imageurl,pric,quan);
                            lis.add(sh);
                        }
                        Log.d(TAG, "onResponse: inside recycler"+lis.toString()+lis.size());
                        cart_shops_adapter adapter=new cart_shops_adapter(lis, getContext(),1);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false) );
                        recyclerView.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>map=new HashMap<>();
                map.put("customer_id",sessionManager.getUserDetail().get("ID"));
                map.put("mode","1");
                return map;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

}