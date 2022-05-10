package com.example.asys.ui.home;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
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
    Button btnIngresoAsistencia;
    Button btnSalidaAsistencia;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        expandableView = (LinearLayout) root.findViewById(R.id.expandableLayout);
        expandBtn = (Button) root.findViewById(R.id.expandBtn);
        cardView = (CardView) root.findViewById(R.id.card_course_item);

        btnIngresoAsistencia = (Button) root.findViewById(R.id.btnAsistenciaIngreso);
        btnSalidaAsistencia = (Button) root.findViewById(R.id.btnAsistenciaSalida);

        /*btnIngresoAsistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Asistencia Registrada")
                        .setContentText("Tu asistencia se registro correctamente")
                        .show();
            }
        });*/

        btnIngresoAsistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardar(root);
            }
        });

        btnSalidaAsistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("¡UPS!")
                        .setContentText("Hubo un problema al registrar tu asistencia, intentalo en unos minutos")
                        .setConfirmText("Ok")
                        .show();
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

    public void showAlert() {
        new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Asistencia Registrada")
                .setContentText("Tu asistencia se registro correctamente")
                .show();
    }

    private boolean isExternalStorageWriteable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            Log.i("State","Yes, it is writable");
            return true;
        }
        return false;
    }

    public void guardar(View v) {
        String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        String fileName = "archivoEnSD";
        String contenido = "Nicolás Caytuiro Silva, marco su asistencia de:\n" +
                "Tecnologías móviles\n"+
                "A las: " + mydate +
                "\nEn el horario de: 09 - 11 am";
        if (isExternalStorageWriteable() && checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            File textFile = new File(Environment.getExternalStorageDirectory(), fileName);
            try {
                FileOutputStream fos = new FileOutputStream(textFile);
                fos.write(contenido.getBytes());
                fos.close();

                showAlert();

                Toast.makeText(getContext(),"Archivo guardado", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getContext(), "No se puede guardar en SD", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkPermission(String permission){
        int check = getActivity().checkSelfPermission(permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }


    public void guardar2(View view) {
        String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        String nombre = "archivoEnSD";
        String contenido = "Nicolás Caytuiro Silva, marco su asistencia de:\n" +
                "Tecnologías móviles\n"+
                "A las: " + mydate +
                "\nEn el horario de: 09 - 11 am";

        try {
            File tarejtaSD = Environment.getExternalStorageDirectory();
            File rutaArchivo = new File(tarejtaSD.getPath(),nombre);
            Toast.makeText(getContext(), tarejtaSD.getPath(), Toast.LENGTH_SHORT).show();
            OutputStreamWriter crearArchivo = new OutputStreamWriter(getContext().openFileOutput(nombre, Context.MODE_PRIVATE));

            crearArchivo.write(contenido);
            crearArchivo.flush();
            crearArchivo.close();

            Toast.makeText(getContext(), "Guardado correctamente", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(getContext(), "No se pudo guardar el archivo", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}