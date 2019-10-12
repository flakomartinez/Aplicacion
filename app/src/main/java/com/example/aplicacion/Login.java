package com.example.aplicacion;

import androidx.annotation.NonNull;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth autenticacion;
    private String user, pass;
    private DatabaseReference bdApp;
    private String rol;
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
                    bdApp.child("Usuarios").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for(DataSnapshot info : dataSnapshot.getChildren()){
                                datosObtenidosLogin data = info.getValue(datosObtenidosLogin.class);
                                String correoMail = data.getCorreo();
                                String roles = data.getRol();

                                if(correoMail== user){
                                    Intent next = new Intent(getApplication(), MainActivity.class);
                                    startActivity(next);
                                }
                                else{
                                    Intent next = new Intent(getApplication(), Conductor.class);
                                    startActivity(next);
                                }

                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
            }

                else {

                        Toast.makeText(Login.this, "no se pudo loguear", Toast.LENGTH_LONG).show();

                }


            }
        });


    }

    @Override
    public void onClick(View v) {

        loguearUsuario();
    }
}
