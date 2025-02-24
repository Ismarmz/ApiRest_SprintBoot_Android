package com.productosDB.gestion_productos;

import org.springframework.data.jpa.repository.JpaRepository;

//Interfaz que extiende de JpaRepository
//Trabaja con la entidad producto y con su PM id de tipo entero
//SpringBoot se encarga automaticamente de implementar la interfaz para interactuar con la BD

//En resumen esta interfaz permite realizar el CRUD sobre la tabla Productos
//Sin tener que escribir codigo SQL

//Al extender de JpaRespoitor heredamos metodos como
//Save, findById, FindAll, DeletedById, ExistById

//Tambien nos permite personalizar Queries, y SpringBoot automaticamente hara la query
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
}
