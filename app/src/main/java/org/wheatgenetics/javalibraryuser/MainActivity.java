package org.wheatgenetics.javalibraryuser;

/**
 * Uses:
 * android.app.Activity
 * android.os.Bundle
 * android.support.annotation.IdRes
 * android.support.v7.app.AppCompatActivity
 * android.view.View
 * android.widget.Button
 * android.widget.TextView
 *
 * org.wheatgenetics.javalib.Dir
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.javalibraryuser.R
 */
public class MainActivity extends android.support.v7.app.AppCompatActivity
{
    private static class Dir extends org.wheatgenetics.javalib.Dir
    {
        private Dir(final java.io.File path, final android.app.Activity activity,
        @android.support.annotation.IdRes final int id)
        {
            super(path, ".javalibraryuser");

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
    private int                     buttonClickCount  = 0   ;
    // endregion

    // region Private Methods
    private void setButtonText(final java.lang.String text)
    { assert null != this.button; this.button.setText(text); }

    private void setMultiLineTextViewText(final java.lang.String fileNames[])
    {
        final java.lang.String text;
        if (null == fileNames)
            text = null;
        else
            if (fileNames.length < 1)
                text = null;
            else
            {
                final java.lang.StringBuilder stringBuilder =
                    new java.lang.StringBuilder(fileNames[0]);
                {
                    final int second = 1, last = fileNames.length - 1;
                    for (int i = second; i <= last; i++)
                        stringBuilder.append('\n').append(fileNames[i]);
                }
                text = stringBuilder.toString();
            }

        assert null != this.multiLineTextView; this.multiLineTextView.setText(text);
    }
    // endregion

    @java.lang.Override protected void onCreate(final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(org.wheatgenetics.javalibraryuser.R.layout.activity_main);

        {
            final java.lang.String unadjusted = "  2 leading spaces";
            final android.widget.TextView adjustTextView =
                this.findViewById(org.wheatgenetics.javalibraryuser.R.id.adjustTextView);
            assert null != adjustTextView;
            adjustTextView.setText(java.lang.String.format("adjust(\"%s\") is \"%s\"",
                unadjusted, org.wheatgenetics.javalib.Utils.adjust(unadjusted)));
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
            final java.lang.String regex = ".+\\.xml";
            switch (this.buttonClickCount)
            {
                case 0: this.setMultiLineTextViewText(this.internalDir.list(     )); break;
                case 1: this.setMultiLineTextViewText(this.internalDir.list(regex)); break;

                case 2: this.setMultiLineTextViewText(this.externalPrivateDir.list(     )); break;
                case 3: this.setMultiLineTextViewText(this.externalPrivateDir.list(regex)); break;

                case 4: this.setMultiLineTextViewText(this.externalPublicDir.list(     )); break;
                case 5: this.setMultiLineTextViewText(this.externalPublicDir.list(regex)); break;
            }
        }

        switch (this.buttonClickCount)
        {
            case 0: case 1: case 2: case 3: case 4: this.buttonClickCount++  ; break;
            default:                                this.buttonClickCount = 0; break;
        }

        switch (this.buttonClickCount)
        {
            case 0: this.setButtonText("internalDir.list()"     ); break;
            case 1: this.setButtonText("internalDir.list(regex)"); break;

            case 2: this.setButtonText("externalPrivateDir.list()"     ); break;
            case 3: this.setButtonText("externalPrivateDir.list(regex)"); break;

            case 4: this.setButtonText("externalPublicDir.list()"     ); break;
            case 5: this.setButtonText("externalPublicDir.list(regex)"); break;
        }
    }
}