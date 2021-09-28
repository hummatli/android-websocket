package com.challenge.app.extensions

import android.annotation.SuppressLint
import okhttp3.OkHttpClient
import timber.log.Timber
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

fun OkHttpClient.Builder.disableSSLVerification(): OkHttpClient.Builder {
    val trustAllCerts = arrayOf<TrustManager>(
        object : X509TrustManager {
            @SuppressLint("TrustAllX509TrustManager")
            override fun checkClientTrusted(
                chain: Array<X509Certificate?>?,
                authType: String?,
            ) {
            }

            @SuppressLint("TrustAllX509TrustManager")
            override fun checkServerTrusted(
                chain: Array<X509Certificate?>?,
                authType: String?,
            ) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate?>? {
                return arrayOf()
            }
        }
    )

    val sslContext = SSLContext.getInstance("SSL")
    sslContext.init(null, trustAllCerts, SecureRandom())

    return this.sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
        .hostnameVerifier { _, _ -> true }

}
