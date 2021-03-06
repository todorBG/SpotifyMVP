package com.kostovtd.spotifymvp.interactor;

import com.kostovtd.spotifymvp.base.BaseResponseHandler;
import com.kostovtd.spotifymvp.model.PlaylistsResponse;

/**
 * Created by kostovtd on 13.07.17.
 */

public interface PlaylistsResponseHandler extends BaseResponseHandler {

    void onPlaylistsFetchedSuccessfully(PlaylistsResponse playlistsResponse);
}
