package com.karen.photopicker.utils.fragment_utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import com.karen.photopicker.R;

import java.util.HashMap;
import java.util.Map;


public class FragmentUtils {

    private FragmentUtils() {
        //no instance
        throw new RuntimeException("Private constructor cannot be accessed");
    }

    public static void openFragment(Fragment fragment,
                                    FragmentManager fragmentManager,
                                    boolean withBackStack,
                                    boolean customAnimation,
                                    FragmentOpenType openType,
                                    FragmentTag fragmentTag) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        if (openType == FragmentOpenType.ADD) {
            if (fragmentTag == null)
                fragmentTransaction.add(R.id.home_activity_container, fragment);
            else
                fragmentTransaction.add(R.id.home_activity_container, fragment, fragmentTag.toString());
        } else {
            if (fragmentTag == null)
                fragmentTransaction.replace(R.id.home_activity_container, fragment);
            else
                fragmentTransaction.replace(R.id.home_activity_container, fragment, fragmentTag.toString());
        }
        if (withBackStack)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public static void openFragment(Fragment fragment,
                                    FragmentManager fragmentManager,
                                    boolean withBackStack,
                                    FragmentOpenType openType,
                                    FragmentTag fragmentTag) {
        openFragment(fragment, fragmentManager, withBackStack, false, openType, fragmentTag);
    }

    public static boolean isFragmentExist(FragmentManager fragmentManager, FragmentTag fragmentTag) {
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTag.toString());
        return fragment != null;
    }

    public static void showSelectedFragment(Fragment fragment,
                                            FragmentTag fragmentTag,
                                            FragmentManager manager,
                                            HashMap<FragmentTag, Fragment> map) {
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        for (FragmentTag tag : map.keySet()) {
            Fragment currentFragment = map.get(tag);
            if (tag == fragmentTag) {
                fragmentTransaction.show(currentFragment);
            } else {
                fragmentTransaction.hide(currentFragment);
            }
        }
        fragmentTransaction.commit();
        addFragmentIfNeeded(fragmentTag, fragment, manager, map);
    }

    private static void addFragmentIfNeeded(FragmentTag key,
                                            Fragment fragment,
                                            FragmentManager manager,
                                            HashMap<FragmentTag, Fragment> map) {
        if (!map.containsKey(key)) {
            map.put(key, fragment);
            if (!isFragmentExist(manager, key)) {
                FragmentUtils.openFragment(fragment,
                        manager,
                        false,
                        FragmentOpenType.ADD,
                        key);
            }
        }

        for (Map.Entry<FragmentTag, Fragment> entry : map.entrySet()) {
            FragmentTag fragmentTag = entry.getKey();
            Fragment value = entry.getValue();
            //Logger.log("addFragmentIfNeeded " + fragmentTag + " " + value);
        }
    }
}
