package com.brytech.registromovil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

public class Camara extends AppCompatActivity {

    Button btn_camera;
    ImageView foto;

    String rutaImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara);

        btn_camera = findViewById(R.id.Btn_tomarFoto);
        foto = findViewById(R.id.Foto);

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Open_Camera();
            }
        });
    }

    private void Open_Camera() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(getPackageManager()) != null) {

            File imgArchivo = null;

            try {
                imgArchivo = Create_Image();
            } catch (IOException ex) {
                Log.e("Error", ex.toString());
            }

            if (imgArchivo != null) {
                Uri photoUri = FileProvider.getUriForFile(this, "com.brytech.registromovil.fileprovider", imgArchivo);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, 1);
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {

            //Bundle extras = data.getExtras();

            Bitmap imgBitmap = BitmapFactory.decodeFile(rutaImg);
            foto.setImageBitmap(imgBitmap);
        }
    }

    private File Create_Image() throws IOException {

        String nameImg = "img_";

        File directory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File img = File.createTempFile(nameImg, ".jpg", directory);

        rutaImg = img.getAbsolutePath();

        return img;
    }
}