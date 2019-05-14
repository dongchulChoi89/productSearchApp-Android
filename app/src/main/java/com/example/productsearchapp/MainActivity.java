package com.example.productsearchapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private String keyword="N/A";
    private String category="N/A";
    private String conditionNew="N/A";
    private String conditionUsed="N/A";
    private String conditionUnspecifed="N/A";
    private String shippingLocal="N/A";
    private String shippingFree="N/A";
    private String mile="N/A";
    private String zip="N/A";
    private String selected_current_or_user="N/A";

    private JSONObject jsonObject;
    private String currentZip;

    private Button form_search;
    private Button form_clear;
    private EditText form_keyword;
    private TextView form_valid_keyword;
    private Spinner form_category;
    private CheckBox form_condition_new;
    private CheckBox form_condition_used;
    private CheckBox form_condition_unspecified;
    private CheckBox form_shipping_options_local_pickup;
    private CheckBox form_shipping_options_free_shipping;
    private CheckBox form_nearby_search;
    private LinearLayout form_nearby_search_container;
    private EditText form_mile;
    private RadioButton form_zip_current;
    private RadioButton form_zip_user;
    private EditText form_zip_user_value;
    private TextView form_valid_zip;

    // key for intent
    public static final String FORM_KEYWORD = "com.example.myapplication.FORM_KEYWORD";
    public static final String FORM_CATEGORY = "com.example.myapplication.FORM_CATEGORY";
    public static final String FORM_CONDITION_NEW = "com.example.myapplication.FORM_CONDITION_NEW";
    public static final String FORM_CONDITION_USED = "com.example.myapplication.FORM_CONDITION_USED";
    public static final String FORM_CONDITION_UNSPECIFIED = "com.example.myapplication.FORM_CONDITION_UNSPECIFIED";
    public static final String FORM_SHIPPING_LOCAL = "com.example.myapplication.FORM_CONDITION_LOCAL";
    public static final String FORM_SHIPPING_FREE = "com.example.myapplication.FORM_CONDITION_FREE";
    public static final String FORM_MILE = "com.example.myapplication.FORM_MILE";
    public static final String FORM_ZIP = "com.example.myapplication.FORM_ZIP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // get currentZip
        requestCurrentZip();

        form_search = (Button) findViewById(R.id.form_search);
        form_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValidKeyword = true;
                boolean isValidZip = true;

                form_keyword = (EditText) findViewById(R.id.form_keyword);
                keyword = form_keyword.getText().toString();

                if (keyword.equals("")){
                    form_valid_keyword = (TextView) findViewById(R.id.form_valid_keyword);
                    form_valid_keyword.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "Please fix all fileds with errors",Toast.LENGTH_SHORT).show();
                    isValidKeyword = false;
                }

                form_mile = (EditText) findViewById(R.id.form_mile);
                mile = form_mile.getText().toString();
                if (mile.equals("")){
                    mile = "10";
                }

                if (selected_current_or_user.equals("current")){
                    zip = currentZip;
                } else if (selected_current_or_user.equals("user")){
                    zip = form_zip_user_value.getText().toString();
                } else {
                    zip = currentZip;
                }

                if (zip.equals("")){
                    form_valid_zip = (TextView) findViewById(R.id.form_valid_zip);
                    form_valid_zip.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "Please fix all fileds with errors",Toast.LENGTH_SHORT).show();
                    isValidZip = false;
                }

                if(isValidKeyword==true && isValidZip==true){
                    Intent intent = new Intent(getApplicationContext(), SearchListActivity.class);
                    intent.putExtra(FORM_KEYWORD, keyword); // key-value pair
                    intent.putExtra(FORM_CATEGORY, category); // key-value pair
                    intent.putExtra(FORM_CONDITION_NEW, conditionNew);
                    intent.putExtra(FORM_CONDITION_USED, conditionUsed);
                    intent.putExtra(FORM_CONDITION_UNSPECIFIED, conditionUnspecifed);
                    intent.putExtra(FORM_SHIPPING_LOCAL, shippingLocal);
                    intent.putExtra(FORM_SHIPPING_FREE, shippingFree);
                    intent.putExtra(FORM_MILE, mile);
                    intent.putExtra(FORM_ZIP, zip);
                    startActivity(intent);
                }
            }
        });

        form_clear = (Button) findViewById(R.id.form_clear);
        form_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                overridePendingTransition(0, 0);
            }
        });

        form_category = (Spinner) findViewById(R.id.form_category);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,R.array.category_array,android.R.layout.simple_spinner_dropdown_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        form_category.setAdapter(spinnerAdapter);

        form_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = parent.getItemAtPosition(position).toString();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                category = "ALL";
            }
        });

        form_condition_new = (CheckBox) findViewById(R.id.form_condition_new);
        form_condition_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isChecked = ((CheckBox)view).isChecked();
                switch(view.getId()) {
                    case R.id.form_condition_new:
                        if (isChecked)
                            conditionNew = "New";
                        else
                            conditionNew = "N/A";
                }
            }
        });

        form_condition_used = (CheckBox) findViewById(R.id.form_condition_used);
        form_condition_used.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isChecked = ((CheckBox)view).isChecked();
                switch(view.getId()) {
                    case R.id.form_condition_used:
                        if (isChecked)
                            conditionUsed = "Used";
                        else
                            conditionUsed = "N/A";
                }
            }
        });

        form_condition_unspecified = (CheckBox) findViewById(R.id.form_condition_unspecified);
        form_condition_unspecified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isChecked = ((CheckBox)view).isChecked();
                switch(view.getId()) {
                    case R.id.form_condition_unspecified:
                        if (isChecked)
                            conditionUnspecifed = "Unspecified";
                        else
                            conditionUnspecifed = "N/A";
                }
            }
        });

        form_shipping_options_local_pickup = (CheckBox) findViewById(R.id.form_shipping_options_local_pickup);
        form_shipping_options_local_pickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isChecked = ((CheckBox)view).isChecked();
                switch(view.getId()) {
                    case R.id.form_shipping_options_local_pickup:
                        if (isChecked)
                            shippingLocal = "Local";
                        else
                            shippingLocal = "N/A";
                }
            }
        });

        form_shipping_options_free_shipping = (CheckBox) findViewById(R.id.form_shipping_options_free_shipping);
        form_shipping_options_free_shipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isChecked = ((CheckBox)view).isChecked();
                switch(view.getId()) {
                    case R.id.form_shipping_options_free_shipping:
                        if (isChecked)
                            shippingFree = "Free";
                        else
                            shippingLocal = "N/A";
                }
            }
        });

        form_nearby_search = (CheckBox) findViewById(R.id.form_nearby_search);
        form_nearby_search_container = (LinearLayout) findViewById(R.id.form_nearby_search_container);
        form_nearby_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isChecked = ((CheckBox)view).isChecked();
                switch(view.getId()) {
                    case R.id.form_nearby_search:
                        if (isChecked) {
                            System.out.println("nearby search clicked");
                            form_nearby_search_container.setVisibility(View.VISIBLE);
                        } else {
                            System.out.println("nearby search unclicked");
                            form_nearby_search_container.setVisibility(View.GONE);

                        }
                }
            }
        });

        form_zip_current = (RadioButton) findViewById(R.id.form_zip_current);
        form_zip_current.setChecked(true);
        form_zip_current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isChecked = ((RadioButton) view).isChecked();
                switch (view.getId()) {
                    case R.id.form_zip_current:
                        if (isChecked){
                            selected_current_or_user = "current";
                        }
                }
            }
        });

        form_zip_user = (RadioButton) findViewById(R.id.form_zip_user);
        form_zip_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isChecked = ((RadioButton) view).isChecked();
                switch (view.getId()) {
                    case R.id.form_zip_user:
                        if (isChecked){
                            selected_current_or_user = "user";
                        }
                }
            }
        });

        form_zip_user_value = (EditText) findViewById(R.id.form_zip_user_value);
    }

    // request current zip using http://ip-api.com/json
    private void requestCurrentZip() {
        String url = "http://ip-api.com/json";
        // Request API && Volley
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        jsonObject = response;
                        System.out.println(jsonObject.toString());
                        try {
                            currentZip = jsonObject.get("zip").toString();
                            System.out.println(currentZip);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Request Failed: "+error);
                    }
                });
        queue.add(jsonObjectRequest);
    }
}
