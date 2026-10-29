package ui;

import entidades.*;
import servicios.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class CLI {
    private final GestorMusical gestor;

    public CLI(GestorMusical gestor) {
        this.gestor = gestor;
    }

    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            int opcion = menuPrincipal();
            switch (opcion) {
                case 1 -> menuCanciones();
                case 2 -> menuArtistas();
                case 3 -> menuPlaylists();
                case 4 -> salir = true;
                default -> Consola.mostrarError("Opción inválida");
            }
        }
        System.out.println("Hasta luego!");
    }

    private int menuPrincipal() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1.- Canciones");
        System.out.println("2.- Artistas");
        System.out.println("3.- Playlists");
        System.out.println("4.- Salir");
        return Consola.leerOpcion("Seleccione una opción", 1, 4);
    }

    private void menuCanciones() {
        boolean regresar = false;
        while (!regresar) {
            System.out.println("\n=== CANCIONES ===");
            System.out.println("1.- Agregar canción");
            System.out.println("2.- Eliminar canción");
            System.out.println("3.- Mostrar canciones");
            System.out.println("4.- Ordenar canciones");
            System.out.println("5.- Búsqueda lineal (por nombre)");
            System.out.println("6.- Búsqueda binaria (por nombre)");
            System.out.println("7.- Buscar canción por ID (tabla hash)");
            System.out.println("8.- Mostrar tabla hash");
            System.out.println("9.- Regresar");
            int opcion = Consola.leerOpcion("Seleccione una opción", 1, 9);
            switch (opcion) {
                case 1 -> agregarCancion();
                case 2 -> eliminarCancion();
                case 3 -> mostrarCanciones();
                case 4 -> ordenarCanciones();
                case 5 -> busquedaLinealPorNombre();
                case 6 -> busquedaBinariaPorNombre();
                case 7 -> buscarCancionPorId();
                case 8 -> mostrarTablaHash();
                case 9 -> regresar = true;
            }
        }
    }

    private void agregarCancion() {
        if (gestor.listarArtistas().isEmpty()) {
            Consola.mostrarError("Primero registre un artista");
            Consola.pausar();
            return;
        }

        String nombre = Consola.leerTexto("Nombre de la canción");
        Artista artista = seleccionarArtista();
        LocalDate anio = Consola.leerFecha("Fecha de lanzamiento");
        String genero = Consola.leerTexto("Género");
        String nombreAlbum = Consola.leerTexto("Nombre del álbum (dejar vacío para ninguno)");

        Album album = null;

        if (!nombreAlbum.isEmpty()) {
            album = gestor.seleccionarOCrearAlbum(nombreAlbum);
        }

        int segundos = Consola.leerEntero("Duración en segundos");
        Cancion cancion = gestor.agregarCancion(nombre, artista, anio, genero, album, segundos);
        System.out.println("Canción agregada: " + cancion.getNombre());

        Consola.pausar();
    }

    private Artista seleccionarArtista() {
        List<Artista> artistas = gestor.listarArtistas();
        for (int i = 0; i < artistas.size(); i++) {
            System.out.println((i + 1) + ".- " + artistas.get(i).getNombre());
        }
        int idx = Consola.leerOpcion("Seleccione artista", 1, artistas.size()) - 1;
        return artistas.get(idx);
    }

    private void eliminarCancion() {
        List<Cancion> canciones = gestor.listarCanciones();

        if (canciones.isEmpty()) {
            Consola.mostrarError("No hay canciones");
            Consola.pausar();
            return;
        }

        mostrarCanciones();
        int idx = Consola.leerOpcion("Seleccione canción a eliminar", 1, canciones.size()) - 1;
        if (Consola.confirmar("¿Eliminar la canción?")) {
            boolean ok = gestor.eliminarCancion(canciones.get(idx));
            System.out.println(ok ? "Canción eliminada" : "No se pudo eliminar");
        }

        Consola.pausar();
    }

    private void mostrarCanciones() {
        List<Cancion> canciones = gestor.listarCanciones();
        if (canciones.isEmpty()) {
            System.out.println("El catálogo está vacío");
            return;
        }
        System.out.println("\n=== LISTA DE CANCIONES ===");
        for (int i = 0; i < canciones.size(); i++) {
            Cancion c = canciones.get(i);
            String artistaNombre = c.getArtista() == null ? "Desconocido" : c.getArtista().getNombre();
            String albumNombre = c.getAlbum() == null ? "Sin álbum" : c.getAlbum().getNombre();
            System.out.println((i + 1) + ".- " + c.getNombre() + " | " + artistaNombre + " | " + 
                    (c.getAnioLanzamiento() == null ? "N/A" : c.getAnioLanzamiento()) + " | " + 
                    albumNombre + " | " + c.getSegundos() + "s");
        }
    }

    private void ordenarCanciones() {
        System.out.println("\nOrdenar por:");
        System.out.println("1. Nombre");
        System.out.println("2. Artista");
        System.out.println("3. Año de lanzamiento");
        System.out.println("4. Duración");
        int crit = Consola.leerOpcion("Seleccione criterio", 1, 4);
        System.out.println("1. Heap Sort");
        System.out.println("2. Merge Sort");
        int alg = Consola.leerOpcion("Seleccione algoritmo", 1, 2);

        CriterioOrden criterio = switch (crit) {
            case 1 -> CriterioOrden.NOMBRE;
            case 2 -> CriterioOrden.ARTISTA;
            case 3 -> CriterioOrden.ANIO;
            case 4 -> CriterioOrden.DURACION;
            default -> CriterioOrden.NOMBRE;
        };

        AlgoritmoOrden algoritmo = alg == 1 ? AlgoritmoOrden.HEAP : AlgoritmoOrden.MERGE;
        gestor.ordenarCanciones(criterio, algoritmo);
        System.out.println("Catálogo ordenado");
        mostrarCanciones();

        Consola.pausar();
    }

    private void busquedaLinealPorNombre() {
        String nombre = Consola.leerTexto("Nombre a buscar");
        List<Cancion> resultados = gestor.busquedaLinealPorNombre(nombre);
        System.out.println("Coincidencias encontradas: " + resultados.size());

        for (Cancion c : resultados) {
            String artistaNombre = c.getArtista() == null ? "Desconocido" : c.getArtista().getNombre();
            System.out.println("- " + c.getNombre() + " | " + artistaNombre);
        }

        Consola.pausar();
    }

    private void busquedaBinariaPorNombre() {
        String nombre = Consola.leerTexto("Nombre a buscar");
        Optional<Cancion> resultado = gestor.busquedaBinariaPorNombre(nombre);

        if (resultado.isEmpty()) {
            System.out.println("No se encontró la canción");
        } else {
            Cancion c = resultado.get();
            String artistaNombre = c.getArtista() == null ? "Desconocido" : c.getArtista().getNombre();
            System.out.println("Encontrada: " + c.getNombre() + " | " + artistaNombre);
        }

        Consola.pausar();
    }

    private void buscarCancionPorId() {
        int id = Consola.leerEntero("ID de la canción");
        Optional<Cancion> resultado = gestor.buscarCancionPorId(id);

        if (resultado.isEmpty()) {
            System.out.println("No se encontró la canción con ese ID");
        } else {
            Cancion c = resultado.get();
            String artistaNombre = c.getArtista() == null ? "Desconocido" : c.getArtista().getNombre();
            System.out.println("Encontrada: " + c.getNombre() + " | " + artistaNombre + " | ID: " + c.getId());
        }

        Consola.pausar();
    }

    private void mostrarTablaHash() {
        try {
            System.out.println(gestor.describirTablaHash());
        } catch (Exception e) {
            System.out.println("[TODO] Excepción en mostrarTablaHash: " + e.getMessage());
            throw new UnsupportedOperationException("mostrarTablaHash con funcionalidad incompleta");
        }
        Consola.pausar();
    }

    private void menuArtistas() {
        boolean regresar = false;
        while (!regresar) {
            System.out.println("\n=== ARTISTAS ===");
            System.out.println("1.- Agregar artista");
            System.out.println("2.- Eliminar artista");
            System.out.println("3.- Mostrar artistas");
            System.out.println("4.- Ordenar artistas");
            System.out.println("5.- Buscar artista y mostrar sus canciones");
            System.out.println("6.- Regresar");
            int opcion = Consola.leerOpcion("Seleccione una opción", 1, 6);
            switch (opcion) {
                case 1 -> agregarArtista();
                case 2 -> eliminarArtista();
                case 3 -> mostrarArtistas();
                case 4 -> ordenarArtistas();
                case 5 -> buscarArtistaYMostrarCanciones();
                case 6 -> regresar = true;
            }
        }
    }

    private void agregarArtista() {
        String nombre = Consola.leerTexto("Nombre del artista");
        LocalDate fecha = Consola.leerFecha("Fecha de nacimiento");
        String tipo = Consola.leerTexto("Tipo de artista");
        Artista artista = gestor.agregarArtista(nombre, fecha, tipo);
        System.out.println("Artista agregado: " + artista.getNombre());

        Consola.pausar();
    }

    private void eliminarArtista() {
        List<Artista> artistas = gestor.listarArtistas();

        if (artistas.isEmpty()) {
            Consola.mostrarError("No hay artistas");
            Consola.pausar();
            return;
        }

        mostrarArtistas();
        int idx = Consola.leerOpcion("Seleccione artista a eliminar", 1, artistas.size()) - 1;
        List<Cancion> canciones = gestor.cancionesDe(artistas.get(idx));

        if (!canciones.isEmpty() && !Consola.confirmar("El artista tiene " + canciones.size() + " canción(es). ¿Eliminar?")) {
            Consola.pausar();
            return;
        }

        if (Consola.confirmar("¿Eliminar el artista?")) {
            boolean ok = gestor.eliminarArtista(artistas.get(idx));
            System.out.println(ok ? "Artista eliminado" : "No se pudo eliminar");
        }

        Consola.pausar();
    }

    private void mostrarArtistas() {
        List<Artista> artistas = gestor.listarArtistas();
        if (artistas.isEmpty()) {
            System.out.println("No hay artistas registrados");
            return;
        }
        System.out.println("\n=== LISTA DE ARTISTAS ===");
        for (int i = 0; i < artistas.size(); i++) {
            Artista a = artistas.get(i);
            System.out.println((i + 1) + ".- " + a.getNombre() + " | " + a.getFechaNacimiento() + " | " + a.getTipoArtista());
        }
    }

    private void ordenarArtistas() {
        System.out.println("\nOrdenar por:");
        System.out.println("1. Nombre");
        System.out.println("2. Edad");
        System.out.println("3. Tipo");
        int crit = Consola.leerOpcion("Seleccione criterio", 1, 3);

        CriterioArtista criterio = switch (crit) {
            case 1 -> CriterioArtista.NOMBRE;
            case 2 -> CriterioArtista.EDAD;
            case 3 -> CriterioArtista.TIPO;
            default -> CriterioArtista.NOMBRE;
        };

        gestor.ordenarArtistas(criterio);
        System.out.println("Artistas ordenados");
        mostrarArtistas();

        Consola.pausar();
    }

    private void buscarArtistaYMostrarCanciones() {
        String nombre = Consola.leerTexto("Nombre del artista");
        List<Artista> artistas = gestor.listarArtistas();
        boolean encontrado = false;

        for (Artista a : artistas) {
            if (a.getNombre() != null && a.getNombre().equalsIgnoreCase(nombre)) {
                encontrado = true;
                System.out.println("\n=== " + a.getNombre() + " ===");
                List<Cancion> canciones = gestor.cancionesDe(a);

                if (canciones.isEmpty()) {
                    System.out.println("Sin canciones registradas");
                } else {
                    for (Cancion c : canciones) {
                        String albumNombre = c.getAlbum() == null ? "Sin álbum" : c.getAlbum().getNombre();
                        System.out.println("- " + c.getNombre() + " | " + albumNombre + " | " + c.getSegundos() + "s");
                    }
                }
            }
        }
        if (!encontrado) System.out.println("No se encontró el artista");

        Consola.pausar();
    }

    private void menuPlaylists() {
        boolean regresar = false;
        while (!regresar) {
            System.out.println("\n=== PLAYLISTS ===");
            System.out.println("1.- Crear playlist");
            System.out.println("2.- Eliminar playlist");
            System.out.println("3.- Mostrar playlists");
            System.out.println("4.- Agregar canción a playlist");
            System.out.println("5.- Eliminar canción de playlist");
            System.out.println("6.- Mostrar canciones de playlist");
            System.out.println("7.- Ordenar canciones de playlist");
            System.out.println("8.- Buscar canción en playlist");
            System.out.println("9.- Reproducir playlist");
            System.out.println("10.- Regresar");
            int opcion = Consola.leerOpcion("Seleccione una opción", 1, 10);
            switch (opcion) {
                case 1 -> crearPlaylist();
                case 2 -> eliminarPlaylist();
                case 3 -> mostrarPlaylists();
                case 4 -> agregarCancionAPlaylist();
                case 5 -> eliminarCancionDePlaylist();
                case 6 -> mostrarCancionesDePlaylist();
                case 7 -> ordenarCancionesDePlaylist();
                case 8 -> buscarCancionEnPlaylist();
                case 9 -> reproducirPlaylist();
                case 10 -> regresar = true;
            }
        }
    }

    private void crearPlaylist() {
        String nombre = Consola.leerTexto("Nombre de la playlist");
        Playlist playlist = gestor.crearPlaylist(nombre);
        System.out.println("Playlist creada: " + playlist.getNombre());

        Consola.pausar();
    }

    private void eliminarPlaylist() {
        List<Playlist> playlists = gestor.listarPlaylists();

        if (playlists.isEmpty()) {
            Consola.mostrarError("No hay playlists");
            Consola.pausar();
            return;
        }

        mostrarPlaylists();
        int idx = Consola.leerOpcion("Seleccione playlist a eliminar", 1, playlists.size()) - 1;

        if (Consola.confirmar("¿Eliminar la playlist?")) {
            boolean ok = gestor.eliminarPlaylist(playlists.get(idx).getNombre());
            System.out.println(ok ? "Playlist eliminada" : "No se pudo eliminar");

        }

        Consola.pausar();
    }

    private void mostrarPlaylists() {
        List<Playlist> playlists = gestor.listarPlaylists();
        if (playlists.isEmpty()) {
            System.out.println("No hay playlists");
            return;
        }
        System.out.println("\n=== LISTA DE PLAYLISTS ===");
        for (int i = 0; i < playlists.size(); i++) {
            Playlist p = playlists.get(i);
            System.out.println((i + 1) + ".- " + p.getNombre() + " | " + p.getCanciones().size() + " canciones | " + p.getDuracionTotal() + "s");
        }
    }

    private Playlist seleccionarPlaylist() {
        List<Playlist> playlists = gestor.listarPlaylists();
        mostrarPlaylists();
        int idx = Consola.leerOpcion("Seleccione playlist", 1, playlists.size()) - 1;
        return playlists.get(idx);
    }

    private Cancion seleccionarCancion() {
        List<Cancion> canciones = gestor.listarCanciones();
        mostrarCanciones();
        int idx = Consola.leerOpcion("Seleccione canción", 1, canciones.size()) - 1;
        return canciones.get(idx);
    }

    private void agregarCancionAPlaylist() {
        if (gestor.listarPlaylists().isEmpty() || gestor.listarCanciones().isEmpty()) {
            Consola.mostrarError("Necesita al menos una playlist y una canción");
            Consola.pausar();
            return;
        }
        Playlist playlist = seleccionarPlaylist();
        Cancion cancion = seleccionarCancion();
        boolean ok = gestor.agregarCancionAPlaylist(playlist.getNombre(), cancion);
        System.out.println(ok ? "Canción agregada a la playlist" : "La canción ya está en la playlist");

        Consola.pausar();
    }

    private void eliminarCancionDePlaylist() {
        Playlist playlist = seleccionarPlaylist();
        List<Cancion> canciones = gestor.cancionesDePlaylist(playlist.getNombre());

        if (canciones.isEmpty()) {
            Consola.mostrarError("La playlist no tiene canciones");
            Consola.pausar();
            return;
        }

        for (int i = 0; i < canciones.size(); i++) {
            System.out.println((i + 1) + ".- " + canciones.get(i).getNombre());
        }

        int idx = Consola.leerOpcion("Seleccione canción a eliminar", 1, canciones.size()) - 1;
        boolean ok = gestor.eliminarCancionDePlaylist(playlist.getNombre(), canciones.get(idx));
        System.out.println(ok ? "Canción eliminada de la playlist" : "No se pudo eliminar");

        Consola.pausar();
    }

    private void mostrarCancionesDePlaylist() {
        Playlist playlist = seleccionarPlaylist();
        List<Cancion> canciones = gestor.cancionesDePlaylist(playlist.getNombre());

        if (canciones.isEmpty()) {
            System.out.println("La playlist no tiene canciones");
        } else {
            System.out.println("\n=== " + playlist.getNombre() + " ===");
            System.out.println("Canciones: " + canciones.size() + " | Duración: " + playlist.getDuracionTotal() + "s");
            for (int i = 0; i < canciones.size(); i++) {
                Cancion c = canciones.get(i);
                String artistaNombre = c.getArtista() == null ? "Desconocido" : c.getArtista().getNombre();
                System.out.println((i + 1) + ".- " + c.getNombre() + " | " + artistaNombre);
            }
        }

        Consola.pausar();
    }

    private void ordenarCancionesDePlaylist() {
        Playlist playlist = seleccionarPlaylist();

        System.out.println("\nOrdenar por:");
        System.out.println("1. Nombre");
        System.out.println("2. Artista");
        System.out.println("3. Año");
        System.out.println("4. Duración");
        int crit = Consola.leerOpcion("Seleccione criterio", 1, 4);
        System.out.println("1. Heap Sort");
        System.out.println("2. Merge Sort");
        int alg = Consola.leerOpcion("Seleccione algoritmo", 1, 2);

        CriterioOrden criterio = switch (crit) {
            case 1 -> CriterioOrden.NOMBRE;
            case 2 -> CriterioOrden.ARTISTA;
            case 3 -> CriterioOrden.ANIO;
            case 4 -> CriterioOrden.DURACION;
            default -> CriterioOrden.NOMBRE;
        };

        AlgoritmoOrden algoritmo = alg == 1 ? AlgoritmoOrden.HEAP : AlgoritmoOrden.MERGE;
        gestor.ordenarCancionesDePlaylist(playlist.getNombre(), criterio, algoritmo);

        System.out.println("Playlist ordenada");
        mostrarCancionesDePlaylist();
    }

    private void buscarCancionEnPlaylist() {
        Playlist playlist = seleccionarPlaylist();
        String nombre = Consola.leerTexto("Nombre de la canción");
        List<Cancion> resultados = gestor.buscarCancionEnPlaylist(playlist.getNombre(), nombre);
        System.out.println("Coincidencias: " + resultados.size());
        for (Cancion c : resultados) {
            String artistaNombre = c.getArtista() == null ? "Desconocido" : c.getArtista().getNombre();
            System.out.println("- " + c.getNombre() + " | " + artistaNombre);
        }

        Consola.pausar();
    }

    private void reproducirPlaylist() {
        Playlist playlist = seleccionarPlaylist();
        boolean ok = gestor.reproducirPlaylist(playlist.getNombre());
        if (!ok) Consola.mostrarError("La playlist no tiene canciones");

        Consola.pausar();
    }
}
