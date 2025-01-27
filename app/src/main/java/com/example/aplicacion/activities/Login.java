package com.example.aplicacion.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aplicacion.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth autenticacion;
    private String user, pass;
    private DatabaseReference bdApp;
    //private String rol;
    private EditText email;
    private EditText password;
    private Button login;
//    private ProgressDialog progreso;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        autenticacion = FirebaseAuth.getInstance();
        bdApp= FirebaseDatabase.getInstance().getReference();


        email = (EditText) findViewById(R.id.txtmail);
        password = (EditText) findViewById(R.id.txtpass);
        login = (Button) findViewById(R.id.btnlogin);

        //user = email.getText().toString().trim();
        //pass = password.getText().toString().trim();

  //      progreso = new ProgressDialog(this);
        login.setOnClickListener(this);

    }

    public void loguearUsuario() {


        user = email.getText().toString().trim();
        pass = password.getText().toString().trim();

        if (TextUtils.isEmpty(user)) {
            Toast.makeText(this, "se debe ingresar info", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "se debe ingresar info", Toast.LENGTH_LONG).show();
            return;
        }

    //    progreso.setMessage("Realizando verificación");
      //  progreso.show();

        autenticacion.signInWithEmailAndPassword(user, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    traerDatos(user);
                }

                else {

                        Toast.makeText(Login.this, "no se pudo loguear usuario y/o contraseña erróneas", Toast.LENGTH_LONG).show();

                }


            }
        });


    }

    private void traerDatos(final String usuario) {
        bdApp.child("Usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot info : dataSnapshot.getChildren()) {
                    datosObtenidosLogin data = info.getValue(datosObtenidosLogin.class);
                    String correoMail = data.getCorreo();
                    String roles = data.getRol();

                    if (data.getCorreo().equalsIgnoreCase(usuario) && data.getRol().equalsIgnoreCase("Admin")) {
                        Intent next = new Intent(getApplication(), MenuActivity.class);// DEBO BUSCAR PONER UNA BANDERA PARA ABRIR ACTIVIDAD
                        startActivity(next);
                    }

                }

                Intent next = new Intent(getApplication(), MenuActivity.class);
                startActivity(next);

                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {

        loguearUsuario();
    }
}
