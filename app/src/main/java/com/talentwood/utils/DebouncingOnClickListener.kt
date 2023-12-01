package com.talentwood.utils

import android.view.View

/**
 * Created by Pradeep
 */
class DebouncingOnClickListener(
        private val intervalMillis: Long,
        private val doClick: ((View) -> Unit)) : View.OnClickListener {

    override fun onClick(v: View) {
        if (enabled) {
            enabled = false
            v.postDelayed(ENABLE_AGAIN, intervalMillis)
            doClick(v)
        }
    }

    companion object {
        var enabled = true
        private val ENABLE_AGAIN =
                Runnable { enabled = true }
    }
}
