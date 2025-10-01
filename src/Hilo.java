// src/Hilo.java
public class Hilo extends Thread {
    private final int numeroHilos = 5;
    private int idHilo;
    private Hilo siguienteHilo;

    public Hilo(int idHilo) {
        super("[Hilo-" + idHilo + "]");
        this.idHilo = idHilo;
    }

    @Override
    public void run() {
        for (int i = 0; i < numeroHilos; i++) {
            System.out.println("Soy el hilo: " + getName() + " y voy por la iteración: " + (i + 1));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Ocurrió un error: " + e.getMessage());
            }
        }
        // Si no es el último hilo, crea el siguiente y espera que termine
        if (idHilo < numeroHilos) {
            siguienteHilo = new Hilo(idHilo + 1);
            siguienteHilo.start();
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
        /*try {
            hilo1.join();
        } catch (InterruptedException e) {
            System.out.println("Error al esperar hilo 1: " + e.getMessage());
        }*/
        System.out.println("Programa terminado");
    }
}
