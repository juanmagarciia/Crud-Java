package com.aprende.model;

import java.sql.Timestamp;

public class Producto {

	private int id;
	private String nombre;
	private double cantidad;
	private double precio;
	private Timestamp fechaCrear;
	private Timestamp fechaActualizar;

	/**
	 * Constructor completo para inicializar un producto con todos sus atributos.
	 * 
	 * @param id              El identificador único del producto.
	 * @param nombre          El nombre del producto.
	 * @param cantidad        La cantidad disponible del producto.
	 * @param precio          El precio del producto.
	 * @param fechaCrear      La fecha de creación del producto.
	 * @param fechaActualizar La fecha de la última actualización del producto.
	 */

	public Producto(int id, String nombre, double cantidad, double precio, Timestamp fechaCrear,
			Timestamp fechaActualizar) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.precio = precio;
		this.fechaCrear = fechaCrear;
		this.fechaActualizar = fechaActualizar;
	}

	/**
	 * Constructor por defecto.
	 */
	public Producto() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Obtiene el ID del producto.
	 * 
	 * @return El ID del producto.
	 */

	public int getId() {
		return id;
	}

	/**
	 * Establece el ID del producto.
	 * 
	 * @param id El nuevo ID del producto.
	 */

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Obtiene el nombre del producto.
	 * 
	 * @return El nombre del producto.
	 */

	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del producto.
	 * 
	 * @param nombre El nuevo nombre del producto.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene la cantidad disponible del producto.
	 * 
	 * @return La cantidad disponible del producto.
	 */

	public double getCantidad() {
		return cantidad;
	}

	/**
	 * Establece la cantidad disponible del producto.
	 * 
	 * @param cantidad La nueva cantidad disponible del producto.
	 */

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * Obtiene el precio del producto.
	 * 
	 * @return El precio del producto.
	 */

	public double getPrecio() {
		return precio;
	}

	/**
	 * Establece el precio del producto.
	 * 
	 * @param precio El nuevo precio del producto.
	 */
	public void setPrecio(double precio) {
		this.precio = precio;
	}

	/**
	 * Obtiene la fecha de creación del producto.
	 * 
	 * @return La fecha de creación del producto.
	 */

	public Timestamp getFechaCrear() {
		return fechaCrear;
	}

	/**
	 * Establece la fecha de creación del producto.
	 * 
	 * @param fechaCrear La nueva fecha de creación del producto.
	 */
	public void setFechaCrear(Timestamp fechaCrear) {
		this.fechaCrear = fechaCrear;
	}

	/**
	 * Obtiene la fecha de la última actualización del producto.
	 * 
	 * @return La fecha de la última actualización del producto.
	 */

	public Timestamp getFechaActualizar() {
		return fechaActualizar;
	}

	/**
	 * Establece la fecha de la última actualización del producto.
	 * 
	 * @param fechaActualizar La nueva fecha de actualización del producto.
	 */

	public void setFechaActualizar(Timestamp fechaActualizar) {
		this.fechaActualizar = fechaActualizar;
	}

	/**
	 * Representa el producto como una cadena de texto mostrando sus atributos.
	 * 
	 * @return Una cadena de texto con los atributos del producto.
	 */

	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", cantidad=" + cantidad + ", precio=" + precio
				+ ", fechaCrear=" + fechaCrear + ", fechaActualizar=" + fechaActualizar + "]";
	}
}
