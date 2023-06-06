package earth.darkwhite.albiononlinemarketdata.database

import android.util.Log
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StringTypeConverter {
  
  @TypeConverter
  fun fromPostToJson(data: List<String>): String {
    return Gson().toJson(data)
  }
  
  @TypeConverter
  fun formJsonToPost(data: String): List<String> {
    return try {
      Gson().fromJson<List<String>>(data)
    } catch (e: Exception) {
      Log.w("StringTypeConverter", "formJsonToPost: fail conversion", e)
      emptyList()
    }
  }
  
  private inline fun <reified T> Gson.fromJson(json: String) =
    fromJson<T>(json, object : TypeToken<T>() {}.type)
}