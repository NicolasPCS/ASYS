package com.example.asys.ui.home;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.asys.R;
import com.example.asys.databinding.FragmentHomeBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    LinearLayout expandableView;
    Button expandBtn;
    CardView cardView;

    // Botones asistencia
    Button btnRegistroAsistencia;
    Button btnActualizarAsistencia;
    Button btnEliminarAsistencia;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        expandableView = (LinearLayout) root.findViewById(R.id.expandableLayout);
        expandBtn = (Button) root.findViewById(R.id.expandBtn);
        cardView = (CardView) root.findViewById(R.id.card_course_item);

        btnRegistroAsistencia = (Button) root.findViewById(R.id.btnRegistroAsistencia);
        btnActualizarAsistencia = (Button) root.findViewById(R.id.btnActualizarAsistencia);
        btnEliminarAsistencia = (Button) root.findViewById(R.id.btnEliminarAsistencia);

        /*btnIngresoAsistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Asistencia Registrada")
                        .setContentText("Tu asistencia se registro correctamente")
                        .show();
            }
        });*/

        btnRegistroAsistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Registrar(root);
            }
        });

        btnActualizarAsistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Modificar(root);
            }
        });

        btnEliminarAsistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Eliminar(root);
            }
        });

        expandBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (expandableView.getVisibility() == View.GONE) {
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    expandableView.setVisibility(View.VISIBLE);
                    expandBtn.setText("OCULTAR");
                } else {
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    expandableView.setVisibility(View.GONE);
                    expandBtn.setText("MARCAR ASISTENCIA");
                }
            }
        });


//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    public void showAlertOk() {
        new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Asistencia Registrada")
                .setContentText("Tu asistencia se registro correctamente")
                .show();
    }

    public void showAlertWarning() {
        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Tu asistencia se modificará")
                .setContentText("¿Seguro que quieres hacerlo?")
                .setConfirmText("Ok")
                .show();
    }

    public void showAlertError() {
        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Tu asistencia se eliminará")
                .setContentText("¿Seguro que quieres hacerlo?")
                .show();
    }

    public void Registrar(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getContext(),
                "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        String myDate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        String codAl = "2019118521";
        String nombre = "Nicolas Caytuiro Silva";
        String curso = "Tecnologías móviles";
        String descripcion = "Marco su asistencia de: " + curso;
        String hora = myDate;
        String horario = "En el horario de: 15:00 - 17:00";
        if (!codAl.isEmpty() && !descripcion.isEmpty() && !horario.isEmpty()) {
            ContentValues registro = new ContentValues();
            registro.put("codigo", codAl);
            registro.put("nombre", nombre);
            registro.put("descripcion", descripcion);
            registro.put("hora", hora);
            registro.put("horario", horario);
            BaseDeDatos.insert("asistencias", null, registro);
            BaseDeDatos.close();

            showAlertOk();
        } else {
            Toast.makeText(getContext(), "Debera llenar todos los campos",Toast.LENGTH_SHORT).show();
        }
    }

    public void Eliminar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper
                (getContext(), "administracion", null, 1);
        SQLiteDatabase BaseDatabase = admin.getWritableDatabase();
        String codAl = "2019118521";
        if(!codAl.isEmpty()){
            int cantidad = BaseDatabase.delete("asistencias","codigo=" + codAl,null);
            BaseDatabase.close();

            showAlertError();

            if(cantidad == 1){
                Toast.makeText(getContext(),"Asistencia eliminada exitosamente", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(),"La asistencia no existe", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getContext(), "Debes introducir tu codigo", Toast.LENGTH_SHORT).show();
        }
    }

    public void Modificar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper
                (getContext(), "administracion", null,1);
        SQLiteDatabase BaseDatabase = admin.getWritableDatabase();

        String myDate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        String codAl = "2019118521";
        String nombre = "Nicolas Caytuiro Silva";
        String curso = "Tecnologías móviles";
        String descripcion = "Marco su asistencia de: " + curso;
        String hora = myDate;
        String horario = "En el horario de: 15:00 - 17:00";
        if (!codAl.isEmpty() && !descripcion.isEmpty() && !horario.isEmpty()) {
            ContentValues registro = new ContentValues();
            registro.put("codigo", codAl);
            registro.put("nombre", nombre);
            registro.put("descripcion", descripcion);
            registro.put("hora", hora);
            registro.put("horario", horario);

            showAlertWarning();

            int cantidad = BaseDatabase.update("asistencias", registro, "codigo=" + codAl,null);
            BaseDatabase.close();
            if(cantidad == 1){
                Toast.makeText(getContext(), "Asistencia modificada correctamente", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(), "La Asistencia no existe", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getContext(), "Debes Marcar tu asistencia antes de realizar esta acción", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}