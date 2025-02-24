package com.productosDB.gestion_productos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Aplicacion principal para inicializar el servidor den Sprint Boot


@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
    	//inicializamos el contexto de nuestra aplicacion
    	//inizializa un servidor embebido para recibir peticiones
        SpringApplication.run(DemoApplication.class, args);
    }
}
