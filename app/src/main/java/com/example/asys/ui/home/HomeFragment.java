package com.example.asys.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asys.Adapters.AdapterCourse;
import com.example.asys.Entities.Course;
import com.example.asys.R;
import com.example.asys.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private LinearLayout expandableView;
    private Button expandBtn;
    private CardView cardView;

    // Firebase
    private DocumentReference docRef;
    private FirebaseAuth mAuth;

    // Adapter para inflar el CardView
    private AdapterCourse adapterCourse;
    private RecyclerView recyclerViewCourses;
    private ArrayList<Course> courseArrayList;

    private static final String TAG = "MyActivity";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        expandableView = (LinearLayout) root.findViewById(R.id.expandableLayout);
        expandBtn = (Button) root.findViewById(R.id.expandBtn);
        cardView = (CardView) root.findViewById(R.id.card_course_item);

        recyclerViewCourses = root.findViewById(R.id.recyclerViewAsis);
        courseArrayList = new ArrayList<>();

        // Firebase
        mAuth = FirebaseAuth.getInstance();

        // Cargar y mostrar lista
        cargarListaFirebase(new myCallBack() {
            @Override
            public void onCallback(ArrayList<Course> courseList) {
                mostrarData();
            }
        });

        /*Intent intentAct = ((Activity) getContext()).getIntent();
        Bundle extras = intentAct.getExtras();

        if (extras != null) {
            if (extras.getString("clave").equals("111111")){
                Toast.makeText(getContext(), extras.getString("clave"), Toast.LENGTH_SHORT).show();

            }
        }*/

        return root;
    }

    private void mostrarData() {
        recyclerViewCourses.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterCourse = new AdapterCourse(getContext(), courseArrayList);
        recyclerViewCourses.setAdapter(adapterCourse);

        Log.d("Size", String.valueOf(courseArrayList.size()));

        for (int i = 0; i < courseArrayList.size(); i++) {
            Log.d("Course", courseArrayList.get(i).getAula());
            Log.d("Course", courseArrayList.get(i).getHorario());
            Log.d("Course", courseArrayList.get(i).getNombrecurso());
            Log.d("Course", courseArrayList.get(i).getNombredocente());
            Log.d("Course", courseArrayList.get(i).getToken());
        }
    }

    public interface myCallBack {
        void onCallback(ArrayList<Course> courseList);
    }

    private void cargarListaFirebase(myCallBack mCallBack) {

        //courseArrayList.add(new Course("Aula", "Horario", "Nombre curso", "Nombre docente"));
        //courseArrayList.add(new Course("Aula", "Horario", "Nombre curso", "Nombre docente"));

        FirebaseFirestore.getInstance().collection("asistencias").document(mAuth.getCurrentUser().getEmail()).collection("cursos")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Course aux = new Course();
                                String dia, horaingreso, horasalida, horario;

                                aux.setAula(document.getString("aula"));

                                dia = document.getString("dia");
                                horaingreso = document.getString("horaingreso");
                                horasalida = document.getString("horasalida");

                                horario = dia + ": " + horaingreso + " - " + horasalida;

                                aux.setNombrecurso(document.getString("nombrecurso"));
                                aux.setNombredocente(document.getString("nombredocente"));

                                aux.setHorario(horario);
                                aux.setDia(dia);
                                aux.setHoraingreso(horaingreso);
                                aux.setHorasalida(horasalida);
                                aux.setToken(document.getString("token"));

                                courseArrayList.add(aux);
                            }
                            mCallBack.onCallback(courseArrayList);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}