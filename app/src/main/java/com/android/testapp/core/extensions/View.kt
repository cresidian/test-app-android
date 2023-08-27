package com.android.testapp.core.extensions

import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.google.android.material.snackbar.Snackbar


fun View.setVisible(isVisible: Boolean) {
    this.visibility = if (isVisible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun View.setInvisible(isInvisible: Boolean) {
    this.visibility = if (isInvisible) {
        View.INVISIBLE
    } else {
        View.VISIBLE
    }
}

fun View.setEnable(isEnable: Boolean) {
    this.isEnabled = isEnable
}

fun View.transit() {
    val transition = AutoTransition()
    transition.duration = 100
    TransitionManager.beginDelayedTransition(this.rootView as ViewGroup, transition)
}

val View.statusBarHeight: Int
    @RequiresApi(Build.VERSION_CODES.M)
    get() {
        rootWindowInsets?.systemWindowInsetTop?.let {
            return it
        }
        //If rootWindowInsets are not ready yet (mostly during activity.onCreate())
        val internalResourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (internalResourceId > 0) {
            return resources.getDimensionPixelSize(internalResourceId)
        }
        return 0
    }

inline fun View.snackMessage(
    message: String,
    length: Int = Snackbar.LENGTH_SHORT,
    action: Snackbar.() -> Unit = {}
) {
    snack(message, length, action)
}

inline fun View.snack(
    @StringRes messageRes: Int,
    length: Int = Snackbar.LENGTH_LONG,
    action: Snackbar.() -> Unit
) {
    snack(resources.getString(messageRes), length, action)
}

inline fun View.snack(
    message: String,
    length: Int = Snackbar.LENGTH_LONG,
    action: Snackbar.() -> Unit,
    anchorView: View? = null
) {
    Snackbar.make(this, message, length).apply {
        /***Setting bottom margin to show snackbar above bottombar***/
        view.translationY = -150f
        anchorView?.let {
            setAnchorView(anchorView)
        }
        action()
    }.show()
}