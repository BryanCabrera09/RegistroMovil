package com.brytech.registromovil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Button btn_logueo, btn_registro;

    DatabaseReference Database;
    FirebaseAuth Auth;

    String correo = "", contra = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);
        btn_logueo = findViewById(R.id.Btn_Logueo);
        btn_registro = findViewById(R.id.Btn_Registro);

        Auth = FirebaseAuth.getInstance();
        Database = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        Iniciar_Control();

    }

    private void Iniciar_Control() {

        View.OnClickListener B = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.Btn_Logueo) {

                    EditText txt_correo = findViewById(R.id.Txt_Correo);
                    correo = txt_correo.getText().toString();

                    EditText txt_contra = findViewById(R.id.Txt_Password);
                    contra = txt_contra.getText().toString();

                    if (!correo.isEmpty() && !contra.isEmpty()) {

                        Iniciar_Sesion();

                    } else {
                        Toast.makeText(MainActivity.this, "Los campos deben estar llenos", Toast.LENGTH_SHORT).show();
                    }
                } else if (v.getId() == R.id.Btn_Registro) {

                    Intent inten = new Intent(getApplicationContext(), Registro.class);
                    startActivity(inten);
                }
            }
        };
        btn_logueo.setOnClickListener(B);
        btn_registro.setOnClickListener(B);
    }

    private void Iniciar_Sesion() {

        Auth.signInWithEmailAndPassword(correo, contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(MainActivity.this, "Acceso Autorizado", Toast.LENGTH_SHORT).show();
                    Intent inten = new Intent(getApplicationContext(), MenuInicio.class);
                    inten.putExtra("Email", correo);
                    inten.putExtra("Password", contra);
                    startActivity(inten);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Debe contrar con un registro", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}