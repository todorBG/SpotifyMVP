package com.kostovtd.spotifymvp.interactor;

import android.util.Log;

import com.kostovtd.spotifymvp.model.UserProfile;
import com.kostovtd.spotifymvp.network.SpotifyAPI;
import com.kostovtd.spotifymvp.util.ApiUtils;
import com.kostovtd.spotifymvp.util.Is;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kostovtd on 27.06.17.
 */

public class UserInteractor {

    private static final String TAG = UserInteractor.class.getSimpleName();

    private UserResponseHandler responseHandler;


    public UserInteractor() {
    }


    public UserInteractor(UserResponseHandler responseHandler) {
        this.responseHandler = responseHandler;
    }


    public void fetchUserProfileData(String accessToken) {
        Log.d(TAG, "fetchUserProfileData: hit");

        if(responseHandler == null) {
            Log.e(TAG, "fetchUserProfileData: responseHandler is NULL");
            return;
        }

        if(Is.empty(accessToken)) {
            Log.e(TAG, "fetchUserProfileData: accessToken is NULL or EMPTY");
            return;
        }

        String accessBearerToken = ApiUtils.AUTHORIZATION_WORD + " " +accessToken;
        SpotifyAPI spotifyAPI = ApiUtils.getSpotifyAPI();

        spotifyAPI.getProfileData(accessBearerToken).enqueue(new Callback<UserProfile>() {
            int retry = 0;

            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                if(response.isSuccessful()) {
                    UserProfile userProfile = response.body();
                    responseHandler.onUserProfileDataFetchedSuccessfully(userProfile);
                } else {
                    int errorCode = response.code();
                    responseHandler.onErrorReceived(errorCode);
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                if(retry < ApiUtils.MAX_RETRY) {
                    retry++;
                    call.enqueue(this);
                } else {
                    responseHandler.onMaxRetryReached();
                }
            }
        });
    }


    public void setResponseHandler(UserResponseHandler responseHandler) {
        this.responseHandler = responseHandler;
    }
}
