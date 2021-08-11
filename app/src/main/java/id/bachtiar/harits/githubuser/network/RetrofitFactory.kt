package id.bachtiar.harits.githubuser.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import id.bachtiar.harits.githubuser.BuildConfig
import id.bachtiar.harits.githubuser.util.Constant
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object RetrofitFactory {

    private const val BASE_URL = "https://api.github.com"

    private val json = Json {
        ignoreUnknownKeys = true
    }

    @ExperimentalSerializationApi
    fun makeRetrofitService(): ApiService {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient())
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(ApiService::class.java)
    }

    private fun loggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
        return interceptor
    }

    private fun okHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(loggingInterceptor())
        builder.connectTimeout(Constant.TIME_OUT, TimeUnit.SECONDS)
        builder.writeTimeout(Constant.TIME_OUT, TimeUnit.SECONDS)
        builder.readTimeout(Constant.TIME_OUT, TimeUnit.SECONDS)
        return builder.build()
    }
}