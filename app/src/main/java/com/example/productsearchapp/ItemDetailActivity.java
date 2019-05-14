package com.example.productsearchapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.example.productsearchapp.adapter.ItemDetailViewPagerAdapter;
import com.example.productsearchapp.adapter.SearhListRecyclerViewAdapter;
import java.util.ArrayList;

public class ItemDetailActivity extends AppCompatActivity {
    private ViewPager viewPager ;
    private ItemDetailViewPagerAdapter detailViewPagerAdapter;
    private String itemId;
    private String itemTitle;
    private String itemShipping;
    private String itemViewUrl;
    private String itemPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        Intent intent = getIntent();
        itemId = intent.getStringExtra(SearhListRecyclerViewAdapter.ITEMID);
        itemTitle = intent.getStringExtra(SearhListRecyclerViewAdapter.ITEMTITLE);
        itemShipping = intent.getStringExtra(SearhListRecyclerViewAdapter.ITEMSHIPPING);
        itemViewUrl = intent.getStringExtra(SearhListRecyclerViewAdapter.ITEMVIEWURL);
        itemPrice = intent.getStringExtra(SearhListRecyclerViewAdapter.ITEMPRICE);

        // setTitle
        this.setTitle(itemTitle);

        // make each fragment page
        viewPager = (ViewPager) findViewById(R.id.viewPager2);
        viewPager.setVisibility(View.INVISIBLE);
        detailViewPagerAdapter = new ItemDetailViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(detailViewPagerAdapter);

        // make tabs for each page
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout2);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.information_variant);
        tabLayout.getTabAt(1).setIcon(R.drawable.truck_delivery);
        tabLayout.getTabAt(2).setIcon(R.drawable.google);
        tabLayout.getTabAt(3).setIcon(R.drawable.equal);

        // progressbar
        TaskUsingProgressbar task = new TaskUsingProgressbar();
        task.execute();

    }

    // progressbar
    private class TaskUsingProgressbar extends AsyncTask<Void, Void, Void> {
        ProgressDialog asyncDialog = new ProgressDialog(ItemDetailActivity.this);

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
            viewPager.setVisibility(View.VISIBLE);
            super.onPostExecute(result);
        }
    }

    // make new menu for itemDetailActivity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item_detail, menu);
        return true;
    }

    // set toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                return true;
            }
            case R.id.facebook:{
                String quote="Buy "+itemTitle+" for $"+itemPrice+" from Ebay!";
                String href=itemViewUrl;
                String hashtag="#CSCI571SPRING2019Ebay";
                Uri uri = Uri.parse("https://www.facebook.com/v3.2/dialog/share?app_id=2028972824062745&channel_url=https%3A%2F%2Fstaticxx.facebook.com%2Fconnect%2Fxd_arbiter%2Fr%2Fd_vbiawPdxB.js%3Fversion%3D44%23cb%3Df41889e0028%26domain%3Ddongchulchoi.us-east-2.elasticbeanstalk.com%26origin%3Dhttp%253A%252F%252Fdongchulchoi.us-east-2.elasticbeanstalk.com%252Ff181fbd0fa24ad%26relation%3Dopener&display=popup&e2e=%7B%7D&fallback_redirect_uri=http%3A%2F%2Fdongchulchoi.us-east-2.elasticbeanstalk.com%2F&href="+href+"&locale=en_US&next=https%3A%2F%2Fstaticxx.facebook.com%2Fconnect%2Fxd_arbiter%2Fr%2Fd_vbiawPdxB.js%3Fversion%3D44%23cb%3Df3be1ef881b2a7c%26domain%3Ddongchulchoi.us-east-2.elasticbeanstalk.com%26origin%3Dhttp%253A%252F%252Fdongchulchoi.us-east-2.elasticbeanstalk.com%252Ff181fbd0fa24ad%26relation%3Dopener%26frame%3Df3faeac3d68bfa%26result%3D%2522xxRESULTTOKENxx%2522"+"&quote="+quote+"&sdk=joey&version=v3.2");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    // getter for fragment
    public ArrayList<String> getData(){
        ArrayList<String> dataArrayList = new ArrayList<String>();
        dataArrayList.add(itemId);
        dataArrayList.add(itemTitle);
        dataArrayList.add(itemShipping);
        return dataArrayList;
    }

}
