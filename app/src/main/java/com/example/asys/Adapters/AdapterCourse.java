package com.example.asys.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asys.Entities.Course;
import com.example.asys.R;

import java.util.ArrayList;

public class AdapterCourse extends RecyclerView.Adapter<AdapterCourse.ViewHolder> {

    LayoutInflater inflater;
    ArrayList<Course> model;

    public AdapterCourse(Context context, ArrayList<Course> model) {
        this.inflater = LayoutInflater.from(context);
        this.model = model;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_course_item, parent, false);

        return new ViewHolder(view);
    }

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
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombreCurso, nombreAula, nombreDocente,horario;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreCurso = itemView.findViewById(R.id.nombreCurso);
            nombreAula = itemView.findViewById(R.id.nombreAula);
            nombreDocente = itemView.findViewById(R.id.nombreDocente);
            horario = itemView.findViewById(R.id.horario);
        }
    }
}
