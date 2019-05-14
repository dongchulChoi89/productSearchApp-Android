package com.example.productsearchapp.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.productsearchapp.ItemDetailActivity;
import com.example.productsearchapp.R;
import com.example.productsearchapp.pojo.DetailItemShipping;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentShipping extends Fragment {
    private JSONObject jsonObject;
    private DetailItemShipping detailItemShipping;

    private TextView store_name;
    private TableRow store_name_container;
    private TextView feedback_score;
    private TableRow feedback_score_container;
    private TextView popularity;
    private TableRow popularity_container;
    private TextView shipping_cost;
    private TableRow shipping_cost_container;
    private TextView global_shipping;
    private TableRow global_shipping_container;
    private TextView handling_time;
    private TableRow handling_time_container;
    private TextView condition;
    private TableRow condition_container;
    private TextView policy;
    private TableRow policy_container;
    private TextView returns_within;
    private TableRow returns_within_container;
    private TextView refund_mode;
    private TableRow refund_mode_container;
    private TextView shipped_by;
    private TableRow shipped_by_container;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View root = inflater.inflate(R.layout.fragment_shipping,container,false);

        ItemDetailActivity itemDetailActivity = (ItemDetailActivity) getActivity();
        ArrayList<String> dataArrayList = itemDetailActivity.getData();
        String itemId = dataArrayList.get(0);
        final String itemShipping = dataArrayList.get(2);

        store_name = (TextView) root.findViewById(R.id.store_name);
        store_name_container = (TableRow) root.findViewById(R.id.store_name_container);
        feedback_score = (TextView) root.findViewById(R.id.feedback_score);
        feedback_score_container = (TableRow) root.findViewById(R.id.feedback_score_container);
        popularity = (TextView) root.findViewById(R.id.popularity);
        popularity_container = (TableRow) root.findViewById(R.id.popularity_container);
        shipping_cost = (TextView) root.findViewById(R.id.shipping_cost);
        shipping_cost_container = (TableRow) root.findViewById(R.id.shipping_cost_container);
        global_shipping = (TextView) root.findViewById(R.id.global_shipping);
        global_shipping_container = (TableRow) root.findViewById(R.id.global_shipping_container);;
        handling_time = (TextView) root.findViewById(R.id.handling_time);
        handling_time_container = (TableRow) root.findViewById(R.id.handling_time_container);
        condition = (TextView) root.findViewById(R.id.condition);
        condition_container = (TableRow) root.findViewById(R.id.condition_container);
        policy = (TextView) root.findViewById(R.id.policy);
        policy_container = (TableRow) root.findViewById(R.id.policy_container);
        returns_within = (TextView) root.findViewById(R.id.returns_within);
        returns_within_container = (TableRow) root.findViewById(R.id.returns_within_container);;
        refund_mode = (TextView) root.findViewById(R.id.return_mode);
        refund_mode_container = (TableRow) root.findViewById(R.id.refund_mode_container);
        shipped_by = (TextView) root.findViewById(R.id.shipped_by);
        shipped_by_container = (TableRow) root.findViewById(R.id.shipped_by_container);

        // Request API && Volley
        String auth = "DONGCHUL-findingA-PRD-a16e557a4-5e21619e";
        String url = "http://open.api.ebay.com/shopping?callname=GetSingleItem&responseencoding=JSON&appid="+auth+"&siteid=0&version=967&ItemID="+itemId+"&IncludeSelector=Description,Details,ItemSpecifics";
        RequestQueue queue = Volley.newRequestQueue(root.getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                jsonObject = response;
                detailItemShipping = new DetailItemShipping();

                try {
                    jsonObject = (JSONObject)jsonObject.get("Item");

                    try {
                        detailItemShipping.setStoreName(((JSONObject)jsonObject.get("Storefront")).get("StoreName").toString());
                        detailItemShipping.setStoreUrl(((JSONObject)jsonObject.get("Storefront")).get("StoreURL").toString());
                    } catch (JSONException error) {
                        detailItemShipping.setStoreName("N/A");
                        detailItemShipping.setStoreUrl("N/A");
                        System.out.println("Request Failed: " + error);
                    }

                    try {
                        detailItemShipping.setFeedbackScore(((JSONObject)jsonObject.get("Seller")).get("FeedbackScore").toString());
                    } catch (JSONException error) {
                        detailItemShipping.setFeedbackScore("N/A");
                        System.out.println("Request Failed: " + error);
                    }

                    try {
                        detailItemShipping.setPopularity(((JSONObject)jsonObject.get("Seller")).get("PositiveFeedbackPercent").toString()+"%");
                    } catch (JSONException error) {
                        detailItemShipping.setPopularity("N/A");
                        System.out.println("Request Failed: " + error);
                    }

                    if (!itemShipping.equals("N/A")){
                        detailItemShipping.setShippingCost(itemShipping);
                    } else {
                        detailItemShipping.setShippingCost("N/A");
                        System.out.println("Request Failed: No value for itemShipping");
                    }

                    try {
                        detailItemShipping.setGlobalShipping(jsonObject.get("GlobalShipping").toString());
                    } catch (JSONException error) {
                        detailItemShipping.setGlobalShipping("N/A");
                        System.out.println("Request Failed: " + error);
                    }

                    try {
                        detailItemShipping.setHandlingTime(jsonObject.get("HandlingTime").toString());
                    } catch (JSONException error) {
                        detailItemShipping.setHandlingTime("N/A");
                        System.out.println("Request Failed: " + error);
                    }

                    try {
                        String condition = jsonObject.get("ConditionDescription").toString();
                        if (condition.length()>30){
                            condition = condition.substring(0,30) + "...";
                        }
                        detailItemShipping.setCondition(condition);
                    } catch (JSONException error) {
                        detailItemShipping.setCondition("N/A");
                        System.out.println("Request Failed: " + error);
                    }

                    try {
                        detailItemShipping.setPolicy(((JSONObject)jsonObject.get("ReturnPolicy")).get("ReturnsAccepted").toString());
                    } catch (JSONException error) {
                        detailItemShipping.setPolicy("N/A");
                        System.out.println("Request Failed: " + error);
                    }

                    try {
                        detailItemShipping.setReturnsWithin(((JSONObject)jsonObject.get("ReturnPolicy")).get("ReturnsWithin").toString());
                    } catch (JSONException error) {
                        detailItemShipping.setReturnsWithin("N/A");
                        System.out.println("Request Failed: " + error);
                    }

                    try {
                        String refundMode = ((JSONObject)jsonObject.get("ReturnPolicy")).get("Refund").toString();
                        if (refundMode.length()>30){
                            refundMode = refundMode.substring(0,30) + "...";
                        }
                        detailItemShipping.setRefundMode(refundMode);
                    } catch (JSONException error) {
                        detailItemShipping.setRefundMode("N/A");
                        System.out.println("Request Failed: " + error);
                    }

                    try {
                        detailItemShipping.setShippedBy(((JSONObject)jsonObject.get("ReturnPolicy")).get("ShippingCostPaidBy").toString());
                    } catch (JSONException error) {
                        detailItemShipping.setShippedBy("N/A");
                        System.out.println("Request Failed: " + error);
                    }

                } catch (JSONException error) {
                    String errorMessage=null;
                    try{
                        errorMessage = ((JSONObject)((JSONArray)jsonObject.get("Errors")).get(0)).get("ShortMessage").toString();
                    } catch (JSONException err) {
                        System.out.println("Request Success");
                    }
                    if(errorMessage != null){
                        System.out.println("Request Failed: "+errorMessage);
                    } else {
                        System.out.println("Request Failed: " + error);
                    }
                }

                // For setting views
                if(!detailItemShipping.getStoreName().equals("N/A")) {
                    store_name.setText(detailItemShipping.getStoreName());
                    store_name.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Uri uri = Uri.parse(detailItemShipping.getStoreUrl());
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }
                    });
                } else {
                    store_name_container.setVisibility(View.GONE);
                }

                if(!detailItemShipping.getFeedbackScore().equals("N/A")) {
                    feedback_score.setText(detailItemShipping.getFeedbackScore());
                } else {
                    feedback_score_container.setVisibility(View.GONE);
                }

                if(!detailItemShipping.getPopularity().equals("N/A")) {
                    popularity.setText(detailItemShipping.getPopularity());
                } else {
                    popularity_container.setVisibility(View.GONE);
                }

                if(!detailItemShipping.getShippingCost().equals("N/A")) {
                    if (Double.parseDouble(detailItemShipping.getShippingCost())==0.00){
                        shipping_cost.setText("Free Shipping");
                    } else {
                        shipping_cost.setText("$"+Double.parseDouble(detailItemShipping.getShippingCost()));
                    }
                } else {
                    shipping_cost_container.setVisibility(View.GONE);
                }

                if(!detailItemShipping.getGlobalShipping().equals("N/A")) {
                    String yesNo = "";
                    if (detailItemShipping.getGlobalShipping().equals("true")){
                        yesNo = "Yes";
                    } else {
                        yesNo = "No";
                    }
                    global_shipping.setText(yesNo);
                } else {
                    feedback_score_container.setVisibility(View.GONE);
                }

                if(!detailItemShipping.getHandlingTime().equals("N/A")) {
                    String dayDays = "";
                    if (detailItemShipping.getHandlingTime().equals("0") || detailItemShipping.getHandlingTime().equals("1")){
                        dayDays = "Day";
                    } else {
                        dayDays = "Days";
                    }
                    handling_time.setText(detailItemShipping.getHandlingTime()+" "+dayDays);
                } else {
                    handling_time_container.setVisibility(View.GONE);
                }

                if(!detailItemShipping.getCondition().equals("N/A")) {
                    condition.setText(detailItemShipping.getCondition());
                } else {
                    condition_container.setVisibility(View.GONE);
                }

                if(!detailItemShipping.getPolicy().equals("N/A")) {
                    policy.setText(detailItemShipping.getPolicy());
                } else {
                    policy_container.setVisibility(View.GONE);
                }

                if(!detailItemShipping.getReturnsWithin().equals("N/A")) {
                    returns_within.setText(detailItemShipping.getReturnsWithin());
                } else {
                    returns_within_container.setVisibility(View.GONE);
                }

                if(!detailItemShipping.getRefundMode().equals("N/A")) {
                    refund_mode.setText(detailItemShipping.getRefundMode());
                } else {
                    refund_mode_container.setVisibility(View.GONE);
                }

                if(!detailItemShipping.getShippedBy().equals("N/A")) {
                    shipped_by.setText(detailItemShipping.getShippedBy());
                } else {
                    shipped_by_container.setVisibility(View.GONE);
                }

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
