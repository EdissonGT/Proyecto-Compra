package Modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ProductosDao {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public boolean registrar(Productos pro) {
        String sql = "INSERT INTO productos (codigo, descripcion, precio_compra, precio_venta, id_proveedor, id_medida, id_categoria) VALUES (?,?,?,?,?,?,?)";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, pro.getCodigo());
            ps.setString(2, pro.getDescripcion());
            ps.setDouble(3, pro.getPrecio_compra());
            ps.setDouble(4, pro.getPrecio_venta());
            ps.setInt(5, pro.getId_proveedor());
            ps.setInt(6, pro.getId_medida());
            ps.setInt(7, pro.getId_categoria());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }
    }

    public List ListaProductos(String valor) {
        List<Productos> listaPro = new ArrayList();
        String sql = "SELECT * FROM productos ORDER BY estado";
        String buscar = "SELECT * FROM productos WHERE codigo LIKE '%" + valor + "%' OR descripcion LIKE '%" + valor + "%'";
        try {
            con = cn.getConexion();
            if (valor.equalsIgnoreCase("")) {
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
            } else {
                ps = con.prepareStatement(buscar);
                rs = ps.executeQuery();
            }

            while (rs.next()) {
                Productos pro = new Productos();
                pro.setId(rs.getInt("id"));
                pro.setCodigo(rs.getString("codigo"));
                pro.setDescripcion(rs.getString("descripcion"));
                pro.setPrecio_venta(rs.getInt("precio_venta"));
                pro.setCantidad(rs.getInt("cantidad"));
                pro.setEstado(rs.getString("estado"));
                listaPro.add(pro);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return listaPro;
    }

    public boolean modificar(Productos pro) {
        String sql = "UPDATE productos SET codigo = ?, descripcion = ?, precio_compra = ?, precio_venta = ?, id_proveedor = ?, id_medida = ?, id_categoria = ? WHERE id = ?";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, pro.getCodigo());
            ps.setString(2, pro.getDescripcion());
            ps.setDouble(3, pro.getPrecio_compra());
            ps.setDouble(4, pro.getPrecio_venta());
            ps.setInt(5, pro.getId_proveedor());
            ps.setInt(6, pro.getId_medida());
            ps.setInt(7, pro.getId_categoria());
            ps.setInt(8, pro.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }
    }

    public boolean accion(String estado, int id) {
        String sql = "UPDATE productos SET estado = ? WHERE id = ?";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, estado);
            ps.setInt(2, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }
    }

    public Productos buscarPro(int id) {
        String sql = "SELECT p.*, pr.id, pr.proveedor, m.id, m.medida, c.id, c.categoria FROM productos p INNER JOIN proveedor pr ON p.id_proveedor = pr.id INNER JOIN medidas m ON p.id_medida = m.id INNER JOIN categorias c ON p.id_categoria = c.id WHERE p.id = ?";
        Productos pro = new Productos();
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                pro.setCodigo(rs.getString("codigo"));
                pro.setDescripcion(rs.getString("descripcion"));
                pro.setPrecio_compra(rs.getDouble("precio_compra"));
                pro.setPrecio_venta(rs.getDouble("precio_venta"));
                pro.setId_proveedor(rs.getInt("id_proveedor"));
                pro.setId_medida(rs.getInt("id_medida"));
                pro.setId_categoria(rs.getInt("id_categoria"));
                pro.setProveedor(rs.getString("proveedor"));
                pro.setMedida(rs.getString("medida"));
                pro.setCat(rs.getString("categoria"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return pro;
    }

    public Productos buscarCodigo(String codigo) {
        String sql = "SELECT * FROM productos WHERE codigo = ?";
        Productos pro = new Productos();
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, codigo);
            rs = ps.executeQuery();
            if (rs.next()) {
                pro.setId(rs.getInt("id"));
                pro.setDescripcion(rs.getString("descripcion"));
                pro.setPrecio_compra(rs.getDouble("precio_compra"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return pro;
    }

    public Productos buscarId(int id) {
        String sql = "SELECT * FROM productos WHERE id = ?";
        Productos pro = new Productos();
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                pro.setCantidad(rs.getInt("cantidad"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return pro;
    }

    public boolean registrarCompra(int id, String total) {
        String sql = "INSERT INTO compras (id_proveedor, total) VALUES (?,?)";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, total);
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    public boolean registrarCompraDetalle(int id_compra, int id, double precio, int cantidad, double sub_total) {
        String sql = "INSERT INTO detalle_compra (id_compra, id_producto, precio, cantidad, subtotal) VALUES (?,?,?,?,?)";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_compra);
            ps.setInt(2, id);
            ps.setDouble(3, precio);
            ps.setInt(4, cantidad);
            ps.setDouble(5, sub_total);
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    public boolean ActualizarStock(int cant, int id) {
        String sql = "UPDATE productos SET cantidad = ? WHERE id = ?";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, cant);
            ps.setInt(2, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }
    }

    public int id_compra() {
        int id = 0;
        String sql = "SELECT MAX(id) AS id FROM compras";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    public List ListaDetalle(int id_compra) {
        List<Productos> listaPro = new ArrayList();
        String sql = "SELECT c.*, d.*, p.id, p.descripcion FROM compras c INNER JOIN detalle_compra d ON d.id_compra = c.id INNER JOIN productos p ON p.id = d.id_producto WHERE c.id = ?";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_compra);
            rs = ps.executeQuery();
            while (rs.next()) {
                Productos pro = new Productos();
                pro.setCantidad(rs.getInt("cantidad"));
                pro.setDescripcion(rs.getString("descripcion"));
                pro.setPrecio_compra(rs.getDouble("precio"));
                pro.setPrecio_venta(rs.getDouble("subtotal"));
                listaPro.add(pro);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return listaPro;
    }
    
    public Productos buscarCodigoVenta(String codigo) {
        String sql = "SELECT * FROM productos WHERE codigo = ?";
        Productos pro = new Productos();
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, codigo);
            rs = ps.executeQuery();
            if (rs.next()) {
                pro.setId(rs.getInt("id"));
                pro.setDescripcion(rs.getString("descripcion"));
                pro.setPrecio_compra(rs.getDouble("precio_compra"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return pro;
    }
}
