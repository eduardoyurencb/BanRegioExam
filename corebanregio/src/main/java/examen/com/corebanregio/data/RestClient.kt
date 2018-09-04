package examen.com.corebanregio.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RestClient {
    companion object {
        var readTimeOut: Long = 30
        var connectTimeout: Long = 30
        fun getClient(uri: String): ApiInterfaces {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val okClient = OkHttpClient.Builder()
                    .readTimeout(
                            readTimeOut,
                            TimeUnit.SECONDS
                    ).connectTimeout(
                            connectTimeout,
                            TimeUnit.SECONDS
                    ).addInterceptor { chain ->
                        val requestBuilder = chain.request().newBuilder()
                        chain.proceed(requestBuilder.build())
                    }.addInterceptor(
                            loggingInterceptor
                    ).build()

            val client = Retrofit.Builder()
                    .baseUrl(uri)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okClient)
                    .build();

            return client.create(ApiInterfaces::class.java)
        }
    }
}