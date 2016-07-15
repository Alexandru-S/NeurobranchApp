package com.glassbyte.neurobranch.Services.Helpers;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.glassbyte.neurobranch.R;

/**
 * Created by ed on 06/07/2016.
 */
public class Fragments {
    public static void setFragment(android.support.v4.app.FragmentManager fragmentManager,
                                   android.support.v4.app.Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.portal_frame, fragment).commit();
    }

    public static class AsyncSetFrag extends AsyncTask<Fragment, String, Void> {
        Fragment fragment;
        FragmentManager fragmentManager;

        public AsyncSetFrag(FragmentManager fragmentManager, Fragment fragment) {
            this.fragment = fragment;
            this.fragmentManager = fragmentManager;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Fragment... fragments) {
            Fragments.setFragment(fragmentManager, fragment);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
