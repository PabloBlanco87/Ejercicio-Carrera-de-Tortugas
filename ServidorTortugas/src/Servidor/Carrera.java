package Servidor;

import java.util.concurrent.ThreadLocalRandom;

/*clase que implementará los Threads (un nuevo Thread por cada tortuga), en esta se creará un bucle donde empezaremos en 0 y acabaremos en 500, 
en vez de avanzar de 1 en 1, avanzaremos mediante números aleatorios y de esta manera conseguiremos crear la “carrera”*/
public class Carrera extends Thread {

    public static String ganador;

    public Carrera(String fraseString) {
        super(fraseString);
    }

    //Método run() que contiene el bloque de ejecución de Thread
    @Override
    public void run() {
        int aleatorio = ThreadLocalRandom.current().nextInt(0, 500 + 1);    //Generamos un número aleatorio entre 0 y 500
        for (int i = 0; i >= 500; i++) {
            i += aleatorio; //Al índice del bucle for le vamos sumando el número aleatorio, para que unas tortugas avancen más rápido y otras más lentas
        }
        if (ganador == null) {  //Esta condición solo permitirá entrar una vez al primero que llegue a meta, a los demás no les dejará porque !null
            ganador = getName();
            System.out.println("Ya existe un ganador");
        }
    }
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  ********************* ERRORES/ COSAS A MEJORAR EN MI CÓDIGO:   ****************************                             //
// 1. LA TORTUGA QUE SE CREA EN PRIMER LUGAR GANA MUCHAS MÁS VECES QUE LAS OTRAS TORTUGAS:                                  //
//    POSIBLEMENTE SEA PORQUE ES LA PRIMERA TORTUGA EN ENTRAR EN EL BUCLE FOR QUE ASIGNA EL start()                         //
//    (línea 141 clase Servidor)                                                                                            //
// 2. CUANDO CREAMOS LA CARRERA, PERMITE HACER TANTAS CARRERAS COMO TORTUGAS ESTÉN CREADAS, SI HAGO MÁS, SE QUEDA COLGADO   //
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////