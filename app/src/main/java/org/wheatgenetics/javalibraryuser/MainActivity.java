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
    @android.support.annotation.IntRange(from = 0, to = 6) private int buttonClickCount = 0;

    private android.content.Intent intentInstance = null;
    private java.net.URL           urlInstance    = null;
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

    private android.content.Intent intent()
    {
        if (null == this.intentInstance) this.intentInstance = new android.content.Intent(
            this, org.wheatgenetics.javalibraryuser.WebViewActivity.class);
        return this.intentInstance;
    }

    private java.net.URL url() throws java.net.MalformedURLException
    {
        if (null == this.urlInstance) this.urlInstance = new java.net.URL(   // throws java.net.Mal-
            /* protocol => */ "http"           ,                             //  formedURLException
            /* host     => */ "www.example.org",
            /* file     => */ "index.html"     );
        return this.urlInstance;
    }

    private void get()
    {
        final android.content.Intent intent = this.intent();
        try
        {
            intent.putExtra(android.content.Intent.EXTRA_TEXT,
                org.wheatgenetics.javalib.Utils.get(        // throws java.io.IOException
                    this.url()));                           // throws java.net.MalformedURLException
            this.startActivity(intent);
        }
        catch (final java.lang.Exception e)
        {
            final java.lang.String message = e.getMessage();
            this.setMultiLineTextViewText(null == message ? e.getClass().getName() : message);
        }
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
        {
            switch (this.buttonClickCount)
            {
                case 0: this.listAll(this.internalDir); break;
                case 1: this.listXml(this.internalDir); break;

                case 2: this.listAll(this.externalPrivateDir); break;
                case 3: this.listXml(this.externalPrivateDir); break;

                case 4: this.listAll(this.externalPublicDir); break;
                case 5: this.listXml(this.externalPublicDir); break;

                case 6:
                    @java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
                    class Runnable extends java.lang.Object implements java.lang.Runnable
                    {
                        @java.lang.Override public void run()
                        { org.wheatgenetics.javalibraryuser.MainActivity.this.get(); }
                    }
                    final java.lang.Thread thread = new java.lang.Thread(new Runnable(), "get()");
                    thread.start(); break;
            }
        }

        switch (this.buttonClickCount)
        {
            case 0: case 1: case 2: case 3: case 4: case 5: this.buttonClickCount++  ; break;
            default:                                        this.buttonClickCount = 0; break;
        }

        switch (this.buttonClickCount)
        {
            case 0: this.setButtonText("internalDir.list()"     ); break;
            case 1: this.setButtonText("internalDir.list(regex)"); break;

            case 2: this.setButtonText("externalPrivateDir.list()"     ); break;
            case 3: this.setButtonText("externalPrivateDir.list(regex)"); break;

            case 4: this.setButtonText("externalPublicDir.list()"     ); break;
            case 5: this.setButtonText("externalPublicDir.list(regex)"); break;

            case 6: this.setButtonText("http://www.example.org/"); break;
        }
    }
}