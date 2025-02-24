package com.productosDB.gestion_productos;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

//Creacion de la entidad productos

@Entity
//tabla donde estara mapeada la entidad
@Table(name = "productos")
public class Producto {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

	//notBlank evita los null o cadenas de texto vacias
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor que 0")
    private Double precio;

    //mapea el atributo en la columna fecha de creacion
    //evita que se pueda modificar
    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    //se ejecuta antes de que guarde la entidad en la base de datos
    @PrePersist
    protected void onCreate() {
    	//Le da el valor de la fecha actual al momento de crear el producto
        this.fechaCreacion = LocalDateTime.now();
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", precio=" + precio + ", fechaCreacion=" + fechaCreacion
				+ "]";
	}

    
}

