package entidades;

import java.time.LocalDate;
import java.util.List;

public class Album {
    private String nombre;
    private String genero;

    private Album album;
    LocalDate anioLanzamiento;
    List<Cancion> canciones;

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

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
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
