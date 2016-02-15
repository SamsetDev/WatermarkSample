package com.example.weesync.watermarkimage_sample;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class Main2Activity extends AppCompatActivity {
 ImageView imageView;
    static Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        imageView= (ImageView) findViewById(R.id.result2);
        Intent intent=getIntent();
        //Bitmap bitmap = (Bitmap) intent.getParcelableExtra("data");
        imageView.setImageBitmap(bitmap);


    }
    public static Bitmap set(Bitmap by)
    {
        bitmap=by;
        return bitmap;
    }
}
