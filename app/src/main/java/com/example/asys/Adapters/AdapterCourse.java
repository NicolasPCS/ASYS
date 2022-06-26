package com.example.asys.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.Slide;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.asys.Entities.Course;
import com.example.asys.R;
import com.example.asys.ui.qr.QrScannerActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

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
        String token = model.get(position).getToken();

        holder.nombreCurso.setText(nombreCurso);
        holder.nombreAula.setText(nombreAula);
        holder.nombreDocente.setText(nombreDocente);
        holder.horario.setText(horario);

        String dia = model.get(position).getDia();
        String horaingreso = model.get(position).getHoraingreso();
        String horasalida = model.get(position).getHorasalida();

        holder.dia = dia;
        holder.horaingreso = horaingreso;
        holder.horasalida = horasalida;
        holder.token = token;

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

        String dia, horaingreso, horasalida;
        String token;

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

            validateIncome(context);
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
                    validateAttendanceWithQr(view, "Ingreso");
                    break;
                case R.id.btnAsistenciaSalida:
                    attendanceRegistration("Salida");
                    btnAsistenciaSalida.setEnabled(false);
                    break;
                case R.id.expandBtn:
                    //if (getDayOfWeek().equals(dia) && getHourMinutes(horaingreso)) {
                    if (getDayOfWeek().equals(dia) && (getHourMinutes(horaingreso) || getHourMinutes(horasalida))) {
                    //if (getDayOfWeek().equals(dia)) {
                        expandBtn.setEnabled(true);
                        expandLayout();
                        if (getHourMinutes(horaingreso)) {
                            btnAsistenciaIngreso.setEnabled(true);
                            btnAsistenciaSalida.setEnabled(false);
                        } else if (getHourMinutes(horasalida)) {
                            btnAsistenciaIngreso.setEnabled(false);
                            btnAsistenciaSalida.setEnabled(true);
                        }
                    } else {
                        expandBtn.setEnabled(false);
                        showAlertWarning();
                    }
                    break;
            }
        }

        private void validateAttendanceWithQr(View v, String tipoAsis) {
            Intent intent = new Intent(v.getContext(), QrScannerActivity.class);
            intent.putExtra("tipoAsis", tipoAsis);
            intent.putExtra("token", token);
            v.getContext().startActivity(intent);
        }

        public void validateIncome(Context context) {
            Intent intentAct = ((Activity) context).getIntent();
            Bundle extras = intentAct.getExtras();
            preRegistration(extras);
        }

        void preRegistration(Bundle extras) {
            if (extras != null) {
                if (extras.getString("clave").equals(extras.getString("token"))){
                    //Log.d("Hola: ", extras.getString("clave"));
                    //Log.d("Hola: ", extras.getString("tipoAsis"));
                    attendanceRegistration(extras.getString("tipoAsis"));
                    btnAsistenciaIngreso.setEnabled(false);
                } else {
                    Log.d("TAG: ", "son diferentes");
                }
            }
        }

        void attendanceRegistration(String attendanceType) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseAuth auth = FirebaseAuth.getInstance();
            String currentUser = auth.getCurrentUser().getEmail();

            Map<String, Object> attendance = new HashMap<>();
            attendance.put("tipoasistencia", attendanceType);
            attendance.put("aula", nombreAula.getText().toString());
            attendance.put("dia", dia);
            attendance.put("horaingreso", horaingreso);
            attendance.put("horasalida", horasalida);
            attendance.put("nombrecurso", nombreCurso.getText().toString());
            attendance.put("nombredocente", nombreDocente.getText().toString());

            db.collection("users").document(currentUser).collection("asistencias").add(attendance)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            //showAlertOk();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            showAlertError();
                        }
                    });
        }

        boolean getHourMinutes(String hora) {
            // Obtiene fecha y hora actuales
            Calendar fecha = Calendar.getInstance(TimeZone.getTimeZone(ZoneId.of("America/Lima")));
            Calendar fecha2 = Calendar.getInstance(TimeZone.getTimeZone(ZoneId.of("America/Lima")));

            fecha.add(Calendar.MINUTE,-5);
            fecha2.add(Calendar.MINUTE,5);

            String format = String.format("%1$tH:%1$tM:%1$tS", fecha);
            String format2 = String.format("%1$tH:%1$tM:%1$tS", fecha2);

            System.out.println("menos " + format);
            System.out.println("mas " + format2);

            String menos5min = format;
            String mas5min = format2;

            if (hora.compareTo(menos5min) > 0 && hora.compareTo(mas5min) < 0) {
                return true;
            }
            return false;
        }

        String getDayOfWeek() {
            LocalDate date2 = LocalDate.now(ZoneId.of("America/Lima")); // Gets current date in Paris
            // System.out.println("Dia lima " + date2.getDayOfWeek());

            switch (date2.getDayOfWeek().toString()) {
                case "MONDAY":
                    System.out.println("Lunes");
                    return "Lunes";
                case "TUESDAY":
                    System.out.println("Martes");
                    return "Martes";
                case "WEDNESDAY":
                    System.out.println("Miercoles");
                    return "Miercoles";
                case "THURSDAY":
                    System.out.println("Jueves");
                    return "Jueves";
                case "FRIDAY":
                    System.out.println("Viernes");
                    return "Viernes";
                case "SATURDAY":
                    System.out.println("Sábado");
                    return "Sábado";
                default:
                    break;
            }
            return "Invalid DOW";
        }

        void expandLayout() {
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
    }

    public void showAlertOk() {
        new SweetAlertDialog(inflater.getContext(), SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Asistencia Registrada")
                .setContentText("Tu asistencia se registro correctamente")
                .show();
    }

    public void showAlertWarning() {
        new SweetAlertDialog(inflater.getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Ups!")
                .setContentText("No te pertenece marcar tu asistencia en este horario")
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
