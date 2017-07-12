package com.kostovtd.spotifymvp.view;

import android.os.Bundle;
import android.util.Log;

import com.kostovtd.spotifymvp.R;
import com.kostovtd.spotifymvp.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created by kostovtd on 12.07.17.
 */
public class CategoriesActivity extends BaseActivity implements CategoriesView {

    private static final String TAG = CategoriesActivity.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: hit");
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        setTitle(R.string.categories_screen_title);

        ButterKnife.bind(this);


    }


    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: hit");
        super.onResume();
    }


    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: hit");
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: hit");
        super.onDestroy();
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.categories_layout;
    }
}