

package com.github.shadowsocks.widget

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isGone
import timber.log.Timber

class AutoCollapseTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
                                                     defStyleAttr: Int = 0) :
        AppCompatTextView(context, attrs, defStyleAttr) {
    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        isGone = text.isNullOrEmpty()
    }

    // #1874
    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) = try {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
    } catch (e: IndexOutOfBoundsException) {
        Timber.w(e)
    }

    override fun onTouchEvent(event: MotionEvent?) = try {
        super.onTouchEvent(event)
    } catch (e: IndexOutOfBoundsException) {
        Timber.w(e)
        false
    }
}
