package com.productosDB.gestion_productos;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

//Es una clase de servicio lo que indica que SpringBoot la gestiona como un componente de la app
//Se encarga de interactuar con el repositorio y aplicar las reglas del negecio antes de devolver 
//Los datos al controlador

//Lo marca como un servicio de SpringBoot y que le inyecte sus dependencias automaticamente
@Service
public class ProductoService {

	//Se le inyectan sus servicios atraves del constructor
//Esto permite que ProductoService acceda a la BD sin instanciar un ProductoRepository manualmente
    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    //Usa el metodo FindAll para devolver todos los productos
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    //Usa el metodo FindById para obtener un producto especifico
    public Producto obtenerProductoPorId(int id) {
        return productoRepository.findById(id)
        		//Si no lo encuentra devuelve un error
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
    }

   //Guarda un producto con el metodo save
    public Producto agregarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    //Usa el metodo existByID para busca un producto especifico y eliminarlo
    //Con el metedo deleteById
    public void eliminarProducto(int id) {
        if (!productoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado");
        }
        productoRepository.deleteById(id);
    }
    
    //Busca un producto por su Id y si lo encuentra
    public Producto actualizarProducto(int id, Producto producto) {
        return productoRepository.findById(id).map(p -> {
        	//Actualiza los datos
            p.setNombre(producto.getNombre());
            p.setPrecio(producto.getPrecio());
            return productoRepository.save(p);
            //si no retorna null
        }).orElse(null);
    }

}

