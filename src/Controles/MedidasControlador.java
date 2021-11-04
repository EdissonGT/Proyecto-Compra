package Controles;

import Modelos.Combo;
import Modelos.Medidas;
import Modelos.MedidasDao;
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

public class MedidasControlador implements ActionListener, MouseListener, KeyListener {

    private Medidas med;
    private MedidasDao medDao;
    private PanelAdmin views;

    DefaultTableModel modelo = new DefaultTableModel();

    public MedidasControlador(Medidas med, MedidasDao medDao, PanelAdmin views) {
        this.med = med;
        this.medDao = medDao;
        this.views = views;
        this.views.btnRegitrarMed.addActionListener(this);
        this.views.btnNuevoMed.addActionListener(this);
        this.views.btnModificarMed.addActionListener(this);
        this.views.TableMedida.addMouseListener(this);
        this.views.JLabelMedidas.addMouseListener(this);
        this.views.JMenuEliminarMed.addActionListener(this);
        this.views.JMenuReingresarMed.addActionListener(this);
        this.views.txtBuscarMed.addKeyListener(this);
        listarMedidas();
        llenarMedida();
        AutoCompleteDecorator.decorate(views.cbxMedidaPro);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btnRegitrarMed) {
            if (views.txtNombreMed.getText().equals("") || views.txtNombreCortoMed.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todo los campos son obligatorios");
            } else {
                med.setNombre(views.txtNombreMed.getText());
                med.setNombre_corto(views.txtNombreCortoMed.getText());
                if (medDao.registrar(med)) {
                    limpiarTable();
                    listarMedidas();
                    Nuevo();
                    JOptionPane.showMessageDialog(null, "Medida Registrado");
                } else {
                    JOptionPane.showMessageDialog(null, "La medida ya existe");
                }
            }
        } else if (e.getSource() == views.btnModificarMed) {
            if (views.txtNombreMed.getText().equals("") || views.txtNombreCortoMed.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todo los campos son obligatorios");
            } else {
                med.setNombre(views.txtNombreMed.getText());
                med.setNombre_corto(views.txtNombreCortoMed.getText());
                med.setId(Integer.parseInt(views.txtIdMed.getText()));
                if (medDao.modificar(med)) {
                   limpiarTable();
                    listarMedidas();
                    Nuevo();
                    JOptionPane.showMessageDialog(null, "Medida Modificado");
                }
            }
        } else if (e.getSource() == views.JMenuEliminarMed) {
            if (views.txtIdMed.getText().equals("") || views.txtIdMed.getText() == null) {
                JOptionPane.showMessageDialog(null, "Seleccione una fila para eliminar");
            } else {
                int pregunta = JOptionPane.showConfirmDialog(null, "Estas seguro de eliminar", "Pregunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (pregunta == 0) {
                    int id = Integer.parseInt(views.txtIdMed.getText());
                    if (medDao.accion("Inactivo", id)) {
                      limpiarTable();
                    listarMedidas();
                    Nuevo();
                        JOptionPane.showMessageDialog(null, "Medida Eliminado");
                    }
                }
            }
        } else if (e.getSource() == views.JMenuReingresarMed) {
            if (views.txtIdMed.getText().equals("") || views.txtIdMed.getText() == null) {
                JOptionPane.showMessageDialog(null, "Seleccione una fila para reingresar");
            } else {
                int pregunta = JOptionPane.showConfirmDialog(null, "Estas seguro de reingresar", "Pregunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (pregunta == 0) {
                    int id = Integer.parseInt(views.txtIdMed.getText());
                    if (medDao.accion("Activo", id)) {
                        limpiarTable();
                    listarMedidas();
                    Nuevo();
                        JOptionPane.showMessageDialog(null, "Medida Reingresado");
                    }
                }
            }
        } else if (e.getSource() == views.btnNuevoMed) {
            Nuevo();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.TableMedida) {
            int fila = views.TableMedida.rowAtPoint(e.getPoint());
            views.txtIdMed.setText(views.TableMedida.getValueAt(fila, 0).toString());
            views.txtNombreMed.setText(views.TableMedida.getValueAt(fila, 1).toString());
            views.txtNombreCortoMed.setText(views.TableMedida.getValueAt(fila, 2).toString());
        } else if (e.getSource() == views.JLabelMedidas) {
            views.jTabbedPane1.setSelectedIndex(5);
            limpiarTable();
            listarMedidas();
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

    private void Nuevo() {
        views.txtIdMed.setText("");
        views.txtNombreMed.setText("");
        views.txtNombreCortoMed.setText("");
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == views.txtBuscarMed) {
            limpiarTable();
            listarMedidas();
        }
    }

    public void listarMedidas() {
        Tables color = new Tables();
        views.TableMedida.setDefaultRenderer(views.TableMedida.getColumnClass(0), color);
        List<Medidas> lista = medDao.ListarMed(views.txtBuscarMed.getText());
        modelo = (DefaultTableModel) views.TableMedida.getModel();
        Object[] ob = new Object[4];
        for (int i = 0; i < lista.size(); i++) {
            ob[0] = lista.get(i).getId();
            ob[1] = lista.get(i).getNombre();
            ob[2] = lista.get(i).getNombre_corto();
            ob[3] = lista.get(i).getEstado();
            modelo.addRow(ob);
        }
        views.TableMedida.setModel(modelo);
        JTableHeader header = views.TableMedida.getTableHeader();
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
    private void llenarMedida() {
        List<Medidas> lista = medDao.ListarMed(views.txtBuscarMed.getText());
        for (int i = 0; i < lista.size(); i++) {
            int id = lista.get(i).getId();
            String nombre = lista.get(i).getNombre();
            views.cbxMedidaPro.addItem(new Combo(id, nombre));
        }
    }
}
