package com.example.productsearchapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.productsearchapp.ItemDetailActivity;
import com.example.productsearchapp.R;
import com.example.productsearchapp.pojo.DetailItemProduct;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class FragmentProduct extends Fragment {
    private JSONObject jsonObject;
    private DetailItemProduct detailItemProduct;

    private ImageView pictureUrl;
    private TextView title;
    private TextView subtitle;
    private TextView price1;
    private TextView price2;
    private TextView brand;
    private TextView shipping;
    private TextView itemSpecifics;
    private LinearLayout subtitle_container;
    private LinearLayout price2_container;
    private LinearLayout brand_container;
    private LinearLayout specifications_container;

    private final int NO_BRAND = -1;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View root = inflater.inflate(R.layout.fragment_product,container,false);

        ItemDetailActivity itemDetailActivity = (ItemDetailActivity) getActivity();
        ArrayList<String> dataArrayList = itemDetailActivity.getData();
        String itemId = dataArrayList.get(0);
        final String itemShipping = dataArrayList.get(2);

        pictureUrl = (ImageView)root.findViewById(R.id.pictureUrl);
        title = (TextView)root.findViewById(R.id.title);
        subtitle = (TextView)root.findViewById(R.id.subtitle);
        price1 = (TextView)root.findViewById(R.id.price1);
        price2 = (TextView)root.findViewById(R.id.price2);
        brand = (TextView)root.findViewById(R.id.brand);
        shipping = (TextView)root.findViewById(R.id.shipping);
        itemSpecifics = (TextView)root.findViewById(R.id.itemSpecifics);
        subtitle_container = (LinearLayout)root.findViewById(R.id.subtitle_container);
        price2_container = (LinearLayout)root.findViewById(R.id.price2_container);
        brand_container = (LinearLayout)root.findViewById(R.id.brand_container);
        specifications_container = (LinearLayout)root.findViewById(R.id.brand_container);

        // Request API && Volley
        String auth = "DONGCHUL-findingA-PRD-a16e557a4-5e21619e";
        String url = "http://open.api.ebay.com/shopping?callname=GetSingleItem&responseencoding=JSON&appid="+auth+"&siteid=0&version=967&ItemID="+itemId+"&IncludeSelector=Description,Details,ItemSpecifics";
        RequestQueue queue = Volley.newRequestQueue(root.getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        jsonObject = response;
                        detailItemProduct = new DetailItemProduct();

                        try {
                            jsonObject = (JSONObject)jsonObject.get("Item");

                            try {
                                detailItemProduct.setPictureUrl1(((JSONArray)jsonObject.get("PictureURL")).get(0).toString()); // need to modify
                            } catch (JSONException error) {
                                detailItemProduct.setPictureUrl1("N/A");
                                System.out.println("Request Failed: " + error);
                            }
                            try {
                                detailItemProduct.setTitle(jsonObject.get("Title").toString());
                            } catch (JSONException error) {
                                detailItemProduct.setTitle("N/A");
                                System.out.println("Request Failed: " + error);
                            }
                            try {
                                detailItemProduct.setSubtitle(jsonObject.get("Subtitle").toString());
                            } catch (JSONException error) {
                                detailItemProduct.setSubtitle("N/A");
                                System.out.println("Request Failed: " + error);
                            }
                            try {
                                detailItemProduct.setPrice(((JSONObject)jsonObject.get("CurrentPrice")).get("Value").toString());
                            } catch (JSONException error) {
                                detailItemProduct.setPrice("N/A");
                                System.out.println("Request Failed: " + error);
                            }
                            if (!itemShipping.equals("N/A")){
                                detailItemProduct.setShipping(itemShipping);
                            } else {
                                detailItemProduct.setShipping("N/A");
                                System.out.println("Request Failed: No value for itemShipping");
                            }
                            try {
                                ArrayList<String> itemSpecificsName = new ArrayList<String>();
                                ArrayList<ArrayList<String>> itemSpecificsValue = new ArrayList<ArrayList<String>>();
                                int itemSpecificsLength = ((JSONArray)(((JSONObject)(jsonObject.get("ItemSpecifics"))).get("NameValueList"))).length();

                                for (int i=0; i<itemSpecificsLength; i++) {
                                    itemSpecificsName.add(((JSONObject) ((JSONObject) (((JSONArray) (((JSONObject) (jsonObject.get("ItemSpecifics"))).get("NameValueList"))).get(i)))).get("Name").toString());
                                    int itemSpecificsEachLength = ((JSONArray) ((JSONObject) ((JSONObject) (((JSONArray) (((JSONObject) (jsonObject.get("ItemSpecifics"))).get("NameValueList"))).get(i)))).get("Value")).length();
                                    //System.out.println(itemSpecificsEachLength);
                                    ArrayList<String> itemSpecificsValueEach = new ArrayList<String>();
                                    for (int j = 0; j < itemSpecificsEachLength; j++) {
                                        itemSpecificsValueEach.add(((JSONArray) ((JSONObject) ((JSONObject) (((JSONArray) (((JSONObject) (jsonObject.get("ItemSpecifics"))).get("NameValueList"))).get(i)))).get("Value")).get(j).toString());
                                    }
                                    itemSpecificsValue.add(itemSpecificsValueEach);
                                }
                                detailItemProduct.setItemSpecificsName(itemSpecificsName);
                                detailItemProduct.setItemSpecificsValue(itemSpecificsValue);
                            } catch (JSONException error) {
                                ArrayList<String> itemSpecificsName = new ArrayList<String>();
                                ArrayList<ArrayList<String>> itemSpecificsValue = new ArrayList<ArrayList<String>>();
                                detailItemProduct.setItemSpecificsName(itemSpecificsName);
                                detailItemProduct.setItemSpecificsValue(itemSpecificsValue);
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
                        if(!detailItemProduct.getPictureUrl1().equals("N/A")) {
                            Picasso.with(root.getContext()).load(detailItemProduct.getPictureUrl1()).into(pictureUrl);
                        } else {
                            pictureUrl.setVisibility(View.GONE);
                        }

                        if(!detailItemProduct.getTitle().equals("N/A")){
                            title.setText(detailItemProduct.getTitle());
                        } else {
                            title.setVisibility(View.INVISIBLE);
                        }

                        if(!detailItemProduct.getSubtitle().equals("N/A")) {
                            subtitle.setText(detailItemProduct.getSubtitle());
                        } else {
                            subtitle_container.removeAllViews();
                        }

                        if(!detailItemProduct.getShipping().equals("N/A")) {
                            if (Double.parseDouble(detailItemProduct.getShipping())==0.00){
                                shipping.setText(" With Free Shipping");
                            } else {
                                shipping.setText(" With $"+Double.parseDouble(detailItemProduct.getShipping()));
                            }
                        } else {
                            shipping.setVisibility(View.INVISIBLE);
                        }

                        if(!detailItemProduct.getPrice().equals("N/A")) {
                            price1.setText("$"+detailItemProduct.getPrice());
                            price2.setText("$"+detailItemProduct.getPrice());
                        } else {
                            price1.setVisibility(View.INVISIBLE);
                            price2_container.removeAllViews();
                        }

                        if(!detailItemProduct.getSubtitle().equals("N/A")) {
                            subtitle.setText(detailItemProduct.getSubtitle());
                        } else {
                            subtitle_container.removeAllViews();
                        }

                        int findBrandIdx = NO_BRAND;
                        for (int i=0;i<detailItemProduct.getItemSpecificsName().size();i++){
                            if(detailItemProduct.getItemSpecificsName().get(i).equals("Brand")){
                                if (!detailItemProduct.getItemSpecificsValue().get(0).get(0).equals("No")){
                                    findBrandIdx = i;
                                }
                            }
                        }

                        if (findBrandIdx!=NO_BRAND){
                            brand.setText(detailItemProduct.getItemSpecificsValue().get(0).get(0));
                        }
                        else{
                            brand_container.removeAllViews();
                        }

                        if(!(detailItemProduct.getItemSpecificsValue().size()==0)) {
                            String itemSpecificsString = "";
                            for (int i=0; i<detailItemProduct.getItemSpecificsValue().size(); i++){
                                int itemSpecificsEachLength = detailItemProduct.getItemSpecificsValue().get(i).size();
                                for (int j=0; j<itemSpecificsEachLength; j++){
                                    itemSpecificsString += "\u2022 ";
                                    itemSpecificsString += detailItemProduct.getItemSpecificsValue().get(i).get(j);
                                    itemSpecificsString += "\n";
                                }
                            }
                            itemSpecifics.setText(itemSpecificsString);
                        } else {
                            specifications_container.removeAllViews();
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
