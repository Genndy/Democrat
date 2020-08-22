package com.example.democrat.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.democrat.R;
import com.example.democrat.data.model.LoggedInUser;
import com.vk.api.sdk.VK;
import com.vk.api.sdk.VKApiCallback;
import com.vk.api.sdk.VKApiManager;
import com.vk.api.sdk.auth.VKAccessToken;
import com.vk.api.sdk.auth.VKAuthCallback;
import com.vk.api.sdk.auth.VKScope;
import com.vk.api.sdk.exceptions.VKApiException;
import com.vk.api.sdk.internal.ApiCommand;
import com.vk.api.sdk.requests.VKRequest;
import com.vk.api.sdk.utils.VKUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    Activity activity = this;
    private LoginViewModel loginViewModel;
    public String[] fingerprints;
    private VKScope[] scope = new VKScope[]{VKScope.FRIENDS, VKScope.WALL};
    VKApiCallback vkCallback;
    ApiCommand<LoggedInUser> apiCommand;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText usernameEditText = findViewById(R.id.username);
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
        vkCallback = new VKApiCallback() {
            @Override
            public void success(Object o) {
                System.out.println("vkApiCallback success");
                Object testObject = o;
            }

            @Override
            public void fail(@NotNull Exception e) {
                System.out.println("vkApiCallback fail");
                e.printStackTrace();
            }
        };



//        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                // loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

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
//                fingerprints = VKUtils.getCertificateFingerprint(this, this.getPackageName());
//                System.out.println("fingerprints: " + Arrays.asList(fingerprints));
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
        VKAuthCallback callback;
        if (!VK.onActivityResult(requestCode, resultCode, data, callback = new VKAuthCallback() {

            @Override
            public void onLogin(@NotNull VKAccessToken vkAccessToken) {
                Toast.makeText(getApplicationContext(), "Авторизация прошла успешно", Toast.LENGTH_LONG).show();
                VK.execute(apiCommand, vkCallback);
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

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (!VK.onActivityResult(requestCode, resultCode, data, new VKAuthCallback() {
//            @Override
//            public void onLogin(@NotNull VKAccessToken vkAccessToken) {
//                String userName = VKScope.WALL.name();
//                Toast toast = Toast.makeText(getApplicationContext(), "Авторизация прошла успешно. Пользователь: " + userName, Toast.LENGTH_LONG);
//                System.out.println("Авторизация прошла успешно. Пользователь: " + userName);
//            }
//
//            @Override
//            public void onLoginFailed(int i) {
//                Toast toast = Toast.makeText(getApplicationContext(), "Ошибка авторизации", Toast.LENGTH_LONG);
//            }
//        }));
//    }


//    ApiCommand<LoggedInUser> apiCommand = new ApiCommand<LoggedInUser>() {
//        @Override
//        protected LoggedInUser onExecute(@NotNull VKApiManager vkApiManager) throws InterruptedException, IOException, VKApiException {
//            VKRequest request = new VKRequest("wall.get").addParam("first_name", 1);
//            try {
//                VK.execute(request, vkCallback);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            String name = vkCallback.toString();
//            Toast.makeText(getApplicationContext(), "name", Toast.LENGTH_LONG).show();
//            return null;
//        }
//    };
//                VK.execute(apiCommand);