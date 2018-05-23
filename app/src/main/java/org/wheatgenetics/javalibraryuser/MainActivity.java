package org.wheatgenetics.javalibraryuser;

/**
 * Uses:
 * android.os.Bundle
 * android.support.v7.app.AppCompatActivity
 * android.widget.TextView
 *
 * org.wheatgenetics.javalib.Dir
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.javalibraryuser.R
 */
public class MainActivity extends android.support.v7.app.AppCompatActivity
{
    @java.lang.Override protected void onCreate(final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(org.wheatgenetics.javalibraryuser.R.layout.activity_main);

        {
            final java.lang.String        unadjusted     = "  2 leading spaces";
            final android.widget.TextView adjustTextView =
                this.findViewById(org.wheatgenetics.javalibraryuser.R.id.adjustTextView);
            assert null != adjustTextView;
            adjustTextView.setText(java.lang.String.format("adjust(\"%s\") is \"%s\"",
                unadjusted, org.wheatgenetics.javalib.Utils.adjust(unadjusted)));
        }

        class Dir extends org.wheatgenetics.javalib.Dir
        { private Dir(final java.io.File path) { super(path, ".javalibraryuser"); } }

        org.wheatgenetics.javalib.Dir dir      = new Dir(this.getFilesDir());
        android.widget.TextView       textView = this.findViewById(
            org.wheatgenetics.javalibraryuser.R.id.internalDirTextView);
        assert null != textView; textView.setText(dir.getPathAsString());

        dir      = new Dir(this.getExternalFilesDir(null));
        textView = this.findViewById(
            org.wheatgenetics.javalibraryuser.R.id.externalPrivateDirTextView);
        assert null != textView; textView.setText(dir.getPathAsString());

        dir      = new Dir(android.os.Environment.getExternalStorageDirectory());
        textView = this.findViewById(
            org.wheatgenetics.javalibraryuser.R.id.externalPublicDirTextView);
        assert null != textView; textView.setText(dir.getPathAsString());
    }
}