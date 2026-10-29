package entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Album extends Entidad {
    private String nombre;
    private String genero;

    LocalDate anioLanzamiento;
    List<Cancion> canciones;

    public Album(int id, String nombre, String genero, LocalDate anioLanzamiento) {
        this.id = id;
        this.nombre = nombre;
        this.genero = genero;
        this.anioLanzamiento = anioLanzamiento;

        canciones = new ArrayList<>();
    }

    /* Metodos importantes */
    int getDuracionTotal() {
        int ans = 0;
        for (var c : canciones)
            ans += c.getSegundos();
        return ans;
    }

    /* Getters y Setters */
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public LocalDate getAnioLanzamiento() {
        return anioLanzamiento;
    }

    public void setAnioLanzamiento(LocalDate anioLanzamiento) {
        this.anioLanzamiento = anioLanzamiento;
    }

    public List<Cancion> getCanciones() {
        return canciones;
    }
}
