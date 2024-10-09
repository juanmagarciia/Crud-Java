<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="style.css">
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Listar Productos</title>
</head>
<body>
<%
    String mensaje = (String) request.getAttribute("mensaje");
    if(mensaje != null){%>
        <%= mensaje %>
    
    <%
    }
    %>
    <h1>Listar Productos</h1>

    <h2>Productos existentes</h2>
    <table border="1">
        <tr>
            <td>Id</td>
            <td>Nombre</td>
            <td>Cantidad</td>
            <td>Precio</td>
            <td>Fecha Creación</td>
            <td>Fecha Actualización</td>
            <td>Acción</td>
        </tr>
        <c:forEach var="producto" items="${lista}">
            <tr>
                <td>
                    <a href="productos?opcion=meditar&id=<c:out value="${producto.id}"></c:out>">
                        <c:out value="${producto.id}"></c:out>
                    </a>
                </td>
                <td><c:out value="${producto.nombre}"></c:out></td>
                <td><c:out value="${producto.cantidad}"></c:out></td>
                <td><c:out value="${producto.precio}"></c:out></td>
                <td><c:out value="${producto.fechaCrear}"></c:out></td>
                <td><c:out value="${producto.fechaActualizar}"></c:out></td>
                <td>
                    <a href="productos?opcion=eliminar&id=<c:out value="${producto.id}"></c:out>">
                        Eliminar
                    </a>
                </td>
            </tr>
        </c:forEach>
    </table>
    
    <h2>Nuevo Producto</h2>
    <form action="productos" method="POST">
        <input type="hidden" name="opcion" value="guardar" />
        <label for="nombre">Nombre:</label>
        <input type="text" name="nombre" id="nombre" required />
        <br/>
        <label for="cantidad">Cantidad:</label>
        <input type="number" name="cantidad" id="cantidad" required  />
        <br/>
        <label for="precio">Precio:</label>
        <input type="text" name="precio" id="precio" required />
        <br/>
        <input type="submit" value="Guardar Producto" />
    </form>

</body>
</html>