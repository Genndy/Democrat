package com.example.democrat.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.database.sqlite.*;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.example.democrat.R;
import com.example.democrat.data.model.LoggedInUser;
import com.example.democrat.ui.ProfileActivity;
import com.example.democrat.vkapi.request.UserGet;
import com.vk.api.sdk.VK;
import com.vk.api.sdk.VKApiCallback;
import com.vk.api.sdk.VKApiConfig;
import com.vk.api.sdk.VKApiManager;
import com.vk.api.sdk.auth.VKAccessToken;
import com.vk.api.sdk.auth.VKAuthCallback;
import com.vk.api.sdk.auth.VKScope;
import com.vk.api.sdk.exceptions.VKApiException;
import com.vk.api.sdk.internal.ApiCommand;
import com.vk.api.sdk.utils.VKUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    Activity activity = this;
    private LoginViewModel loginViewModel;
    public String[] fingerprints;
    private VKScope[] scope = new VKScope[]{VKScope.FRIENDS, VKScope.WALL};
    VKApiCallback<String> vkCallback;
    ApiCommand<LoggedInUser> apiCommand;

    private String userName;
    private EditText usernameEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final ImageButton loginButton = findViewById(R.id.login);
        final ImageButton VKButton = findViewById(R.id.vk_button);
        apiCommand = new ApiCommand<LoggedInUser>() {
            @Override
            protected LoggedInUser onExecute(@NotNull VKApiManager vkApiManager) throws InterruptedException, IOException, VKApiException {
                System.out.println("apiCommand");
                return null;
            }
        };

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
        VKButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                fingerprints = VKUtils.getCertificateFingerprint(LoginActivity.super.getApplicationContext(), LoginActivity.super.getPackageName());
                System.out.println("fingerprints: " + Arrays.asList(fingerprints));
                VK.login(activity, Arrays.asList(scope));
            }
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final VKAuthCallback callback;
        if (!VK.onActivityResult(requestCode, resultCode, data, callback = new VKAuthCallback() {

            @Override
            public void onLogin(@NotNull VKAccessToken vkAccessToken) {
                Toast.makeText(getApplicationContext(), "Авторизация прошла успешно", Toast.LENGTH_LONG).show();
                System.out.println("Авторизация прошла успешно");

//                VKApiConfig testConfig = new VKApiConfigBuilderJava(getApplicationContext()).setLang("ru")
//                        .getVKApiConfig();
//                VK.setConfig(testConfig);
//                System.out.println("VKApiCpnfig прошёл");
                // Request to VK 
             //   VK.execute(new UserGet<LoggedInUser>("account.getProfileInfo"), new VKApiCallback(){
                VK.execute(new UserGet<String>("account.getProfileInfo"), new VKApiCallback(){
                    @Override
                    public void success(Object o) {
                        System.out.println("vkApiCallback success");
                        Object testObject = o;
                        System.out.println(o.getClass());
                        try {
                            JSONObject jObject = new JSONObject(o.toString());
                            userName = jObject.getString("name");
                            usernameEditText.setText(userName);

                            Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void fail(@NotNull Exception e) {
                        System.out.println("vkApiCallback fail");
                        e.printStackTrace();
                    }
                });
            }

            @Override
            public void onLoginFailed(int i) {
                Toast.makeText(getApplicationContext(), "Ошибка авторизации", Toast.LENGTH_LONG).show();
            }
        }));
        if(data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)){
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}

