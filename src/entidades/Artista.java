package entidades;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Artista extends Entidad {
    private static int ultimoId = 1;
    private String nombre;
    private LocalDate fechaNacimiento;
    private String tipoArtista;

    public Artista(String nombre, LocalDate fechaNacimiento, String tipoArtista) {
        this.id = ultimoId++;
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
