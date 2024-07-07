

package com.github.shadowsocks

import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.URLSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.net.toUri
import androidx.core.text.HtmlCompat
import androidx.core.text.parseAsHtml
import androidx.core.view.ViewCompat
import com.github.shadowsocks.widget.ListHolderListener
import com.github.shadowsocks.widget.MainListListener
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity

class AboutFragment : ToolbarFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.layout_about, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setOnApplyWindowInsetsListener(view, ListHolderListener)
        toolbar.title = getString(R.string.about_title, BuildConfig.VERSION_NAME)
        view.findViewById<TextView>(R.id.tv_about).apply {
            ViewCompat.setOnApplyWindowInsetsListener(this, MainListListener)
            text = SpannableStringBuilder(resources.openRawResource(R.raw.about).bufferedReader().readText()
                    .parseAsHtml(HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_LIST_ITEM)).apply {
                for (span in getSpans(0, length, URLSpan::class.java)) {
                    setSpan(object : ClickableSpan() {
                        override fun onClick(view: View) = when {
                            span.url.startsWith("#") -> {
                                startActivity(Intent(context, OssLicensesMenuActivity::class.java))
                            }
                            span.url.startsWith("mailto:") -> {
                                startActivity(Intent.createChooser(Intent().apply {
                                    action = Intent.ACTION_SENDTO
                                    data = span.url.toUri()
                                }, getString(R.string.send_email)))
                            }
                            else -> (activity as MainActivity).launchUrl(span.url)
                        }
                    }, getSpanStart(span), getSpanEnd(span), getSpanFlags(span))
                    removeSpan(span)
                }
            }
            movementMethod = LinkMovementMethod.getInstance()
        }
    }
}
