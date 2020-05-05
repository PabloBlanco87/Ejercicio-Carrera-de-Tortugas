package Servidor;

import java.io.IOException;

public class ServidorMain {

    public static void main(String[] args) throws IOException {
        //Creamos el objeto Servidor
        Servidor servidor = new Servidor();
        //Hacemos llamada al método iniciarServidor
        servidor.iniciarServidor();
    }
}
