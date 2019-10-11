package com.example.aplicacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Registrar extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private DatabaseReference bdApp;
    private Spinner Tipo;
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
    private String cedula,nombre,apellido1,apellido2,telefono,area,email,pass,tipos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        autenticacion = FirebaseAuth.getInstance();
        bdApp= FirebaseDatabase.getInstance().getReference();

        Tipo=(Spinner) findViewById(R.id.tipo);
        txtMail = (EditText) findViewById(R.id.campoMail);
        txtPass =(EditText) findViewById(R.id.campoPass);
        txtCedula =(EditText) findViewById(R.id.campoCedula);
        txtNombre =(EditText) findViewById(R.id.campoNombre);
        txtApellido1 =(EditText) findViewById(R.id.campoApellido);
        txtApellido2 =(EditText) findViewById(R.id.campoApellido2);
        txtTelefono =(EditText) findViewById(R.id.campoTelefono);
        txtArea =(EditText) findViewById(R.id.campoArea);
        BtRegistrar=(Button) findViewById(R.id.btregistrar);

        ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(this, R.array.roles,android.R.layout.simple_spinner_item);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Tipo.setAdapter(adaptador);


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
                tipos=Tipo.getSelectedItem().toString().trim();



                if(!nombre.isEmpty()&& !email.isEmpty() && !pass.isEmpty() && !cedula.isEmpty() && !apellido1.isEmpty() && !apellido2.isEmpty() && !telefono.isEmpty() && !area.isEmpty()){

                    if(pass.length() >6){
                        registrarUsuario();
                    }
                    else {
                        Toast.makeText(Registrar.this,"Contraseña minimo 6 caracteres", Toast.LENGTH_LONG).show();
                    }

                }
                else {
                    Toast.makeText(Registrar.this,"Debe completar los campos ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void registrarUsuario(){

        tipos=Tipo.getSelectedItem().toString();
        cedula= txtCedula.getText().toString();
        nombre= txtNombre.getText().toString();
        apellido1= txtApellido1.getText().toString();
        apellido2= txtApellido2.getText().toString();
        telefono= txtTelefono.getText().toString();
        area= txtArea.getText().toString();
        email=txtMail.getText().toString().trim();
        pass=txtPass.getText().toString().trim();

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
                    informacion.put("rol",tipos);

                    String id=autenticacion.getCurrentUser().getUid();
                    bdApp.child("Usuarios").child(id).setValue(informacion).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task1) {
                            if(task1.isSuccessful()){
                                Toast.makeText(Registrar.this,"Logueado y guardado en realtime", Toast.LENGTH_LONG).show();
                                finish();
                            }
                            else {
                                if (task1.getException() instanceof FirebaseAuthUserCollisionException) {
                                    Toast.makeText(Registrar.this, "username ya esta registrado", Toast.LENGTH_LONG).show();
                                }
                                else{
                                    Toast.makeText(Registrar.this, "No se pudo registrar en la bd", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });
                    Toast.makeText(Registrar.this,"Registrado correctamente", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(Registrar.this,"No se pudo registrar", Toast.LENGTH_LONG).show();
                }
                progreso.dismiss();
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
