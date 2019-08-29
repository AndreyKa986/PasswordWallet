package by.letum8658.passwordwallet.net

import by.letum8658.passwordwallet.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetProvider {

    private var itemApi: ItemApi? = null

    fun provideGson(): Gson = GsonBuilder().create()

    fun provideOkHttp(): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            okHttpBuilder.addInterceptor(logging)
        }

        return okHttpBuilder.build()
    }

    fun provideRetrofit(baseUrl: String, okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun provideItemApi(retrofit: Retrofit): ItemApi {
        if (itemApi == null) itemApi = retrofit.create(ItemApi::class.java)
        return itemApi!!
    }
}