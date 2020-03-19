package com.example.deardoctor.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;

import com.example.deardoctor.BasicActivity;
import com.example.deardoctor.R;
import com.example.deardoctor.Register.Doctor_Register;
import com.example.deardoctor.Register.Login.Login;
import com.example.deardoctor.Register.SignUp;
import com.example.deardoctor.constants.FragmentStateManager;
import com.example.deardoctor.constants.KeepStateNavigator;
import com.example.deardoctor.doctor.DoctorAdd;
import com.example.deardoctor.slots.Slots;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.IllegalFormatCodePointException;
import java.util.Objects;
import java.util.Stack;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("Registered")
public class BottomNavigator extends BasicActivity {
    @BindView(R.id.screen_container)
    FrameLayout screenContainer;

    private HashMap<String, Stack<Fragment>> mStacks;
    public static BottomNavigationView bottomNavigation;
    public static final String TAB_HOME  = "home";
    public static final String TAB_SHOES  = "shoes";
    public static final String TAB_SEARCH = "search";
    public static final String TAB_NEWS = "news";
    public static final String TAB_PROFILE = "profile";
    private String mCurrentTab;
     Fragment   homeScreen = new Doctor_Register();
     Fragment register = new  Doctor_Register();
    SparseArray<Fragment> myFragments;
    int previousIndex;

    FragmentStateManager fragmentStateManager;
    final FragmentManager fm = getSupportFragmentManager();
    Fragment fragments;

    @Override
    protected void backClick() {

    }

    @Override
    protected void onViewBound(Bundle savedInstanceState) {
        myFragments = new SparseArray<Fragment>();
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mStacks = new HashMap<String, Stack<Fragment>>();
        mStacks.put(TAB_HOME, new Stack<Fragment>());
        mStacks.put(TAB_SHOES, new Stack<Fragment>());
        mStacks.put(TAB_SEARCH, new Stack<Fragment>());
        mStacks.put(TAB_PROFILE, new Stack<>());
        mStacks.put(TAB_NEWS, new Stack<>());
        selectedTab(TAB_HOME);
    }

    @Override
    protected int layout() {
        return R.layout.bootom_navigator;
    }
    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = item -> {

        switch (item.getItemId()) {
            case R.id.navigation_home:
                selectedTab(TAB_HOME);
                return true;
            case R.id.navigation_shoes:
                selectedTab(TAB_SHOES);
                return true;
            case R.id.navigation_search:
                selectedTab(TAB_SEARCH);
                return true;
            case R.id.navigation_cart:
                selectedTab(TAB_PROFILE);
                return true;
            case R.id.navigation_profile:
                selectedTab(TAB_NEWS);
                return true;

        }
        return false;
    };


    private void selectedTab(String tabId) {
        switch (tabId) {
            case TAB_HOME:
                fragments = new Doctor_Register();
                Log.i("TAG","Available"+ fragments.getClass().getName());
                changeFragment(fragments,TAB_HOME);

                break;
            case TAB_SHOES:
                fragments = new DoctorAdd();
                changeFragment(fragments,TAB_SHOES);

                break;
            case TAB_SEARCH:
                break;
            case TAB_PROFILE:
                fragments = new Slots();
                changeFragment(fragments,TAB_PROFILE);
//                fragment = myFragments.get(3);
//                if (fragment == null) {
//                    fragment = new Slots();
//                    myFragments.put(3, fragment);
//                }

                break;
            case TAB_NEWS:
                break;

        }


    }

    public void changeFragment(Fragment fragment, String tagFragmentName) {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Fragment currentFragment = mFragmentManager.getPrimaryNavigationFragment();
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }
        Fragment fragmentTemp = mFragmentManager.findFragmentByTag(tagFragmentName);
        if (fragmentTemp == null) {
            fragmentTemp = fragment;
            fragmentTransaction.add(R.id.screen_container, fragmentTemp, tagFragmentName);
        } else {
            fragmentTransaction.show(fragmentTemp);
        }
        fragmentTransaction.setPrimaryNavigationFragment(fragmentTemp);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitNowAllowingStateLoss();
    }


}
