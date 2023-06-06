package earth.darkwhite.albiononlinemarketdata.util

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import earth.darkwhite.albiononlinemarketdata.R
import earth.darkwhite.albiononlinemarketdata.util.Constant.AT
import earth.darkwhite.albiononlinemarketdata.util.Constant.DEFAULT_OFFSET
import earth.darkwhite.albiononlinemarketdata.util.Constant.DEFAULT_TIME
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs

object Util {
  @SuppressLint("SimpleDateFormat")
  private val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
  private var currentUtcDate: String = ""
  
  fun setCurrentUtcDate() {
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    currentUtcDate = formatter.format(Calendar.getInstance().time)
  }
  
  fun convertRawTimeToLongMinutes(itemTime: String): Long {
    if (itemTime == DEFAULT_TIME) {
      return 0
    }
    
    try {
      val dateServer: Date? = formatter.parse(itemTime)
      val dateUtcNow: Date? = formatter.parse(currentUtcDate)
      if (dateServer == null || dateUtcNow == null) {
        return convertRawTimeToLongMinutesSecondAttempt(itemTime)
      }
      
      //            Date dateUtcNow = formatter.parse(formatter.format(utcDate));
      val diffInMillis = abs(dateUtcNow.time - dateServer.time)
      //            Log.d(TAG, "stringTimeCalculation: between in millis " + diffInMillis);
      return TimeUnit.MINUTES.convert(diffInMillis, TimeUnit.MILLISECONDS).coerceAtLeast(1)
      
    } catch (e: ParseException) {
      Log.d("TAG", "convertRawTimeToLongMinutes: catch " + e.message)
    }
    // If this fail, try my own code :)
    return convertRawTimeToLongMinutesSecondAttempt(itemTime)
  }
  
  private fun convertRawTimeToLongMinutesSecondAttempt(itemTime: String): Long {
    val timeNow = Date()
    val stringTimeNow: String = formatter.format(timeNow)

//        Log.i(TAG, "timeCalculation: " + stringTimeNow + " " + itemTime);
    val year = stringTimeNow.substring(0, 4).toInt() - itemTime.substring(0, 4).toInt()
    val month = stringTimeNow.substring(5, 7).toInt() - itemTime.substring(5, 7).toInt()
    var day = stringTimeNow.substring(8, 10).toInt() - itemTime.substring(8, 10).toInt()
    val hour = stringTimeNow.substring(11, 13).toInt() - itemTime.substring(11, 13).toInt()
    val min = stringTimeNow.substring(14, 16).toInt() - itemTime.substring(14, 16).toInt()
    // because by default we put 1 month = 30 days
    // so if that month is 31 days we take one day off
    if (day == -30) day = -29

//        Log.i(TAG, "timeCalculation: " + year + " " +
//                month + " " +
//                day + " " +
//                hour + " " +
//                min);
    var minutes = (year.toLong() * 60 * 24 * 365 + month.toLong() * 60 * 24 * 30 + day.toLong() * 60 * 24 + hour * 60L
      + min)
    //        Log.i(TAG, "stringTimeCalculation minutes " + minutes);
    
    // Get time zone offset (result = +0000)
    val calendar = Calendar.getInstance(
      TimeZone.getTimeZone("UTC"),
      Locale.getDefault()
    )
    val currentLocalTime = calendar.time
    @SuppressLint("SimpleDateFormat") val date: DateFormat = SimpleDateFormat("Z")
    var localTime = date.format(currentLocalTime)
    //        Log.i(TAG, "Time offset: " + localTime);
    
    // current device time - offset
    if (!localTime.endsWith(DEFAULT_OFFSET)) {
      // remove "+"
      if (localTime.startsWith("+")) {
        localTime = localTime.replace("+", "")
        //                Log.i(TAG, "timeCalculation: plus hours " + localTime);
        for (i in 0..3) {
          val offset = localTime[i].toString().toInt().toByte()
          //                    Log.i(TAG, "offset: " + offset);
          minutes =
            if (i == 0) minutes - offset * 10 * 60 else if (i == 1) minutes - offset * 60 else if (i == 2) minutes - offset * 10 else minutes - offset
          //                    Log.i(TAG, "minutes + now" + minutes);
        }
      } else {
        localTime = localTime.replace("-", "")
        //                Log.i(TAG, "timeCalculation: minus hours " + localTime);
        for (i in 0..3) {
          val offset = localTime[i].toString().toInt().toByte()
          //                    Log.i(TAG, "offset: " + offset);
          minutes =
            if (i == 0) minutes + offset * 10 * 60 else if (i == 1) minutes + offset * 60 else if (i == 2) minutes + offset * 10 else minutes + offset
          //                    Log.i(TAG, "minutes - now" + minutes);
        }
      }
    }
    return minutes.coerceAtLeast(1)
  }
  
  fun convertLongMinutesTimeToString(minutes: Long): String {
//        if (minutes == 0) {
//            return "Never";
//        }
  
    // Year - day - hour - min
    val timeToDisplay: String = if (minutes >= 525600) {
      (minutes / 525600).toString() + "Y"
    } else if (minutes >= 1440) {
      (minutes / 1440).toString() + "D"
    } else if (minutes >= 60) {
      (minutes / 60).toString() + "H" + minutes % 60 + 'M'
    } else if (minutes == 1L) {
      "Now"
    } else {
      minutes.toString() + "M"
    }
    //        Log.i(TAG, "timeToDisplay: " + timeToDisplay);
    return timeToDisplay // + " ago"
  }
  
  fun extractItemTier(itemName: String): Int {
    val character = itemName[1]
    return try {
      character.toString().toInt()
    } catch (nfe: NumberFormatException) {
      1
    }
  }
  
  fun extractItemEnchant(itemName: String): Int {
    return if (itemName.contains(AT)) {
      //            Log.i(TAG, "itemEnchant: " + Character.getNumericValue(itemName.charAt(itemName.length() - 1)));
      Character.getNumericValue(itemName[itemName.length - 1])
    } else 0
  }
  
  fun getImageUrl(itemId: String, quality: Int): String {
    return "https://render.albiononline.com/v1/item/$itemId?quality=$quality&size=120"
  }
  
  fun startActivityIntent(context: Context, uri: String) {
    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri)))
  }
  
  fun shareApp(context: Context) {
    val intentShare = Intent(Intent.ACTION_SEND)
    val title = R.string.app_name
    val apkUrl = " http://play.google.com/store/apps/details?id=" + context.packageName
    val description = context.getString(R.string.share_app_desc) + apkUrl
    intentShare.type = "text/plain"
    intentShare.putExtra(Intent.EXTRA_SUBJECT, title)
    intentShare.putExtra(Intent.EXTRA_TEXT, description)
    context.startActivity(Intent.createChooser(intentShare, context.getString(R.string.share_app)))
  }
  
  fun onRateClick(context: Context) {
    val packageName = context.packageName
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
    try {
      context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
      intent.data = Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
      context.startActivity(intent)
    }
  }
  
  fun contactSupport(context: Context) {
    val email = context.getString(R.string.support_email)
    val intentRequest = Intent(Intent.ACTION_SENDTO)
    intentRequest.data = Uri.parse("mailto:$email")
    try {
      context.startActivity(Intent.createChooser(intentRequest, context.getString(R.string.email_handler_title)))
    } catch (ex: ActivityNotFoundException) {
      Toast.makeText(context, context.getString(R.string.error_no_email_client_installed), Toast.LENGTH_SHORT).show()
    }
  }
  
}