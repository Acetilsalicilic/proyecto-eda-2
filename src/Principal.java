import servicios.GestorMusical;

public class Principal {
    public static void main(String... args) {
        servicios.GestorMusical gestor = GestorMusical.instance;
        ui.CLI cli = new ui.CLI(gestor);
        cli.iniciar();
    }
}
