package com.brytech.registromovil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {

    Button btn_login, btn_registro;

    FirebaseAuth Auth_1;
    DatabaseReference Database_1;

    String nombre = "", corre = "", contras = "", confirmar = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Auth_1 = FirebaseAuth.getInstance();
        Database_1 = FirebaseDatabase.getInstance().getReference();

        btn_login = findViewById(R.id.Btn_SingUp);
        btn_registro = findViewById(R.id.Btn_CrearCuenta);
        Iniciar_Control();
        Intent intent = getIntent();
    }

    private void Iniciar_Control() {

        View.OnClickListener B = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (v.getId() == R.id.Btn_CrearCuenta) {

                    EditText txt_correo = findViewById(R.id.Txt_Email);
                    corre = txt_correo.getText().toString();

                    EditText txt_contra = findViewById(R.id.Txt_Password_S);
                    contras = txt_contra.getText().toString();

                    EditText txt_nombre = findViewById(R.id.Txt_Usuario);
                    nombre = txt_nombre.getText().toString();

                    EditText txt_confirmar = findViewById(R.id.Txt_Confripassword);
                    confirmar = txt_confirmar.getText().toString();

                    if (!corre.isEmpty() && !contras.isEmpty() && !nombre.isEmpty() && !confirmar.isEmpty()) {
                        if (contras.length() < 6) {
                            Toast.makeText(Registro.this, "La contraseña debe conter al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                        } else if (!(contras.equals(confirmar))) {
                            Toast.makeText(Registro.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                        } else {
                            Registrar_User();
                        }
                    } else {
                        Toast.makeText(Registro.this, "Los campos deben estar llenos", Toast.LENGTH_SHORT).show();
                    }
                } else if (v.getId() == R.id.Btn_SingUp) {
                    Intent inten = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(inten);
                }
            }
        };
        btn_login.setOnClickListener(B);
        btn_registro.setOnClickListener(B);
    }

    private void Registrar_User() {

        Auth_1.createUserWithEmailAndPassword(corre, contras).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Map<String, Object> map = new HashMap<>();
                    map.put("Usuario", nombre);
                    map.put("Email", corre);
                    map.put("Password", contras);

                    String id = Auth_1.getCurrentUser().getUid();

                    Database_1.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()) {
                                Toast.makeText(Registro.this, "Cuenta Creada Con Exito!", Toast.LENGTH_SHORT).show();
                                Intent inten = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(inten);
                                finish();
                            } else {
                                Toast.makeText(Registro.this, "Error al ejecutar el proceso TASK2S", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(Registro.this, "Error al ejecutar el proceso", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}