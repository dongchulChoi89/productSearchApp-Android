package com.example.productsearchapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.productsearchapp.ItemDetailActivity;
import com.example.productsearchapp.R;
import com.example.productsearchapp.adapter.SimilarRecyclerViewAdapter;
import com.example.productsearchapp.pojo.DetailItemSimilar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class FragmentSimilar extends Fragment {
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private DetailItemSimilar detailItemSimilar;
    private ArrayList<DetailItemSimilar> detailItemSimilarArrayList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View root = inflater.inflate(R.layout.fragment_similar,container,false);

        ItemDetailActivity itemDetailActivity = (ItemDetailActivity) getActivity();
        ArrayList<String> dataArrayList = itemDetailActivity.getData();
        String itemId = dataArrayList.get(0);

        // RecyclerView && RecyclerView.Adapter
        recyclerView = (RecyclerView) root.findViewById(R.id.fragment_similar_recycler_view);
        // LayoutManger && GridLayoutManager
        layoutManager = new GridLayoutManager(root.getContext(),1);

        String consumerId = "DONGCHUL-findingA-PRD-a16e557a4-5e21619e";
        String url = "http://svcs.ebay.com/MerchandisingService?OPERATION-NAME=getSimilarItems&SERVICE-NAME=MerchandisingService&SERVICE-VERSION=1.1.0&CONSUMER-ID="+consumerId+"&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD&itemId="+itemId+"&maxResults=20";

        RequestQueue queue = Volley.newRequestQueue(root.getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                jsonObject = response;

                try {
                    jsonObject = (JSONObject) jsonObject.get("getSimilarItemsResponse");
                    jsonObject = (JSONObject) jsonObject.get("itemRecommendations");
                    jsonArray = (JSONArray) jsonObject.get("item");

                    detailItemSimilarArrayList = new ArrayList<DetailItemSimilar>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        detailItemSimilar = new DetailItemSimilar();

                        try {
                            detailItemSimilar.setImageUrl(((JSONObject) jsonArray.get(i)).get("imageURL").toString());
                        } catch (JSONException error) {
                            detailItemSimilar.setImageUrl("N/A");
                            System.out.println("Request Failed: " + error);
                        }

                        try {
                            String title = ((JSONObject) jsonArray.get(i)).get("title").toString();
                            if (title.length()>60){
                                title = title.substring(0,60)+"...";
                            }
                            detailItemSimilar.setTitle(title);
                            detailItemSimilar.setEbayUrl(((JSONObject) jsonArray.get(i)).get("viewItemURL").toString());

                        } catch (JSONException error) {
                            detailItemSimilar.setTitle("N/A");
                            System.out.println("Request Failed: " + error);
                        }

                        try {
                            String shippingCostLabel;

                            if(((JSONObject) ((JSONObject) jsonArray.get(i)).get("shippingCost")).get("__value__").toString().equals("0.00")) {
                                shippingCostLabel = "Free Shipping";
                            } else {
                                shippingCostLabel = "$"+((JSONObject) ((JSONObject) jsonArray.get(i)).get("shippingCost")).get("__value__").toString();
                            }
                            detailItemSimilar.setShippingCost(shippingCostLabel);
                        } catch (JSONException error) {
                            detailItemSimilar.setShippingCost("N/A");
                            System.out.println("Request Failed: " + error);
                        }

                        try {
                            String timeLeft = ((JSONObject) jsonArray.get(i)).get("timeLeft").toString();

                            if(timeLeft.substring(2,3).equals("D")){
                                timeLeft = timeLeft.substring(1,2);
                            }else{
                                timeLeft = timeLeft.substring(1,3);
                            }
                            detailItemSimilar.setShippingDay(timeLeft+" Days Left");
                        } catch (JSONException error) {
                            detailItemSimilar.setShippingDay("N/A");
                            System.out.println("Request Failed: " + error);
                        }

                        try {
                            detailItemSimilar.setPrice("$"+((JSONObject) ((JSONObject) jsonArray.get(i)).get("buyItNowPrice")).get("__value__").toString());
                        } catch (JSONException error) {
                            detailItemSimilar.setPrice("N/A");
                            System.out.println("Request Failed: " + error);
                        }

                        detailItemSimilarArrayList.add(detailItemSimilar);
                    }

                } catch (JSONException error) {
                    System.out.println("Request Failed: " + error);
                }

                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(layoutManager);
                recyclerViewAdapter = new SimilarRecyclerViewAdapter(detailItemSimilarArrayList);
                recyclerView.setAdapter(recyclerViewAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Request Failed: "+error);
            }
        });
        queue.add(jsonObjectRequest);

        return root;
    }
}
