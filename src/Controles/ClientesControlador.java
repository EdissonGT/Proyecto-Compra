
package Controles;

import Modelos.Clientes;
import Modelos.ClientesDao;
import Modelos.Tables;
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


public class ClientesControlador implements ActionListener, MouseListener, KeyListener{
    private Clientes cl;
    private ClientesDao clDao;
    private PanelAdmin views;
    DefaultTableModel modelo = new DefaultTableModel();
    
    public ClientesControlador(Clientes cl, ClientesDao clDao, PanelAdmin views) {
        this.cl = cl;
        this.clDao = clDao;
        this.views = views;
        this.views.btnRegistrarCliente.addActionListener(this);
        this.views.btnModificarCli.addActionListener(this);
        this.views.btnNuevoCli.addActionListener(this);
        this.views.TableClientes.addMouseListener(this);
        this.views.JMenuEliminarCli.addActionListener(this);
        this.views.JMenuReingresarCli.addActionListener(this);
        this.views.txtBuscarCli.addKeyListener(this);
        this.views.JLabelClientes.addMouseListener(this);
        listarClientes();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btnRegistrarCliente){
            if (views.txtNombreCli.getText().equals("")
                    || views.txtTelefonoCli.getText().equals("")
                    || views.txtDireccionCli.getText().equals("")){
                JOptionPane.showMessageDialog(null, "TODOS LOS CAMPOS SON OBLIGATORIOS");
            } else {
                cl.setNombre(views.txtNombreCli.getText());
                cl.setTelefono(views.txtTelefonoCli.getText());
                cl.setDireccion(views.txtDireccionCli.getText());
                if(clDao.registrar(cl)){
                    limpiarTable();
                    listarClientes();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "CLIENTE REGISTRADO EXITOSAMENTE");
                } else {
                    JOptionPane.showMessageDialog(null, "ERROR AL REGISTRAR EL CLIENTE");
            }
            }
        } else if (e.getSource() == views.btnModificarCli){
            if (views.txtIdCli.getText().equals("")){
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                if (views.txtNombreCli.getText().equals("")
                    || views.txtTelefonoCli.getText().equals("")
                    || views.txtDireccionCli.getText().equals("")){
                JOptionPane.showMessageDialog(null, "TODOS LOS CAMPOS SON OBLIGATORIOS");
            } else {
                cl.setNombre(views.txtNombreCli.getText());
                cl.setTelefono(views.txtTelefonoCli.getText());
                cl.setDireccion(views.txtDireccionCli.getText());
                cl.setId(Integer.parseInt(views.txtIdCli.getText()));
                if(clDao.modificar(cl)){
                    limpiarTable();
                    listarClientes();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "CLIENTE MODIFICADO EXITOSAMENTE");
                } else {
                    JOptionPane.showMessageDialog(null, "ERROR AL MODIFICAR EL CLIENTE");
            }
            }

            }
        } else if (e.getSource() == views.JMenuEliminarCli){
            if (views.txtIdCli.getText().equals("")){
                JOptionPane.showMessageDialog(null, "SELECCIONA UNA FILA");
            } else {
                int id = Integer.parseInt(views.txtIdCli.getText());
                if (clDao.accion("Inactivo", id)){
                    limpiarTable();
                    listarClientes();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "CLIENTE BORRADO");
                }else {
                    JOptionPane.showMessageDialog(null, "ERROR AL BORRAR CLIENTE");
                }
            }
        }else if (e.getSource() == views.JMenuReingresarCli){
            if (views.txtIdCli.getText().equals("")){
                JOptionPane.showMessageDialog(null, "SELECCIONA UNA FILA");
            } else {
                int id = Integer.parseInt(views.txtIdCli.getText());
                if (clDao.accion("Activo", id)){
                    limpiarTable();
                    listarClientes();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "CLIENTE MODIFICADO");
                }else {
                    JOptionPane.showMessageDialog(null, "ERROR AL MODIFICAR CLIENTE");
                }
            }
        } else{
            limpiar();
        }
    }
        public void listarClientes(){
        Tables color = new Tables();
        views.TableClientes.setDefaultRenderer(views.TableClientes.getColumnClass(0), color);
        List<Clientes> lista = clDao.ListaClientes(views.txtBuscarCli.getText());
        modelo = (DefaultTableModel) views.TableClientes.getModel();
        Object[] ob = new Object[5];
        for (int i = 0; i < lista.size(); i++) {
            ob[0] = lista.get(i).getId();
            ob[1] = lista.get(i).getNombre();
            ob[2] = lista.get(i).getTelefono();
            ob[3] = lista.get(i).getDireccion();
            ob[4] = lista.get(i).getEstado();
            modelo.addRow(ob);
            
        }
        views.TableClientes.setModel(modelo);
        JTableHeader header = views.TableClientes.getTableHeader();
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
             if (e.getSource() == views.TableClientes){
            int fila = views.TableClientes.rowAtPoint(e.getPoint());
            views.txtIdCli.setText(views.TableClientes.getValueAt(fila, 0).toString());
            views.txtNombreCli.setText(views.TableClientes.getValueAt(fila, 1).toString());
            views.txtTelefonoCli.setText(views.TableClientes.getValueAt(fila, 2).toString());
            views.txtDireccionCli.setText(views.TableClientes.getValueAt(fila, 3).toString());
        } else if (e.getSource() == views.JLabelClientes){
                 views.jTabbedPane1.setSelectedIndex(1);
                 limpiarTable();
                 listarClientes();
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
    
    private void limpiar(){
        views.txtNombreCli.setText("");
        views.txtTelefonoCli.setText("");
        views.txtDireccionCli.setText("");
        views.txtIdCli.setText("");
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == views.txtBuscarCli){
            limpiarTable();
            listarClientes();
        }
    }
}
