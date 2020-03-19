package com.example.deardoctor.constants;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class KeepStateNavigator {




   @SuppressLint("Assert")
   public static Fragment addFragment(Context context, Fragment destination, FragmentManager manager, int containerId) {

        int tag = destination.getId();
        FragmentTransaction transaction = manager.beginTransaction();
        boolean initialNavigate = false;
        Fragment curext = manager.getPrimaryNavigationFragment();
        if (curext != null) {
            transaction.detach(curext);
        } else {
            initialNavigate = true;
        }
        Fragment fragment = manager.findFragmentById(tag);
        if (fragment != null) {
            String className = destination.getClass().getName();
            fragment = manager.getFragmentFactory().instantiate(context.getClassLoader(), className);
            transaction.add(containerId, fragment, String.valueOf(tag));
        } else {
            assert false;
            transaction.attach(fragment);
        }
        transaction.setPrimaryNavigationFragment(fragment);
        transaction.setReorderingAllowed(true);
        transaction.commit();
        return initialNavigate ? destination : null;
    }
}

  /*  override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ): NavDestination? {
        val tag = destination.id.toString()
        val transaction = manager.beginTransaction()

        var initialNavigate = false
        val currentFragment = manager.primaryNavigationFragment
        if (currentFragment != null) {
            transaction.detach(currentFragment)
        } else {
            initialNavigate = true
        }

        var fragment = manager.findFragmentByTag(tag)
        if (fragment == null) {
            val className = destination.className
            fragment = manager.fragmentFactory.instantiate(context.classLoader, className)
            transaction.add(containerId, fragment, tag)
        } else {
            transaction.attach(fragment)
        }

        transaction.setPrimaryNavigationFragment(fragment)
        transaction.setReorderingAllowed(true)
        transaction.commitNow()

        return if (initialNavigate) {
            destination
        } else {
            null
        }
    }
}*/
