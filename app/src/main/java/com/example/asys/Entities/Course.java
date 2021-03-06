package com.example.asys.Entities;

public class Course {
    private String aula;
    private String horario;
    private String nombrecurso;
    private String nombredocente;
    private String token;

    private String dia;
    private String horaingreso;
    private String horasalida;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getNombrecurso() {
        return nombrecurso;
    }

    public void setNombrecurso(String nombrecurso) {
        this.nombrecurso = nombrecurso;
    }

    public String getNombredocente() {
        return nombredocente;
    }

    public void setNombredocente(String nombredocente) {
        this.nombredocente = nombredocente;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHoraingreso() {
        return horaingreso;
    }

    public void setHoraingreso(String horaingreso) {
        this.horaingreso = horaingreso;
    }

    public String getHorasalida() {
        return horasalida;
    }

    public void setHorasalida(String horasalida) {
        this.horasalida = horasalida;
    }

    public Course(){}

    public Course(String aula, String horario, String nombrecurso, String nombredocente, String dia, String horaingreso, String horasalida) {
        this.aula = aula;
        this.horario = horario;
        this.nombrecurso = nombrecurso;
        this.nombredocente = nombredocente;
        this.dia = dia;
        this.horaingreso = horaingreso;
        this.horasalida = horasalida;
    }
}
