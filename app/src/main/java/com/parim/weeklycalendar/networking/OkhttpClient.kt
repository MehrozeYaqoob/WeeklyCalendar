import com.parim.weeklycalendar.BuildConfig
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object OkhttpClient {

    val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(okhttp3.logging.HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG)
                okhttp3.logging.HttpLoggingInterceptor.Level.BODY
            else
                okhttp3.logging.HttpLoggingInterceptor.Level.NONE
        }  )
        .build()
}