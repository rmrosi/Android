            package com.example.android.bookmatch;

            import android.graphics.Bitmap;
            import android.graphics.BitmapFactory;
            import android.graphics.Canvas;
            import android.graphics.Matrix;
            import android.os.Bundle;
            import android.support.v7.app.AppCompatActivity;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.ImageButton;
            import android.widget.ImageView;

            import static java.lang.Boolean.FALSE;

            public class MainActivity extends AppCompatActivity implements View.OnClickListener {
                // Type definition
                public static final int COMBINE_VERTICAL = 1;
                public static final int COMBINE_HORIZONTAL = 2;
                // Type definition
                public static final int FLIP_VERTICAL = 1;
                public static final int FLIP_HORIZONTAL = 2;

                private Bitmap bookMatch1,bookMatch2, bookMatch3, bookMatch4;
                ImageButton imagebtn1, imagebtn2, imagebtn3, imagebtn4, columnbtn, gridbtn;
                View linearLayout;

                private int height;
                private int width;
                @Override
                protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    setContentView(R.layout.activity_main);

                    imagebtn1 = (ImageButton) findViewById(R.id.button1);
                    imagebtn2 = (ImageButton) findViewById(R.id.button2);
                    imagebtn3 = (ImageButton) findViewById(R.id.button3);
                    imagebtn4 = (ImageButton) findViewById(R.id.button4);
                    columnbtn = (ImageButton) findViewById(R.id.button_column);
                    gridbtn = (ImageButton) findViewById(R.id.button_grid_2);


                    imagebtn1.setOnClickListener(this);
                    imagebtn2.setOnClickListener(this);
                    imagebtn3.setOnClickListener(this);
                    imagebtn4.setOnClickListener(this);
                    columnbtn.setOnClickListener(this);
                    gridbtn.setOnClickListener(this);

                    // Get width and height of LinearLayout BookMatch View
                    linearLayout= findViewById(R.id.view_linear_layout);

                    linearLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            ViewGroup.MarginLayoutParams paramsView = (ViewGroup.MarginLayoutParams)
                                    linearLayout.getLayoutParams();
                            ViewGroup.MarginLayoutParams paramsImage = (ViewGroup
                                    .MarginLayoutParams)
                                    imagebtn1.getLayoutParams();
                            height = linearLayout.getHeight(); //height is ready
                            width = linearLayout.getWidth();

                            // Calculate the size of each four ImageView to distribute them in equal size
                            // along the LinearLayout
                            height = height - paramsView.topMargin - paramsView.bottomMargin;
                            width = (width - paramsView.leftMargin - paramsView.rightMargin
                                    - 6*paramsImage.leftMargin)/4;
                            // Choice between height or width the size of square ImageView
                            if(height < width)
                            {
                                imagebtn1.getLayoutParams().width = height;
                                imagebtn1.getLayoutParams().height = height;

                                imagebtn2.getLayoutParams().width = height;
                                imagebtn2.getLayoutParams().height = height;

                                imagebtn3.getLayoutParams().width = height;
                                imagebtn3.getLayoutParams().height = height;

                                imagebtn4.getLayoutParams().width = height;
                                imagebtn4.getLayoutParams().height = height;
                            }
                            else
                            {
                                imagebtn1.getLayoutParams().width = width;
                                imagebtn1.getLayoutParams().height = width;

                                imagebtn2.getLayoutParams().width = width;
                                imagebtn2.getLayoutParams().height = width;

                                imagebtn3.getLayoutParams().width = width;
                                imagebtn3.getLayoutParams().height = width;

                                imagebtn4.getLayoutParams().width = width;
                                imagebtn4.getLayoutParams().height = width;
                            }

                        }
                    });

                }

                // Set ImageView from one of the generated bookmatch
                @Override
                public void onClick(View arg0) {
                    ImageView plateImageView = (ImageView) findViewById(R.id.android_bookmatch_image_view);
                    if(arg0 == imagebtn1){
                        // Associate the Bitmap to the ImageView
                        plateImageView.setImageBitmap(bookMatch1);
                    }else if (arg0 == imagebtn2){
                        // Associate the Bitmap to the ImageView
                        plateImageView.setImageBitmap(bookMatch2);
                    }else if (arg0 == imagebtn3){
                        // Associate the Bitmap to the ImageView
                        plateImageView.setImageBitmap(bookMatch3);
                    }else if (arg0 == imagebtn4){
                        // Associate the Bitmap to the ImageView
                        plateImageView.setImageBitmap(bookMatch4);
                    }else if(arg0 == columnbtn){
                        arg0.setActivated(!arg0.isActivated());
                        arg0.setClickable(false);
                        bookMatchColumn(arg0);
                        gridbtn.setClickable(true);
                        gridbtn.setActivated(!arg0.isActivated());
                    }else if(arg0==gridbtn) {
                        arg0.setActivated(!arg0.isActivated());
                        arg0.setClickable(false);
                        bookMatchGrid2(arg0);
                        columnbtn.setClickable(true);
                        columnbtn.setActivated(!arg0.isActivated());
                    }
                }
                /**
                 * Called when the bookmatch_column construction image_view button is pressed.
                 */
                public void bookMatchColumn(View view) {
                    Bitmap bmpUnion, bmpFlip, bmp1, bmp2;
                   // TODO: Find a reference to the ImageView in the layout. Create the bookmatch 1 and set ImageView and button1.
                    ImageView plateImageView = (ImageView) findViewById(R.id.android_bookmatch_image_view);
                    plateImageView.setImageResource(R.drawable.plate);
                    bmp1 = BitmapFactory.decodeResource(getResources(),R.drawable.plate);

                    // Flip image vertically
                    bmp2 = flip(bmp1, FLIP_VERTICAL);

                    // Join image 1 and image 2 vertically
                    bmpUnion = combineImages(bmp1,bmp2,COMBINE_VERTICAL);

                    // Resize image to ImageView dimension
                    bookMatch1 = resizeBmp(plateImageView, bmpUnion);

                    // Associate the Bitmap to the ImageView
                    plateImageView.setImageBitmap(bookMatch1);

                    // Associate the Bitmap to the button1
                    ImageButton imgButton1 = (ImageButton) findViewById(R.id.button1);
                    imgButton1.setImageBitmap(resizeBmp(imgButton1,bookMatch1));

                    // TODO: Create the bookmatch 2.
                    // Join image 2 and image 1 vertically

                    bmpUnion = combineImages(bmp2,bmp1,COMBINE_VERTICAL);

                    // Resize image to ImageView dimension
                    bookMatch2 = resizeBmp(plateImageView, bmpUnion);

                    // Associate the Bitmap to the button2
                    ImageButton imgButton2 = (ImageButton) findViewById(R.id.button2);
                    imgButton2.setImageBitmap(resizeBmp(imgButton2,bookMatch2));

                    // TODO: Create the bookmatch 3.
                    // Flip image vertically
                    bmp2 = flip(bmp1, FLIP_HORIZONTAL);

                    // Join image 1 and image 2 horizontally
                    bmpUnion = combineImages(bmp1,bmp2,COMBINE_HORIZONTAL);

                    // Resize image to ImageView dimension
                    bookMatch3 = resizeBmp(plateImageView, bmpUnion);

                    // Associate the Bitmap to the button3
                    ImageButton imgButton3 = (ImageButton) findViewById(R.id.button3);
                    imgButton3.setImageBitmap(resizeBmp(imgButton3,bookMatch3));

                    // TODO: Create the bookmatch 4.
                    // Join image 2 and image 1 horizontally creating a top image
                    bmpUnion = combineImages(bmp2,bmp1,COMBINE_HORIZONTAL);

                    // Resize image to ImageView dimension
                    bookMatch4 = resizeBmp(plateImageView, bmpUnion);

                    // Associate the Bitmap to the button4
                    ImageButton imgButton4 = (ImageButton) findViewById(R.id.button4);
                    imgButton4.setImageBitmap(resizeBmp(imgButton4,bookMatch4));
                }

                /**
                 * Called when the bookmatch_grid construction image_view button is pressed.
                 */
                public void bookMatchGrid2(View view) {
                    Bitmap bmpUnion, bmpFlip, bmp1, bmp2;

                    // TODO: Find a reference to the ImageView in the layout. Create the bookmatch 1 and set ImageView and button1.
                    ImageView plateImageView = (ImageView) findViewById(R.id.android_bookmatch_image_view);
                    plateImageView.setImageResource(R.drawable.plate);
                    bmp1 = BitmapFactory.decodeResource(getResources(),R.drawable.plate);

                    // Flip image horizontally
                    bmp2 = flip(bmp1, FLIP_HORIZONTAL);

                    // Join image 1 and image 2 horizontally creating a top image
                    bmpUnion = combineImages(bmp1,bmp2,COMBINE_HORIZONTAL);

                    // Flip join image creating a bottom image
                    bmpFlip = flip(bmpUnion, FLIP_VERTICAL);

                    // Join top and bottom image
                    bmpUnion = combineImages(bmpUnion,bmpFlip,COMBINE_VERTICAL);

                    // Resize image to ImageView dimension
                    bookMatch1 = resizeBmp(plateImageView, bmpUnion);

                    // Associate the Bitmap to the ImageView
                    plateImageView.setImageBitmap(bookMatch1);

                    // Associate the Bitmap to the button1
                    ImageButton imgButton1 = (ImageButton) findViewById(R.id.button1);
                    imgButton1.setImageBitmap(resizeBmp(imgButton1,bookMatch1));

                    // TODO: Create the bookmatch 2.
                    // Join image 1 and image 2 horizontally creating a top image
                    bmpUnion = combineImages(bmp1,bmp2,COMBINE_HORIZONTAL);

                    // Flip join image creating a bottom image
                    bmpFlip = flip(bmpUnion, FLIP_VERTICAL);

                    // Join top and bottom image
                    bmpUnion = combineImages(bmpFlip,bmpUnion, COMBINE_VERTICAL);

                    // Resize image to ImageView dimension
                    bookMatch2 = resizeBmp(plateImageView, bmpUnion);

                    // Associate the Bitmap to the button2
                    ImageButton imgButton2 = (ImageButton) findViewById(R.id.button2);
                    imgButton2.setImageBitmap(resizeBmp(imgButton2,bookMatch2));

                    // TODO: Create the bookmatch 3.
                    // Join image 2 and image 1 horizontally creating a top image
                    bmpUnion = combineImages(bmp2,bmp1,COMBINE_HORIZONTAL);

                    // Flip join image creating a bottom image
                    bmpFlip = flip(bmpUnion, FLIP_VERTICAL);

                    // Join top and bottom image
                    bmpUnion = combineImages(bmpFlip,bmpUnion, COMBINE_VERTICAL);

                    // Resize image to ImageView dimension
                    bookMatch3 = resizeBmp(plateImageView, bmpUnion);

                    // Associate the Bitmap to the ImageView
                    plateImageView.setImageBitmap(bookMatch3);

                    // Associate the Bitmap to the button3
                    ImageButton imgButton3 = (ImageButton) findViewById(R.id.button3);
                    imgButton3.setImageBitmap(resizeBmp(imgButton3,bookMatch3));

                    // TODO: Create the bookmatch 4.
                    // Join image 2 and image 1 horizontally creating a top image
                    bmpUnion = combineImages(bmp2,bmp1,COMBINE_HORIZONTAL);

                    // Flip join image creating a bottom image
                    bmpFlip = flip(bmpUnion, FLIP_VERTICAL);

                    // Join top and bottom image
                    bmpUnion = combineImages(bmpUnion, bmpFlip,COMBINE_VERTICAL);

                    // Resize image to ImageView dimension
                    bookMatch4 = resizeBmp(plateImageView, bmpUnion);

                    // Associate the Bitmap to the button4
                    ImageButton imgButton4 = (ImageButton) findViewById(R.id.button4);
                    imgButton4.setImageBitmap(resizeBmp(imgButton4,bookMatch4));
                }

                // Changes the size of the bitmap
                public Bitmap resizeBmp(View someView, Bitmap c){
                    Bitmap cs = null;

                    /* Get the size of the View (ImageView or Button)*/
                    int targetW = someView.getWidth();
                    int targetH = someView.getHeight();

                    cs = Bitmap.createScaledBitmap(c, targetW, targetH, FALSE);

                    return cs;
                }

                public Bitmap combineImages(Bitmap c, Bitmap s, int type) { // can add a 3rd parameter 'String
                    // loc' if you want to save the new image - left some code to do that at the bottom
                    Bitmap cs = null;
                    int width, height;

                    if(type==COMBINE_VERTICAL){
                        if(c.getWidth() > s.getWidth()) {
                            width = c.getWidth();
                            height = c.getHeight() + s.getHeight();
                        } else {
                            width = s.getWidth();
                            height = c.getHeight() + s.getHeight();
                        }
                    }
                    else if(type == COMBINE_HORIZONTAL){
                        if(c.getHeight() > s.getHeight()) {
                            height = c.getHeight();
                            width = c.getWidth() + s.getWidth();
                        } else {
                            height = s.getHeight();
                            width = c.getWidth() + s.getWidth();
                        }
                    }
                    else{
                        return null;
                    }

                    cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

                    Canvas comboImage = new Canvas(cs);

                    comboImage.drawBitmap(c, 0f, 0f, null);

                    if(type==COMBINE_VERTICAL) {
                        comboImage.drawBitmap(s, 0f, c.getHeight(), null);
                    }
                    else {
                        comboImage.drawBitmap(s, c.getWidth(), 0f, null);
                    }

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

                public static Bitmap flip(Bitmap src, int type) {
                    // Build new matrix for transformation
                    Matrix matrix = new Matrix();
                    // if vertical
                    if(type == FLIP_VERTICAL) {
                        // y = y * -1
                        matrix.preScale(1.0f, -1.0f);
                    }
                    // if horizontal
                    else if(type == FLIP_HORIZONTAL) {
                        // x = x * -1
                        matrix.preScale(-1.0f, 1.0f);
                        // unknown type
                    } else {
                        return null;
                    }

                    // return transformed image
                    return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
                }

            }