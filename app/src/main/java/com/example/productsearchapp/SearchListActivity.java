package com.example.productsearchapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.productsearchapp.adapter.SearhListRecyclerViewAdapter;
import com.example.productsearchapp.pojo.SearchItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class SearchListActivity extends AppCompatActivity {
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private ArrayList<SearchItem> searchItemArrayList = new ArrayList<SearchItem>();
    public static final String SEARCHLIST_KEYWORD = "com.example.myapplication.SEARCHLIST_KEYWORD";
    // Form
    private String keyword;
    private String category;
    private String conditionNew;
    private String conditionUsed;
    private String conditionUnspecifed;
    private String shippingLocal;
    private String shippingFree;
    private String url;
    // SearchResult
    private String itemId;
    private String title;
    private String galleryUrl;
    private double price;
    private double shippingCost;
    private String zipCode;
    private String condition;
    private String viewItemURL;
    private String mile;
    private String zip;
    private int searchCount;
    // RecyclerView && RecyclerView.Adapter
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    // LayoutManger && GridLayoutManager
    private RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        // make searchlist using progressbar
        TaskUsingProgressbar task = new TaskUsingProgressbar();
        task.execute();
    }
    // progressbar
    private class TaskUsingProgressbar extends AsyncTask<Void, Void, Void> {
        ProgressDialog asyncDialog = new ProgressDialog(SearchListActivity.this);

        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("In progress ...");
            asyncDialog.show();
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                    // make requestUrl and process inputs
                    makeRequestUrl();
                    // request Url using Volley
                    requestUrl();
                    // for natural seeming flow
                    Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            asyncDialog.dismiss();
            super.onPostExecute(result);
        }
    }

    // set toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    // set Activity title, Body title
    public void setTitles(){
        findViewById(R.id.search_list_body_title).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.search_list_body_title1)).setText(" "+searchCount+" ");
        ((TextView)findViewById(R.id.search_list_body_title2)).setText(" "+keyword);
    }

    // make requestUrl
    private void makeRequestUrl() {
        // intent from MainActivity
        Intent intent = getIntent();
        keyword = intent.getStringExtra(MainActivity.FORM_KEYWORD);
        category = intent.getStringExtra(MainActivity.FORM_CATEGORY);
        conditionNew = intent.getStringExtra(MainActivity.FORM_CONDITION_NEW);
        conditionUsed = intent.getStringExtra(MainActivity.FORM_CONDITION_USED);
        conditionUnspecifed = intent.getStringExtra(MainActivity.FORM_CONDITION_UNSPECIFIED);
        shippingLocal = intent.getStringExtra(MainActivity.FORM_SHIPPING_LOCAL);
        shippingFree = intent.getStringExtra(MainActivity.FORM_SHIPPING_FREE);
        mile = intent.getStringExtra(MainActivity.FORM_MILE);
        zip = intent.getStringExtra(MainActivity.FORM_ZIP);

        // make request url
        int i=0, j=0;
        String auth = "DONGCHUL-findingA-PRD-a16e557a4-5e21619e";
        url = "http://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0";
        url += "&SECURITY-APPNAME=" + auth;
        url += "&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD&paginationInput.entriesPerPage=50&outputSelector(0)=SellerInfo&outputSelector(1)=StoreInfo%20";

        // process inputs
        String processedKeywords = keyword.trim();
        processedKeywords = processedKeywords.replace(" ","%20");
        url += "&keywords=" + processedKeywords;

        if (category.equals("All")){
            //category = "";
        } else {
            if (category.equals("Art")){
                category = "550";
            } else if (category.equals("Baby")){
                category = "2984";
            } else if (category.equals("Books")){
                category = "267";
            } else if (category.equals("Clothing, Shoes & Accessories")){
                category = "11450";
            } else if (category.equals("Computers, Tablets & Networking")){
                category = "58058";
            } else if (category.equals("Music")){
                category = "11233";
            } else if (category.equals("Health & Beauty")){
                category = "26395";
            } else { // Video Games &amp; Consoles
                category = "1249";
            }
            url += "&categoryId=" + category;
        }

        url += "&buyerPostalCode="+zip;

        if(!mile.equals("N/A")) {
            url += "&itemFilter(" + i + ").name=MaxDistance" + "&itemFilter(" + i + ").value=" + mile;
            i++;
        }else{
            url += "&itemFilter(" + i + ").name=MaxDistance" + "&itemFilter(" + i + ").value=10";
            i++;
        }
        url += "&itemFilter("+i+").name=HideDuplicateItems" + "&itemFilter("+i+").value=true";
        i++;

        if(shippingFree.equals("Free")){
            url += "&itemFilter("+i+").name=FreeShippingOnly" + "&itemFilter("+i+").value=true";
            i++;
        }

        if(shippingLocal.equals("Local")){
            url += "&itemFilter("+i+").name=LocalPickupOnly" + "&itemFilter("+i+").value=true";
            i++;
        }

        if(conditionNew.equals("New") || conditionUsed.equals("Used") || conditionUnspecifed.equals("Unspecified")){
            url += "&itemFilter("+i+").name=Condition";
            if(conditionNew.equals("New")){
                url += "&itemFilter("+i+").value("+j+")=New";
                j++;
            }
            if(conditionUsed.equals("Used")){
                url += "&itemFilter("+i+").value("+j+")=Used";
                j++;
            }
            if(conditionUnspecifed.equals("Unspecified")){
                url += "&itemFilter("+i+").value("+j+")=Unspecified";
                j=0;
            }
        }
        //System.out.println(url);
    }

    // request Url using Volley
    private void requestUrl() {
        // Request API && Volley
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        jsonObject = response;
                        System.out.println(jsonObject.toString());
                        try {
                            jsonArray = (JSONArray) jsonObject.get("findItemsAdvancedResponse");
                            System.out.println(jsonArray);
                            jsonObject = (JSONObject) jsonArray.get(0);
                            System.out.println(jsonObject);
                            jsonArray = (JSONArray)jsonObject.get("searchResult");
                            System.out.println(jsonArray);
                            jsonObject = (JSONObject) jsonArray.get(0);
                            System.out.println(jsonObject);
                            jsonArray = (JSONArray) jsonObject.get("item"); // item
                            System.out.println(jsonArray);
                            jsonObject = (JSONObject) jsonArray.get(0);
                            System.out.println(jsonObject);

                            searchCount = jsonArray.length();
                            for (int i=0;i<jsonArray.length();i++){
                                jsonObject = (JSONObject) jsonArray.get(i);

                                itemId = (String)((JSONArray)jsonObject.get("itemId")).get(0);
                                galleryUrl = (String)((JSONArray)jsonObject.get("galleryURL")).get(0);
                                title = (String)((JSONArray)jsonObject.get("title")).get(0);
                                price = Double.parseDouble((String)((JSONObject)((JSONArray)((JSONObject)((JSONArray)jsonObject.get("sellingStatus")).get(0)).get("currentPrice")).get(0)).get("__value__"));
                                shippingCost = Double.parseDouble((String)((JSONObject)((JSONArray)((JSONObject)((JSONArray)jsonObject.get("shippingInfo")).get(0)).get("shippingServiceCost")).get(0)).get("__value__"));
                                zipCode = (String)((JSONArray)jsonObject.get("postalCode")).get(0);
                                condition = (String)((JSONArray)((JSONObject)((JSONArray)jsonObject.get("condition")).get(0)).get("conditionDisplayName")).get(0);
                                viewItemURL = (String)((JSONArray)jsonObject.get("viewItemURL")).get(0);

                                searchItemArrayList.add(new SearchItem(itemId,galleryUrl,title,price,shippingCost,zipCode,condition,viewItemURL));
                            }
                            setTitles();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            TextView no_results = (TextView) findViewById(R.id.no_results);
                            no_results.setVisibility(View.VISIBLE);
                        }

                        recyclerView = (RecyclerView) findViewById(R.id.search_list);
                        // use this setting to improve performance if you know that changes in content do not change the layout size of the RecyclerView
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerViewAdapter = new SearhListRecyclerViewAdapter(searchItemArrayList);
                        recyclerView.setAdapter(recyclerViewAdapter);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Request Failed: "+error);
                        TextView no_results = (TextView) findViewById(R.id.no_results);
                        no_results.setText("Error");
                        no_results.setVisibility(View.VISIBLE);
                    }
                });
        queue.add(jsonObjectRequest);
    }

}


