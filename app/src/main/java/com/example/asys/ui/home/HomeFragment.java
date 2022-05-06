package com.example.asys.ui.home;

import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.asys.R;
import com.example.asys.databinding.FragmentHomeBinding;

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

        btnIngresoAsistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Asistencia Registrada")
                        .setContentText("Tu asistencia se registro correctamente")
                        .show();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}