package earth.darkwhite.albiononlinemarketdata.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import earth.darkwhite.albiononlinemarketdata.database.MyDao
import earth.darkwhite.albiononlinemarketdata.database.MyDatabase
import earth.darkwhite.albiononlinemarketdata.domain.network.AlbionApiEast
import earth.darkwhite.albiononlinemarketdata.domain.network.AlbionApiEurope
import earth.darkwhite.albiononlinemarketdata.domain.network.AlbionApiWest
import earth.darkwhite.albiononlinemarketdata.util.Constant.DB_ASSET_FILE_NAME
import earth.darkwhite.albiononlinemarketdata.util.Constant.EAST_SERVER_BASE_URL
import earth.darkwhite.albiononlinemarketdata.util.Constant.EUROPE_SERVER_BASE_URL
import earth.darkwhite.albiononlinemarketdata.util.Constant.WEST_SERVER_BASE_URL
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
  
  @Provides
  @Singleton
  fun provideDatabase(@ApplicationContext context: Context): MyDatabase =
    Room.databaseBuilder(
      context,
      MyDatabase::class.java,
      context.packageName
    )
      .createFromAsset(DB_ASSET_FILE_NAME)
      .build()
  
  @Provides
  @Singleton
  fun provideDao(database: MyDatabase): MyDao =
    database.databaseMyDao
  
  @Provides
  @Singleton
  fun provideRetrofitWestServer(): AlbionApiWest =
    Retrofit.Builder()
      .baseUrl(WEST_SERVER_BASE_URL)
      .addConverterFactory(gsonConverterFactory)
      .client(client)
      .build()
      .create(AlbionApiWest::class.java)
  
  @Provides
  @Singleton
  fun provideRetrofitEastServer(): AlbionApiEast =
    Retrofit.Builder()
      .baseUrl(EAST_SERVER_BASE_URL)
      .addConverterFactory(gsonConverterFactory)
      .client(client)
      .build()
      .create(AlbionApiEast::class.java)
  
  @Provides
  @Singleton
  fun provideRetrofitEuropeServer(): AlbionApiEurope =
    Retrofit.Builder()
      .baseUrl(EUROPE_SERVER_BASE_URL)
      .addConverterFactory(gsonConverterFactory)
      .client(client)
      .build()
      .create(AlbionApiEurope::class.java)
  
  private val gsonConverterFactory = GsonConverterFactory.create()
  private val interceptor = HttpLoggingInterceptor().apply {
//    level = HttpLoggingInterceptor.Level.BODY
  }
  
  private val client = OkHttpClient.Builder().apply {
    addInterceptor(interceptor)
      .connectTimeout(10, TimeUnit.SECONDS)
      .readTimeout(10, TimeUnit.SECONDS)
  }.build()
}