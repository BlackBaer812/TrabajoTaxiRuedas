# TrabajoTaxiRuedas
Funcionalidades Principales:
Para los Usuarios:
Registro y Autenticación: Los usuarios podrán registrarse e iniciar sesión en la aplicación.El usuario pondrá su usuario y clave y entrará en su menú de opciones, sino deberá registrarse. Este será el primer menú con las opciones (Login / Registrarse / Volver).
Reserva de Taxis: Los usuarios podrán buscar taxis disponibles para reservar según su ubicación y hora deseada. El usuario pedirá un taxi indicando su dirección y su destino.
Selección de Servicios: Podrán seleccionar el tipo de servicio requerido (por ejemplo, taxi estándar, taxi de lujo, servicio de entrega, etc.).
Indicación de Ruta y Destino: Los usuarios podrán ingresar su ubicación actual y destino para obtener una estimación de costo y tiempo. Antes de contratar el usuario podrá saber el coste del trayecto. 
Visualización de Disponibilidad: Verificarán la disponibilidad de taxis y recibirán confirmación de reserva. Cuando el taxista acepte el trabajo se debe avisar al usuario.
Historial de Viajes: Los usuarios podrán ver un historial de sus viajes pasados, incluyendo detalles como fechas, rutas, costos, etc.
Para los Taxistas:
Registro y Autenticación: Los taxistas podrán registrarse e iniciar sesión en la aplicación. Igual que los clientes.
Gestión de Disponibilidad: Podrán marcar su disponibilidad para recibir solicitudes de viaje.Si un taxista acepta un viaje debe ponerse como ocupado y cuando termina debe ponerse como libre.
Aceptación/Rechazo de Trabajos: Los taxistas recibirán notificaciones de solicitudes de viaje y podrán aceptar o rechazar según su disponibilidad. Cuando un usuario hace una petición, debe salirle al taxista el listado de peticiones y aceptar la petición.
Registro de Viajes Realizados: Los taxistas podrán registrar los detalles de los viajes realizados, incluyendo rutas, tiempos, tarifas, etc. Las rutas puedes establecerlas como quieras, desde direcciones de inicio y fin, coordenadas gps,...
Cálculo de Tarifas: El sistema calculará automáticamente las tarifas de los viajes basándose en factores como distancia, tipo de servicio, tarifas por hora, etc. Para este caso como no tenemos mapas ni coordenadas reales, cuando el cliente ponga dirección de origen y fin, debemos tener una tabla que indique los km que hay de una dirección a otra con los km calculados. En una aplicación real usaríamos una API que te hace ese cálculo automáticamente.
Comentarios y Calificaciones: Los usuarios podrán dejar comentarios y calificaciones para los taxistas, y viceversa.
Requerimientos Técnicos:
Lenguaje de Programación: Java
Base de Datos: Se utilizará MySQL para almacenar los datos de usuarios, taxistas, reservas, historiales, etc. Puedes utilizar ficheros y Collections sin utilizar BBDD.
Interfaz Gráfica: No se hará de forma gráfica, aunque se permitirá el uso de javax.
Seguridad: No se implementará encriptación de contraseñas, aunque se deja la opción de poder hacerlo.
Documentación: Se requerirá documentación detallada del código, incluyendo diagramas UML, descripción de clases, métodos, etc.
