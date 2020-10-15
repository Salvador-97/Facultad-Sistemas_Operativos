package Procesos;

public class Memory_management {
    int localidadA, localidadB, localidad;
    String nombre, id;
    boolean available;

    /**
     * Contructor que asigna los atributos del objeto proceso a un objeto que
     * manejara la memoria.
     * 
     * @param id
     * @param nombre
     * @param localidad
     * @param available
     */
    public Memory_management(String id, String nombre, int localidad, boolean available) {
        this.nombre = nombre;
        this.id = id;
        this.localidad = localidad;
        this.available = available;
    }

    /**
     * Asignación de un nombre al proceso, en este caso se utiliza para eliminar el
     * nombre con una cadena vacía para simbolizar que no hay ningun proceso en esta
     * ubicación.
     */
    public void setName(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Asignación de un ID, en este caso se utiliza para eliminar el ID con una
     * cadena vacía para simbolizar que no hay ningun proceso en esta ubicación.
     */
    public void setID(String id) {
        this.id = id;
    }

    /**
     * Asignación de la disponibilidad de la memoria.
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * @return Localidades del proceso.
     */
    public void setLocalidad(int localidad) {
        this.localidad = localidad;
    }

    /**
     * Asigna primer y ulttima localidad que ocupa el proceso.
     * 
     * @param localidadA
     * @param localidadB
     */
    public void setLocalidades(int localidadA, int localidadB) {
        this.localidadA = localidadA;
        this.localidadB = localidadB;
    }

    /**
     * @return Nombre del proceso.
     */
    public String getName() {
        return nombre;
    }

    /**
     * @return ID del proceso.
     */
    public String getID() {
        return id;
    }

    /**
     * @return Localidades del proceso.
     */
    public int getLocalidades() {
        return localidad;
    }

    /**
     * @return Disponibilidad del proceso en memoria.
     */
    public boolean getAvailable() {
        return available;
    }

    /**
     * @return Primera localidad ocupada.
     */
    public int getLocalidadA() {
        return localidadA;
    }

    /**
     * @return Ultima localidad ocupada.
     */
    public int getLocalidadB() {
        return localidadB;
    }
}
