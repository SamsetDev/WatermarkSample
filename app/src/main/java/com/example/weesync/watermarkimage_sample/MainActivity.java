package com.example.weesync.watermarkimage_sample;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnLoadImage1,next;
    TextView textSource1;
    EditText editText;
    Button btnProcessing;
    ImageView imageResult;

    final int RQS_IMAGE1 = 1;
    Uri urii;

    Bitmap saveBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLoadImage1 = (Button)findViewById(R.id.loadimage1);
        next = (Button)findViewById(R.id.next);
        textSource1 = (TextView)findViewById(R.id.sourceuri1);
        editText = (EditText)findViewById(R.id.caption);
        btnProcessing = (Button)findViewById(R.id.processing);
        imageResult = (ImageView)findViewById(R.id.result);

        btnLoadImage1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RQS_IMAGE1);
            }
        });

        btnProcessing.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (urii != null) {
                    Bitmap processedBitmap = ProcessingBitmap();
                    if (processedBitmap != null) {
                        imageResult.setImageBitmap(processedBitmap);
                        imageResult.buildDrawingCache();
                        saveBitmap=((BitmapDrawable)imageResult.getDrawable()).getBitmap();

                        //saveImageToExternalStorage(image);

                       // Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Something wrong in processing!",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Select both image!",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (saveBitmap!=null)// putExtra("data",saveBitmap)
                {
                    Main2Activity.set(saveBitmap);
                    startActivity(new Intent(MainActivity.this, Main2Activity.class));
                }

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case RQS_IMAGE1:
                    urii = data.getData();
                    textSource1.setText(urii.toString());
                    break;
            }
        }
    }

    private Bitmap ProcessingBitmap(){
        Bitmap bm1 = null;
        Bitmap newBitmap = null;

        try {
            //Bitmap b=waterMark(BitmapFactory.decodeResource(getResources(), R.drawable.img), "Welcome To Hamad's Blog", p, Color.WHITE, 90, 30, true);
                //bm1=BitmapFactory.decodeResource(getResources(), R.drawable.img);
            bm1 = BitmapFactory.decodeStream(getContentResolver().openInputStream(urii));

            Bitmap.Config config = bm1.getConfig();
            if(config == null){
                config = Bitmap.Config.ARGB_8888;
            }

            newBitmap = Bitmap.createBitmap(bm1.getWidth(), bm1.getHeight(), config);
            Canvas newCanvas = new Canvas(newBitmap);

            newCanvas.drawBitmap(bm1, 0, 0, null);

            String captionString = editText.getText().toString();
            if(captionString != null){

                Paint paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
                paintText.setColor(Color.BLUE);
                paintText.setTextSize(50);
                paintText.setStyle(Paint.Style.FILL);
                paintText.setShadowLayer(10f, 10f, 10f, Color.BLACK);

                Rect rectText = new Rect();
                paintText.getTextBounds(captionString, 0, captionString.length(), rectText);

                newCanvas.drawText(captionString,
                        0, rectText.height(), paintText);

                Toast.makeText(getApplicationContext(),
                        "drawText: " + captionString,
                        Toast.LENGTH_LONG).show();

            }else{
                Toast.makeText(getApplicationContext(),
                        "caption empty!",
                        Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();//FileNotFoundException
        }

        return newBitmap;
    }


}