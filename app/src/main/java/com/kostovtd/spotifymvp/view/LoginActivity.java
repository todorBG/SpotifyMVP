package com.kostovtd.spotifymvp.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kostovtd.spotifymvp.R;
import com.kostovtd.spotifymvp.base.BaseActivity;
import com.kostovtd.spotifymvp.manager.AuthenticationManager;
import com.kostovtd.spotifymvp.presenter.LoginPresenter;
import com.kostovtd.spotifymvp.presenter.LoginPresenterImpl;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kostovtd on 21.06.17.
 */
public class LoginActivity extends BaseActivity implements LoginView {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private LoginPresenter loginPresenter;

    @BindView(R.id.login_button)
    Button bLogin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: hit");
        super.onCreate(savedInstanceState);
        setTitle(R.string.login_screen_title);

        ButterKnife.bind(this);

        loginPresenter = new LoginPresenterImpl(this);

        // login button click
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            loginPresenter.authenticate();
            }
        });
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
        return R.layout.login_activity;
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == AuthenticationManager.REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);

            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:
                    // Handle successful response
                    Log.i(TAG, "token obtained");
                    loginPresenter.successfulAuthentication(response);
                    break;

                // Auth flow returned an error
                case ERROR:
                    // Handle error response
                    Log.i(TAG, "error while obtaining a token");
                    Toast.makeText(this, getString(R.string.login_screen_login_failed_msg), Toast.LENGTH_SHORT).show();
                    break;

                // Most likely auth flow was cancelled
                default:
                    // Handle other cases
                    Log.i(TAG, "error while obtaining a token");
                    Toast.makeText(this, getString(R.string.login_screen_login_failed_msg), Toast.LENGTH_SHORT).show();
            }
        }
    }
}