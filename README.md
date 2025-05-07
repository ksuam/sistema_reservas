# sistema_reservas
Prueba tecnica

# Descripción
Este proyecto consiste en un sistema de reservas para espacios compartidos, el cual le permite al usuario gestionar las reservas desde su creacion, lectura,eliminacion y modificacion.

## Frameworks usados
- Angular 18
- Springboot 3

# Arquitectura
- Microservicios

# Validaciones
- Tiempo minimo de 1 dia al reservar y maximo de 2 dias al reservar
- Se valida que niguna reeserva se solape con otra, con las notificaciones al usuario necesarias

## Validaciones por Usuario

### Usuario cgomez
- Usuario: cgomez Password: cgomez

este usuario puede ver las reservaciones de los demas usuarios

### Usuario diferente de cgomez
- los demas usuarios al ingresar solo podrán ver las reservas que les pertenecen


# Escalabilidad

Actualmente en el backend se desarrollaron Apis, para la gestion de usuarios y espacios,de llegarse a desarrolar la interfaz grafica para usar dichas apis, el sistema podria ser administrado completamente desde la interfaz

# Documentación
- Para las apis existe un archivo .json ubicado en la siguiente ruta dentro del respositorio documentacion -> apis, el cual se deberá exportar en postman

- Para el despliegue del aplicativo como tal, revisar el archivo "Despliegue.pdf" ubicado en la siguiente ruta Documentacion -> Despliegue -> Despliegue.pdf
