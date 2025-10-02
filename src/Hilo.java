// src/Hilo.java
public class Hilo extends Thread {
    private final int numeroIteraciones = 5;
    private int idHilo;
    private Hilo siguienteHilo;

    public Hilo(int idHilo) {
        super("[Hilo-" + idHilo + "]");
        this.idHilo = idHilo;
    }

    @Override
    public void run() {

        // Iteraciones del hilo actual
        for (int i = 0; i < numeroIteraciones; i++) {
            System.out.println("Soy el hilo: " + getName() + " y voy por la iteración: " + (i + 1));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Ocurrió un error: " + e.getMessage());
            }
        }

        // Crear el siguiente hilo si corresponde
        if (idHilo < 5) {
            siguienteHilo = new Hilo(idHilo + 1);
            siguienteHilo.start();
        }

        // Esperar a que termine el hijo (si existe)
        if (siguienteHilo != null) {
            try {
                siguienteHilo.join();
            } catch (InterruptedException e) {
                System.out.println("Error al esperar al siguiente hilo: " + e.getMessage());
            }
        }

        System.out.println("Acabó el hilo: " + getName());
    }

    public static void main(String[] args) {
        System.out.println("Programa inicializado");
        Hilo hilo1 = new Hilo(1);
        hilo1.start();
        try {
            hilo1.join(); // Esperar a que todos los hilos acaben
        } catch (InterruptedException e) {
            System.out.println("Error al esperar hilo 1: " + e.getMessage());
        }
        System.out.println("Programa terminado");
    }
}
