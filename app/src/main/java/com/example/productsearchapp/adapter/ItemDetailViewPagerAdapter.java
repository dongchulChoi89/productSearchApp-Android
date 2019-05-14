package com.example.productsearchapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.example.productsearchapp.fragment.FragmentPhotos;
import com.example.productsearchapp.fragment.FragmentProduct;
import com.example.productsearchapp.fragment.FragmentShipping;
import com.example.productsearchapp.fragment.FragmentSimilar;

public class ItemDetailViewPagerAdapter extends FragmentPagerAdapter {
    public ItemDetailViewPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                return new FragmentProduct();
            case 1:
                return new FragmentShipping();
            case 2:
                return new FragmentPhotos();
            case 3:
                return new FragmentSimilar();
        }
        return null;
    }

    @Override
    public int getCount(){
        return 4;
    }

    // tab title
    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0){
            return "Product";
        } else if (position==1){
            return "Shipping";
        } else if (position==2){
            return "Photos";
        } else {
            return "Similar";
        }
    }
}
