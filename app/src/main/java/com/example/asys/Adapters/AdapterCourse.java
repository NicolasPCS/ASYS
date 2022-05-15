package com.example.asys.Adapters;

import android.content.Context;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asys.Entities.Course;
import com.example.asys.R;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AdapterCourse extends RecyclerView.Adapter<AdapterCourse.ViewHolder> {

    LayoutInflater inflater;
    ArrayList<Course> model;

    // Listener
    private View.OnClickListener listener;

    public AdapterCourse(Context context, ArrayList<Course> model) {
        this.inflater = LayoutInflater.from(context);
        this.model = model;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_course_item, parent, false);
        //view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    /*public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }*/

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String nombreCurso = model.get(position).getNombrecurso();
        String nombreAula = model.get(position).getAula();
        String nombreDocente = model.get(position).getNombredocente();
        String horario = model.get(position).getHorario();

        holder.nombreCurso.setText(nombreCurso);
        holder.nombreAula.setText(nombreAula);
        holder.nombreDocente.setText(nombreDocente);
        holder.horario.setText(horario);

        // Set events
        holder.setOnClickListeners();
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    /*@Override
    public void onClick(View view) {

    }*/

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Context context;

        TextView nombreCurso, nombreAula, nombreDocente,horario;
        Button btnAsistenciaIngreso, btnAsistenciaSalida;

        // Expandable view
        public LinearLayout expandableView;
        public CardView cardView;
        public Button expandBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();

            nombreCurso = itemView.findViewById(R.id.nombreCurso);
            nombreAula = itemView.findViewById(R.id.nombreAula);
            nombreDocente = itemView.findViewById(R.id.nombreDocente);
            horario = itemView.findViewById(R.id.horario);

            // Botones Asistencia
            btnAsistenciaIngreso = itemView.findViewById(R.id.btnAsistenciaIngreso);
            btnAsistenciaSalida = itemView.findViewById(R.id.btnAsistenciaSalida);

            // Expandable CardView
            expandableView = (LinearLayout) itemView.findViewById(R.id.expandableLayout);
            expandBtn = (Button) itemView.findViewById(R.id.expandBtn);
            cardView = (CardView) itemView.findViewById(R.id.card_course_item);
        }

        void setOnClickListeners() {
            btnAsistenciaIngreso.setOnClickListener(this);
            btnAsistenciaSalida.setOnClickListener(this);
            expandBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnAsistenciaIngreso:
                    showAlertOk();
                    break;
                case R.id.btnAsistenciaSalida:
                    showAlertWarning();
                    break;
                case R.id.expandBtn:
                    if (expandableView.getVisibility() == View.GONE) {
                        TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                        expandableView.setVisibility(View.VISIBLE);
                        expandBtn.setText("OCULTAR");
                    } else {
                        TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                        expandableView.setVisibility(View.GONE);
                        expandBtn.setText("MARCAR ASISTENCIA");
                    }
                    break;
            }
        }
    }

    public void showAlertOk() {
        new SweetAlertDialog(inflater.getContext(), SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Asistencia Registrada")
                .setContentText("Tu asistencia se registro correctamente")
                .show();
    }

    public void showAlertWarning() {
        new SweetAlertDialog(inflater.getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Tu asistencia se modificará")
                .setContentText("¿Seguro que quieres hacerlo?")
                .setConfirmText("Ok")
                .show();
    }

    public void showAlertError() {
        new SweetAlertDialog(inflater.getContext(), SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Tu asistencia se eliminará")
                .setContentText("¿Seguro que quieres hacerlo?")
                .show();
    }
}
