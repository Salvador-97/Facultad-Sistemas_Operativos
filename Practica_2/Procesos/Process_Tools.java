/**
    * Esta clase se encarga de realizar todas las operaciones 
    * @author: Gutiérrez Olvera Salvador
*/
package Procesos;

import java.util.LinkedList;

public class Process_Tools {
    static int memoriaTotal = 1024;
    static int numeroProcesos = 0;
    static LinkedList<Memory_management> memoria = new LinkedList<Memory_management>();
    static LinkedList<Create_Process> procesosQueue = new LinkedList<Create_Process>();
    static LinkedList<Create_Process> procesosFinalizados = new LinkedList<Create_Process>();
    static LinkedList<Create_Process> procesosAsesinados = new LinkedList<Create_Process>();
    static LinkedList<String> killAll = new LinkedList<>();

    /**
     * Se verifica si hay espacio disponible en la memoria realizando una resta de
     * las localidades que ocupara el proceso con la memoria disponible. En caso de
     * que haya memoria disponible se manda llamar el método allocateMemory, de lo
     * contrario se mostrara un mensaje de que no hay suficiente memoria y se
     * bloqueara la llamada a ingresar nuevos procesos.
     * 
     * @param nombreProceso Nombre que el usuario le dio al proceso.
     * @return true si el proceso no se ha podido asignar por falta de memoria,
     *         false si se asigno correctamente;
     */
    public static boolean checkMemory(String nombreProceso) {
        boolean stop;
        procesosQueue.add(new Create_Process(nombreProceso));
        int espacioDisponible = memoriaTotal - procesosQueue.get(numeroProcesos).getLocalidad();
        if (espacioDisponible > 0) {
            memoriaTotal = memoriaTotal - procesosQueue.get(numeroProcesos).getLocalidad();
            allocateMemory();
            numeroProcesos++;
            stop = false;
        } else {
            stop = true;
            procesosQueue.remove(numeroProcesos);
            System.out.println("No hay memoria suficiente para el proceso: " + nombreProceso);
        }
        return stop;
    }

    /**
     * @return Total de memoria disponible.
     */
    public static int getMemory() {
        return memoriaTotal;
    }

    /**
     * Se muestra la lista de procesos que estan en cola.
     */
    public static void getQueueProcess() {
        System.out.println("####################################################");
        if (!procesosQueue.isEmpty()) {
            for (int j = 0; j < procesosQueue.size(); j++) {
                System.out.print("Proceso[" + j + "]: " + procesosQueue.get(j).getName());
                System.out.println("         \tID: " + procesosQueue.get(j).getID());
            }
        } else {
            System.out.println("La cola de procesos esta vacia");
        }
        System.out.println("####################################################");
    }

    /**
     * Se encarga de ir restando 5 a el número de instrucciones asigandas al
     * proceso. Se manda llamar al método processFinished para ver si el proceso no
     * ha finalizado.
     * 
     * @return true si el proceso sigue existiendo, false si ha finalizado.
     */
    public static boolean executeActualProcess(boolean follow) {
        System.out.println("####################################################");
        if (!procesosQueue.isEmpty()) {
            int newIntructions = procesosQueue.element().getNumeroInstrucciones();
            procesosQueue.element().numeroInstrucciones = newIntructions - 5;
            System.out.println("Nuevo numero de instrucciones: " + procesosQueue.element().getNumeroInstrucciones());
            follow = processFinished(follow);
            System.out.println("####################################################");
        } else {
            System.out.println("La lista de procesos esta vacia, ingrese un proceso para ejecutar");
        }
        return follow;
    }

    /**
     * Muestra el estado del sistema, mostrando los procesos que aun se estan
     * ejecutando, los procesos que finalizaron, los procesos interrumpidos y el
     * estado de la memoria.
     */
    public static void actualStateSystem() {
        System.out.println("####################################################");
        System.out.println("Procesos en espera: " + procesosQueue.size());
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Procesos finalizados: ");
        getProcessFinished();
        System.out.println("Procesos interrumpidos: ");
        getKillProcess();
        System.out.println("Estado de la memoria: " + getMemory());
        System.out.println("####################################################");
    }

    /**
     * Muestra todos los atributos del procesos en la cabeza de la cola.
     */
    public static void actualProcess() {
        System.out.println("####################################################");
        if (!procesosQueue.isEmpty()) {
            System.out.println("Nombre: " + procesosQueue.element().getName());
            System.out.println("ID: " + procesosQueue.element().getID());
            System.out.println("Instrucciones: " + procesosQueue.element().getNumeroInstrucciones());
            System.out.println("Localidades: " + procesosQueue.element().getLocalidad());
        } else {
            System.out.println("La lista de procesos esta vacia");
        }
        System.out.println("####################################################");
    }

    /**
     * Si el proceso ha finalizado se reasinga la cabeza de la cola al proceso
     * siguiente. Se aumenta la cantidad de la memoria total con las localidades
     * liberadas y se remueve el elemento de la cola. Se manda llamar al metodo
     * reallocateMemory para asignarle un espacio en la memoria.
     * 
     * @param follow
     * @return Si ya hay espacio disponible en la memoria.
     */
    public static boolean processFinished(boolean follow) {
        Create_Process finalizado;
        if (procesosQueue.element().getNumeroInstrucciones() <= 0) {
            System.out.println("Proceso " + procesosQueue.element().getName() + " finalizado");
            finalizado = procesosQueue.element();
            procesosFinalizados.addFirst(finalizado);
            memoriaTotal = memoriaTotal + procesosQueue.element().getLocalidad();
            reallocateMemory(procesosQueue.element().getName(), procesosQueue.element().getNumeroInstrucciones());
            procesosQueue.remove();
            numeroProcesos--;
            if (memoriaTotal >= 128)
                follow = false;
        } else {
            nextProcess();
        }
        return follow;
    }

    /**
     * Muestra todos los procesos finalizados correctamente.
     */
    public static void getProcessFinished() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        for (int i = 0; i < procesosFinalizados.size(); i++) {
            System.out.print("[" + i + "]" + procesosFinalizados.get(i).getName());
            System.out.println("      \tID: " + procesosFinalizados.get(i).getID());
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    /**
     * Si la cola de procesos no esta vacía se intercambia el proceso que estaba en
     * la cabeza con el siguiente en cola. Para esto se guardan los valores del
     * proceso a encolar y se elimina de la lista.
     */
    public static void nextProcess() {
        System.out.println("####################################################");
        if (!procesosQueue.isEmpty()) {
            Create_Process nuevoProceso;
            nuevoProceso = procesosQueue.element();
            procesosQueue.add(nuevoProceso);
            procesosQueue.remove();
            System.out.println("El proceso actual ahora es: " + procesosQueue.element().getName());
        } else {
            System.out.println("La lista de procesos esta vacia");
        }
        System.out.println("################################################");
    }

    /**
     * Si el proceso ha eliminado se reasinga la cabeza de la cola al proceso
     * siguiente. Se aumenta la cantidad de la memoria total con las localidades
     * liberadas y se remueve el elemento de la cola. Se manda llamar al metodo
     * reallocateMemory para asignarle un espacio en la memoria.
     * 
     * @return
     */
    public static boolean killProcess() {
        boolean follow = false;
        System.out.println("####################################################");
        if (!procesosQueue.isEmpty()) {
            System.out.println("Proceso " + procesosQueue.element().getName() + " eliminado");
            procesosAsesinados.addFirst(procesosQueue.element());
            memoriaTotal = memoriaTotal + procesosQueue.element().getLocalidad();
            reallocateMemory(procesosQueue.element().getName(), 0);
            procesosQueue.remove();
            numeroProcesos--;
        } else {
            System.out.println("La lista de procesos esta vacia");
        }
        System.out.println("####################################################");
        return follow;
    }

    /**
     * Se muestran los procesos terminados por el usuario.
     */
    public static void getKillProcess() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        for (int i = 0; i < procesosAsesinados.size(); i++) {
            System.out.print("[" + i + "]:" + procesosAsesinados.get(i).getName());
            System.out.println("      \tID: " + procesosAsesinados.get(i).getID());
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    /**
     * Se recorre la cola elimando los procesos y guardando su nombre en una lista.
     */
    public static void killAll() {
        if (!procesosQueue.isEmpty()) {
            System.out.println("############## Todos los procesos eliminados ##############");
            int size = procesosQueue.size();
            for (int i = 0; i < size; i++) {
                killAll.add(procesosQueue.element().getName());
                procesosQueue.remove();
            }
            getKillAll();
        } else {
            System.out.println("####################################################");
            System.out.println("No se eliminaron procesos");
            System.out.println("####################################################");
        }
    }

    /**
     * Se muestran todos los procesos que fueron eliminados.
     */
    public static void getKillAll() {
        for (int i = 0; i < killAll.size(); i++) {
            System.out.println("Proceso[" + i + "]: " + killAll.get(i));
            System.out.println("      \tID: " + procesosAsesinados.get(i).getID());
        }
        System.out.println("####################################################");
    }

    /**
     * Se asigna la memoria al proceso dependiendo si hay espacio libre o es
     * necesario agregar a la cola de la memoria.
     */
    public static void allocateMemory() {
        int diferencia;
        int localidadA, localidadB;
        // Se obtiene los diferentes atributos del proceso que se va a agregar.
        String nombre = procesosQueue.get(numeroProcesos).nombreProceso;
        String id = procesosQueue.get(numeroProcesos).getID();
        int localidad = procesosQueue.get(numeroProcesos).getLocalidad();
        // Si la memeria esta vacía entonces se agrega el proceso al inicio.
        if (memoria.isEmpty()) {
            memoria.add(new Memory_management(id, nombre, localidad, false));
            memoria.element().setLocalidades(0, memoria.element().getLocalidades() - 1);
        }
        // De tener elementos, se recorre la memoria con un ciclo for para buscar algún
        // espacio disponible.
        else {
            for (int i = 0; i < memoria.size(); i++) {
                /*
                 * Si se encuentra un espacio disponible se procede a verificar si es posible
                 * introducir ese proceso en el espacio de memoria, ya que se puede dar que el
                 * proceso ocupe más memoria de la que hay disponible en ese espacio.
                 */
                if (memoria.get(i).available == true
                        && memoria.get(i).localidad >= procesosQueue.get(numeroProcesos).getLocalidad()) {
                    diferencia = memoria.get(i).localidad - procesosQueue.get(numeroProcesos).getLocalidad();
                    /*
                     * Si el espacio disponible es igual al que va a ocupar el proceso entonces se
                     * asigna de forma directa.
                     */
                    if (diferencia == 0) {
                        localidadA = memoria.get(i).getLocalidadA();
                        localidadB = memoria.get(i).getLocalidadB();
                        memoria.set(i, new Memory_management(id, nombre, localidad, false));
                        memoria.get(i).setLocalidades(localidadA, localidadB);
                    }
                    /*
                     * Si el espacio es mayor al requerido entonces se asigna el proceso y se crea
                     * otro espacio de memoria que contendra unicamente disponible la memoria
                     * sobrante que no ocupo el proceso.
                     */
                    else {
                        localidadA = memoria.get(i).getLocalidadA();
                        memoria.set(i, new Memory_management(id, nombre, localidad, false));
                        localidadB = memoria.get(i).getLocalidades() + localidadA - 1;
                        memoria.get(i).setLocalidades(localidadA, localidadB);
                        memoria.add(i + 1, new Memory_management("", "", diferencia, true));
                        int localidadC = memoria.get(i).getLocalidadB() + diferencia;
                        memoria.get(i + 1).setLocalidades(localidadB + 1, localidadC);
                    }
                    System.out.println("Asignando Memoria...");
                    System.out.println("####################################################");
                    return;
                }
                /*
                 * Si en ningun momento se encontro un espacio disponible entonces solo se
                 * agrega el proceso a la cola de memoria.
                 */
                else if ((i + 1) == memoria.size()) {
                    memoria.add(new Memory_management(id, nombre, localidad, false));
                    localidadA = memoria.get(i).getLocalidadB();
                    localidadB = localidadA + memoria.get(i + 1).getLocalidades();
                    memoria.get(i + 1).setLocalidades(localidadA + 1, localidadB);
                    return;
                }
            }
        }
    }

    /**
     * Se unen espacios de memoria consecutivos si los hay al momento de terminado
     * un proceso.
     * 
     * @param nombre Nombre del proceso a buscar.
     */
    public static void reallocateMemory(String nombre, int instrucciones) {
        int localidadA, localidadB;
        // String nombreProceso = procesosQueue.element().getName();
        int nuevaLocalidad;
        for (int j = 0; j < memoria.size(); j++) {
            /*
             * Si el nombre el proceso es igual al que se encuentra en la memoria entonces
             * se combian sus atributos de forma que se muestre como libre.
             */
            if (nombre == memoria.get(j).getName() && instrucciones <= 0 ) {
                memoria.get(j).setAvailable(true);
                memoria.get(j).setName("");
                memoria.get(j).setID("");
                /*
                 * Se verifica la siguiente localidad de memoria, si se encuentra libre entonces
                 * se fusionan.
                 */
                if ((j + 1) != memoria.size() && memoria.get(j + 1).getAvailable() == true) {
                    /*
                     * Si se da el caso de que solo queden 2 elementos en la memoria sin ningun
                     * proceso entonces se eliminan esos 2 elementos
                     */
                    if (memoria.size() == 2) {
                        memoria.remove();
                        memoria.remove();
                    } else {
                        nuevaLocalidad = memoria.get(j).getLocalidades() + memoria.get(j + 1).getLocalidades();
                        localidadA = memoria.get(j).getLocalidadA();
                        localidadB = memoria.get(j + 1).getLocalidadB();
                        locateAssignment(nuevaLocalidad, localidadA, localidadB, j, j + 1);
                    }
                }
                /*
                 * Se verifica la anterior localidad de memoria, si se encuentra libre entonces
                 * se fusionan.
                 */
                if ((j - 1) != -1 && memoria.get(j - 1).getAvailable() == true) {
                    if (memoria.size() == 2) {
                        memoria.remove();
                        memoria.remove();
                    } else {
                        nuevaLocalidad = memoria.get(j).getLocalidades() + memoria.get(j - 1).getLocalidades();
                        localidadA = memoria.get(j - 1).getLocalidadA();
                        localidadB = memoria.get(j).getLocalidadB();
                        locateAssignment(nuevaLocalidad, localidadA, localidadB, j, j - 1);
                    }
                }
                return;
            }
        }
    }

    public static void locateAssignment(int nuevaLocalidad, int localidadA, int localidadB, int posicion,
            int colocacion) {
        memoria.get(posicion).setLocalidad(nuevaLocalidad);
        memoria.get(posicion).setLocalidades(localidadA, localidadB);
        memoria.remove(colocacion);
    }

    /**
     * Se muestra el estado de la memoria con los procesos que se encuentran en ella
     * con su cantidad de localidades ocupadas así como la memoria disponible.
     */
    public static void getStatusMemory() {
        int TotalMemory = 1024;
        int localidadA, localidadB;
        System.out.println("#############################################################");
        for (int i = 0; i < memoria.size(); i++) {
            localidadA = memoria.get(i).getLocalidadA();
            localidadB = memoria.get(i).getLocalidadB();
            if (memoria.get(i).getAvailable() == true) {
                System.out.print("Memory_Location[" + localidadA + "-" + localidadB + "]:");
                System.out.println("    \tUnallocated_Memory = " + memoria.get(i).getLocalidades());
            } else {
                System.out.print("Memory_Location[" + localidadA + "-" + localidadB + "]: ");
                System.out.print("  \t" + memoria.get(i).getName());
                System.out.println("    \tassigned = " + memoria.get(i).getLocalidades());
            }
        }
        if (!memoria.isEmpty()) {
            int finalMemory = memoria.size() - 1;
            int localidadF = memoria.get(finalMemory).getLocalidadB() + 1;
            System.out.print("Memory_Location[" + localidadF + "-" + TotalMemory + "]:");
            System.out.println("\tUnallocated_Memory = " + memoriaTotal);
        } else {
            System.out.print("Memory_Location[0-" + TotalMemory + "]:");
            System.out.println("\tUnallocated_Memory = " + memoriaTotal);
        }
        System.out.println("#############################################################");
    }
}
