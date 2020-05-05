# Ejercicio-Carrera-de-Tortugas
Ejercicio para asignatura Programación de Servicios y Procesos, Lenguaje Java:

Enunciado:

En esta PAC de Desarrollo, veremos de forma practica una pequeña utilidad de los Threads y las comunicaciones mediante sockets.
La PAC va a consistir en crear un programa de carreras de tortugas y que mediante sockets envíe mensajes entre cliente y servidor.

Cliente:

El cliente será el encargado de pedir los datos al usuario y contará con un pequeño menú de 5 opciones, después de seleccionar cada opción volverá a solicitar una opción volviendo a mostrar el menú:

1. Introducir nueva tortuga
2. Eliminar una tortuga
3. Mostrar tortugas
4. Iniciar carrera
5. Salir.

Cada vez que el usuario pulse una de las teclas realizará la opción necesaria enviando la petición correspondiente al servidor. De esta manera podemos crear múltiples tortugas, podemos eliminar y mostrarlas, finalmente empezar una carrera con las tortugas creadas.

Introducir nueva tortuga:
Enviará al servidor el nombre y dorsal de la tortuga, este la creará y la guardará en una Lista de tortugas. Crear tortuga no implica mostrarlas o empezar la carrera.
El servidor responderá al cliente con el mensaje correspondiente, el cual mostrará el mensaje.

Eliminar una tortuga:
Enviará al servidor la posición a eliminar de la tortuga, para hacerlo más entendible la tortuga en la posición de la lista 0, será la 1.
El servidor responderá al cliente con el mensaje correspondiente, el cual mostrará el mensaje.

Mostrar tortugas:
Enviará al servidor la petición y este responderá al cliente con una lista de las diferentes tortugas que tiene en su lista. Una vez recibidos los datos el cliente los mostrará.

Iniciar carrera:
Método “más” importante, será el encargado de enviar la petición al servidor, este responderá al cliente con la tortuga ganadora. Una vez recibido el mensaje el cliente lo mostrará.

Salir:
Cerrará todas las conexiones y finalizará el programa tanto del cliente como del servidor.

Servidor:
En el servidor encontraremos toda la lógica de nuestro programa, como mínimo tendremos las clases necesarias para el correcto funcionamiento:
• Tortuga: clase para crear una nueva tortuga la misma tendrá nombre y dorsal
• Carrera: clase que implementará los Threads (un nuevo Thread por cada tortuga), en esta se creará un bucle donde empezaremos en 0 y acabaremos en 500, en vez de avanzar de 1 en 1, avanzaremos mediante números aleatorios y de esta manera conseguiremos crear la “carrera”.
• Servidor: clase necesaria para toda la configuración de los sockets y recibir/enviar las diferentes peticiones del cliente
• Main: Clase utilizada para inicializar el servidor, estableciendo los datos necesarios para que el cliente se conecte correctamente.

Cuando se acabe la carrera, deberéis prestar atención a no producir condiciones de carrera y que el resultado sea el correcto, implementando los mecanismos necesarios para conseguirlo.

¿Como proceder a la programación de esta PAC? Como recomendación os dejo 2 pasos que bajo mi punto de vista os harán más ameno el desarrollo de la actividad propuesta.

Paso 1:
Crea la estructura cliente-servidor para conseguir las comunicaciones entre ambos.
Ejecuta el servidor en bucle infinito (while(true)) y esperará las diferentes peticiones del cliente, cada vez que se reciba una petición responderá con un mensaje al cliente.

Paso 2:
Aumenta la funcionalidad del ejercicio 1, implementa las clases necesarias y crea los Threads para realizar la carrera de tortugas.
