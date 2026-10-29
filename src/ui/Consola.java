package ui;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Consola {
    private static final Scanner scanner = new Scanner(System.in);

    public static String leerTexto(String mensaje) {
        System.out.print(mensaje + ": ");
        return scanner.nextLine().trim();
    }

    public static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje + ": ");
            String texto = scanner.nextLine().trim();
            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                mostrarError("Valor numérico inválido");
            }
        }
    }

    public static int leerOpcion(String mensaje, int min, int max) {
        while (true) {
            System.out.print(mensaje + ": ");
            String texto = scanner.nextLine().trim();
            try {
                int opcion = Integer.parseInt(texto);
                if (opcion < min || opcion > max) {
                    mostrarError("Opción fuera de rango");
                    continue;
                }
                return opcion;
            } catch (NumberFormatException e) {
                mostrarError("Opción inválida");
            }
        }
    }

    public static LocalDate leerFecha(String mensaje) {
        while (true) {
            System.out.print(mensaje + " (AAAA-MM-DD): ");
            String texto = scanner.nextLine().trim();
            try {
                return LocalDate.parse(texto);
            } catch (Exception e) {
                mostrarError("Fecha inválida");
            }
        }
    }

    public static boolean confirmar(String mensaje) {
        System.out.print(mensaje + " (s/n): ");
        String respuesta = scanner.nextLine().trim().toLowerCase();
        return respuesta.equals("s") || respuesta.equals("si");
    }

    public static void pausar() {
        System.out.println("Presione Enter para continuar...");
        scanner.nextLine();
    }

    public static void mostrarError(String mensaje) {
        System.out.println("ERROR: " + mensaje);
    }

    public static void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}
