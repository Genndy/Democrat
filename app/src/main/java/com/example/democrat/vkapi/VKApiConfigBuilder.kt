package com.example.democrat.vkapi

import android.content.Context
import com.vk.api.sdk.*
import com.vk.api.sdk.utils.log.DefaultApiLogger
import com.vk.api.sdk.utils.log.Logger
import org.w3c.dom.Text
import java.util.concurrent.TimeUnit

class VKApiConfigBuilder() {
    private var context: Context = TODO();
    private var appId: Int = 0
    private var validationHandler: VKApiValidationHandler? = null
    private var deviceId: Lazy<String> = lazy { "" }
    private var version: String = VKApiConfig.DEFAULT_API_VERSION
    private var okHttpProvider: VKOkHttpProvider = VKOkHttpProvider.DefaultProvider()
    private var defaultTimeoutMs: Long = TimeUnit.SECONDS.toMillis(10)
    private var postRequestsTimeout: Long = TimeUnit.MINUTES.toMillis(5)
    private var logger: Logger = DefaultApiLogger(lazy { Logger.LogLevel.NONE }, "VKSdkApi")
    private var accessToken: Lazy<String> = lazy { "" }
    private var secret: Lazy<String?> = lazy { null }
    private var logFilterCredentials: Boolean = true
    private var debugCycleCalls: Lazy<Boolean> = lazy { false }
    private var callsPerSecondLimit: Int = 3
    private var httpApiHost: Lazy<String> = lazy { VKApiConfig.DEFAULT_API_DOMAIN }
    private var lang: String = "en"
    private var keyValueStorage: VKKeyValueStorage = VKPreferencesKeyValueStorage(context)

    fun setDefault(): VKApiConfigBuilder{
        context = TODO()
        appId = 0
        validationHandler = null
        deviceId = lazy { "" }
        version = VKApiConfig.DEFAULT_API_VERSION
        okHttpProvider = VKOkHttpProvider.DefaultProvider()
        defaultTimeoutMs = TimeUnit.SECONDS.toMillis(10)
        postRequestsTimeout = TimeUnit.MINUTES.toMillis(5)
        logger = DefaultApiLogger(lazy { Logger.LogLevel.NONE }, "VKSdkApi")
        accessToken = lazy { "" }
        secret = lazy { null }
        logFilterCredentials = true
        debugCycleCalls = lazy { false }
        callsPerSecondLimit = 3
        httpApiHost = lazy { VKApiConfig.DEFAULT_API_DOMAIN }
        lang = "en"
        keyValueStorage = VKPreferencesKeyValueStorage(context)

        return this
    }
}