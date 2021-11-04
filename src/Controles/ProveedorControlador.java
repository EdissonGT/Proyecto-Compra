package Controles;

import Modelos.Combo;
import Modelos.Proveedor;
import Modelos.ProveedorDao;
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
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class ProveedorControlador implements ActionListener, MouseListener, KeyListener {

    private Proveedor prov;
    private ProveedorDao provDao;
    private PanelAdmin views;
    DefaultTableModel modelo = new DefaultTableModel();

    public ProveedorControlador(Proveedor prov, ProveedorDao provDao, PanelAdmin views) {
        this.prov = prov;
        this.provDao = provDao;
        this.views = views;
        this.views.btnRegistrarProv.addActionListener(this);
        this.views.btnNuevoProv.addActionListener(this);
        this.views.btnModificarProv.addActionListener(this);
        this.views.TableProveedor.addMouseListener(this);
        this.views.JLabelProveedor.addMouseListener(this);
        this.views.JMenuEliminarProv.addActionListener(this);
        this.views.JMenuReingresarProv.addActionListener(this);
        this.views.txtBuscarProv.addKeyListener(this);
        listarProv();
        llenarProveedor();
        AutoCompleteDecorator.decorate(views.cbxProveedorPro);
        AutoCompleteDecorator.decorate(views.cbxProCompra);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btnRegistrarProv) {
            if (views.txtRucProv.getText().equals("") || views.txtNombreProv.getText().equals("") || views.txtTelefonoProv.getText().equals("") || views.txtDireccionProv.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todo los campos son obligatorios");
            } else {
                prov.setRuc(views.txtRucProv.getText());
                prov.setNombre(views.txtNombreProv.getText());
                prov.setTelefono(views.txtTelefonoProv.getText());
                prov.setDireccion(views.txtDireccionProv.getText());
                if (provDao.registrar(prov)) {
                    Nuevo();
                    limpiarTable();
                    listarProv();
                    JOptionPane.showMessageDialog(null, "Proveedor Registrado");
                } else {
                    JOptionPane.showMessageDialog(null, "El proveedor ya existe");
                }
            }
        } else if (e.getSource() == views.btnModificarProv) {
            if (views.txtRucProv.getText().equals("") || views.txtNombreProv.getText().equals("") || views.txtTelefonoProv.getText().equals("") || views.txtDireccionProv.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todo los campos son obligatorios");
            } else {
                prov.setRuc(views.txtRucProv.getText());
                prov.setNombre(views.txtNombreProv.getText());
                prov.setTelefono(views.txtTelefonoProv.getText());
                prov.setDireccion(views.txtDireccionProv.getText());
                prov.setId(Integer.parseInt(views.txtIdProv.getText()));
                if (provDao.modificar(prov)) {
                    Nuevo();
                    limpiarTable();
                    listarProv();
                    JOptionPane.showMessageDialog(null, "Proveedor Modificado");
                }
            }
        } else if (e.getSource() == views.JMenuEliminarProv) {
            if (views.txtIdProv.getText().equals("") || views.txtIdProv.getText() == null) {
                JOptionPane.showMessageDialog(null, "Seleccione una fila para eliminar");
            } else {
                int pregunta = JOptionPane.showConfirmDialog(null, "Estas seguro de eliminar", "Pregunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (pregunta == 0) {
                    int id = Integer.parseInt(views.txtIdProv.getText());
                    if (provDao.accion("Inactivo", id)) {
                        Nuevo();
                        limpiarTable();
                        listarProv();
                        JOptionPane.showMessageDialog(null, "Proveedor Eliminado");
                    }
                }
            }
        } else if (e.getSource() == views.JMenuReingresarProv) {
            if (views.txtIdProv.getText().equals("") || views.txtIdProv.getText() == null) {
                JOptionPane.showMessageDialog(null, "Seleccione una fila para reingresar");
            } else {
                int pregunta = JOptionPane.showConfirmDialog(null, "Estas seguro de reingresar", "Pregunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (pregunta == 0) {
                    int id = Integer.parseInt(views.txtIdProv.getText());
                    if (provDao.accion("Activo", id)) {
                        Nuevo();
                        limpiarTable();
                        listarProv();
                        JOptionPane.showMessageDialog(null, "Proveedor Reingresado");
                    }
                }
            }
        } else if (e.getSource() == views.btnNuevoProv) {
            Nuevo();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.TableProveedor) {
            int fila = views.TableProveedor.rowAtPoint(e.getPoint());
            String estado = views.TableProveedor.getValueAt(fila, 5).toString();
            if (estado.equals("Inactivo")) {
                views.JMenuEliminarProv.setVisible(false);
                views.JMenuReingresarProv.setVisible(true);
            } else {
                views.JMenuEliminarProv.setVisible(true);
                views.JMenuReingresarProv.setVisible(false);
            }
            views.txtIdProv.setText(views.TableProveedor.getValueAt(fila, 0).toString());
            views.txtRucProv.setText(views.TableProveedor.getValueAt(fila, 1).toString());
            views.txtNombreProv.setText(views.TableProveedor.getValueAt(fila, 2).toString());
            views.txtTelefonoProv.setText(views.TableProveedor.getValueAt(fila, 3).toString());
            views.txtDireccionProv.setText(views.TableProveedor.getValueAt(fila, 4).toString());
        } else if (e.getSource() == views.JLabelProveedor) {
            views.jTabbedPane1.setSelectedIndex(2);
            limpiarTable();
            listarProv();
        }

    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    private void Nuevo() {
        views.txtIdProv.setText("");
        views.txtRucProv.setText("");
        views.txtNombreProv.setText("");
        views.txtTelefonoProv.setText("");
        views.txtDireccionProv.setText("");
    }

    public void listarProv() {
        Tables color = new Tables();
        views.TableProveedor.setDefaultRenderer(views.TableProveedor.getColumnClass(0), color);
        List<Proveedor> lista = provDao.ListarProveedor(views.txtBuscarProv.getText());
        modelo = (DefaultTableModel) views.TableProveedor.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < lista.size(); i++) {
            ob[0] = lista.get(i).getId();
            ob[1] = lista.get(i).getRuc();
            ob[2] = lista.get(i).getNombre();
            ob[3] = lista.get(i).getTelefono();
            ob[4] = lista.get(i).getDireccion();
            ob[5] = lista.get(i).getEstado();
            modelo.addRow(ob);
        }
        views.TableProveedor.setModel(modelo);
        JTableHeader header = views.TableProveedor.getTableHeader();
        header.setOpaque(false);
        header.setBackground(Color.blue);
        header.setForeground(Color.white);
    }

    public void limpiarTable() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        limpiarTable();
        listarProv();
    }

    private void llenarProveedor() {
        List<Proveedor> lista = provDao.ListarProveedor(views.txtBuscarProv.getText());
        for (int i = 0; i < lista.size(); i++) {
            int id = lista.get(i).getId();
            String nombre = lista.get(i).getNombre();
            views.cbxProveedorPro.addItem(new Combo(id, nombre));
            views.cbxProCompra.addItem(new Combo(id, nombre));
        }
    }
}
