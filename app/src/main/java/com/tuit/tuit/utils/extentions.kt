package com.tuit.tuit.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Patterns
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavArgument
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import com.google.android.material.navigation.NavigationView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.tuit.tuit.R

fun CharSequence?.isValidEmail() =
    !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun FragmentActivity.statusBarColor(
    @ColorInt statusBarColor: Int,
    @ColorInt navigationBarColor: Int,
    darkStatusBarTint: Boolean
) {
    val win: Window = (window).also {
        it.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        it.statusBarColor = statusBarColor
        it.navigationBarColor = navigationBarColor
    }

    val dec = win.decorView
    if (darkStatusBarTint) {
        dec.systemUiVisibility = dec.systemUiVisibility or
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    } else {
        dec.systemUiVisibility = 0
    }
}


fun NavigationView.changeCornerRadius() {
    val navViewBackground: MaterialShapeDrawable = background as MaterialShapeDrawable
    val radius = resources.getDimension(R.dimen.dimen_16dp)
    navViewBackground.shapeAppearanceModel = navViewBackground.shapeAppearanceModel
        .toBuilder()
        .setTopLeftCorner(CornerFamily.ROUNDED, radius)
        .setBottomLeftCorner(CornerFamily.ROUNDED, radius)
        .build()
}

var newCardAdded: Boolean = false
var firstCardFragmentEntrance: Boolean = true
fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun dipToPixels(context: Context, dipValue: Float): Float {
    val metrics: DisplayMetrics = context.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics)
}

fun universalToken(context: Context): String {
    return "Bearer " + SharedPreferences.getToken(context)
}

//fun headerMapUniversal(context: Context): Map<String, String> {
//    val map = mutableMapOf<String, String>()
//    val finalToken = "Bearer " + SaveData.getData(context)
//    map["Authorization"] = finalToken
//    map["Lang"] = SaveData.getLanguage(context).toString()
//    return map
//}


fun gotoTelegram(driverTelegram: String, context: Context) {
    try {
        val telegramIntent = Intent(Intent.ACTION_VIEW)
        val telegram = if (driverTelegram.startsWith("@")) {
            driverTelegram.replace("@", "")
        } else {
            driverTelegram
        }
        telegramIntent.data = Uri.parse("https://telegram.me/$telegram")
        context.startActivity(telegramIntent)
    } catch (e: Exception) {
        // show error message
    }
}

fun gotoContact(driverPhoneNumber1: String, context: Context) {
    try {
        val phone = driverPhoneNumber1
        val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
        context.startActivity(intent)
    } catch (e: Exception) {
    }
}

//fun headerMapUniversalForRoute(context: Context): Map<String, String> {
//    var map = mutableMapOf<String, String>()
//    val finalToken = "Bearer " + SaveData.getData(context)
//
//    map["Authorization"] = finalToken
//    map["Content-Type"] = "application/json"
//    map["Accept"] = "*/*"
//    map["Lang"] = SaveData.getLanguage(context).toString()
//
//    return map
//}


fun changeMoneyType(sum: String): String {
    var reversedSum = ""
    for (i in sum.length - 1 downTo 0) {
        reversedSum += sum[i]
    }
    var changedSum = ""
    if (reversedSum.length > 3) {
        var k = 2
        for (i in reversedSum.indices) {
            changedSum += reversedSum[i]
            if (i >= k) {
                changedSum += " "
                k += 3
            }
        }
        reversedSum = ""
        for (i in changedSum.length - 1 downTo 0) {
            reversedSum += changedSum[i]
        }
    } else {
        reversedSum = sum
    }
    return reversedSum.trim()
}

fun getNavOptions(): NavOptions {
    return NavOptions.Builder()
        .setEnterAnim(R.anim.fade_in)
        .setExitAnim(R.anim.fade_out)
        .setPopEnterAnim(R.anim.fade_in)
        .setPopExitAnim(R.anim.fade_out)
        .build()
}

fun getNavigationAnimation(): NavOptions {
    return NavOptions.Builder()
        .setEnterAnim(R.anim.fade_in)
        .setExitAnim(R.anim.fade_out)
        .setPopEnterAnim(R.anim.fade_in)
        .setPopExitAnim(R.anim.fade_out)
        .build()
}

fun putArgs(): NavArgument {
    return NavArgument.Builder()
        .setType(NavType.StringType)
        .build()
}


fun selectedCard(num: Int): Int {
    return num
}

var selectedCardPosition = 0

fun View?.blockClickable(blockTimeMilles: Long = 500) {
    this?.isClickable = false
    Handler().postDelayed({ this?.isClickable = true }, blockTimeMilles)
}

fun toast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun dismissKeyboard(activity: Activity) {
    val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (null != activity.currentFocus) imm.hideSoftInputFromWindow(
        activity.currentFocus!!
            .applicationWindowToken, 0
    )
}

@SuppressLint("ClickableViewAccessibility")
fun AppCompatEditText.onRightDrawableClicked(onClicked: (view: AppCompatEditText) -> Unit) {
    this.setOnTouchListener { v, event ->
        var hasConsumed = false
        if (v is AppCompatEditText) {
            if (event.x >= v.width - v.totalPaddingRight) {
                if (event.action == MotionEvent.ACTION_UP) {
                    onClicked(this)
                }
                hasConsumed = true
            }
        }
        hasConsumed
    }
}


public var xValue: Float = 0F
public var yValue: Float = 0F


fun View.showKeyboard() {
    this.requestFocus()
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun Context.hasPermission(permission: String): Boolean {

    return ActivityCompat.checkSelfPermission(this, permission) ==
            PackageManager.PERMISSION_GRANTED
}

fun Activity.requestPermissionWithRationale(
    permission: String,
    requestCode: Int,
    rationaleStr: String
) {
    val provideRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, permission)

    if (provideRationale) {
        AlertDialog.Builder(this).apply {
            setTitle("Permission")
            setMessage(rationaleStr)
            setPositiveButton("Ok") { _, _ ->
                ActivityCompat.requestPermissions(
                    this@requestPermissionWithRationale,
                    arrayOf(permission),
                    requestCode
                )
            }
            create()
            show()
        }
    } else {
        ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
    }
}


@SuppressLint("ClickableViewAccessibility")
fun AppCompatTextView.onRightDrawableClicked(onClicked: (view: AppCompatTextView) -> Unit) {
    this.setOnTouchListener { v, event ->
        var hasConsumed = false
        if (v is AppCompatTextView) {
            if (event.x >= v.width - v.totalPaddingRight) {
                if (event.action == MotionEvent.ACTION_UP) {
                    onClicked(this)
                }
                hasConsumed = true
            }
        }
        hasConsumed
    }
}

const val KEY = "intent_key"

