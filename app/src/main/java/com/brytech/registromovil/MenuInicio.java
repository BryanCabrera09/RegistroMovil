package com.brytech.registromovil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MenuInicio extends AppCompatActivity {

    FirebaseAuth Auth;

    Button btn_cerrarSesion, btn_foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicio);

        btn_cerrarSesion = findViewById(R.id.Btn_LogOut);
        btn_foto = findViewById(R.id.Btn_foto);

        Auth = FirebaseAuth.getInstance();

        Iniciar_Control();
        Intent inten = getIntent();

        String correo = inten.getStringExtra("Email");
        String contra = inten.getStringExtra("Password");

        TextView text_correo = findViewById(R.id.txt_mensaje_correo);
        TextView text_contra = findViewById(R.id.txt_mensaje_contra);

        text_correo.setText(correo);
        text_contra.setText(contra);

    }

    private void Iniciar_Control() {

        View.OnClickListener B = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.Btn_LogOut) {
                    Auth.signOut();
                    Toast.makeText(MenuInicio.this, "Cerrado Sesion...", Toast.LENGTH_SHORT).show();
                    Intent inten = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(inten);
                    finish();
                }
                if (v.getId() == R.id.Btn_foto) {
                    Intent inten = new Intent(getApplicationContext(), Camara.class);
                    startActivity(inten);
                }
            }
        };
        btn_cerrarSesion.setOnClickListener(B);
        btn_foto.setOnClickListener(B);
    }
}