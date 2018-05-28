package com.karen.photopicker.utils;

import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

public class GraphUtils {
    public static void getPhotos(String id) {
        GraphRequest request = GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(),
                "/fcbarcelona",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        if (response.getError() == null) {
                            GraphRequest graphRequest = response.getRequest();
                            String s = graphRequest.getGraphObject().toString();
                            int a = 0;
                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "albums{id,count,name,cover_photo{id,source},photos{id,source}}");
        request.setParameters(parameters);
        request.executeAsync();
    }
}