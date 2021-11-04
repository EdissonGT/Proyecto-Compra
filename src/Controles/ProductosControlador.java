package Controles;

import Modelos.Combo;
import Modelos.Productos;
import Modelos.ProductosDao;
import Modelos.Tables;
import Vistas.Impresion;
import Vistas.PanelAdmin;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class ProductosControlador implements ActionListener, MouseListener, KeyListener {

    private Productos pro;
    private ProductosDao proDao;
    private PanelAdmin views;
    DefaultTableModel modelo = new DefaultTableModel();
    DefaultTableModel tmp;

    public ProductosControlador(Productos pro, ProductosDao proDao, PanelAdmin views) {
        this.pro = pro;
        this.proDao = proDao;
        this.views = views;
        this.views.btnRegistrarPro.addActionListener(this);
        this.views.btnModificarPro.addActionListener(this);
        this.views.btnNuevoPro.addActionListener(this);
        this.views.JMenuEliminarPro.addActionListener(this);
        this.views.JMenuReingresarPro.addActionListener(this);
        this.views.TableProductos.addMouseListener(this);
        this.views.JlabelProductos.addMouseListener(this);
        this.views.txtCodNC.addKeyListener(this);
        this.views.txtCantNC.addKeyListener(this);
        this.views.txtPagarConNC.addKeyListener(this);
        this.views.btnGenerarCompra.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btnRegistrarPro) {
            if (views.txtCodigoPro.getText().equals("")
                    || views.txtDescripcionPro.getText().equals("")
                    || views.txtPrecioCompraPro.getText().equals("")
                    || views.txtPrecioVentaPro.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "TODO LOS CAMPOS SON OBLIGATORIOS");
            } else {
                pro.setCodigo(views.txtCodigoPro.getText());
                pro.setDescripcion(views.txtDescripcionPro.getText());
                pro.setPrecio_compra(Double.parseDouble(views.txtPrecioCompraPro.getText()));
                pro.setPrecio_venta(Double.parseDouble(views.txtPrecioVentaPro.getText()));
                Combo itemP = (Combo) views.cbxProveedorPro.getSelectedItem();
                Combo itemC = (Combo) views.cbxCatPro.getSelectedItem();
                Combo itemM = (Combo) views.cbxMedidaPro.getSelectedItem();
                pro.setId_proveedor(itemP.getId());
                pro.setId_categoria(itemC.getId());
                pro.setId_medida(itemM.getId());
                if (proDao.registrar(pro)) {
                    limpiarTable();
                    listarProductos();
//                    limpiar();
                    JOptionPane.showMessageDialog(null, "PRODUCTO REGISTRADO CON EXITO");
                } else {
                    JOptionPane.showMessageDialog(null, "ERROR AL REGISTRAR PRODUCTO");
                }
            }
        } else if (e.getSource() == views.btnModificarPro) {
            if (views.txtCodigoPro.getText().equals("")
                    || views.txtDescripcionPro.getText().equals("")
                    || views.txtPrecioCompraPro.getText().equals("")
                    || views.txtPrecioVentaPro.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "TODO LOS CAMPOS SON OBLIGATORIOS");
            } else {
                pro.setCodigo(views.txtCodigoPro.getText());
                pro.setDescripcion(views.txtDescripcionPro.getText());
                pro.setPrecio_compra(Double.parseDouble(views.txtPrecioCompraPro.getText()));
                pro.setPrecio_venta(Double.parseDouble(views.txtPrecioVentaPro.getText()));
                Combo itemP = (Combo) views.cbxProveedorPro.getSelectedItem();
                Combo itemC = (Combo) views.cbxCatPro.getSelectedItem();
                Combo itemM = (Combo) views.cbxMedidaPro.getSelectedItem();
                pro.setId_proveedor(itemP.getId());
                pro.setId_categoria(itemC.getId());
                pro.setId_medida(itemM.getId());
                pro.setId(Integer.parseInt(views.txtIdPro.getText()));
                if (proDao.modificar(pro)) {
                    limpiarTable();
                    listarProductos();
//                    limpiar();
                    JOptionPane.showMessageDialog(null, "PRODUCTO MODIFICADO EXITOSAMENTE");
                } else {
                    JOptionPane.showMessageDialog(null, "ERROR AL MOFICIAR PRODUCTO");
                }
            }
        } else if (e.getSource() == views.JMenuEliminarPro) {
            if (views.txtIdPro.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Seleccione una fila para eliminar");
            } else {
                int id = Integer.parseInt(views.txtIdPro.getText());
                if (proDao.accion("Inactivo", id)) {
                    limpiarTable();
                    listarProductos();
//                    limpiar();
                    JOptionPane.showMessageDialog(null, "PRODUCTO ELIMINADO");
                } else {
                    JOptionPane.showMessageDialog(null, "Error AL ELIMINAR EL PRODUCTO");
                }
            }
        } else if (e.getSource() == views.JMenuReingresarPro) {
            if (views.txtIdPro.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Seleccione una fila para Reingresar");
            } else {
                int id = Integer.parseInt(views.txtIdPro.getText());
                if (proDao.accion("Activo", id)) {
                    limpiarTable();
                    listarProductos();
//                    limpiar();
                    JOptionPane.showMessageDialog(null, "PRODUCTO REINGRESADO");
                } else {
                    JOptionPane.showMessageDialog(null, "ERROR AL REINGRESAR PRODUCTO");
                }
            }
        } else if (e.getSource() == views.btnGenerarCompra) {
            insertaCompra();
        } else {

        }
    }

    public void listarProductos() {
        Tables color = new Tables();
        views.TableProductos.setDefaultRenderer(views.TableProductos.getColumnClass(0), color);
        List<Productos> lista = proDao.ListaProductos(views.txtBuscarProducto.getText());
        modelo = (DefaultTableModel) views.TableProductos.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < lista.size(); i++) {
            ob[0] = lista.get(i).getId();
            ob[1] = lista.get(i).getCodigo();
            ob[2] = lista.get(i).getDescripcion();
            ob[3] = lista.get(i).getPrecio_venta();
            ob[4] = lista.get(i).getCantidad();
            ob[5] = lista.get(i).getEstado();
            modelo.addRow(ob);

        }
        views.TableProductos.setModel(modelo);
        JTableHeader header = views.TableProductos.getTableHeader();
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

    public void limpiarTableDetalle() {
        for (int i = 0; i < tmp.getRowCount(); i++) {
            tmp.removeRow(i);
            i = i - 1;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.TableProductos) {
            int fila = views.TableProductos.rowAtPoint(e.getPoint());
            views.txtIdPro.setText(views.TableProductos.getValueAt(fila, 0).toString());
            pro = proDao.buscarPro(Integer.parseInt(views.txtIdPro.getText()));
            views.txtCodigoPro.setText(pro.getCodigo());
            views.txtDescripcionPro.setText(pro.getDescripcion());
            views.txtPrecioCompraPro.setText("" + pro.getPrecio_compra());
            views.txtPrecioVentaPro.setText("" + pro.getPrecio_venta());
            views.cbxProveedorPro.setSelectedItem(new Combo(pro.getId_proveedor(), pro.getProveedor()));
            views.cbxMedidaPro.setSelectedItem(new Combo(pro.getId_medida(), pro.getMedida()));
            views.cbxCatPro.setSelectedItem(new Combo(pro.getId_categoria(), pro.getCat()));
        } else if (e.getSource() == views.JlabelProductos) {
            views.jTabbedPane1.setSelectedIndex(0);
            limpiarTable();
            listarProductos();
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
        if (e.getSource() == views.txtCodNC) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (views.txtCodNC.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "INGRESE EL CODIGO");
                } else {
                    String cod = views.txtCodNC.getText();
                    pro = proDao.buscarCodigo(cod);
                    views.txtIdNC.setText("" + pro.getId());
                    views.txtProductoNC.setText(pro.getDescripcion());
                    views.txtPrecioNC.setText("" + pro.getPrecio_compra());
                    views.txtCantNC.requestFocus();
                }
            }
        } else if (e.getSource() == views.txtCantNC) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                int cant = Integer.parseInt(views.txtCantNC.getText());
                String desc = views.txtProductoNC.getText();
                double precio = Double.parseDouble(views.txtPrecioNC.getText());
                int id = Integer.parseInt(views.txtIdNC.getText());
                if (cant > 0) {
                    tmp = (DefaultTableModel) views.TableNuevaCompra.getModel();
                    ArrayList lista = new ArrayList();
                    int item = 1;
                    lista.add(item);
                    lista.add(id);
                    lista.add(desc);
                    lista.add(cant);
                    lista.add(precio);
                    lista.add(cant * precio);
                    Object[] obj = new Object[5];
                    obj[0] = lista.get(1);
                    obj[1] = lista.get(2);
                    obj[2] = lista.get(3);
                    obj[3] = lista.get(4);
                    obj[4] = lista.get(5);
                    tmp.addRow(obj);
                    views.TableNuevaCompra.setModel(tmp);
                    limpiarCammpos();
                    calcularCompra();
                    views.txtCodNC.requestFocus();
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == views.txtCantNC) {
            int cantidad;
            double precio;
            if (views.txtCantNC.getText().equals("")) {
                cantidad = 1;
                precio = Double.parseDouble(views.txtPrecioNC.getText());
                views.txtTotalNC.setText("" + precio);
            } else {
                cantidad = Integer.parseInt(views.txtCantNC.getText());
                precio = Double.parseDouble(views.txtPrecioNC.getText());
                views.txtTotalNC.setText("" + cantidad * precio);
            }
        } else if (e.getSource() == views.txtPagarConNC) {
            int pagar;
            if (views.txtPagarConNC.getText().equals("")) {
                views.txtVueltoNC.setText("");
            } else {
                pagar = Integer.parseInt(views.txtPagarConNC.getText());
                double total = Double.parseDouble(views.JLabelTotalCompra.getText());
                views.txtVueltoNC.setText("" + (pagar - total));
            }
        }
    }

    private void limpiarCammpos() {
        views.txtCodNC.setText("");
        views.txtIdNC.setText("");
        views.txtProductoNC.setText("");
        views.txtCantNC.setText("");
        views.txtPrecioNC.setText("");
        views.txtTotalNC.setText("");
    }

    private void calcularCompra() {
        double total = 0.00;
        int numFila = views.TableNuevaCompra.getRowCount();
        for (int i = 0; i < numFila; i++) {
            total = total + Double.parseDouble(String.valueOf(views.TableNuevaCompra.getValueAt(i, 4)));

        }
        views.JLabelTotalCompra.setText("" + total);
    }

    private void insertaCompra() {
        Combo id_p = (Combo) views.cbxProCompra.getSelectedItem();
        int id_proveedor = id_p.getId();
        String total = views.JLabelTotalCompra.getText();
        if (proDao.registrarCompra(id_proveedor, total)) {
            int id_compra = proDao.id_compra();
            for (int i = 0; i < views.TableNuevaCompra.getRowCount(); i++) {
                
                double precio = Double.parseDouble(views.TableNuevaCompra.getValueAt(i, 3).toString());
                int cantidad = Integer.parseInt(views.TableNuevaCompra.getValueAt(i, 2).toString());
                int id = Integer.parseInt(views.TableNuevaCompra.getValueAt(i, 0).toString());
                double sub_total = precio * cantidad;
                proDao.registrarCompraDetalle(id_compra, id, precio, cantidad, sub_total);
                pro = proDao.buscarId(id);
                int stockActual = pro.getCantidad() + cantidad;
                proDao.ActualizarStock(stockActual, id);
            }
            limpiarTableDetalle();
            JOptionPane.showMessageDialog(null, "Compra Generada");
            Impresion p = new Impresion(id_compra);
            p.setVisible(true);
        }
    }

}
