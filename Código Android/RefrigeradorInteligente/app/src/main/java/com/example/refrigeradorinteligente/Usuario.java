package com.example.refrigeradorinteligente;

public class Usuario {

    /* En esta clase se definen los atributos que tiene un usuario los cuales serán ingresados en pantalla para
    ser subido a la base de datos Refrigerador Inteligente de Firebase la cual fue registrada utilizando la
    opción tools-Firebase.
     */

    private String nombre;
    private String apellidos;
    private String usuario;
    private String password;

    public Usuario() {
    }

    public Usuario(String nombre, String apellidos, String usuario, String password) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.usuario = usuario;
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
