package com.kostovtd.spotifymvp.presenter;

import android.content.Context;
import android.util.Log;

import com.kostovtd.spotifymvp.manager.PlaylistsManager;
import com.kostovtd.spotifymvp.manager.PlaylistsManagerListener;
import com.kostovtd.spotifymvp.manager.UserManager;
import com.kostovtd.spotifymvp.model.Playlist;
import com.kostovtd.spotifymvp.model.Playlists;
import com.kostovtd.spotifymvp.model.PlaylistsResponse;
import com.kostovtd.spotifymvp.model.User;
import com.kostovtd.spotifymvp.util.Is;
import com.kostovtd.spotifymvp.view.PlaylistsView;

import java.util.List;

/**
 * Created by kostovtd on 13.07.17.
 */

public class PlaylistsPresenterImpl implements PlaylistsPresenter {

    private static final String TAG = PlaylistsPresenterImpl.class.getSimpleName();


    private Context context;
    private PlaylistsView playlistsView;


    public PlaylistsPresenterImpl(Context context, PlaylistsView playlistsView) {
        this.context = context;
        this.playlistsView = playlistsView;
    }


    @Override
    public void fetchPlaylists(String categoryId) {
        Log.d(TAG, "fetchPlaylists: hit");

        if(context == null) {
            Log.e(TAG, "fetchPlaylists: context is NULL");
            return;
        }

        if(playlistsView == null) {
            Log.e(TAG, "fetchPlaylists: playlistsView is NULL");
            return;
        }

        if(Is.empty(categoryId)) {
            Log.e(TAG, "fetchPlaylists: categoryId is NULL or EMPTY");
            return;
        }


        UserManager userManager = new UserManager(context);
        User user = userManager.getUserData();

        if(user == null) {
            Log.e(TAG, "fetchPlaylists: user is NULL");
            return;
        }

        String accessToken = user.getAccessToken().getToken();

        if(Is.empty(accessToken)) {
            Log.e(TAG, "fetchPlaylists: accessToken is NULL or EMPTY");
            return;
        }

        PlaylistsManager playlistsManager = new PlaylistsManager(context);

        playlistsManager.setListener(new PlaylistsManagerListener() {
            @Override
            public void onPlaylistsAvailable(PlaylistsResponse playlistsResponse) {
                playlistsView.hideProgress();

                if(playlistsResponse == null) {
                    Log.e(TAG, "onPlaylistsAvailable: playlistsResponse is NULL");
                    return;
                }

                Playlists playlists = playlistsResponse.getPlaylists();
                if(playlists == null) {
                    Log.e(TAG, "onPlaylistsAvailable: playlists is NULL");
                    return;
                }

                List<Playlist> playlistItems = playlists.getItems();
                if(playlistItems != null) {
                    playlistsView.showPlaylists(playlistItems);
                } else {
                    Log.e(TAG, "onPlaylistsAvailable: playlistItems is NULL");
                }
            }
        });

        playlistsView.showProgress();
        playlistsManager.fetchPlaylists(accessToken, categoryId);
    }
}
