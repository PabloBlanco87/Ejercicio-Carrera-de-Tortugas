package Cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;


public class Cliente {

    private final int puerto = 1411;
    private final String host = "localhost";
    private Socket socket;  //Socket del cliente
    Scanner scan = new Scanner(System.in);  //Con la clase Scanner para ingresar datos por teclado

    public Cliente() throws IOException {
        System.out.println("Iniciando cliente..."); //Mostramos este mensaje en el Output de Cliente
        socket = new Socket(host, puerto);  //Definimos el host y el puerto del Cliente
    }

    public void iniciarCliente() throws IOException {   //Creamos el método iniciarCliente

        DataInputStream entradaDelservidor = new DataInputStream(socket.getInputStream());  //Recibe datos del cliente (in)
        DataOutputStream salidaDelServidor = new DataOutputStream(socket.getOutputStream()); //Envia datos del cliente (out)
        System.out.println(entradaDelservidor.readUTF());   //Recibimos la confirmación del servidor
        salidaDelServidor.writeUTF("Se ha conectado con el Cliente" + "\n");
        int opcion = 0; //Creamos una variable de tipo entero y valor 0, para que el bucle while siempre se repita y vuelva a mostrar el MENÚ
        String opcionMenu;

        while (opcion == 0) {
            System.out.println();
            System.out.println("****MENU CARRERA DE TORTUGAS****");
            System.out.println("***ELIJA LA OPCIÓN QUE DESEE:***");
            System.out.println("*1* INTRODUCIR UNA NUEVA TORTUGA");
            System.out.println("*2* ELIMINAR UNA TORTUGA");
            System.out.println("*3* MOSTRAR TORTUGAS");
            System.out.println("*4* INICIAR CARRERA");
            System.out.println("*5* SALIR");
            System.out.println("*********\n");
            Scanner scan = new Scanner(System.in);

            opcionMenu = scan.nextLine(); //Utilizamos la clase Scanner para recibir información por teclado
            salidaDelServidor.writeUTF(opcionMenu + "\n");//Enviamos la opción escogida al servidor

            switch (opcionMenu) {   //Utilizamos una estructura switch-case para configurar el menú de nuestro programa
                case "1":
                    anadirTortuga(scan, salidaDelServidor, entradaDelservidor);
                    break;
                case "2":
                    eliminarTortuga(scan, salidaDelServidor, entradaDelservidor);
                    break;
                case "3":
                    mostrarTortugas(entradaDelservidor);
                    break;
                case "4":
                    carrera(entradaDelservidor);
                    break;
                case "5":
                    System.out.println("Cerrando servidor. . .");
                    salidaDelServidor.writeUTF("5" + "\n");
                    opcion = 1; //cambiamos condición de entrada en el bucle para que salga de él
                    break;
                default:    //Controlar errores
                    System.out.println("Escribe solo un número del 1 al 5");
                    break;
            }   //Fin del switch-case
        }   //Fin del bucle while

        salidaDelServidor.writeUTF("Finalizando conexión...");  //
        System.out.println("Finalizando cliente...");   //Mostramos esto en la consola Cliente
        socket.close(); //Cerramos el socket
    }

    /*Enviará al servidor el nombre y dorsal de la tortuga, este la creará y la guardará en una Lista de tortugas. 
    Crear tortuga no implica mostrarlas o empezar la carrera.
    El servidor responderá al cliente con el mensaje correspondiente, el cual mostrará el mensaje*/
    private void anadirTortuga(Scanner scanner, DataOutputStream salidaDeServidorOutputStream, DataInputStream entradaDeServidorInputStream) throws IOException {
        System.out.println("Introduzca el nombre de la nueva tortuga");
        salidaDeServidorOutputStream.writeUTF(scanner.nextLine() + "\n"); //Obtenemos datos por teclado y se envían al servidor
        System.out.println("Introduzca el dorsal de la nueva tortuga");
        salidaDeServidorOutputStream.writeUTF(scanner.nextLine() + "\n"); //Obtenemos datos por teclado y se envían al servidor
        System.out.println(entradaDeServidorInputStream.readUTF()); //Recibimos confirmación por parte del Servidor
    }

    /*Enviará al servidor la posición a eliminar de la tortuga, para hacerlo más entendible 
    la tortuga en la posición de la lista 0, será la 1.
    El servidor responderá al cliente con el mensaje correspondiente, el cual mostrará el mensaje*/
    private void eliminarTortuga(Scanner scanner, DataOutputStream salidaDeServidorOutputStream, DataInputStream entradaDeServidorInputStream) throws IOException {
        System.out.println("¿Qué número de tortuga desea eliminar?");
        salidaDeServidorOutputStream.writeUTF(scanner.nextLine() + "\n"); //Obtenemos datos por teclado y se envían al servidor
        System.out.println(entradaDeServidorInputStream.readUTF()); //Recibimos confirmación por parte del Servidor
    }

    /*Enviará al servidor la petición y este responderá al cliente con una lista de las diferentes 
    tortugas que tiene en su lista. Una vez recibidos los datos el cliente los mostrará.*/
    private void mostrarTortugas(DataInputStream entradaDeServidorInputStream) throws IOException {
        String mensajeDeServidor;   //Creamos una variable de tipo String para que guarde los valores de la entrada del servidor
        System.out.println(entradaDeServidorInputStream.readUTF() + "\n");
        while (true) {  //Ejecutamos bucle infinito
            mensajeDeServidor = entradaDeServidorInputStream.readUTF(); //Igualamos el valor de la variable al mensaje que nos entre desde el servidor
            if (mensajeDeServidor.equals("Lista de tortugas creadas mostrada")) {  //Si la variable contiene el valor "Fin de la lista de tortugas" se saldrá del bucle
                break;
            }
            System.out.println(mensajeDeServidor);  //Muestra el mensaje por pantalla
            
        }
        
    }

    /*Método “más” importante, será el encargado de enviar la petición al servidor, este responderá al cliente 
    con la tortuga ganadora. Una vez recibido el mensaje el cliente lo mostrará.*/
    private void carrera(DataInputStream entradaDeServidorInputStream) throws IOException {
        System.out.println("¡¡¡Comienza la carrera... Hagan sus apuestas!!! \n");
        String mensajeDeServidor = entradaDeServidorInputStream.readUTF();
        if (mensajeDeServidor.equals("No hay suficientes tortugas")) {    //Si se quiere hacer una carrera con 0 o 1 tortugas nos controlará este error
            System.out.println("No hay suficientes tortugas para iniciar una carrera \n");
        } else {
            System.out.println(mensajeDeServidor);  //Mostramos por pantalla la tortuga ganadora de la carrera
        }
    }

}
