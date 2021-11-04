package Controles;

import Modelos.Configuracion;
import Modelos.UsuarioDao;
import Vistas.PanelAdmin;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;

public class ConfigControles implements MouseListener, ActionListener {

    private Configuracion cof;
    private UsuarioDao Cdao;
    private PanelAdmin views;

    public ConfigControles(Configuracion cof, UsuarioDao Cdao, PanelAdmin views) {
        this.cof = cof;
        this.Cdao = Cdao;
        this.views = views;
        this.views.JLabelCat.addMouseListener(this);
        this.views.JLabelClientes.addMouseListener(this);
        this.views.JLabelConfig.addMouseListener(this);
        this.views.JLabelMedidas.addMouseListener(this);
        this.views.JLabelProveedor.addMouseListener(this);
        this.views.JLabelUsers.addMouseListener(this);
        this.views.JPanelProductos.addMouseListener(this);
        cof = Cdao.getConfig();
        views.txtRucEmpresa.setText(cof.getRuc());
        views.txtTelefonoEmpresa.setText(cof.getTelefono());
        views.txtNombreEmpresa.setText(cof.getNombre());
        views.txtDireccionEmpresa.setText(cof.getDireccion());
        views.txtMensaje.setText(cof.getMensaje());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.JLabelConfig) {
            views.jTabbedPane1.setSelectedIndex(6);
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == views.JLabelCat) {
            views.JPanelCategorias.setBackground(new Color(0, 254, 254));
        } else if (e.getSource() == views.JLabelClientes) {
            views.JPanelClientes.setBackground(new Color(0, 254, 254));
        } else if (e.getSource() == views.JLabelConfig) {
            views.JPanelConfig.setBackground(new Color(0, 254, 254));
        } else if (e.getSource() == views.JLabelMedidas) {
            views.JPanelMedidas.setBackground(new Color(0, 254, 254));
        } else if (e.getSource() == views.JLabelProveedor) {
            views.JPanelProveedor.setBackground(new Color(0, 254, 254));
        } else if (e.getSource() == views.JLabelUsers) {
            views.JPanelUsers.setBackground(new Color(0, 254, 254));
        } else {
            views.JPanelProductos.setBackground(new Color(0, 254, 254));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == views.JLabelCat) {
            views.JPanelCategorias.setBackground(new Color(0, 153, 153));
        } else if (e.getSource() == views.JLabelClientes) {
            views.JPanelClientes.setBackground(new Color(0, 153, 153));
        } else if (e.getSource() == views.JLabelConfig) {
            views.JPanelConfig.setBackground(new Color(0, 153, 153));
        } else if (e.getSource() == views.JLabelMedidas) {
            views.JPanelMedidas.setBackground(new Color(0, 153, 153));
        } else if (e.getSource() == views.JLabelProveedor) {
            views.JPanelProveedor.setBackground(new Color(0, 153, 153));
        } else if (e.getSource() == views.JLabelUsers) {
            views.JPanelUsers.setBackground(new Color(0, 153, 153));
        } else {
            views.JPanelProductos.setBackground(new Color(0, 153, 153));
        }
    }

    @Override
   public void actionPerformed(ActionEvent e) {

    }

}
