package org.wheatgenetics.javalibraryuser;

/**
 * Uses:
 * android.os.Bundle
 * android.support.v7.app.AppCompatActivity
 * android.widget.TextView
 *
 * org.wheatgenetics.javalib.Utils
 * org.wheatgenetics.javalibraryuser.R
 */

public class MainActivity extends android.support.v7.app.AppCompatActivity
{
    @java.lang.Override
    protected void onCreate(final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(org.wheatgenetics.javalibraryuser.R.layout.activity_main);

        final java.lang.String        unadjusted = "  2 leading spaces";
        final android.widget.TextView textView   = (android.widget.TextView)
            this.findViewById(org.wheatgenetics.javalibraryuser.R.id.textView);
        assert null != textView;
        textView.setText(java.lang.String.format("adjust(\"%s\") is \"%s\"",
            unadjusted, org.wheatgenetics.javalib.Utils.adjust(unadjusted)));
    }
}