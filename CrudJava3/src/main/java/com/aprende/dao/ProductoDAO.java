package com.aprende.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aprende.conexion.Conexion;
import com.aprende.model.Producto;

public class ProductoDAO {

	private Connection connection;
	private PreparedStatement statement;
	private boolean estadoOperacion;
	
	/**
     * Guarda un nuevo producto en la base de datos.
     * Primero verifica si el producto ya existe basado en el nombre.
     * Si el producto ya existe, no se realiza la inserción.
     * 
     * @param producto El producto a guardar.
     * @return true si el producto fue guardado con éxito, false si el producto ya existe.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */

	// guardar producto
	public boolean guardar(Producto producto) throws SQLException {
		String sql = null;
		estadoOperacion = false;
		connection = obtenerConexion();

		try {
			connection.setAutoCommit(false);

			// Verificar si el producto ya existe basado en el nombre
			String sqlVerificar = "SELECT COUNT(*) FROM productos WHERE nombre = ?";
			PreparedStatement verificarStmt = connection.prepareStatement(sqlVerificar);
			verificarStmt.setString(1, producto.getNombre());

			ResultSet rs = verificarStmt.executeQuery();
			rs.next();
			int count = rs.getInt(1); // Obtenemos el número de coincidencias por nombre

			if (count > 0) {
				// Si el producto ya existe, devolvemos false o podemos realizar otra acción
				// como una actualización
				System.out.println("El producto con nombre '" + producto.getNombre() + "' ya existe.");
				estadoOperacion = false;
			} else {
				// Si el producto no existe, procedemos a insertarlo
				sql = "INSERT INTO productos (nombre, cantidad, precio, fecha_crear, fecha_actualizar) VALUES(?,?,?,?,?)";
				statement = connection.prepareStatement(sql);

				statement.setString(1, producto.getNombre());
				statement.setDouble(2, producto.getCantidad());
				statement.setDouble(3, producto.getPrecio());
				statement.setTimestamp(4, producto.getFechaCrear());
				statement.setTimestamp(5, producto.getFechaActualizar());

				estadoOperacion = statement.executeUpdate() > 0;

				if (estadoOperacion) {
					System.out.println("Producto insertado correctamente.");
				}
			}

			connection.commit();
			verificarStmt.close();
			if (statement != null)
				statement.close();
			connection.close();
		} catch (SQLException e) {
			connection.rollback();
			System.out.println(e.getMessage());
		}

		return estadoOperacion;
	}
	
	/**
     * Edita un producto existente en la base de datos.
     * Verifica que no exista otro producto con el mismo nombre antes de actualizar.
     * 
     * @param producto El producto con los nuevos datos a actualizar.
     * @return true si el producto fue actualizado con éxito, false si ya existe un producto con el mismo nombre.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */

	// editar producto
	public boolean editar(Producto producto) throws SQLException {
		String sql = null;
		estadoOperacion = false;
		connection = obtenerConexion();
		String sqlVerificar = "SELECT COUNT(*) FROM productos WHERE nombre = ? and id != ?";
		PreparedStatement verificarStmt = connection.prepareStatement(sqlVerificar);
		verificarStmt.setString(1, producto.getNombre());
		verificarStmt.setInt(2, producto.getId());

		ResultSet rs = verificarStmt.executeQuery();
		rs.next();
		int count = rs.getInt(1); // Obtenemos el número de coincidencias por nombre

		if (count > 0) {
			// Si el producto ya existe, devolvemos false o podemos realizar otra acción
			// como una actualización
			System.out.println("El producto con nombre '" + producto.getNombre() + "' ya existe.");
			estadoOperacion = false;
		} else {
			try {
				connection.setAutoCommit(false);
				sql = "UPDATE productos SET nombre=?, cantidad=?, precio=?, fecha_actualizar=? WHERE id=?";
				statement = connection.prepareStatement(sql);

				statement.setString(1, producto.getNombre());
				statement.setDouble(2, producto.getCantidad());
				statement.setDouble(3, producto.getPrecio());
				statement.setTimestamp(4, producto.getFechaActualizar());
				statement.setInt(5, producto.getId());

				estadoOperacion = statement.executeUpdate() > 0;
				connection.commit();
				statement.close();
				connection.close();

			} catch (SQLException e) {
				connection.rollback();
				e.printStackTrace();
			}

			
		}
		return estadoOperacion;
	}
	
	/**
     * Elimina un producto de la base de datos basado en su ID.
     * 
     * @param idProducto El ID del producto a eliminar.
     * @return true si el producto fue eliminado con éxito, false si hubo un error.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */

	// eliminar producto
	public boolean eliminar(int idProducto) throws SQLException {
		String sql = null;
		estadoOperacion = false;
		connection = obtenerConexion();
		try {
			connection.setAutoCommit(false);
			sql = "DELETE FROM productos WHERE id=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, idProducto);

			estadoOperacion = statement.executeUpdate() > 0;
			connection.commit();
			statement.close();
			connection.close();

		} catch (SQLException e) {
			connection.rollback();
			e.printStackTrace();
		}

		return estadoOperacion;
	}
	
	/**
     * Obtiene una lista de todos los productos almacenados en la base de datos.
     * 
     * @return Una lista de productos.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */

	// obtener lista de productos
	public List<Producto> obtenerProductos() throws SQLException {
		ResultSet resultSet = null;
		List<Producto> listaProductos = new ArrayList<>();

		String sql = null;
		estadoOperacion = false;
		connection = obtenerConexion();

		try {
			sql = "SELECT * FROM productos";
			statement = connection.prepareStatement(sql);
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				Producto p = new Producto();
				p.setId(resultSet.getInt(1));
				p.setNombre(resultSet.getString(2));
				p.setCantidad(resultSet.getDouble(3));
				p.setPrecio(resultSet.getDouble(4));
				p.setFechaCrear(resultSet.getTimestamp(5));
				p.setFechaActualizar(resultSet.getTimestamp(6));
				listaProductos.add(p);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listaProductos;
	}
	
	/**
     * Obtiene un producto basado en su ID.
     * 
     * @param idProducto El ID del producto a buscar.
     * @return El producto si se encuentra en la base de datos, null si no se encuentra.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */

	// obtener producto
	public Producto obtenerProducto(int idProducto) throws SQLException {
		ResultSet resultSet = null;
		Producto p = new Producto();

		String sql = null;
		estadoOperacion = false;
		connection = obtenerConexion();

		try {
			sql = "SELECT * FROM productos WHERE id =?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, idProducto);

			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				p.setId(resultSet.getInt(1));
				p.setNombre(resultSet.getString(2));
				p.setCantidad(resultSet.getDouble(3));
				p.setPrecio(resultSet.getDouble(4));
				p.setFechaCrear(resultSet.getTimestamp(5));
				p.setFechaActualizar(resultSet.getTimestamp(6));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return p;
	}
	
	/**
     * Establece una conexión con la base de datos utilizando un pool de conexiones.
     * 
     * @return La conexión a la base de datos.
     * @throws SQLException Si ocurre un error al establecer la conexión.
     */

	// obtener conexion pool
	private Connection obtenerConexion() throws SQLException {
		return Conexion.getConnection();
	}
}
