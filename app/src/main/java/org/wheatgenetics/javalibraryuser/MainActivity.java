package org.wheatgenetics.javalibraryuser;

/**
 * Uses:
 * android.os.Bundle
 * android.support.v7.app.AppCompatActivity
 * android.widget.TextView
 *
 * org.wheatgenetics.javalib.Utils
 * org.wheatgenetics.javalibraryuser.BuildConfig
 * org.wheatgenetics.javalibraryuser.R
 */

public class MainActivity extends android.support.v7.app.AppCompatActivity
{
    @java.lang.Override
    protected void onCreate(final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(org.wheatgenetics.javalibraryuser.R.layout.activity_main);

        final int                     number   = 2;
        final android.widget.TextView textView = (android.widget.TextView)
            this.findViewById(org.wheatgenetics.javalibraryuser.R.id.textView);
        if (org.wheatgenetics.javalibraryuser.BuildConfig.DEBUG && null == textView)
            throw new java.lang.AssertionError();
        textView.setText(java.lang.String.format("doubleOf(%d) is %d",
            number, org.wheatgenetics.javalib.Utils.doubleOf(number)));
    }
}