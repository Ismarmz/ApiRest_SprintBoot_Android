package com.productosDB.gestion_productos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.validation.Valid;
import java.util.List;

//Este controlador gestiona la comunicación entre el cliente (frontend o API externa) 
//y el servicio que maneja la lógica de negocio.

//Un ResponseEntity es una clase de springboot que representa la respuesta HTTP completa
//Devuelve los losd datos del objeto y el codigo del esta de HTTP
//Con esto por ejemplo si no se encontrara un producto devolveria un error de compilacion
//Y no un error 404
//Es mas claro y seguro para manejar API

//Los codigos de estado indica el estado de la operacion
//Ayudan al usuario a entender mejor las operaciones
//Ejemplo la etiqueta @Valid puede hacer uso del codigo de estado 400 cuando se cree un producto
//Y existan errores de sintaxis


//la etiqueta @Valid valida que un los objetos tengan los valores adecuados

//Es un controlador REST
//Recibe solicitudes en HTTP y las devuelve en formato JSON
@RestController

//Esta sera la URL base para manejar todas las operaciones del controlador
@RequestMapping("/api/productos")
public class ProductoController {

	
	//Inyecta el servicio ProductoService para manejar la logica del programa
	//asi no los gestinamos nosotros si no SpringBoot
    @Autowired
    private ProductoService productoService;

    //Esta etiqueta significa que responde a las sollicitudes GET  
    @GetMapping
    public List<Producto> listarProductos() {
    	//Mostramos los productos
        return productoService.listarProductos();
    }
    
    //Esta indica que maneja las solicitudes GET con un ID dinamico
    @GetMapping("/{id}")
    //PathVariable Extrae el id de la URL
    public ResponseEntity<Producto> obtenerProducto(@PathVariable int id) {
        try {
            Producto producto = productoService.obtenerProductoPorId(id);
            //Retorna el producto con el estado 200 OK
            return ResponseEntity.ok(producto);
        } catch (ResponseStatusException e) {
        	//si no lo encuentra, responde con el error 404 NOT FOUND
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //Esta etiqueta responde a solicitudes POST
    @PostMapping
    //La etiqueta RequestBody convierte el JSON en un objeto Producto
    public ResponseEntity<?> agregarProducto(@Valid @RequestBody Producto producto) {
    	//Devuelve el estado del producto 200 OK
        return ResponseEntity.ok(productoService.agregarProducto(producto));
    }

    //Esta etiqueta responde a solicitudes DELETE
    @DeleteMapping("/{id}")
    //PathVariable que maneja solicitudes DELETE con ID
    public ResponseEntity<String> eliminarProducto(@PathVariable int id) {
        try {
        	//Si el producto existe lo elimina y devuelve un mensaje
            productoService.eliminarProducto(id);
            return ResponseEntity.ok("Producto eliminado correctamente");
        } catch (ResponseStatusException e) {
        	//Si no devuelve el error 404 NOT FOUND
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
        }
    }


  //Esta etiqueta responde a solicitudes PUT	
    @PutMapping("/{id}")
  //PathVariable que maneja solicitudes PUT con ID
    public ResponseEntity<?> actualizarProducto(@PathVariable int id, @Valid @RequestBody Producto producto) {
        //Si el producto existe lo actualiza
    	Producto actualizado = productoService.actualizarProducto(id, producto);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
        	//Si no devuelve el error 404 NOT FOUND
            return ResponseEntity.notFound().build();
        }
    }
}
