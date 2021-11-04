
package Controles;

import Modelos.Tables;
import Modelos.UsuarioDao;
import Modelos.Usuarios;
import Vistas.PanelAdmin;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;


public class UsuariosControladores implements ActionListener, MouseListener, KeyListener{

    private Usuarios us;
    private UsuarioDao usDao;
    private PanelAdmin views;
    
    DefaultTableModel modelo = new DefaultTableModel();

    public UsuariosControladores(Usuarios us, UsuarioDao usDao, PanelAdmin views) {
        this.us = us;
        this.usDao = usDao;
        this.views = views;
        this.views.btnRegistrarUser.addActionListener(this);
        this.views.btnModificarUser.addActionListener(this);
        this.views.JMenuEliminarUsers.addActionListener(this);
        this.views.JMenuReingresarUsers.addActionListener(this);
        this.views.btnNuevoUser.addActionListener(this);
        this.views.txtBuscarUsers.addKeyListener(this);
        this.views.TableUsers.addMouseListener(this);
        this.views.JLabelUsers.addMouseListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btnRegistrarUser){
            if (views.txtUsuarioUser.getText().equals("")
                    || views.txtNombreUser.getText().equals("")
                    || String.valueOf(views.txtClaveUser.getPassword()).equals("")){
                JOptionPane.showMessageDialog(null, "TODO LOS CAMPOS SON OBLIGATORIOS");
            } else {
            us.setUsuario(views.txtUsuarioUser.getText());
            us.setNombre(views.txtNombreUser.getText());
            us.setClave(String.valueOf(views.txtClaveUser.getPassword()));
            us.setCaja(views.cbxCajaUser.getSelectedItem().toString());
            us.setRol(views.cbxRolUser.getSelectedItem().toString());
            if (usDao.registrar(us)){
                limpiarTable();
                listarUsuarios();
                limpiar();
                JOptionPane.showMessageDialog(null, "SE REGISTRO EL USUARIO EXITOSAMENTE");
            } else {
                JOptionPane.showMessageDialog(null, "ERROR AL REGISTRAR EL USUARIO");
            }
            }
        }else if (e.getSource() == views.btnModificarUser){
            if (views.txtUsuarioUser.getText().equals("")
                    || views.txtNombreUser.getText().equals("")){
                JOptionPane.showMessageDialog(null, "TODO LOS CAMPOS SON OBLIGATORIOS");
            } else {
            us.setUsuario(views.txtUsuarioUser.getText());
            us.setNombre(views.txtNombreUser.getText());
            us.setCaja(views.cbxCajaUser.getSelectedItem().toString());
            us.setRol(views.cbxRolUser.getSelectedItem().toString());
            us.setId(Integer.parseInt(views.txtIdUser.getText()));
            if (usDao.modificar(us)){
                limpiarTable();
                listarUsuarios();
                limpiar();
                JOptionPane.showMessageDialog(null, "SE MODIFICO EL USUARIO EXITOSAMENTE");
            } else {
                JOptionPane.showMessageDialog(null, "ERROR AL MOFICIAR EL USUARIO");
                }
            }
        }else if(e.getSource() == views.JMenuEliminarUsers){
            if(views.txtIdUser.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Seleccione una fila para eliminar");
            }else {
                int id = Integer.parseInt(views.txtIdUser.getText());
                if (usDao.accion("Inactivo", id)){
                    limpiarTable();
                    listarUsuarios();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "USUARIO ELIMINADO");
                }else {
                    JOptionPane.showMessageDialog(null, "Error al eliminar Usuario");
                }
            }
        }else if(e.getSource() == views.JMenuReingresarUsers){
            if(views.txtIdUser.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Seleccione una fila para Reingresar");
            }else {
                int id = Integer.parseInt(views.txtIdUser.getText());
                if (usDao.accion("Activo", id)){
                    limpiarTable();
                    listarUsuarios();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "USUARIO REINGRESADO");
                }else {
                    JOptionPane.showMessageDialog(null, "Error al eliminar Usuario");
                }
            }
        }else{
            limpiar();
        }
    }
    
    public void listarUsuarios(){
        Tables color = new Tables();
        views.TableUsers.setDefaultRenderer(views.TableUsers.getColumnClass(0), color);
        List<Usuarios> lista = usDao.ListaUsuarios(views.txtBuscarUsers.getText());
        modelo = (DefaultTableModel) views.TableUsers.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < lista.size(); i++) {
            ob[0] = lista.get(i).getId();
            ob[1] = lista.get(i).getUsuario();
            ob[2] = lista.get(i).getNombre();
            ob[3] = lista.get(i).getCaja();
            ob[4] = lista.get(i).getRol();
            ob[5] = lista.get(i).getEstado();
            modelo.addRow(ob);
            
        }
        views.TableUsers.setModel(modelo);
        JTableHeader header = views.TableUsers.getTableHeader();
        header.setOpaque(false);
        header.setBackground(Color.blue);
        header.setForeground(Color.white);
    }

    public void limpiarTable(){
        for (int i = 0; i < modelo.getRowCount(); i++){
            modelo.removeRow(i);
            i = i - 1;
        }
    }
    
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.TableUsers){
            int fila = views.TableUsers.rowAtPoint(e.getPoint());
            views.txtIdUser.setText(views.TableUsers.getValueAt(fila, 0).toString());
            views.txtUsuarioUser.setText(views.TableUsers.getValueAt(fila, 1).toString());
            views.txtNombreUser.setText(views.TableUsers.getValueAt(fila, 2).toString());
            views.cbxCajaUser.setSelectedItem(views.TableUsers.getValueAt(fila, 3).toString());
            views.cbxRolUser.setSelectedItem(views.TableUsers.getValueAt(fila, 4).toString());

        }else if (e.getSource() == views.JLabelUsers){
                 views.jTabbedPane1.setSelectedIndex(3);
                 limpiarTable();
                 listarUsuarios();
             }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == views.txtBuscarUsers){
            limpiarTable();
            listarUsuarios();
        }
    }
    private void limpiar(){
        views.txtIdUser.setText("");
        views.txtUsuarioUser.setText("");
        views.txtNombreUser.setText("");
        views.txtClaveUser.setText("");

    }
}

