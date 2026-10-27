package entidades;

import java.time.LocalDate;

public class Cancion extends Entidad {
    private String nombre;
    private Artista artista;
    private LocalDate anioLanzamiento;
    private String genero;
    private Album album;
    private int segundos;

    public Cancion(String nombre, Artista artista, LocalDate anioLanzamiento, String genero, Album album, int segundos) {
        this.nombre = nombre;
        this.artista = artista;
        this.anioLanzamiento = anioLanzamiento;
        this.genero = genero;
        this.album = album;
        this.segundos = segundos;
    }

    @Override
    public String toString() {
        return "Cancion{" +
                "nombre='" + nombre + '\'' +
                ", artista=" + artista +
                ", anioLanzamiento=" + anioLanzamiento +
                ", genero='" + genero + '\'' +
                ", album=" + album +
                ", segundos=" + segundos +
                '}';
    }

    public Cancion(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    public LocalDate getAnioLanzamiento() {
        return anioLanzamiento;
    }

    public void setAnioLanzamiento(LocalDate anioLanzamiento) {
        this.anioLanzamiento = anioLanzamiento;
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

    public int getSegundos() {
        return segundos;
    }

    public void setSegundos(int segundos) {
        this.segundos = segundos;
    }
}
