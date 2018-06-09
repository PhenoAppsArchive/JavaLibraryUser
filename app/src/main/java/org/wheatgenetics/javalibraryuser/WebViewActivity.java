package org.wheatgenetics.javalibraryuser;

/**
 * Uses:
 * android.content.Intent
 * android.os.Bundle
 * android.support.v7.app.AppCompatActivity
 * android.webkit.WebView
 *
 * org.wheatgenetics.javalibraryuser.R
 */
public class WebViewActivity extends android.support.v7.app.AppCompatActivity
{
    @java.lang.Override protected void onCreate(final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(org.wheatgenetics.javalibraryuser.R.layout.activity_web_view);

        final android.webkit.WebView webView =
            this.findViewById(org.wheatgenetics.javalibraryuser.R.id.webView);
        assert null != webView; webView.loadData(
            this.getIntent().getStringExtra(android.content.Intent.EXTRA_TEXT), "text/html", null);
    }
}