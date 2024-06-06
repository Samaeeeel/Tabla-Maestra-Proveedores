package ProveedoresApp;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.ByteArrayInputStream;

public class TablaProveedores extends JFrame {
    private Connection connection;
    private JTable table;
    private DefaultTableModel tableModel;

    public TablaProveedores(Connection connection) {
        this.connection = connection;

        setTitle("Tabla de Proveedores");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Hacer la ventana de la tabla pantalla completa
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Permitir cerrar solo esta ventana
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Código");
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Identificación");
        tableModel.addColumn("Dirección");
        tableModel.addColumn("Teléfono");
        tableModel.addColumn("Celular");
        tableModel.addColumn("Email");
        tableModel.addColumn("Tipo");
        tableModel.addColumn("Status");
        tableModel.addColumn("Foto");

        table = new JTable(tableModel) {
            @Override
            public Class<?> getColumnClass(int column) {
                return (column == 9) ? ImageIcon.class : Object.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que las celdas no sean editables
            }
        };

        // Ajustar tamaños de columnas
        table.setRowHeight(150);
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(3).setPreferredWidth(300);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(100);
        table.getColumnModel().getColumn(6).setPreferredWidth(250);
        table.getColumnModel().getColumn(7).setPreferredWidth(50);
        table.getColumnModel().getColumn(8).setPreferredWidth(50);
        table.getColumnModel().getColumn(9).setPreferredWidth(150);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    ProveedoresApp.getTxtCodigo().setText(table.getValueAt(selectedRow, 0).toString());
                    ProveedoresApp.getTxtNombre().setText(table.getValueAt(selectedRow, 1).toString());
                    ProveedoresApp.getTxtIdentificacion().setText(table.getValueAt(selectedRow, 2).toString());
                    ProveedoresApp.getTxtDireccion().setText(table.getValueAt(selectedRow, 3).toString());
                    ProveedoresApp.getTxtTelefono().setText(table.getValueAt(selectedRow, 4).toString());
                    ProveedoresApp.getTxtCelular().setText(table.getValueAt(selectedRow, 5).toString());
                    ProveedoresApp.getTxtEmail().setText(table.getValueAt(selectedRow, 6).toString());
                    ProveedoresApp.getCmbTipo().setSelectedItem(table.getValueAt(selectedRow, 7).toString());
                    ProveedoresApp.getCmbStatus().setSelectedItem(table.getValueAt(selectedRow, 8).toString());
                    // Gestionar la foto aquí si es necesario
                }
            }
        });

        cargarDatos();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void cargarDatos() {
        try {
            tableModel.setRowCount(0);
            String sql = "SELECT * FROM PROVEEDORES";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Object[] rowData = new Object[10];
                rowData[0] = rs.getString("PRVCODIGO");
                rowData[1] = rs.getString("PRVNOMBRE");
                rowData[2] = rs.getString("PRVIDENTIFICACION");
                rowData[3] = rs.getString("PRVDIRECCION");
                rowData[4] = rs.getString("PRVTELEFONO");
                rowData[5] = rs.getString("PRVCELULAR");
                rowData[6] = rs.getString("PRVEMAIL");
                rowData[7] = rs.getString("PRVTIPO");
                rowData[8] = rs.getString("PRVSTATUS");

                byte[] imgBytes = rs.getBytes("FOTO");
                if (imgBytes != null) {
                    try {
                        ByteArrayInputStream bais = new ByteArrayInputStream(imgBytes);
                        Image img = ImageIO.read(bais);
                        ImageIcon icon = new ImageIcon(img.getScaledInstance(150, 150, Image.SCALE_SMOOTH));
                        rowData[9] = icon;
                    } catch (Exception ex) {
                        rowData[9] = null;
                    }
                } else {
                    rowData[9] = null;
                }

                tableModel.addRow(rowData);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
