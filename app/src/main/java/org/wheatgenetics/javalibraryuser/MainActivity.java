package org.wheatgenetics.javalibraryuser;

/**
 * Uses:
 * android.app.Activity
 * android.content.Intent
 * android.os.Bundle
 * android.os.Environment
 * android.support.annotation.IdRes
 * android.support.annotation.IntRange
 * android.support.v7.app.AppCompatActivity
 * android.view.View
 * android.widget.Button
 * android.widget.TextView
 *
 * org.wheatgenetics.javalib.Dir
 * org.wheatgenetics.javalib.Utils
 * org.wheatgenetics.javalib.Utils.Response
 *
 * org.wheatgenetics.javalibraryuser.R
 *  * org.wheatgenetics.javalibraryuser.WebViewActivity
 */
public class MainActivity extends android.support.v7.app.AppCompatActivity
{
    private static class Dir extends org.wheatgenetics.javalib.Dir
    {
        private Dir(final java.io.File path, final android.app.Activity activity,
        @android.support.annotation.IdRes final int id)
        {
            super(path, /* blankHiddenFileName => */ ".javalibraryuser");

            assert null != activity;
            final android.widget.TextView textView = activity.findViewById(id);
            assert null != textView; textView.setText(this.getPathAsString());
        }
    }

    // region Fields
    private org.wheatgenetics.javalibraryuser.MainActivity.Dir
        internalDir = null, externalPrivateDir = null, externalPublicDir = null;

    private android.widget.Button   button            = null;
    private android.widget.TextView multiLineTextView = null;
    @android.support.annotation.IntRange(from = 0, to = 7) private int buttonClickCount = 0;

    private android.content.Intent intentInstance = null;
    // endregion

    // region Private Methods
    private void setMultiLineTextViewText(final java.lang.String text)
    { assert null != this.multiLineTextView; this.multiLineTextView.setText(text); }

    private void setMultiLineTextViewText(final java.lang.String lines[])
    {
        final java.lang.String text;
        if (null == lines)
            text = null;
        else
            if (lines.length < 1)
                text = null;
            else
            {
                final java.lang.StringBuilder stringBuilder;
                {
                    final int first = 0;
                    stringBuilder = new java.lang.StringBuilder(lines[first]);
                }
                {
                    final int second = 1, last = lines.length - 1;
                    for (int i = second; i <= last; i++)
                        stringBuilder.append('\n').append(lines[i]);
                }
                text = stringBuilder.toString();
            }

        this.setMultiLineTextViewText(text);
    }

    private void listAll(final org.wheatgenetics.javalib.Dir dir)
    { if (null != dir) this.setMultiLineTextViewText(dir.list()); }

    private void listXml(final org.wheatgenetics.javalib.Dir dir)
    { if (null != dir) this.setMultiLineTextViewText(dir.list(".+\\.xml")); }

    private android.content.Intent intent(
    final java.lang.String content, final java.lang.String encoding)
    {
        if (null == this.intentInstance) this.intentInstance = new android.content.Intent(
            this, org.wheatgenetics.javalibraryuser.WebViewActivity.class);

        this.intentInstance.putExtra(
            org.wheatgenetics.javalibraryuser.WebViewActivity.CONTENT, content);
        this.intentInstance.putExtra(
            org.wheatgenetics.javalibraryuser.WebViewActivity.ENCODING, encoding);

        return this.intentInstance;
    }

    private void setButtonText(final java.lang.String text)
    { assert null != this.button; this.button.setText(text); }
    // endregion

    @java.lang.Override protected void onCreate(final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(org.wheatgenetics.javalibraryuser.R.layout.activity_main);

        {
            final java.lang.String        text;
            final android.widget.TextView adjustTextView = this.findViewById(
                org.wheatgenetics.javalibraryuser.R.id.adjustTextView);
            {
                final java.lang.String unadjusted = "  2 leading spaces";
                text = java.lang.String.format("adjust(\"%s\") is \"%s\"",
                    unadjusted, org.wheatgenetics.javalib.Utils.adjust(unadjusted));
            }
            assert null != adjustTextView; adjustTextView.setText(text);
        }

        this.internalDir = new org.wheatgenetics.javalibraryuser.MainActivity.Dir(
            this.getFilesDir(), this,
            org.wheatgenetics.javalibraryuser.R.id.internalDirTextView);
        this.externalPrivateDir = new org.wheatgenetics.javalibraryuser.MainActivity.Dir(
            this.getExternalFilesDir(null), this,
            org.wheatgenetics.javalibraryuser.R.id.externalPrivateDirTextView);
        this.externalPublicDir = new org.wheatgenetics.javalibraryuser.MainActivity.Dir(
            android.os.Environment.getExternalStorageDirectory(), this,
            org.wheatgenetics.javalibraryuser.R.id.externalPublicDirTextView);

        this.button = this.findViewById(org.wheatgenetics.javalibraryuser.R.id.button);
        this.multiLineTextView = this.findViewById(
            org.wheatgenetics.javalibraryuser.R.id.multiLineTextView);
    }

    public void onButtonClick(final android.view.View view)
    {
        switch (this.buttonClickCount)
        {
            case 0: this.listAll(this.internalDir); break;
            case 1: this.listXml(this.internalDir); break;

            case 2: this.listAll(this.externalPrivateDir); break;
            case 3: this.listXml(this.externalPrivateDir); break;

            case 4: this.listAll(this.externalPublicDir); break;
            case 5: this.listXml(this.externalPublicDir); break;

            case 6: case 7:
                final org.wheatgenetics.javalib.Utils.Response response;
                {
                    java.net.URL url;
                    {
                        final java.lang.String protocol = "http";
                        try
                        {
                            switch (this.buttonClickCount)
                            {
                                case 6:
                                    url = new java.net.URL( // throws java.net.MalformedURLException
                                        /* protocol => */ protocol         ,
                                        /* host     => */ "www.example.org",
                                        /* file     => */ "index.html"     );
                                    break;

                                case 7:
                                    url = new java.net.URL( // throws java.net.MalformedURLException
                                        /* protocol => */ protocol                   ,
                                        /* host     => */ "www.youtypeitwepostit.com",
                                        /* file     => */ "api/"                     );
                                    break;

                                default: url = null; break;
                            }
                        }
                        catch (final java.net.MalformedURLException e) { url = null; }
                    }
                    response = org.wheatgenetics.javalib.Utils.threadedGet(url);
                }
                if (null == response)
                    this.setMultiLineTextViewText("response is null");
                else
                    if ("text/html".equals(response.contentType()))
                        this.startActivity(this.intent(
                            response.content(), response.contentEncoding()));
                    else
                        this.setMultiLineTextViewText(response.content());
                break;
        }

        switch (this.buttonClickCount)
        {
            case 0: case 1: case 2: case 3: case 4: case 5: case 6: this.buttonClickCount++; break;
            default:                                              this.buttonClickCount = 0; break;
        }

        switch (this.buttonClickCount)
        {
            case 0: this.setButtonText("internalDir.list()"     ); break;
            case 1: this.setButtonText("internalDir.list(regex)"); break;

            case 2: this.setButtonText("externalPrivateDir.list()"     ); break;
            case 3: this.setButtonText("externalPrivateDir.list(regex)"); break;

            case 4: this.setButtonText("externalPublicDir.list()"     ); break;
            case 5: this.setButtonText("externalPublicDir.list(regex)"); break;

            case 6: this.setButtonText("http://www.example.org/"              ); break;
            case 7: this.setButtonText("http://www.youtypeitwepostit.com/api/"); break;
        }
    }
}