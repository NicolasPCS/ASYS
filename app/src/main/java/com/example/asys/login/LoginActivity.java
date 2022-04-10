package com.example.asys.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asys.MainActivity;
import com.example.asys.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    Button btn_login;
    EditText usuario, contrasenia;
    AlertDialog.Builder builder;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Making notification bar transparent
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_login);

        changeStatusBarColor();

        mAuth = FirebaseAuth.getInstance();

        btn_login = (Button) findViewById(R.id.btnLogin);
        usuario = (EditText) findViewById(R.id.loginUsuario);
        contrasenia = (EditText) findViewById(R.id.loginContrasena);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailUser = usuario.getText().toString().trim();
                String passUser = contrasenia.getText().toString().trim();

                if (emailUser.isEmpty()) {
                    usuario.setError("Ingrese un usuario");
                    usuario.requestFocus();
                } else if (passUser.isEmpty()) {
                    contrasenia.setError("Ingrese una contraseña");
                    contrasenia.requestFocus();
                } else {
                    loginUser(emailUser, passUser);
                }
            }
        });
    }

    private void loginUser(String emailUser, String passUser) {
        mAuth.signInWithEmailAndPassword(emailUser,passUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    finish();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("Se ha producido un error");
                    builder.setMessage("Intentalo más tarde");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Aceptar", null);
                    builder.show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("Error de autenticación");
                builder.setMessage("Usuario o contraseña incorrectos");
                builder.setCancelable(false);
                builder.setPositiveButton("Aceptar", null);
                builder.show();
            }
        });
    }

    private void changeStatusBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            /*Toast.makeText(this, user.getDisplayName(), Toast.LENGTH_SHORT).show();*/
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
        }
    }
}