package com.bluesoftx.noone.Adapter;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.bluesoftx.noone.Activity.MainActivity;
import com.bluesoftx.noone.Activity.SplashActivity;
import com.bluesoftx.noone.Fragment.HomeFragment;
import com.bluesoftx.noone.Fragment.SearchFragment;
import com.bluesoftx.noone.Fragment.SignUpFragment;
import com.bluesoftx.noone.Fragment.SplashImageFragment;
import com.bluesoftx.noone.Fragment.UserFragment;
import com.bluesoftx.noone.Utilities.FirebaseParameters;
import com.bluesoftx.noone.Utilities.Parameters;

import java.util.ArrayList;
import java.util.List;

public class MainViewPagerAdapter extends FragmentStateAdapter {


    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private FragmentManager manager;

    public MainViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    public void addFragment(Fragment fragment, String title, int index) {
        mFragmentList.add(index,fragment);
        mFragmentTitleList.add(index,title);
    }
    public void removeFragment() {
        for(int i = 0; i< mFragmentList.size() ;i++){
            mFragmentList.remove(i);
            mFragmentTitleList.remove(i);
        }
        notifyDataSetChanged();
    }
    public void addAllFragments() {
        HomeFragment homeFragment = new HomeFragment();
        UserFragment userFragment = new UserFragment();
        this.addFragment(homeFragment,"home",0);
        this.addFragment(userFragment,"user",1);
    }

    public void showSplashImage(){
        SplashImageFragment splashImageFragment = new SplashImageFragment();
        this.addFragment(splashImageFragment, "splash",0);
    }
    public void showSignUp(){
        SignUpFragment signUpFragment = new SignUpFragment();
        this.addFragment(signUpFragment,"signup",0);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragmentList.get(position);
    }



    @Override
    public int getItemCount() {
        return mFragmentList.size();
    }
}
