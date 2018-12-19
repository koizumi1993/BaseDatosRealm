package com.fundamentos.abisu.basedatosrealm.Modelos;

import com.fundamentos.abisu.basedatosrealm.Aplicacion.MiAplicacion;

import io.realm.RealmObject;

public class WaifuModelo extends RealmObject {
    private int id;
    private String nombre;
    private String imagen;
    private String descripcion;
    //Para relacionar con otros modelos
    //private RealmList<TuModelo> listaObjeto;

    //Real necesita un contructor vacio

    public WaifuModelo() {
    }

    public WaifuModelo(String nombre, String imagen, String descripcion) {
        this.id = MiAplicacion.waifuId.incrementAndGet();
        this.nombre = nombre;
        this.imagen = imagen;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
