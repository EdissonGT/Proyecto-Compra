package Modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class MedidasDao {

    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;

    public List ListarMed(String valor) {
        List<Medidas> lista = new ArrayList();
        try {
            con = cn.getConexion();
            if ("".equalsIgnoreCase(valor)) {
                String sql = "SELECT * FROM medidas ORDER BY estado ASC";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
            } else {
                String sql = "SELECT * FROM medidas WHERE medida LIKE '%" + valor + "%' OR estado LIKE '%" + valor + "%'";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
            }
            while (rs.next()) {
                Medidas med = new Medidas();
                med.setId(rs.getInt("id"));
                med.setNombre(rs.getString("medida"));
                med.setNombre_corto(rs.getString("nombre_corto"));
                med.setEstado(rs.getString("estado"));
                lista.add(med);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return lista;
    }

    public boolean registrar(Medidas med) {
        String sql = "INSERT INTO medidas (medida, nombre_corto) VALUES (?,?)";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, med.getNombre());
            ps.setString(2, med.getNombre_corto());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }
    }

    public boolean modificar(Medidas med) {
        con = cn.getConexion();
        String sql = "UPDATE medidas SET medida = ?, nombre_corto = ? WHERE id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, med.getNombre());
            ps.setString(2, med.getNombre_corto());
            ps.setInt(3, med.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }
    }

    public boolean accion(String estado, int id) {

        String sql = "UPDATE medidas SET estado = ? WHERE id = ?";
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
}
