package com.example.dsign;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MainActivity extends AppCompatActivity {

    ImageView mImageView;
    Button mButton;
    Button mButton1;
    Button mButton2;

    private static final int IMAGE_PICK_CODE =1000;
    private static final int PERMISSION_CODE  =1001;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //VIEWS
        mImageView= findViewById((R.id.imageView));
        mButton = findViewById((R.id.permission));
        mButton1 = findViewById(R.id.flipleft);
        mButton2 = findViewById(R.id.flipright);
        public static final int FLIP_VERTICAL=1;
        public static final int FLIP_HORIZONTAL = 2;

        mButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        //denied
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_CODE);
                    }
                    else{
                    //granted
                        pickImageFromGallery();
                    }
                }
                else {
                    //lowend device
                    pickImageFromGallery();
                }
            }
        });

        mButton1.setOnClickListener(new OnClickListener() {

            @Override
            protected void onClick(Bundle savedInstanceState) {
                super.onClick(savedInstanceState);
                setContentView(R.layout.activity_main);
                mImageView = (ImageView) findViewById(R.id.imageView);

                mImageView.setImageBitmap(flipImage(BitmapFactory.decodeResource(getResources(), R.drawable.car), 2));
            }
            public Bitmap flipImage(Bitmap src, int type) {
                Matrix matrix = new Matrix();

                matrix.preScale(1.0f, -1.0f);
            }
                        return Bitmap.createBitmap(src, 0, 0,src..getWidth(),



            }
        });

    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery();
                }
                else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            mImageView.setImageURI(data.getData());
        }
    }
}
