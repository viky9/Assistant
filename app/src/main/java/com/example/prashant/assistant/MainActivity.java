package com.example.prashant.assistant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;

    TextView textView;
    private  String token="Hello";

    //URL to RegisterDevice.php
    private static final String URL_REGISTER_DEVICE = "http://192.168.42.133/fcmExample/AssistantNotification.php";

    private Button btn1,btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1=(Button)findViewById(R.id.button);
        btn2=(Button)findViewById(R.id.button2);
        textView=(TextView)findViewById(R.id.textView2);
        sendTokenToServer();
    }
    public  void  LocateSingle(View v){
        Intent i= new Intent(this,ProductSearch.class);
        startActivity(i);
    }
    public  void  LocateMultiple(View v){
        Intent i= new Intent(this,ShoppingCart.class);
        startActivity(i);
    }
    //storing token to mysql server
    private void sendTokenToServer() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering Device...");
        progressDialog.show();
       String test= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("MYLABEL", "defaultStringIfNothingFound");

        token+= SharedPrefManager.getInstance(this).getDeviceToken();
        textView.setText(test+"Hii"+token);

        if (token == null) {
            progressDialog.dismiss();
            Toast.makeText(this, "Token not generated", Toast.LENGTH_LONG).show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTER_DEVICE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            Toast.makeText(MainActivity.this, obj.getString("message"), Toast.LENGTH_LONG).show();
                            textView.setText(token);
                            Log.e("succcess","success");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("error except","errror exception in json");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
//                        textView.setText("error\n");
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("error","errror in volley ");
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("token", token);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
