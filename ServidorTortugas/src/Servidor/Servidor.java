package Servidor;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class Servidor {

    private final int puerto = 1411;    //Mismo puerto que cliente
    private ServerSocket serverSocket;  //Socket del servidor
    private Socket socket;  //Socket del cliente

    public Servidor() throws IOException {
        System.out.println("Iniciando servidor...");
        serverSocket = new ServerSocket(puerto);    //Inicializamos socket del servidor
        socket = new Socket();  //Inicializamos socket del cliente
    }

    public void iniciarServidor() throws IOException {
        System.out.println("Esperando al cliente...");
        socket = serverSocket.accept();     //Espera hasta que se conecta un cliente, cuando se conecta, sigue el flujo del programa
        System.out.println("Cliente conectado y en línea");

        DataOutputStream cliente = new DataOutputStream(socket.getOutputStream()); //Obtener la entrada del cliente 
        BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream())); //Esperamos mensaje del cliente
        cliente.writeUTF("Petición recibida y aceptada");  //Mostrará en cliente este mensaje
        ArrayList<Tortuga> listaDeTortugas = new ArrayList<Tortuga>();  //Creamos un ArrayList de tipo Tortuga donde iremos guardando todas las tortugas
        String opcionMenu;  //Declaramos variable tipo String que recibirá lo que ha ingresado el cliente por teclado (opción del menú)
        boolean flag = false; //Declaramos un boolean que lo vamos a utilizar en el bucle como condición de entrada/salida
        while (!flag) {
            //El método trim() de la clase String en Java, elimina los espacios al principio y al final de las cadenas. SIN ÉL, NO FUNCIONA, ERROR COMÚN EN JAVA
            opcionMenu = entrada.readLine().trim(); 
            switch (opcionMenu) {
                case "1":
                    anadirTortuga(listaDeTortugas, cliente, entrada);
                    break;
                case "2":
                    eliminarTortuga(listaDeTortugas, cliente, entrada);
                    break;
                case "3":
                    mostrarTortuga(listaDeTortugas, cliente);
                    break;
                case "4":
                    carrera(listaDeTortugas, cliente);
                    break;
                case "5":
                    System.out.println("Saliendo del servidor");
                    flag = true;    //Cambiamos la condición de salida para que salga del bucle
                    break;
                default:
                    break;
            }
        }
        System.out.println("Fin de conexión");
        socket.close(); //Cerramos socket cliente   
        serverSocket.close();   //Cerramos socket servidor
    }

    //Función añadir tortuga
    private void anadirTortuga(ArrayList<Tortuga> listaDeTortugas, DataOutputStream cliente, BufferedReader entrada) throws IOException {
        System.out.println("El cliente está introduciendo una tortuga...");
        String lectorString;
        Tortuga nuevaTortuga = new Tortuga(); //Creamos un nuevo objeto tortuga 
        //Creamos el nombre de la tortuga
        lectorString = entrada.readLine() + "\n";    //Creamos una variable tipo String donde guardaremos la entrada que nos manda el cliente (el nombre)
        //El método trim() de la clase String en Java, elimina los espacios al principio y al final de las cadenas. SIN ÉL, NO FUNCIONA, ERROR COMÚN EN JAVA
        nuevaTortuga.setNombre(lectorString.trim());   //Añadimos ese nombre a la tortuga
        System.out.println("Nombre creado");
        //Creamos el dorsal de la tortuga
        lectorString = entrada.readLine() + "\n";   //Volvemos a guardar la entrada que nos manda el cliente (el dorsal)
        //El método trim() de la clase String en Java, elimina los espacios al principio y al final de las cadenas. SIN ÉL, NO FUNCIONA, ERROR COMÚN EN JAVA
        nuevaTortuga.setDorsal(Integer.parseInt(lectorString.trim())); //Añadimos ese dorsal a la tortuga
        System.out.println("Dorsal creado");
        //Añadimos la tortuga creada al ArrayList
        listaDeTortugas.add(nuevaTortuga);
        //Mostramos en cliente el mensaje con todos los datos de la tortuga creada
        cliente.writeUTF("La tortuga con nombre: " + nuevaTortuga.getNombre() + " y dorsal " + nuevaTortuga.getDorsal() + " en su caparazón ha sido creada.");
        System.out.println("La tortuga ha sido creada");    //Mensaje que se mostrará en el servidor
        System.out.println("Existen " + listaDeTortugas.size() + " tortugas en memoria");   //Indicamos las tortugas guardadas
    }

    //Función eliminar tortuga
    private void eliminarTortuga(ArrayList<Tortuga> listaDeTortugas, DataOutputStream cliente, BufferedReader entrada) throws IOException {
        System.out.println("Eliminando tortuga...");
        String lectorString;    //Creamos una variable tipo String donde guardaremos la entrada que nos manda el cliente
        int tortugaEliminada;   //Creamos una variable tipo entero para acceder al elemento del ArrayList y poder borrarlo
        if (!listaDeTortugas.isEmpty()) {   //Controlamos si el usuario intenta borrar tortugas que no existen, o no hay tortugas que eliminar
            System.out.println("El cliente está eliminando una tortuga...");
            lectorString = entrada.readLine() + "\n";   //Variable tipo String donde guardaremos la entrada que nos manda el cliente
            //El método trim() de la clase String en Java, elimina los espacios al principio y al final de las cadenas. SIN ÉL, NO FUNCIONA, ERROR COMÚN EN JAVA
            tortugaEliminada = Integer.parseInt(lectorString.trim()) - 1;   //Parseamos para pasar de String a tipo int
            if (tortugaEliminada < listaDeTortugas.size()) {    //Si el número indicado se encuentra en el ArrayList
                listaDeTortugas.remove(tortugaEliminada);   //Función eliminar elemento del ArrayList
                cliente.writeUTF("La tortuga " + (tortugaEliminada + 1) + "ha sido eliminada correctamente");
                System.out.println("Tortuga eliminada");
            } else {
                cliente.writeUTF("Esa tortuga no se encuentra en la lista");    //Mensaje que aparecerá al cliente
                System.out.println("No se ha podido eliminar la tortuga");  //Mensaje que aparecerá en servidor
            }
        } else {
            cliente.writeUTF("No existen tortugas que eliminar");   //Mensaje que aparecerá al cliente
            System.out.println("No se ha podido eliminar la tortuga");  //Mensaje que aparecerá en servidor
        }
    }

    //Función mostrar tortugas
    private void mostrarTortuga(ArrayList<Tortuga> listaDeTortugas, DataOutputStream cliente) throws IOException {
        System.out.println("El cliente está viendo las tortugas...");

        if (!listaDeTortugas.isEmpty()) {   //Si existen objetos Tortuga dentro del ArrayList entonces las muestra
            System.out.println("Mostrando tortugas...");    //Mensaje que aparecerá en servidor
            cliente.writeUTF("Mostrando tortugas:");  //Mensaje que aparecerá al cliente
            Iterator itr = listaDeTortugas.iterator();//Iterator para recorrer el arraylist junto a un bucle while
            int i = 0;
            while (itr.hasNext()) {
                Tortuga tortuga = (Tortuga) itr.next();
                i++; //Esto nos indicará el numero con el que podemos referenciar a nuestra tortuga pra eliminarla
                cliente.writeUTF(i + ". Tortuga: " + tortuga.getNombre() + " dorsal: " + tortuga.getDorsal()); //Muestra en cliente la lista de tortugas
            }
        } else {
            System.out.println("No hay tortugas para eliminar");  //Mensaje que aparecerá en servidor
            cliente.writeUTF("No hay tortugas para eliminar");    //Mensaje que aparecerá al cliente
        }
        cliente.writeUTF("Lista de tortugas creadas mostrada"); //Mensaje que aparecerá al cliente
        System.out.println("Tortugas mostradas");   //Mensaje que aparecerá en servidor
    }

    //Función iniciar carrera
    private void carrera(ArrayList<Tortuga> listaDeTortugas, DataOutputStream cliente) throws IOException {
        int tortugaGanadora;    //Creamos variable de tipo entero para almacenar el index de 
        String flag = null;
        System.out.println("El cliente ha empezado una carrera...");
        if (listaDeTortugas.size() > 1) {   //Creará la carrera solo si hay 2 o más tortugas
            for (int i = 0; i < listaDeTortugas.size(); i++) {
                Carrera raceCarrera = new Carrera(" " + i); //Creamos un número para cada tortuga
                raceCarrera.start();    //Este llamará al método run()
            }
            while (flag == null) {  //Este bucle va a volver a iniciarse hasta que haya un ganador, lo controlamos con la variable flag
                if (Carrera.ganador != null) {
                    //El método trim() de la clase String en Java, elimina los espacios al principio y al final de las cadenas. SIN ÉL, NO FUNCIONA, ERROR COMÚN EN JAVA
                    tortugaGanadora = Integer.parseInt(Carrera.ganador.trim());//Cogemos el nombre del ganador y lo pasamos a int
                    cliente.writeUTF("¡¡Ha ganado la " + tortugaUnica(listaDeTortugas, tortugaGanadora) + "!!");  //Mensaje que aparecerá al cliente
                    System.out.println("Ha habido ganador");   //Mensaje que aparecerá en servidor
                    flag = "noNull";   //Le damos cualquier otro valor para salir del bucle
                }
            }
            Carrera.ganador = null; //cambiamos de nuevo a null para iniciar otra carrera
        } else {
            cliente.writeUTF("No hay suficientes tortugas");    //Se enviará este mensaje al cliente en el caso de no haber 2 o más tortugas
            System.out.println("No se ha creado la carrera");
        }
    }

    //Función para mostrar una sola tortuga y nos devuelve un String
    private String tortugaUnica(ArrayList<Tortuga> listaDeTortugas, int numero) {
        String ganadora;    //Creamos una variable tipo String donde guardaremos la frase que queremos meter en la función carrera
        Tortuga tortuga = listaDeTortugas.get(numero); //Obtendremos su número de índice
        ganadora = ("tortuga de nombre " + tortuga.getNombre() + " y dorsal " + tortuga.getDorsal() + " al caparazón");  //Construimos la frase que enviaremos a la función carrera()
        return ganadora;
    }
}
