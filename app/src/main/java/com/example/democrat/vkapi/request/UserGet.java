package com.example.democrat.vkapi.request;

import android.os.Parcelable;

import com.example.democrat.data.model.LoggedInUser;
import com.vk.api.sdk.requests.VKRequest;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

// LoggedInUser instead String must be (c) Yoda
public class UserGet<Random> extends VKRequest<Parcelable> {
    // account.getProfileInfo. Require a Standalone key through Implicit Flow
    LoggedInUser user;
    public UserGet(@NotNull String method) {
        super(method);
        // This method may requires a user ID
//        method = "account.getProfileInfo";
//        addParam("fields", method);
    }

    @Override
    public LoggedInUser parse(@NotNull JSONObject r) throws Exception {
        int id = 0;
        String firstName = r.getJSONObject("response").getString("first_name");
        String lastName = r.getJSONObject("response").getString("last_name");
        String photo = null; // Надо будет потом достать фото
        Boolean deactivated = true;
        user = new LoggedInUser(id, firstName, lastName, "null", true);
        return user;
    }
}

/*
    int id = 0;
    String firstName = ""; first_name
    String lastName = ""; last_name
    String photo = "";
    Boolean deactivated = false;

            Intrinsics.checkParameterIsNotNull(json, "json");
            int id = json.optInt("id", 0);
            Intrinsics.checkExpressionValueIsNotNull("first_name", "json.optString(\"first_name\", \"\")");
            String firstName = json.optString("first_name", "");
            Intrinsics.checkExpressionValueIsNotNull("last_name", "json.optString(\"last_name\", \"\")");
            String lastName = json.optString("last_name", "");
            Intrinsics.checkExpressionValueIsNotNull("photo", "json.optString(\"photo_200\", \"\")");
            String photo = json.optString("photo_200", "");
            return new LoggedInUser(id, firstName, lastName, photo, json.optBoolean("deactivated", false));
 */