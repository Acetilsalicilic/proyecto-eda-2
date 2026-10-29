package entidades;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Artista extends Entidad {
    private String nombre;
    private LocalDate fechaNacimiento;
    private String tipoArtista;

    public Artista(int id, String nombre, LocalDate fechaNacimiento, String tipoArtista) {
        this.id = id;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.tipoArtista = tipoArtista;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTipoArtista() {
        return tipoArtista;
    }

    public void setTipoArtista(String tipoArtista) {
        this.tipoArtista = tipoArtista;
    }
}
