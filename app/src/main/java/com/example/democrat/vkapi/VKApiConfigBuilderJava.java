package com.example.democrat.vkapi;

import android.content.Context;

import com.example.democrat.MainActivity;
import com.vk.api.sdk.VKApiConfig;
import com.vk.api.sdk.VKApiValidationHandler;
import com.vk.api.sdk.VKCachedKeyValueStorage;
import com.vk.api.sdk.VKKeyValueStorage;
import com.vk.api.sdk.VKOkHttpProvider;
import com.vk.api.sdk.VKPreferencesKeyValueStorage;
import com.vk.api.sdk.utils.log.DefaultApiLogger;
import com.vk.api.sdk.utils.log.Logger;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.NotImplementedError;
// import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

@Metadata(
        mv = {1, 1, 16},
        bv = {1, 0, 3},
        k = 1,
        d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0011\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u001e\u001a\u00020\u001fJ\u0014\u0010 \u001a\u00020\u00002\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004J\u000e\u0010\"\u001a\u00020\u00002\u0006\u0010#\u001a\u00020\u0007J\u000e\u0010$\u001a\u00020\u00002\u0006\u0010%\u001a\u00020\u0007J\u000e\u0010&\u001a\u00020\u00002\u0006\u0010'\u001a\u00020\nJ\u0014\u0010(\u001a\u00020\u00002\f\u0010)\u001a\b\u0012\u0004\u0012\u00020\f0\u0004J\u000e\u0010*\u001a\u00020\u00002\u0006\u0010+\u001a\u00020\u000eJ\u0014\u0010,\u001a\u00020\u00002\f\u0010-\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004J\u0014\u0010.\u001a\u00020\u00002\f\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004J\u000e\u00100\u001a\u00020\u00002\u0006\u00101\u001a\u000202J\u000e\u00103\u001a\u00020\u00002\u0006\u00104\u001a\u00020\u0005J\u000e\u00105\u001a\u00020\u00002\u0006\u00106\u001a\u00020\fJ\u000e\u00107\u001a\u00020\u00002\u0006\u00108\u001a\u00020\u0016J\u000e\u00109\u001a\u00020\u00002\u0006\u0010:\u001a\u00020\u0018J\u000e\u0010;\u001a\u00020\u00002\u0006\u0010<\u001a\u00020\u000eJ\u0016\u0010=\u001a\u00020\u00002\u000e\u0010>\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0004J\u000e\u0010?\u001a\u00020\u00002\u0006\u0010@\u001a\u00020\u001cJ\u000e\u0010A\u001a\u00020\u00002\u0006\u0010B\u001a\u00020\u0005R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u001a\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006C"},
        d2 = {"Lcom/example/democrat/vkapi/VKApiConfigBuilderJava;", "", "()V", "accessToken", "Lkotlin/Lazy;", "", "appId", "", "callsPerSecondLimit", "context", "Landroid/content/Context;", "debugCycleCalls", "", "defaultTimeoutMs", "", "deviceId", "httpApiHost", "keyValueStorage", "Lcom/vk/api/sdk/VKKeyValueStorage;", "lang", "logFilterCredentials", "logger", "Lcom/vk/api/sdk/utils/log/Logger;", "okHttpProvider", "Lcom/vk/api/sdk/VKOkHttpProvider;", "postRequestsTimeout", "secret", "validationHandler", "Lcom/vk/api/sdk/VKApiValidationHandler;", "version", "getVKApiConfig", "Lcom/vk/api/sdk/VKApiConfig;", "setAccessToken", "_accessToken", "setAppId", "_appId", "setCallsPerSecomdLimit", "_callsPerSecondLimit", "setContext", "_context", "setDebugCycleCalls", "_debugCycleCalls", "setDefaultTimeoutMs", "_defaultTimeoutMs", "setDeviceId", "_deviceId", "setHttpApiHost", "_httpApiHost", "setKeyValueStorage", "_keyValueStorage", "Lcom/vk/api/sdk/VKCachedKeyValueStorage;", "setLang", "_lang", "setLogFilterCredentials", "_logFilterCredentials", "setLogger", "_logger", "setOkHttpProvider", "_okHttpProvider", "setPostRequestsTimeout", "_postRequestsTimeout", "setSecret", "_secret", "setValidationHandler", "_validationHandler", "setVersion", "_version", "app"}
)
public final class VKApiConfigBuilderJava {
    private Context context;
    private int appId = 7555839;
    private VKApiValidationHandler validationHandler = null;
    private Lazy<String> deviceId = kotlin.LazyKt.lazyOf("");
    private String version = VKApiConfig.DEFAULT_API_VERSION;
    private VKOkHttpProvider okHttpProvider = new VKOkHttpProvider.DefaultProvider();
    private long defaultTimeoutMs = TimeUnit.SECONDS.toMillis(10);
    private long postRequestsTimeout = TimeUnit.MINUTES.toMillis(5);
    private Logger logger = new DefaultApiLogger(kotlin.LazyKt.lazyOf(Logger.LogLevel.NONE), "VKSdkApi");
    private Lazy accessToken = kotlin.LazyKt.lazyOf("");
    private Lazy secret = kotlin.LazyKt.lazyOf(null);
    private boolean logFilterCredentials = true;
    private Lazy debugCycleCalls = kotlin.LazyKt.lazyOf(false);;
    private int callsPerSecondLimit = 3;
    private Lazy httpApiHost = kotlin.LazyKt.lazyOf(VKApiConfig.DEFAULT_API_DOMAIN);;
    private String lang = "ru";
    private VKKeyValueStorage keyValueStorage = new VKPreferencesKeyValueStorage(context, "");

    public VKApiConfigBuilderJava(@NotNull Context _context) {
        this.context = _context;
    }

    @NotNull
    public final VKApiConfigBuilderJava setAppId(int _appId) {
        this.appId = _appId;
        return this;
    }

    @NotNull
    public final VKApiConfigBuilderJava setValidationHandler(@NotNull VKApiValidationHandler _validationHandler) {
        Intrinsics.checkParameterIsNotNull(_validationHandler, "_validationHandler");
        this.validationHandler = _validationHandler;
        return this;
    }

    @NotNull
    public final VKApiConfigBuilderJava setDeviceId(@NotNull Lazy _deviceId) {
        Intrinsics.checkParameterIsNotNull(_deviceId, "_deviceId");
        this.deviceId = _deviceId;
        return this;
    }

    @NotNull
    public final VKApiConfigBuilderJava setVersion(@NotNull String _version) {
        Intrinsics.checkParameterIsNotNull(_version, "_version");
        this.version = _version;
        return this;
    }

    @NotNull
    public final VKApiConfigBuilderJava setOkHttpProvider(@NotNull VKOkHttpProvider _okHttpProvider) {
        Intrinsics.checkParameterIsNotNull(_okHttpProvider, "_okHttpProvider");
        this.okHttpProvider = _okHttpProvider;
        return this;
    }

    @NotNull
    public final VKApiConfigBuilderJava setDefaultTimeoutMs(long _defaultTimeoutMs) {
        this.defaultTimeoutMs = _defaultTimeoutMs;
        return this;
    }

    @NotNull
    public final VKApiConfigBuilderJava setPostRequestsTimeout(long _postRequestsTimeout) {
        this.postRequestsTimeout = _postRequestsTimeout;
        return this;
    }

    @NotNull
    public final VKApiConfigBuilderJava setLogger(@NotNull Logger _logger) {
        Intrinsics.checkParameterIsNotNull(_logger, "_logger");
        this.logger = _logger;
        return this;
    }

    @NotNull
    public final VKApiConfigBuilderJava setAccessToken(@NotNull Lazy _accessToken) {
        Intrinsics.checkParameterIsNotNull(_accessToken, "_accessToken");
        this.accessToken = _accessToken;
        return this;
    }

    @NotNull
    public final VKApiConfigBuilderJava setSecret(@NotNull Lazy _secret) {
        Intrinsics.checkParameterIsNotNull(_secret, "_secret");
        this.secret = _secret;
        return this;
    }

    @NotNull
    public final VKApiConfigBuilderJava setLogFilterCredentials(boolean _logFilterCredentials) {
        this.logFilterCredentials = _logFilterCredentials;
        return this;
    }

    @NotNull
    public final VKApiConfigBuilderJava setDebugCycleCalls(@NotNull Lazy _debugCycleCalls) {
        Intrinsics.checkParameterIsNotNull(_debugCycleCalls, "_debugCycleCalls");
        this.debugCycleCalls = _debugCycleCalls;
        return this;
    }

    @NotNull
    public final VKApiConfigBuilderJava setCallsPerSecomdLimit(int _callsPerSecondLimit) {
        this.callsPerSecondLimit = _callsPerSecondLimit;
        return this;
    }

    @NotNull
    public final VKApiConfigBuilderJava setHttpApiHost(@NotNull Lazy _httpApiHost) {
        Intrinsics.checkParameterIsNotNull(_httpApiHost, "_httpApiHost");
        this.httpApiHost = _httpApiHost;
        return this;
    }

    @NotNull
    public final VKApiConfigBuilderJava setLang(@NotNull String _lang) {
        Intrinsics.checkParameterIsNotNull(_lang, "_lang");
        this.lang = _lang;
        return this;
    }

    @NotNull
    public final VKApiConfigBuilderJava setKeyValueStorage(@NotNull VKCachedKeyValueStorage _keyValueStorage) {
        Intrinsics.checkParameterIsNotNull(_keyValueStorage, "_keyValueStorage");
        this.keyValueStorage = (VKKeyValueStorage)_keyValueStorage;
        return this;
    }

    @NotNull
    public final VKApiConfig getVKApiConfig() {
        return new VKApiConfig(this.context, this.appId, this.validationHandler, this.deviceId, this.version, this.okHttpProvider, this.defaultTimeoutMs, this.postRequestsTimeout, this.logger, this.accessToken, this.secret, this.logFilterCredentials, this.debugCycleCalls, this.callsPerSecondLimit, this.httpApiHost, this.lang, this.keyValueStorage);
    }

//    public VKApiConfigBuilderJava() {
//        boolean var1 = false;
//        throw (Throwable)(new NotImplementedError((String)null, 1, (DefaultConstructorMarker)null));
//    }
}
