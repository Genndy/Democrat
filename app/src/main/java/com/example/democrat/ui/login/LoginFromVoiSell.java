//package com.example.democrat.ui.login;
//
//        import android.app.ProgressDialog;
//        import android.content.Intent;
//        import android.support.v7.app.AppCompatActivity;
//        import android.os.Bundle;
//        import android.util.Log;
//        import android.view.View;
//        import android.widget.ImageButton;
//        import android.widget.LinearLayout;
//        import android.widget.Toast;
//
//        import com.crashlytics.android.Crashlytics;
//        import com.facebook.AccessToken;
//        import com.facebook.AccessTokenTracker;
//        import com.facebook.CallbackManager;
//        import com.facebook.FacebookCallback;
//        import com.facebook.FacebookException;
//        import com.facebook.login.LoginManager;
//        import com.facebook.login.LoginResult;
//        import com.facebook.login.widget.LoginButton;
//        import com.google.android.gms.tasks.OnSuccessListener;
//        import com.google.firebase.iid.FirebaseInstanceId;
//        import com.google.firebase.iid.InstanceIdResult;
//        import com.vk.sdk.VKAccessToken;
//        import com.vk.sdk.VKCallback;
//        import com.vk.sdk.VKScope;
//        import com.vk.sdk.VKSdk;
//        import com.vk.sdk.api.VKError;
//        import com.voisell.android.R;
//        import com.voisell.android.VoisellApplication;
//        import com.voisell.android.api.response.ResponseLoginSocUser;
//        import com.voisell.android.model.Config;
//        import com.voisell.android.model.CustomRetrofitCallback;
//        import com.voisell.android.model.ErrorRequest;
//
//        import java.util.Arrays;
//
//        import retrofit2.Call;
//        import retrofit2.Response;
//
//        import static com.vk.sdk.api.VKError.VK_API_ERROR;
//
//public class LoginFromVoiSell extends AppCompatActivity {
//
//    private LinearLayout mBtnLoginVK;
//    private LinearLayout mBtnLoginFB;
//    //private LoginButton mFbLoginButton;
//    private LoginActivity _instance = this;
//    private CallbackManager callbackManager;
//    private LoginManager loginManager;
//    private ProgressDialog mProgressDialog;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( LoginActivity.this,  new OnSuccessListener<InstanceIdResult>() {
//            @Override
//            public void onSuccess(InstanceIdResult instanceIdResult) {
//                String newToken = instanceIdResult.getToken();
//                Config.setFirebaseToken(getApplicationContext(), newToken);
//            }
//        });
//
//        callbackManager = CallbackManager.Factory.create();
//        loginManager = LoginManager.getInstance();
//
//        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                // Пользователь успешно авторизовался
//                Log.w("FB TOKEN", loginResult.getAccessToken().getToken());
//                fbLogin(loginResult.getAccessToken());
//            }
//
//            @Override
//            public void onCancel() {
//                // App code
//                Log.e("FB", "CANCEL");
//            }
//
//            @Override
//            public void onError(FacebookException exception) {
//                // App code
//                Crashlytics.log("FB ERROR LOGIN. exception: "+exception.toString());
//                Toast.makeText(getApplicationContext(), "Ошибка авторизации. Повторите позже или обратитесь в техподдержку", Toast.LENGTH_LONG).show();
//            }
//        });
//
//        //mFbLoginButton = findViewById(R.id.login_button_facebook);
//
//        mBtnLoginVK = (LinearLayout)findViewById(R.id.btn_login_vk);
//        mBtnLoginFB = (LinearLayout)findViewById(R.id.btn_login_facebook);
//        mBtnLoginFB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                loginManager.logInWithReadPermissions(LoginActivity.this, Arrays.asList("email"));
//            }
//        });
//
//        mBtnLoginVK.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                VKSdk.login(_instance, VKScope.OFFLINE);
//            }
//        });
//
//        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
//            @Override
//            protected void onCurrentAccessTokenChanged(
//                    AccessToken oldAccessToken,
//                    AccessToken currentAccessToken) {
//                fbLogin(currentAccessToken);
//                // Set the access token using
//                // currentAccessToken when it's loaded or set.
//            }
//        };
//        // If the access token is available already assign it.
//        AccessToken accessToken = AccessToken.getCurrentAccessToken();
//
//        if (accessToken != null && accessToken.getToken() != null && accessToken.getUserId() != null) {
//            fbLogin(accessToken);
//            Log.e("AccessToken", accessToken.getToken());
//        }
//
////        mBtnLoginFB.setReadPermissions("email");
//
//    }
//
//    @Override
//    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
//        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
//            @Override
//            public void onResult(VKAccessToken res) {
//                // Пользователь успешно авторизовался
//                VoisellApplication.getApi().loginSocUser("vk", res.userId, res.accessToken, Config.getFirebaseToken(getApplicationContext())).enqueue(new CustomRetrofitCallback<ResponseLoginSocUser>(LoginActivity.this) {
//                    @Override
//                    public void onRequestSuccess(Call<ResponseLoginSocUser> call, Response<ResponseLoginSocUser> response) {
//                        if (response.body().getSuccess()) {
//                            Config.setAuthKey(getApplicationContext(), response.body().getUserKey().getKey());
//
//                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                            if (response.body().getUserKey().getNewUser() != 0) {
//                                intent.putExtra("newUser", true);
//                            }
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            startActivity(intent);
//                            overridePendingTransition(0,0);
//
//                        }
//                        else {
//                            Log.d("VK LOG ERR 2", response.body().getMessage());
//                        }
//                    }
//
//                    @Override
//                    public void onRequestFail(Call<ResponseLoginSocUser> call, ErrorRequest err) {
//                        Log.d("VK LOG ERR", err.getMessage()+" - "+err.getMessage_t());
//                    }
//                });
//            }
//            @Override
//            public void onError(VKError error) {
//                // Произошла ошибка авторизации (например, пользователь запретил авторизацию)
//                Crashlytics.log("VK ERROR LOGIN. CODE: "+error.errorCode);
//                Toast.makeText(getApplicationContext(), "Ошибка авторизации. Повторите позже или обратитесь в техподдержку", Toast.LENGTH_LONG).show();
//            }
//        }));
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    private void fbLogin(AccessToken accessToken) {
//        if (accessToken != null && accessToken.getToken() != null && accessToken.getUserId() != null) {
//            VoisellApplication.getApi().loginSocUser("fb", accessToken.getUserId(), accessToken.getToken(), Config.getFirebaseToken(getApplicationContext())).enqueue(new CustomRetrofitCallback<ResponseLoginSocUser>() {
//                @Override
//                public void onRequestSuccess(Call<ResponseLoginSocUser> call, Response<ResponseLoginSocUser> response) {
//                    if (response.body().getSuccess()) {
//                        Config.setAuthKey(getApplicationContext(), response.body().getUserKey().getKey());
//
//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        if (response.body().getUserKey().getNewUser() != 0) {
//                            intent.putExtra("newUser", true);
//                        }
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        startActivity(intent);
//                        overridePendingTransition(0,0);
//
//                    } else {
//                        Log.d("VK LOG ERR 2", response.body().getMessage());
//                    }
//                }
//
//                @Override
//                public void onRequestFail(Call<ResponseLoginSocUser> call, ErrorRequest err) {
//                    Log.d("VK LOG ERR", err.getMessage() + " - " + err.getMessage_t());
//                }
//            });
//        }
//    }
//}
