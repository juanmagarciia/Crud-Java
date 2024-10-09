package com.aprende.conexion;

import java.sql.Connection;
import java.sql.SQLException;
 
import javax.sql.DataSource;
 
import org.apache.commons.dbcp2.BasicDataSource;

public class Conexion {
	private static BasicDataSource dataSource = null;
	 
	/**
     * Obtiene el DataSource que gestiona el pool de conexiones. Si no existe, lo inicializa.
     * 
     * @return Un DataSource configurado para gestionar las conexiones a la base de datos.
     */
	 private static DataSource getDataSource() {
	  if (dataSource == null) {
	   dataSource = new BasicDataSource();
	   dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
	   dataSource.setUsername("root");
	   dataSource.setPassword("1234");
	   dataSource.setUrl("jdbc:mysql://localhost:3306/crud?useTimezone=true&serverTimezone=UTC");
	   dataSource.setInitialSize(20);
	   dataSource.setMaxIdle(15);
	   dataSource.setMaxTotal(20);
	   dataSource.setMaxWaitMillis(5000);
	  }
	  return dataSource;
	 }
	 
	 /**
	     * Proporciona una nueva conexión a la base de datos desde el pool de conexiones.
	     * 
	     * @return Una conexión {@link Connection} a la base de datos.
	     * @throws SQLException Si ocurre un error al obtener la conexión.
	     */
	 public static Connection getConnection() throws SQLException {
	  return getDataSource().getConnection();
	 }
}
