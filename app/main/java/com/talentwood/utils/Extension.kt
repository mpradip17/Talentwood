package com.talentwood.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.text.InputFilter
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.DynamicDrawableSpan
import android.text.style.ImageSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.viewpager2.widget.ViewPager2
import com.talentwood.R
import com.talentwood.databinding.ToastCustomErrorBinding
import java.util.*


fun View.isVisible(): Boolean = visibility == View.VISIBLE

fun View.isGone(): Boolean = visibility == View.GONE

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.setVisible(isVisible:Boolean) {
    visibility = if (isVisible)
        View.VISIBLE
    else
        View.GONE
}

fun View.gone() {
    visibility = View.GONE
}

fun View?.hideKeyboard(context: Activity) {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this?.windowToken, 0)
}

fun hideKeyboardWithView(view: View) {
    val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun TextView.setColor(colorRes: Int) {
    this.setTextColor(ContextCompat.getColor(context, colorRes))
}

fun AppCompatTextView.setColor(colorRes: Int) {
    this.setTextColor(ContextCompat.getColor(context, colorRes))
}

fun View.backgroundDrawable(drawableRes: Int) {
    this.background = ContextCompat.getDrawable(context, drawableRes)
}

fun String.concatenateString(text: String): String = this.plus(" ").plus(text)

fun View.enable() {
    this.isEnabled = true
}

fun View.disable() {
    this.isEnabled = false
}

fun AppCompatEditText.setCompoundDrawableRight(resId: Int) {
    setCompoundDrawablesWithIntrinsicBounds(
        null, null,
        AppCompatResources.getDrawable(context, resId), null
    )
}

fun TextView.setCompoundDrawableRight(resId: Int) {
    setCompoundDrawablesWithIntrinsicBounds(
        null, null, AppCompatResources.getDrawable(context, resId), null
    )
}

fun AppCompatTextView.setCompoundDrawableStarEnd(resIdLeft: Int, resIdRight: Int) {
    setCompoundDrawablesWithIntrinsicBounds(
        AppCompatResources.getDrawable(context, resIdLeft),
        null,
        AppCompatResources.getDrawable(context, resIdRight),
        null
    )
}

fun AppCompatTextView.setCompoundDrawableStart(resId: Int) {
    setCompoundDrawablesWithIntrinsicBounds(
        AppCompatResources.getDrawable(context, resId), null, null, null
    )
}

fun AppCompatTextView.removeCompoundDrawable() {
    setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
}

fun AppCompatTextView.setCompoundDrawableEnd(resId: Int) {
    setCompoundDrawablesWithIntrinsicBounds(
        null, null, AppCompatResources.getDrawable(context, resId), null
    )
}

fun AppCompatTextView.setViewText(resId: Int) {
    this.text = context.getString(resId)
}

fun View.setOnClickWithDebounce(intervalMillis: Long = 1200L, doClick: (View) -> Unit) {
    setOnClickListener(
        DebouncingOnClickListener(
            intervalMillis = intervalMillis, doClick = doClick
        )
    )
}

fun Drawable.setColorFilters(color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
    } else {
        @Suppress("DEPRECATION") setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
    }
}

fun Context.getColorCode(resId: Int) = ContextCompat.getColor(
    this, resId
)

//Navigate from current to destination Fragment
fun NavController.safeNavigate(direction: NavDirections) {
    try {
        currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
    } catch (exception: Exception) {
        Log.d("MFPendingPayment:", "exception ${exception.message}")
    }
}


/**
 * gets uppercase string and returns lowercase  string by capitalizing first letter only
 */
fun String.titleCaseFirstCharIfItIsUppercase() = this.lowercase().replaceFirstChar {
    it.titlecase(Locale.getDefault())
}

/**
 * to append imageview at end of text
 */
fun TextView.setIconifiedText(text: String, @DrawableRes iconResId: Int) {
    SpannableStringBuilder("$text#").apply {
        setSpan(
            ImageSpan(context, iconResId, DynamicDrawableSpan.ALIGN_BASELINE),
            text.length,
            text.length + 1,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }.let {
        setText(it)
    }
}


/**
 * A method to set the  max length in edittext
 */
fun EditText.limitLength(maxLength: Int) {
    filters = arrayOf(InputFilter.LengthFilter(maxLength))
}

/**
 * Check Fragment in BackStack
 */
fun NavController.isFragmentInBackStack(destinationId: Int) =
    try {
        getBackStackEntry(destinationId)
        true
    } catch (e: Exception) {
        false
    }
/**
 * Check Multiple Safecall
 */
fun <A, B, C,D> ifNotNull(
    p1: A?, p2: B?, p3: C?,p4: D?, block: (A, B, C, D) -> Unit
): Unit? {
    if (p1 != null && p2 != null && p3 != null && p4 != null) {
        return block.invoke(p1, p2, p3,p4)
    }
    return null
}

/**
 * Check Multiple Safecall
 */
fun <A, B, C> ifNotNull(
    p1: A?, p2: B?, p3: C?, block: (A, B, C) -> Unit
): Unit? {
    if (p1 != null && p2 != null && p3 != null) {
        return block.invoke(p1, p2, p3)
    }
    return null
}


/**
 * Check Multiple Safecall
 */
fun <A, B> ifNotNull(p1: A?, p2: B?, block: (A, B) -> Unit): Unit? {
    if (p1 != null && p2 != null) {
        return block.invoke(p1, p2)
    }
    return null
}

@Suppress("DEPRECATION")
inline fun <reified P : Parcelable> Intent.getParcelableArrayList(key: String): ArrayList<P>? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableArrayListExtra(key, P::class.java)
    } else {
        getParcelableArrayListExtra(key)
    }
}
@Suppress("DEPRECATION")
inline fun <reified P : Parcelable> Bundle.getParcelableValue(key: String): P? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelable(key, P::class.java)
    } else {
        getParcelable(key)
    }
}
@Suppress("DEPRECATION")
inline fun <reified P : Parcelable> Bundle.getParcelableArrayListValue(key: String): ArrayList<P>? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableArrayList(key, P::class.java)
    } else {
        getParcelableArrayList(key)
    }
}
@Suppress("DEPRECATION")
inline fun <reified P : Parcelable> Intent.getParcelable(key: String): P? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableExtra(key, P::class.java)
    } else {
        getParcelableExtra(key)
    }
}

fun showErrorToast(context: Context, message: String) {
    val parent: ViewGroup? = null
    val toast = Toast.makeText(context, "", Toast.LENGTH_LONG)
    val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val binding: ToastCustomErrorBinding = ToastCustomErrorBinding.inflate(inflater,parent,false)
    binding.errorMessage.text = message
    toast.view = binding.root
    toast.show()
}


// you can define each rule as a separate checking function,
// adding more doesn't change the complexity
fun String.isLongEnough() = length > 3
fun String.hasEnoughDigits() = count(Char::isDigit) > 0
fun String.isMixedCase() = any(Char::isLowerCase) && any(Char::isUpperCase)
fun String.hasSpecialChar() = any { it in "!,+^" }

// you can decide which requirements need to be included (or make separate lists
// of different priority requirements, and check that enough of each have been met)
val requirements = listOf(String::isLongEnough, String::hasEnoughDigits)
val String.meetsRequirements get() = requirements.all { check -> check(this)}

fun ViewPager2.findCurrentFragment(fragmentManager: FragmentManager): Fragment? {
    return fragmentManager.findFragmentByTag("f$currentItem")
}

fun ViewPager2.findFragmentAtPosition(
    fragmentManager: FragmentManager,
    position: Int
): Fragment? {
    return fragmentManager.findFragmentByTag("f$position")
}

fun View.setBackgroundTint(color: Int){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
        this.background.colorFilter = BlendModeColorFilter(ContextCompat.getColor(this.context, color), BlendMode.SRC_IN)
    } else {
        this.background.setColorFilter(ContextCompat.getColor(this.context, color), PorterDuff.Mode.SRC_IN)
    }
}