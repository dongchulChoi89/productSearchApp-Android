package com.example.productsearchapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.productsearchapp.ItemDetailActivity;
import com.example.productsearchapp.R;
import com.example.productsearchapp.pojo.DetailItemPhotos;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class FragmentPhotos extends Fragment {
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private DetailItemPhotos detailItemPhotos;

    private ImageView similar_image_1;
    private ImageView similar_image_2;
    private ImageView similar_image_3;
    private ImageView similar_image_4;
    private ImageView similar_image_5;
    private ImageView similar_image_6;
    private ImageView similar_image_7;
    private ImageView similar_image_8;
    private LinearLayout similar_image_container;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View root = inflater.inflate(R.layout.fragment_photos,container,false);

        ItemDetailActivity itemDetailActivity = (ItemDetailActivity) getActivity();
        ArrayList<String> dataArrayList = itemDetailActivity.getData();
        String itemTitle = dataArrayList.get(1);

        similar_image_1 = (ImageView) root.findViewById(R.id.similar_image_1);
        similar_image_2 = (ImageView) root.findViewById(R.id.similar_image_2);
        similar_image_3 = (ImageView) root.findViewById(R.id.similar_image_3);
        similar_image_4 = (ImageView) root.findViewById(R.id.similar_image_4);
        similar_image_5 = (ImageView) root.findViewById(R.id.similar_image_5);
        similar_image_6 = (ImageView) root.findViewById(R.id.similar_image_6);
        similar_image_7 = (ImageView) root.findViewById(R.id.similar_image_7);
        similar_image_8 = (ImageView) root.findViewById(R.id.similar_image_8);

        // Request API && Volley
        String cx = "014699657342441021403:opyoecxfxbe";
        String key = "AIzaSyArzhxFKMrcCvxdDt6ZHYhtRamCMFrHnBY";
        String url = "https://www.googleapis.com/customsearch/v1?q="+itemTitle+"&cx="+cx+"&imgSize=huge&imgType=news&num=8&searchType=image&key="+key;
        RequestQueue queue = Volley.newRequestQueue(root.getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                jsonObject = response;
                detailItemPhotos = new DetailItemPhotos();

                try {
                    jsonArray = ((JSONArray)jsonObject.get("items"));

                    try {
                        detailItemPhotos.setImageUrl1(((JSONObject)jsonArray.get(0)).get("link").toString());
                    } catch (JSONException error) {
                        detailItemPhotos.setImageUrl1("N/A");
                        System.out.println("Request Failed: " + error);
                    }
                    try {
                        detailItemPhotos.setImageUrl2(((JSONObject)jsonArray.get(1)).get("link").toString());
                    } catch (JSONException error) {
                        detailItemPhotos.setImageUrl2("N/A");
                        System.out.println("Request Failed: " + error);
                    }
                    try {
                        detailItemPhotos.setImageUrl3(((JSONObject)jsonArray.get(2)).get("link").toString());
                    } catch (JSONException error) {
                        detailItemPhotos.setImageUrl3("N/A");
                        System.out.println("Request Failed: " + error);
                    }
                    try {
                        detailItemPhotos.setImageUrl4(((JSONObject)jsonArray.get(3)).get("link").toString());
                    } catch (JSONException error) {
                        detailItemPhotos.setImageUrl4("N/A");
                        System.out.println("Request Failed: " + error);
                    }
                    try {
                        detailItemPhotos.setImageUrl5(((JSONObject)jsonArray.get(4)).get("link").toString());
                    } catch (JSONException error) {
                        detailItemPhotos.setImageUrl5("N/A");
                        System.out.println("Request Failed: " + error);
                    }
                    try {
                        detailItemPhotos.setImageUrl6(((JSONObject)jsonArray.get(5)).get("link").toString());
                    } catch (JSONException error) {
                        detailItemPhotos.setImageUrl6("N/A");
                        System.out.println("Request Failed: " + error);
                    }
                    try {
                        detailItemPhotos.setImageUrl7(((JSONObject)jsonArray.get(6)).get("link").toString());
                    } catch (JSONException error) {
                        detailItemPhotos.setImageUrl7("N/A");
                        System.out.println("Request Failed: " + error);
                    }
                    try {
                        detailItemPhotos.setImageUrl8(((JSONObject)jsonArray.get(7)).get("link").toString());
                    } catch (JSONException error) {
                        detailItemPhotos.setImageUrl8("N/A");
                        System.out.println("Request Failed: " + error);
                    }


                } catch (JSONException error) {
                    String errorMessage=null;
                    try{
                        errorMessage = ((JSONObject)jsonObject.get("error")).get("message").toString();
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
                if(!detailItemPhotos.getImageUrl1().equals("N/A")) {
                    Picasso.with(root.getContext()).load(detailItemPhotos.getImageUrl1()).into(similar_image_1);
                } else {
                    similar_image_container.removeView(similar_image_1);
                }
                if(!detailItemPhotos.getImageUrl2().equals("N/A")) {
                    Picasso.with(root.getContext()).load(detailItemPhotos.getImageUrl2()).into(similar_image_2);
                } else {
                    similar_image_container.removeView(similar_image_2);
                }
                if(!detailItemPhotos.getImageUrl3().equals("N/A")) {
                    Picasso.with(root.getContext()).load(detailItemPhotos.getImageUrl3()).into(similar_image_3);
                } else {
                    similar_image_container.removeView(similar_image_3);
                }
                if(!detailItemPhotos.getImageUrl4().equals("N/A")) {
                    Picasso.with(root.getContext()).load(detailItemPhotos.getImageUrl4()).into(similar_image_4);
                } else {
                    similar_image_container.removeView(similar_image_4);
                }
                if(!detailItemPhotos.getImageUrl5().equals("N/A")) {
                    Picasso.with(root.getContext()).load(detailItemPhotos.getImageUrl5()).into(similar_image_5);
                } else {
                    similar_image_container.removeView(similar_image_5);
                }
                if(!detailItemPhotos.getImageUrl6().equals("N/A")) {
                    Picasso.with(root.getContext()).load(detailItemPhotos.getImageUrl6()).into(similar_image_6);
                } else {
                    similar_image_container.removeView(similar_image_6);
                }
                if(!detailItemPhotos.getImageUrl7().equals("N/A")) {
                    Picasso.with(root.getContext()).load(detailItemPhotos.getImageUrl7()).into(similar_image_7);
                } else {
                    similar_image_container.removeView(similar_image_7);
                }
                if(!detailItemPhotos.getImageUrl8().equals("N/A")) {
                    Picasso.with(root.getContext()).load(detailItemPhotos.getImageUrl8()).into(similar_image_8);
                } else {
                    similar_image_container.removeView(similar_image_8);
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
