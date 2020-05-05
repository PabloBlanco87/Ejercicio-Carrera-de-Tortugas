package Cliente;

import java.io.IOException;

public class ClienteMain {

    public static void main(String[] args) throws IOException {
        
        //Creamos el objeto Cliente
        Cliente cliente = new Cliente();
        
        //Hacemos llamada al método iniciarCliente
        cliente.iniciarCliente();
    }
}
