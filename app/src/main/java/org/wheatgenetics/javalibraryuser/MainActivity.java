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
 * org.wheatgenetics.javalib.Dir.PermissionException
 * org.wheatgenetics.javalib.PermissionDir
 * org.wheatgenetics.javalib.Utils
 * org.wheatgenetics.javalib.Utils.Response
 *
 * org.wheatgenetics.javalibraryuser.R
 *  * org.wheatgenetics.javalibraryuser.WebViewActivity
 */
public class MainActivity extends android.support.v7.app.AppCompatActivity
{
    private static final java.lang.String BLANK_HIDDEN_FILE = ".javalibraryuser";

    // region Types
    private static class Dir extends org.wheatgenetics.javalib.Dir
    {
        private Dir(final java.io.File path, final android.app.Activity activity,
        @android.support.annotation.IdRes final int id)
        {
            super(path, org.wheatgenetics.javalibraryuser.MainActivity.BLANK_HIDDEN_FILE);

            assert null != activity;
            final android.widget.TextView textView = activity.findViewById(id);
            assert null != textView; textView.setText(this.getPathAsString());
        }
    }

    private static class PermissionDir extends org.wheatgenetics.javalib.PermissionDir
    {
        private PermissionDir(final java.io.File path)
        { super(path, org.wheatgenetics.javalibraryuser.MainActivity.BLANK_HIDDEN_FILE); }
    }
    // endregion

    // region Fields
    private org.wheatgenetics.javalibraryuser.MainActivity.Dir
        internalDir = null, externalPrivateDir = null, externalPublicDir = null;
    private org.wheatgenetics.javalibraryuser.MainActivity.PermissionDir
        internalPermissionDir = null, externalPrivatePermissionDir = null,
        externalPublicPermissionDir = null;

    private android.widget.Button   button            = null;
    private android.widget.TextView multiLineTextView = null;
    @android.support.annotation.IntRange(from = 0, to = 13) private int buttonClickCount = 0;

    private android.content.Intent intentInstance = null;
    // endregion

    // region Private Methods
    private void setMultiLineTextViewText(final java.lang.String text)
    { assert null != this.multiLineTextView; this.multiLineTextView.setText(text); }

    private void setMultiLineTextViewText(final java.lang.String lines[])
    {
        final java.lang.String text;
        if (null == lines)
            text = "null";
        else
            if (lines.length < 1)
                text = "null";
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
    {
        if (null != dir)
            try { this.setMultiLineTextViewText(dir.list()); }
            catch (final org.wheatgenetics.javalib.Dir.PermissionException e)
            { this.setMultiLineTextViewText(e.getMessage()); }
    }

    private void listXml(final org.wheatgenetics.javalib.Dir dir)
    {
        if (null != dir)
            try { this.setMultiLineTextViewText(dir.list(".+\\.xml")); }
            catch (final org.wheatgenetics.javalib.Dir.PermissionException e)
            { this.setMultiLineTextViewText(e.getMessage()); }
    }

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

        this.internalPermissionDir =
            new org.wheatgenetics.javalibraryuser.MainActivity.PermissionDir(this.getFilesDir());
        this.externalPrivatePermissionDir =
            new org.wheatgenetics.javalibraryuser.MainActivity.PermissionDir(
                this.getExternalFilesDir(null));
        this.externalPublicPermissionDir =
            new org.wheatgenetics.javalibraryuser.MainActivity.PermissionDir(
                android.os.Environment.getExternalStorageDirectory());

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

            case 2: this.listAll(this.internalPermissionDir); break;
            case 3: this.listXml(this.internalPermissionDir); break;

            case 4: this.listAll(this.externalPrivateDir); break;
            case 5: this.listXml(this.externalPrivateDir); break;

            case 6: this.listAll(this.externalPrivatePermissionDir); break;
            case 7: this.listXml(this.externalPrivatePermissionDir); break;

            case 8: this.listAll(this.externalPublicDir); break;
            case 9: this.listXml(this.externalPublicDir); break;

            case 10: this.listAll(this.externalPublicPermissionDir); break;
            case 11: this.listXml(this.externalPublicPermissionDir); break;

            case 12: case 13:
                final org.wheatgenetics.javalib.Utils.Response response;
                {
                    java.net.URL url;
                    {
                        final java.lang.String protocol = "http";
                        try
                        {
                            switch (this.buttonClickCount)
                            {
                                case 12:
                                    url = new java.net.URL( // throws java.net.MalformedURLException
                                        /* protocol => */ protocol         ,
                                        /* host     => */ "www.example.org",
                                        /* file     => */ "index.html"     );
                                    break;

                                case 13:
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
            case  0: case  1: case  2: case 3: case 4: case 5: case 6: case 7: case 8: case 9:
            case 10: case 11: case 12: this.buttonClickCount++; break;

            default: this.buttonClickCount = 0; break;
        }

        switch (this.buttonClickCount)
        {
            case 0: this.setButtonText("internalDir.list()"     ); break;
            case 1: this.setButtonText("internalDir.list(regex)"); break;

            case 2: this.setButtonText("internalPermissionDir.list()"     ); break;
            case 3: this.setButtonText("internalPermissionDir.list(regex)"); break;

            case 4: this.setButtonText("externalPrivateDir.list()"     ); break;
            case 5: this.setButtonText("externalPrivateDir.list(regex)"); break;

            case 6: this.setButtonText("externalPrivatePermissionDir.list()"     ); break;
            case 7: this.setButtonText("externalPrivatePermissionDir.list(regex)"); break;

            case 8: this.setButtonText("externalPublicDir.list()"     ); break;
            case 9: this.setButtonText("externalPublicDir.list(regex)"); break;

            case 10: this.setButtonText("externalPublicPermissionDir.list()"     ); break;
            case 11: this.setButtonText("externalPublicPermissionDir.list(regex)"); break;

            case 12: this.setButtonText("http://www.example.org/"              ); break;
            case 13: this.setButtonText("http://www.youtypeitwepostit.com/api/"); break;
        }
    }
}