import java.util.Scanner;

/**
 *
 * Programa que crea una serie de hilos que se ejecutan de forma secuencial.
 * Cada hilo imprime su nombre y un número de iteración, luego duerme por un
 * tiempo aleatorio entre 1 y 6 segundos antes de continuar con la siguiente
 * iteración. El número de hilos a crear es especificado por el usuario al
 * inicio del programa.
 */
public class Hilo extends Thread {
    private static int numeroHilos = 5;
    private int idHilo;
    private Hilo siguienteHilo;

    /**
     * Constructor que inicializa el hilo con un ID específico y le asigna un
     * nombre basado en ese ID.
     *
     * @param idHilo El ID del hilo, utilizado para nombrarlo y para crear el
     *               siguiente hilo en la secuencia.
     */
    public Hilo(int idHilo) {
        super("[Hilo-" + idHilo + "]");
        this.idHilo = idHilo;
    }

    /**
     * Método que define el comportamiento del hilo. Cada hilo crea el siguiente
     * hilo en la secuencia (si no ha alcanzado el número máximo de hilos),
     * luego itera cinco veces, imprimiendo su nombre y número de iteración,
     * seguido de una pausa aleatoria entre 1 y 6 segundos. Finalmente, espera a
     * que el siguiente hilo termine antes de finalizar.
     */
    @Override
    public void run() {
        // Esto crea el siguiente hilo y lo inicializa
        if (idHilo < numeroHilos) {
            siguienteHilo = new Hilo(idHilo + 1);
            siguienteHilo.start();
        }

        // Aqui itera el hilo que esta corriendo
        for (int i = 0; i < 5; i++) {
            System.out.println("Soy el hilo: " + getName() + " y voy por la iteración: " + (i + 1));
            try {
                int segundosAleatorio = (int) (Math.random() * 6) + 1; // Genera un número aleatorio entre 1 y 6
                Thread.sleep(segundosAleatorio * 1000); // Duerme el hilo por ese número de segundos
            } catch (InterruptedException e) {
                System.out.println("Ocurrió un error: " + e.getMessage());
            }
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

    /**
     * Método principal que inicia el programa. Solicita al usuario el número de
     * hilos a crear y maneja la creación y ejecución de los hilos.
     *
     * @param args Argumentos de línea de comandos (no se usan).
     */
    public static void main(String[] args) {
        System.out.println("Programa inicializado");
        do {
            long Inicio = System.currentTimeMillis();
            Scanner sc  = new Scanner(System.in);
            System.out.println("Escribe el numero de hilos que quieres que iteren o escribe 'exit' para salir:");
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Terminando el programa...");
                break;
            }
            if (input.isEmpty() || !input.matches("\\d+") || Integer.parseInt(input) < 1) {
                System.out.println("Valor invalido, procedo a utilizar valores por defecto");
                input = "5";
            }
            numeroHilos = Integer.parseInt(input);

            Hilo hilo1 = new Hilo(1);
            hilo1.start();
            try {
                hilo1.join(); // Esperar a que todos los hilos acaben
            } catch (InterruptedException e) {
                System.out.println("Error al esperar hilo 1: " + e.getMessage());
            }
            long fin = System.currentTimeMillis();
            System.out.println("Tiempo total de la caída: " + (fin - Inicio) + " ms");
        } while (true);

        System.out.println("Programa terminado");
    }
}
