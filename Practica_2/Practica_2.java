
/**
    * Esta clase se encarga de mostrar el menú y mandar a llamar los métodos correspondientes.
    * @author: Gutiérrez Olvera Salvador
*/
import Procesos.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Practica_2 {
    boolean next = false;

    public static void main(String[] args) {

        int opcionUser;
        boolean follow = false, exit = false;
        Scanner opcionObj = new Scanner(System.in);
        Scanner nombreObj = new Scanner(System.in);
        /*
         * Se mete el menú en una excepción dado que puede que el usuario ingrese un
         * valor o caracter no valido.
         */
        try {
            do {
                System.out.println("-------------- Simulacion de procesos --------------");
                System.out.println("1. Crear proceso\n2. Estado actual del sistema\n3. Ver estado de la memoria");
                System.out.println("4. Imprimir cola de procesos\n5. Ejecutar proceso actual\n6. Ver proceso actual");
                System.out.println("7. Pasar al proceso siguiente\n8. Matar proceso actual\n9. Matar todo y terminar");
                System.out.print("10. Salir\nOpcion: ");

                opcionUser = opcionObj.nextInt();
                switch (opcionUser) {
                    case 1:
                        /*
                         * Se comprueba que el valor de follow sea false, ya que de esta forma se sabe
                         * que aún hay memoria disponible para seguir metiendo procesos a la cola. De lo
                         * contrario si el valor regresado es true se evita que se ingresen nuevos
                         * procesos.
                         */
                        if (!follow) {
                            System.out.println("-------------- Crear proceso --------------");
                            System.out.print("Ingrese el nombre del proceso: ");
                            String nombreProceso = nombreObj.nextLine();
                            follow = Process_Tools.checkMemory(nombreProceso + ".exe");
                        } else
                            System.out.println("No puedes crear más procesos hasta que liberes memoria");
                        break;
                    case 2:
                        System.out.println("-------------- Estado actual del sistema --------------");
                        Process_Tools.actualStateSystem();
                        break;
                    case 3:
                        System.out.println("-------------- Ver estado de memoria --------------");
                        Process_Tools.getStatusMemory();
                        break;
                    case 4:
                        System.out.println("-------------- Imprimir cola de procesos --------------");
                        Process_Tools.getQueueProcess();
                        break;
                    case 5:
                        System.out.println("-------------- Ejecutar proceso actual --------------");
                        follow = Process_Tools.executeActualProcess(follow);
                        break;
                    case 6:
                        System.out.println("-------------- Ver proceso actual --------------");
                        Process_Tools.actualProcess();
                        break;
                    case 7:
                        System.out.println("-------------- Pasar al proceso siguiente --------------");
                        Process_Tools.nextProcess();
                        break;
                    case 8:
                        System.out.println("-------------- Matar proceso actual --------------");
                        follow = Process_Tools.killProcess();
                        break;
                    case 9:
                        System.out.println("-------------- Matar todo y terminar --------------");
                        Process_Tools.killAll();
                        exit = true;
                        break;
                    case 10:
                        System.out.println("-------------- Saliendo --------------");
                        exit = true;
                        break;
                    default:
                        System.out.println("********** Opcion no valida **********");
                }

            } while (exit != true);
        } catch (InputMismatchException e) {
            System.out.println("********** Opcion no valida **********");
            main(args);
        }
        opcionObj.close();
        nombreObj.close();
    }
}
