package com.example.aplicacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
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

public class MainActivity extends AppCompatActivity  {

    private DatabaseReference bdApp;


    //Spinner Tipo;
    private EditText txtCedula;
    private EditText txtNombre;
    private EditText txtApellido1;
    private EditText txtApellido2;
    private EditText txtTelefono;
    private EditText txtArea;
    private EditText txtMail;
    private EditText txtPass;
    private ProgressDialog progreso;
    private FirebaseAuth autenticacion;
    private Button BtRegistrar;
    private String cedula,nombre,apellido1,apellido2,telefono,area,email,pass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autenticacion = FirebaseAuth.getInstance();
        bdApp= FirebaseDatabase.getInstance().getReference();

        //Tipo=(Spinner) findViewById(R.id.tipo);
        txtMail = (EditText) findViewById(R.id.campoMail);
        txtPass =(EditText) findViewById(R.id.campoPass);
        txtCedula =(EditText) findViewById(R.id.campoCedula);
        txtNombre =(EditText) findViewById(R.id.campoNombre);
        txtApellido1 =(EditText) findViewById(R.id.campoApellido1);
        txtApellido2 =(EditText) findViewById(R.id.campoApellido2);
        txtTelefono =(EditText) findViewById(R.id.campoTelefono);
        txtArea =(EditText) findViewById(R.id.campoArea);
        BtRegistrar=(Button) findViewById(R.id.btregistrar);



        progreso = new ProgressDialog(this);
        BtRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cedula=txtCedula.getText().toString().trim();
                nombre=txtNombre.getText().toString().trim();
                apellido1=txtApellido1.getText().toString().trim();
                apellido2=txtApellido2.getText().toString().trim();
                telefono=txtTelefono.getText().toString().trim();
                area=txtArea.getText().toString().trim();
                email=txtMail.getText().toString().trim();
                pass=txtPass.getText().toString().trim();



                if(!nombre.isEmpty()&& !email.isEmpty() && !pass.isEmpty() && !cedula.isEmpty() && !apellido1.isEmpty() && !apellido2.isEmpty() && !telefono.isEmpty() && !area.isEmpty()){

                    if(pass.length() >6){
                        registrarUsuario();
                    }
                    else {
                        Toast.makeText(MainActivity.this,"Contraseña minimo 6 caracteres", Toast.LENGTH_LONG).show();
                    }

                }
                else {
                    Toast.makeText(MainActivity.this,"Debe completar los campos ", Toast.LENGTH_LONG).show();
                }
            }
        });



    }




    public void registrarUsuario(){

        //String tipos=Tipo.getSelectedItem().toString();
        final String cedula= txtCedula.getText().toString();
        final String nombre= txtNombre.getText().toString();
        final String apellido1= txtApellido1.getText().toString();
        final String apellido2= txtApellido2.getText().toString();
        final String telefono= txtTelefono.getText().toString();
        final String area= txtArea.getText().toString();
        final String email=txtMail.getText().toString().trim();
        final String pass=txtPass.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"se debe ingresar info", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(this,"se debe ingresar info", Toast.LENGTH_LONG).show();
            return;
        }

        progreso.setMessage("Realizando registro");
        progreso.show();

        autenticacion.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Map<String, Object> informacion = new HashMap<>();

                    informacion.put("nombre", nombre);
                    informacion.put("cedula", cedula);
                    informacion.put("apellido1", apellido1);
                    informacion.put("apellido2", apellido2);
                    informacion.put("telefono", telefono);
                    informacion.put("area", area);
                    informacion.put("correo",email);
                    informacion.put("password",pass);

                    String id=autenticacion.getCurrentUser().getUid();
                    bdApp.child("Usuarios").child(id).setValue(informacion).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task1) {
                            if(task1.isSuccessful()){
                                Toast.makeText(MainActivity.this,"Logueado y guardado en realtime", Toast.LENGTH_LONG).show();
                                finish();
                            }
                            else {
                                Toast.makeText(MainActivity.this,"No se pudieron crear lo datos en la bd", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    Toast.makeText(MainActivity.this,"Registrado correctamente", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(MainActivity.this,"No se pudo registrar", Toast.LENGTH_LONG).show();
                }
                progreso.dismiss();
            }
        });


    }

}

