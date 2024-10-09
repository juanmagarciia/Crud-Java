package com.aprende.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aprende.dao.ProductoDAO;
import com.aprende.model.Producto;

/**
 * 
 * Servlet implementation class ProductoController
 * 
 */

@WebServlet(description = "administra peticiones para la tabla productos", urlPatterns = { "/productos" })

public class ProductoController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ProductoController() {

		super();

	}
	
	/**
     * Maneja las solicitudes GET enviadas al servlet. Procesa acciones como 
     * crear, listar, editar (meditar) y eliminar productos según el valor del 
     * parámetro "opcion".
     * 
     * @param request Objeto {@link HttpServletRequest} que contiene la solicitud del cliente.
     * @param response Objeto {@link HttpServletResponse} que contiene la respuesta al cliente.
     * @throws ServletException Si ocurre un error relacionado con el servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)

			throws ServletException, IOException {

		String opcion = request.getParameter("opcion");

		if (opcion.equals("crear")) {

			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/crear.jsp");

			requestDispatcher.forward(request, response);

		} else if (opcion.equals("listar")) {

			ProductoDAO productoDAO = new ProductoDAO();

			List<Producto> lista = new ArrayList<>();

			try {

				lista = productoDAO.obtenerProductos();

				request.setAttribute("lista", lista);

				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/listar.jsp");

				requestDispatcher.forward(request, response);

			} catch (SQLException e) {

				e.printStackTrace();

				request.setAttribute("mensaje", "Error al obtener productos");

				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/listar.jsp");

				requestDispatcher.forward(request, response);

			}

		} else if (opcion.equals("meditar")) {

			int id = Integer.parseInt(request.getParameter("id"));

			ProductoDAO productoDAO = new ProductoDAO();

			Producto p = new Producto();

			try {

				p = productoDAO.obtenerProducto(id);

				request.setAttribute("producto", p);

				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/editar.jsp");

				requestDispatcher.forward(request, response);

			} catch (SQLException e) {

				e.printStackTrace();

				request.setAttribute("mensaje", "Error al obtener el producto");

				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/editar.jsp");

				requestDispatcher.forward(request, response);

			}

		} else if (opcion.equals("eliminar")) {

			ProductoDAO productoDAO = new ProductoDAO();

			int id = Integer.parseInt(request.getParameter("id"));

			try {

				productoDAO.eliminar(id); // Eliminar el producto de la base de datos

				// Actualizar la lista de productos después de la eliminación

				List<Producto> listaActualizada = productoDAO.obtenerProductos();

				request.setAttribute("lista", listaActualizada);

				// Agregar mensaje de éxito

				request.setAttribute("mensaje", "Producto eliminado correctamente.");

				// Redirigir nuevamente a la página listar.jsp con la lista actualizada

				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/listar.jsp");

				requestDispatcher.forward(request, response);

			} catch (SQLException e) {

				e.printStackTrace();

				request.setAttribute("mensaje", "Error al eliminar el producto");

				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/listar.jsp");

				requestDispatcher.forward(request, response);

			}

		}

	}
	
	/**
     * Maneja las solicitudes POST enviadas al servlet. Procesa acciones como 
     * guardar y editar productos según el valor del parámetro "opcion".
     * 
     * <p>Realiza validaciones de campos para asegurarse de que los valores como 
     * el nombre, cantidad y precio sean correctos antes de proceder con las 
     * operaciones de base de datos.
     * 
     * @param request Objeto {@link HttpServletRequest} que contiene la solicitud del cliente.
     * @param response Objeto {@link HttpServletResponse} que contiene la respuesta al cliente.
     * @throws ServletException Si ocurre un error relacionado con el servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)

			throws ServletException, IOException {

		String opcion = request.getParameter("opcion");

		Date fechaActual = new Date();

		if (opcion.equals("guardar")) {

			ProductoDAO productoDAO = new ProductoDAO();

			Producto producto = new Producto();

			String nombre = request.getParameter("nombre");

			String cantidadStr = request.getParameter("cantidad");

			String precioStr = request.getParameter("precio");

			// Validación de campos no nulos ni vacíos

			if (nombre == null || nombre.trim().isEmpty()) {

				request.setAttribute("mensaje", "El nombre no puede estar vacío");

				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/crear.jsp");

				requestDispatcher.forward(request, response);

				return;

			}

			if (cantidadStr == null || cantidadStr.trim().isEmpty()) {

				request.setAttribute("mensaje", "La cantidad no puede estar vacía");

				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/crear.jsp");

				requestDispatcher.forward(request, response);

				return;

			}

			if (precioStr == null || precioStr.trim().isEmpty()) {

				request.setAttribute("mensaje", "El precio no puede estar vacío");

				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/crear.jsp");

				requestDispatcher.forward(request, response);

				return;

			}

			// Validación de formato numérico

			double cantidad;

			double precio;

			try {

				cantidad = Double.parseDouble(cantidadStr);

			} catch (NumberFormatException e) {

				request.setAttribute("mensaje", "La cantidad debe ser un número válido");

				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/crear.jsp");

				requestDispatcher.forward(request, response);

				return;

			}

			try {

				precio = Double.parseDouble(precioStr);

			} catch (NumberFormatException e) {

				request.setAttribute("mensaje", "El precio debe ser un número válido");

				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/crear.jsp");

				requestDispatcher.forward(request, response);

				return;

			}

			// Seteo de valores

			producto.setNombre(nombre);

			producto.setCantidad(cantidad);

			producto.setPrecio(precio);

			producto.setFechaCrear(new java.sql.Timestamp(fechaActual.getTime()));

			try {

				if (productoDAO.guardar(producto)) {

					request.setAttribute("mensaje", "Registro guardado satisfactoriamente");

				} else {

					request.setAttribute("mensaje", "Error: el producto ya existe");

				}

			} catch (SQLException e) {

				e.printStackTrace();

				request.setAttribute("mensaje", "Error al acceder a la base de datos");

			}

			// Obtener la lista actualizada de productos para mostrarla en la vista

			try {

				List<Producto> listaActualizada = productoDAO.obtenerProductos();

				request.setAttribute("lista", listaActualizada);

			} catch (SQLException e) {

				e.printStackTrace();

				request.setAttribute("mensaje", "Error al obtener la lista de productos");

			}

			// Redirigir a la vista de listado

			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/listar.jsp");

			requestDispatcher.forward(request, response);

		} else if (opcion.equals("editar")) {

			Producto producto = new Producto();
			ProductoDAO productoDAO = new ProductoDAO();

			String idStr = request.getParameter("id");
			String nombre = request.getParameter("nombre");
			String cantidadStr = request.getParameter("cantidad");
			String precioStr = request.getParameter("precio");

			// Validación de campos no nulos ni vacíos
			if (nombre == null || nombre.trim().isEmpty()) {
				request.setAttribute("mensaje", "El nombre no puede estar vacío");
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/editar.jsp");
				requestDispatcher.forward(request, response);
				return;
			}

			if (cantidadStr == null || cantidadStr.trim().isEmpty()) {
				request.setAttribute("mensaje", "La cantidad no puede estar vacía");
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/editar.jsp");
				requestDispatcher.forward(request, response);
				return;
			}

			if (precioStr == null || precioStr.trim().isEmpty()) {
				request.setAttribute("mensaje", "El precio no puede estar vacío");
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/editar.jsp");
				requestDispatcher.forward(request, response);
				return;
			}

			// Validación de formato numérico
			double cantidad;
			double precio;
			int id;

			try {
				id = Integer.parseInt(idStr);
			} catch (NumberFormatException e) {
				request.setAttribute("mensaje", "El ID del producto debe ser un número válido");
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/editar.jsp");
				requestDispatcher.forward(request, response);
				return;
			}

			try {
				cantidad = Double.parseDouble(cantidadStr);
			} catch (NumberFormatException e) {
				request.setAttribute("mensaje", "La cantidad debe ser un número válido");
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/editar.jsp");
				requestDispatcher.forward(request, response);
				return;
			}

			try {
				precio = Double.parseDouble(precioStr);
			} catch (NumberFormatException e) {
				request.setAttribute("mensaje", "El precio debe ser un número válido");
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/editar.jsp");
				requestDispatcher.forward(request, response);
				return;
			}

			// Seteo de valores
			producto.setId(id);
			producto.setNombre(nombre);
			producto.setCantidad(cantidad);
			producto.setPrecio(precio);
			producto.setFechaActualizar(new java.sql.Timestamp(fechaActual.getTime()));

			try {
				boolean exito = productoDAO.editar(producto);
				if (exito) {
					request.setAttribute("mensaje", "Registro editado satisfactoriamente");
				}else {
					request.setAttribute("mensaje", "No se ha podido editar el producto");}
				// Obtener la lista actualizada de productos
				List<Producto> listaActualizada = productoDAO.obtenerProductos();
				request.setAttribute("lista", listaActualizada);

				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/listar.jsp");
				requestDispatcher.forward(request, response);

			} catch (SQLException e) {
				e.printStackTrace();
				request.setAttribute("mensaje", "Error al acceder a la base de datos");
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/editar.jsp");
				requestDispatcher.forward(request, response);
			}
		}
	}
}