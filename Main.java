
import java.util.Scanner;

// ======================================
// CLASE NODO
// ======================================

class Nodo {

    int dato;
    Nodo izquierdo;
    Nodo derecho;

    public Nodo(int dato) {
        this.dato = dato;
        this.izquierdo = null;
        this.derecho = null;
    }
}

// ======================================
// CLASE PRINCIPAL
// ======================================

public class Main {

    static Scanner sc = new Scanner(System.in);
    static Nodo raiz = null;

    // ==================================
    // CREAR ARBOL BINARIO
    // ==================================

    static Nodo crearArbol(String posicion) {

        int valor;

        while (true) {
            System.out.print("Ingrese el valor de " + posicion +
                    " (-1 para dejar vacio): ");

            if (sc.hasNextInt()) {
                valor = sc.nextInt();
                break;
            } else {
                System.out.println("Error: ingrese un numero entero.");
                sc.next();
            }
        }

        // Si ingresa -1, no se crea el nodo
        if (valor == -1) {
            return null;
        }

        Nodo nuevo = new Nodo(valor);

        System.out.println("\nNodo izquierdo de " + valor);
        nuevo.izquierdo = crearArbol("izquierda");

        System.out.println("\nNodo derecho de " + valor);
        nuevo.derecho = crearArbol("derecha");

        return nuevo;
    }

    // ==================================
    // RECORRIDO PREORDEN
    // Raiz - Izquierda - Derecha
    // ==================================

    static void preorden(Nodo nodo) {

        if (nodo != null) {

            System.out.print(nodo.dato + " ");

            preorden(nodo.izquierdo);

            preorden(nodo.derecho);
        }
    }

    // ==================================
    // RECORRIDO INORDEN
    // Izquierda - Raiz - Derecha
    // ==================================

    static void inorden(Nodo nodo) {

        if (nodo != null) {

            inorden(nodo.izquierdo);

            System.out.print(nodo.dato + " ");

            inorden(nodo.derecho);
        }
    }

    // ==================================
    // RECORRIDO POSTORDEN
    // Izquierda - Derecha - Raiz
    // ==================================

    static void postorden(Nodo nodo) {

        if (nodo != null) {

            postorden(nodo.izquierdo);

            postorden(nodo.derecho);

            System.out.print(nodo.dato + " ");
        }
    }

    // ==================================
    // METODO PARA MOSTRAR TODOS LOS RECORRIDOS
    // ==================================

    static void mostrarRecorridos() {

        if (raiz == null) {
            System.out.println("\nPrimero debe crear un arbol.");
            return;
        }

        System.out.println("\n===== RECORRIDOS DEL ARBOL =====");

        System.out.print("Preorden: ");
        preorden(raiz);

        System.out.print("\nInorden: ");
        inorden(raiz);

        System.out.print("\nPostorden: ");
        postorden(raiz);

        System.out.println("\n================================");
    }

    // ==================================
    // MENU PRINCIPAL
    // ==================================

    public static void main(String[] args) {

        int opcion;

        do {

            System.out.println("\n================================");
            System.out.println("         ARBOL BINARIO");
            System.out.println("================================");
            System.out.println("1. Crear arbol binario");
            System.out.println("2. Recorrido Preorden");
            System.out.println("3. Recorrido Inorden");
            System.out.println("4. Recorrido Postorden");
            System.out.println("5. Mostrar todos los recorridos");
            System.out.println("6. Salir");
            System.out.println("================================");
            System.out.print("Seleccione una opcion: ");

            if (sc.hasNextInt()) {
                opcion = sc.nextInt();
            } else {
                System.out.println("Error: ingrese una opcion numerica.");
                sc.next();
                opcion = 0;
                continue;
            }

            switch (opcion) {

                case 1:

                    System.out.println("\n===== CREAR ARBOL =====");
                    System.out.println("Ingrese -1 cuando no exista un nodo.");

                    raiz = crearArbol("raiz");

                    System.out.println("\nArbol creado correctamente.");

                    break;

                case 2:

                    if (raiz == null) {
                        System.out.println("\nPrimero debe crear el arbol.");
                    } else {
                        System.out.print("\nRecorrido Preorden: ");
                        preorden(raiz);
                        System.out.println();
                    }

                    break;

                case 3:

                    if (raiz == null) {
                        System.out.println("\nPrimero debe crear el arbol.");
                    } else {
                        System.out.print("\nRecorrido Inorden: ");
                        inorden(raiz);
                        System.out.println();
                    }

                    break;

                case 4:

                    if (raiz == null) {
                        System.out.println("\nPrimero debe crear el arbol.");
                    } else {
                        System.out.print("\nRecorrido Postorden: ");
                        postorden(raiz);
                        System.out.println();
                    }

                    break;

                case 5:

                    mostrarRecorridos();

                    break;

                case 6:

                    System.out.println("\nGracias por utilizar el programa.");
                    System.out.println("Programa finalizado.");

                    break;

                default:

                    System.out.println("\nOpcion no valida. Intente nuevamente.");

                    break;
            }

        } while (opcion != 6);

        sc.close();
    }
}
