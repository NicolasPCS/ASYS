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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.asys.R;
import com.example.asys.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
    TextView tvCurrentUser;

    // Botones asistencia
    Button btnAsistenciaIngreso;
    Button btnAsistenciaSalida;

    private static final String TAG = "MyActivity";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        expandableView = (LinearLayout) root.findViewById(R.id.expandableLayout);
        expandBtn = (Button) root.findViewById(R.id.expandBtn);
        cardView = (CardView) root.findViewById(R.id.card_course_item);

        btnAsistenciaIngreso = (Button) root.findViewById(R.id.btnAsistenciaIngreso);
        btnAsistenciaSalida = (Button) root.findViewById(R.id.btnAsistenciaSalida);

        tvCurrentUser = (TextView) root.findViewById(R.id.textViewUser);

        /*btnIngresoAsistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Asistencia Registrada")
                        .setContentText("Tu asistencia se registro correctamente")
                        .show();
            }
        });*/

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}