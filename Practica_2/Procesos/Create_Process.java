package Procesos;

import java.util.Random;

public class Create_Process {

    String nombreProceso, idProceso;
    int numeroInstrucciones, localidad, ubicacion;
    int arrLocalidades[] = { 16, 32, 64, 128 };
    boolean online = true;
    Random numRandom = new Random();

    /**
     * Contructor que asigna el nombre del proceso que el usuario ingreso, así como
     * se generan los diferentes atributos llamando a los métodos correspondientes.
     * 
     * @param nombreProceso Nombre del procesos asignado por el usuario.
     */
    public Create_Process(String nombreProceso) {
        this.nombreProceso = nombreProceso;
        setID();
        setNumeroInstrucciones();
        setlocalidad();
    }

    /**
     * @return Nombre del proceso.
     */
    public String getName() {
        return nombreProceso;
    }

    /**
     * Se genera el ID del proceso a partir de su código hash y se le asignan 2
     * letras al inicio y al final y un número al final de forma aleatorio para
     * evitar que en cierta forma se repitan los ID's al agregar procesos con el
     * mismo nombre.
     */
    public void setID() {
        char idLetter = (char) (numRandom.nextInt(90 - 65) + 65);
        char idLetterTwo = (char) (numRandom.nextInt(90 - 65) + 65);
        int idNumber = (int) (numRandom.nextInt(10));
        String idHash = Integer.toHexString(nombreProceso.hashCode());
        idProceso = idLetter + idHash.toUpperCase() + idLetterTwo + "-" + idNumber;
    }

    /**
     * @return ID del proceso.
     */
    public String getID() {
        return idProceso;
    }

    /**
     * Se generan el número de intrucciones de forma aleatoria.
     */
    public void setNumeroInstrucciones() {
        numeroInstrucciones = (int) (numRandom.nextInt(30 - 10) + 10);
    }

    /**
     * @return Número de instrucciones del proceso.
     */
    public int getNumeroInstrucciones() {
        return numeroInstrucciones;
    }

    /**
     * @return Número de localidades ocupadas.
     */
    public int getLocalidad() {
        return localidad;
    }

    /**
     * Se asignan las localidades del proceso de forma que se genera un número
     * aleatorio entre 0 y 3 para sacarlo de un arreglo que contiene las localidades
     * posibles.
     */
    public void setlocalidad() {
        int posicion = (int) (numRandom.nextInt(4));
        localidad = arrLocalidades[posicion];
    }
    // public boolean setAvailable() {
    // return online;
    // }
}