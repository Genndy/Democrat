package com.example.democrat.app;
import android.content.Intent;

import com.example.democrat.ui.login.LoginActivity;
import com.vk.api.sdk.VK;
import com.vk.api.sdk.VKTokenExpiredHandler;
import com.vk.api.sdk.auth.VKAccessToken;
import com.vk.api.sdk.auth.VKAccessToken.Companion;
import com.vk.api.sdk.auth.VKAuthCallback;

public class Application extends android.app.Application {

    VKTokenExpiredHandler tokenExpiredHandler =  new VKTokenExpiredHandler() {
        @Override
        public void onTokenExpired() {
            // Token expired.. what is it mean?
            System.out.println("Token expired");
        }

//        @Override
//        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
//            if (newToken == null) {
//                Intent intent = new Intent(Application.this, LoginActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//            }
//        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        VK.addTokenExpiredHandler(tokenExpiredHandler);
    }
}
