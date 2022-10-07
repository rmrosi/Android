    package com.example.android.cookies;

    import android.graphics.Bitmap;
    import android.graphics.BitmapFactory;
    import android.graphics.Canvas;
    import android.graphics.Matrix;
    import android.os.Bundle;
    import android.support.v7.app.AppCompatActivity;
    import android.view.View;
    import android.widget.ImageView;
    import android.widget.TextView;

    import static java.lang.Boolean.FALSE;

    public class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
        }

        /**
         * Called when the cookie should be eaten.
         */
        public void eatCookie(View view) {
            Bitmap bmpUnion, bmpFlip, bmp1, bmp2;

            // TODO: Find a reference to the ImageView in the layout. Change the image.
            ImageView eatCokieImageView = (ImageView) findViewById(R.id.android_cookie_image_view);
            eatCokieImageView.setImageResource(R.drawable.before_cookie);
            bmp1 = BitmapFactory.decodeResource(getResources(),R.drawable.before_cookie);
            bmp2 = flip(bmp1, FLIP_HORIZONTAL);
            bmpUnion = combineImagesH(bmp1,bmp2,"R.drawable");
            bmpFlip = flip(bmpUnion, FLIP_VERTICAL);
            bmpUnion = combineImagesV(bmpUnion,bmpFlip);

            bmpUnion = resizeBmp(eatCokieImageView, bmpUnion);
            /* Associate the Bitmap to the ImageView */
            eatCokieImageView.setImageBitmap(bmpUnion);

            // TODO: Find a reference to the TextView in the layout. Change the text.
            TextView statusTextView = (TextView) findViewById(R.id.status_text_view);
            statusTextView.setText("I´m so full");

        }

        public Bitmap resizeBmp(ImageView imgView, Bitmap c){
            Bitmap cs = null;
            /* Get the size of the ImageView */
            int targetW = imgView.getWidth();
            int targetH = imgView.getHeight();

            //cs = Bitmap.createBitmap(c, 0, 0, targetW, targetH);
            cs = Bitmap.createScaledBitmap(c, targetW, targetH, FALSE);
            //Canvas comboImage = new Canvas(cs);
            //comboImage.drawBitmap(c, 0f, 0f, null);

            return cs;
        }

        public Bitmap combineImagesH(Bitmap c, Bitmap s, String loc) { // can add a 3rd parameter 'String loc' if you want to save the new image - left some code to do that at the bottom
            Bitmap cs = null;
            int width, height = 0;

            if(c.getHeight() > s.getHeight()) {
                height = c.getHeight();
                width = c.getWidth() + s.getWidth();
            } else {
                height = s.getHeight();
                width = c.getWidth() + s.getWidth();
            }

            cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

            Canvas comboImage = new Canvas(cs);

            comboImage.drawBitmap(c, 0f, 0f, null);
            comboImage.drawBitmap(s, c.getWidth(), 0f, null);

            // this is an extra bit I added, just incase you want to save the new image somewhere and then return the location
            /*String tmpImg = String.valueOf(System.currentTimeMillis()) + ".png";

            OutputStream os = null;
            try {
              os = new FileOutputStream(loc + tmpImg);
              cs.compress(Bitmap.CompressFormat.PNG, 100, os);
            } catch(IOException e) {
              Log.e("combineImages", "problem combining images", e);
            }*/

            return cs;
        }
        public Bitmap combineImagesV(Bitmap c, Bitmap s) { // can add a 3rd parameter 'String loc' if you want to save the new image - left some code to do that at the bottom
            Bitmap cs = null;

            int width, height = 0;

            if(c.getWidth() > s.getWidth()) {
                width = c.getWidth();
                height = c.getHeight() + s.getHeight();
            } else {
                width = s.getWidth();
                height = c.getHeight() + s.getHeight();
            }

            cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

            Canvas comboImage = new Canvas(cs);

            comboImage.drawBitmap(c, 0f, 0f, null);
            comboImage.drawBitmap(s, 0f, c.getHeight(), null);

            // this is an extra bit I added, just incase you want to save the new image somewhere and then return the location
            /*String tmpImg = String.valueOf(System.currentTimeMillis()) + ".png";

            OutputStream os = null;
            try {
              os = new FileOutputStream(loc + tmpImg);
              cs.compress(CompressFormat.PNG, 100, os);
            } catch(IOException e) {
              Log.e("combineImages", "problem combining images", e);
            }*/

            return cs;
        }
        // definicao de tipo
        public static final int FLIP_VERTICAL = 1;
        public static final int FLIP_HORIZONTAL = 2;

        public static Bitmap flip(Bitmap src, int type) {
            // criar new matrix para transformacao
            Matrix matrix = new Matrix();
            // if vertical
            if(type == FLIP_VERTICAL) {
                // y = y * -1
                matrix.preScale(1.0f, -1.0f);
            }
            // if horizonal
            else if(type == FLIP_HORIZONTAL) {
                // x = x * -1
                matrix.preScale(-1.0f, 1.0f);
                // unknown type
            } else {
                return null;
            }

            // return image transformada
            return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        }

    }