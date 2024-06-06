package ProveedoresApp;

import config.Config;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;

public class ProveedoresApp {
    private static JTextField txtCodigo, txtNombre, txtIdentificacion, txtDireccion, txtTelefono, txtCelular, txtEmail;
    private static JComboBox<String> cmbTipo, cmbStatus;
    private static JLabel lblFoto;
    private static JButton btnInsertar, btnSeleccionarFoto, btnEliminarFoto, btnBuscar, btnActualizar, btnEliminar, btnLimpiar, btnMostrarTabla;
    private static byte[] foto;
    private static Connection connection;

    public static void main(String[] args) {
        Config config = new Config();
        connection = config.Conexion();

        if (connection == null) {
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos. Verifica la configuración.");
            return;
        }

        JFrame frame = new JFrame("Gestión de Proveedores");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Hacer el JFrame pantalla completa
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        agregarComponentes(frame);
        agregarListeners();

        frame.setVisible(true);
    }

    private static void agregarComponentes(JFrame frame) {
        JLabel lblTitulo = new JLabel("GESTIÓN PROVEEDORES", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 36));
        lblTitulo.setBounds(20, 20, 1240, 50);
        frame.add(lblTitulo);

        JLabel lblCodigo = new JLabel("Código:");
        lblCodigo.setBounds(20, 100, 100, 25);
        frame.add(lblCodigo);

        txtCodigo = new JTextField();
        txtCodigo.setBounds(150, 100, 75, 25);
        frame.add(txtCodigo);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 140, 100, 25);
        frame.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(150, 140, 200, 25);
        frame.add(txtNombre);

        JLabel lblIdentificacion = new JLabel("Identificación:");
        lblIdentificacion.setBounds(20, 180, 100, 25);
        frame.add(lblIdentificacion);

        txtIdentificacion = new JTextField();
        txtIdentificacion.setBounds(150, 180, 80, 25);
        frame.add(txtIdentificacion);

        JLabel lblDireccion = new JLabel("Dirección:");
        lblDireccion.setBounds(20, 220, 100, 25);
        frame.add(lblDireccion);

        txtDireccion = new JTextField();
        txtDireccion.setBounds(150, 220, 200, 25);
        frame.add(txtDireccion);

        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setBounds(20, 260, 100, 25);
        frame.add(lblTelefono);

        txtTelefono = new JTextField();
        txtTelefono.setBounds(150, 260, 80, 25);
        frame.add(txtTelefono);

        JLabel lblCelular = new JLabel("Celular:");
        lblCelular.setBounds(20, 300, 100, 25);
        frame.add(lblCelular);

        txtCelular = new JTextField();
        txtCelular.setBounds(150, 300, 80, 25);
        frame.add(txtCelular);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(20, 340, 100, 25);
        frame.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(150, 340, 170, 25);
        frame.add(txtEmail);

        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setBounds(20, 380, 100, 25);
        frame.add(lblTipo);

        cmbTipo = new JComboBox<>(new String[]{"NAT", "JUR"});
        cmbTipo.setBounds(150, 380, 75, 25);
        frame.add(cmbTipo);

        JLabel lblStatus = new JLabel("Status:");
        lblStatus.setBounds(20, 420, 100, 25);
        frame.add(lblStatus);

        cmbStatus = new JComboBox<>(new String[]{"ACT", "INA"});
        cmbStatus.setBounds(150, 420, 75, 25);
        frame.add(cmbStatus);

        lblFoto = new JLabel("Foto:");
        lblFoto.setBounds(20, 460, 100, 25);
        frame.add(lblFoto);

        btnSeleccionarFoto = new JButton("Seleccionar Foto");
        btnSeleccionarFoto.setBounds(150, 460, 200, 25);
        frame.add(btnSeleccionarFoto);

        btnEliminarFoto = new JButton("Eliminar Foto");
        btnEliminarFoto.setBounds(150, 490, 200, 25);
        frame.add(btnEliminarFoto);

        btnInsertar = new JButton("Insertar");
        btnInsertar.setBounds(400, 100, 200, 25);
        frame.add(btnInsertar);

        btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(400, 140, 200, 25);
        frame.add(btnBuscar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(400, 180, 200, 25);
        frame.add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(400, 220, 200, 25);
        frame.add(btnEliminar);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(400, 260, 200, 25);
        frame.add(btnLimpiar);

        btnMostrarTabla = new JButton("Mostrar Tabla");
        btnMostrarTabla.setBounds(400, 300, 200, 25);
        frame.add(btnMostrarTabla);
    }

    private static void agregarListeners() {
        btnSeleccionarFoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        java.io.File file = fileChooser.getSelectedFile();
                        java.io.FileInputStream fis = new java.io.FileInputStream(file);
                        java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        for (int readNum; (readNum = fis.read(buffer)) != -1;) {
                            bos.write(buffer, 0, readNum);
                        }
                        foto = bos.toByteArray();
                        lblFoto.setText("Foto seleccionada");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        btnEliminarFoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                foto = null;
                lblFoto.setText("Foto:");
            }
        });

        btnInsertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertarProveedor();
            }
        });

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProveedor();
            }
        });

        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarProveedor();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar este proveedor?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    eliminarProveedor();
                }
            }
        });

        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });

        btnMostrarTabla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TablaProveedores(connection);
            }
        });
    }

    private static boolean validarCampos() {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        String nombreRegex = "^[A-Za-zÁÉÍÓÚáéíóúÑñ. ]+$";
        String celularRegex = "^09[0-9]{8}$";
        String identificacionRegex = "^[0-9]+$";
        String direccionRegex = "^[A-Za-z0-9ÁÉÍÓÚáéíóúÑñ\\-./, ]+$";
        String telefonoRegex = "^0[1-9]-[0-9]{7}$";

        if (txtCodigo.getText().isEmpty() || txtNombre.getText().isEmpty() || txtIdentificacion.getText().isEmpty() ||
            txtDireccion.getText().isEmpty() || cmbTipo.getSelectedItem() == null || cmbStatus.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Todos los campos obligatorios deben ser llenados.");
            return false;
        }

        if (!cmbTipo.getSelectedItem().toString().matches("NAT|JUR")) {
            JOptionPane.showMessageDialog(null, "El campo Tipo debe ser NAT o JUR.");
            return false;
        }

        if (!cmbStatus.getSelectedItem().toString().matches("ACT|INA")) {
            JOptionPane.showMessageDialog(null, "El campo Status debe ser ACT o INA.");
            return false;
        }

        if (!txtEmail.getText().matches(emailRegex)) {
            JOptionPane.showMessageDialog(null, "El campo Email no tiene un formato válido.");
            return false;
        }

        if (!txtNombre.getText().matches(nombreRegex)) {
            JOptionPane.showMessageDialog(null, "El campo Nombre contiene caracteres inválidos.");
            return false;
        }

        if (!txtCelular.getText().matches(celularRegex)) {
            JOptionPane.showMessageDialog(null, "El campo Celular no tiene un formato válido.");
            return false;
        }

        if (!txtIdentificacion.getText().matches(identificacionRegex)) {
            JOptionPane.showMessageDialog(null, "El campo Identificación contiene caracteres inválidos.");
            return false;
        }

        if (!txtDireccion.getText().matches(direccionRegex)) {
            JOptionPane.showMessageDialog(null, "El campo Dirección contiene caracteres inválidos.");
            return false;
        }

        if (!txtTelefono.getText().isEmpty() && !txtTelefono.getText().matches(telefonoRegex)) {
            JOptionPane.showMessageDialog(null, "El campo Teléfono no tiene un formato válido.");
            return false;
        }

        return true;
    }

    public static void insertarProveedor() {
        if (!validarCampos()) {
            return;
        }

        String codigo = txtCodigo.getText();
        String nombre = txtNombre.getText();
        String identificacion = txtIdentificacion.getText();
        String direccion = txtDireccion.getText();
        String telefono = txtTelefono.getText();
        String celular = txtCelular.getText();
        String email = txtEmail.getText();
        String tipo = cmbTipo.getSelectedItem().toString();
        String status = cmbStatus.getSelectedItem().toString();

        String sql = "INSERT INTO PROVEEDORES (PRVCODIGO, PRVNOMBRE, PRVIDENTIFICACION, PRVDIRECCION, PRVTELEFONO, PRVCELULAR, PRVEMAIL, PRVTIPO, PRVSTATUS, FOTO) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            stmt.setString(2, nombre);
            stmt.setString(3, identificacion);
            stmt.setString(4, direccion);
            stmt.setString(5, telefono);
            stmt.setString(6, celular);
            stmt.setString(7, email);
            stmt.setString(8, tipo);
            stmt.setString(9, status);
            stmt.setBytes(10, foto);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "Proveedor insertado correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "No se insertó el proveedor. Verifica los datos e intenta nuevamente.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al insertar proveedor: " + ex.getMessage());
        }
    }

    public static void buscarProveedor() {
        String codigo = txtCodigo.getText();

        String sql = "SELECT * FROM PROVEEDORES WHERE PRVCODIGO = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, codigo);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                txtNombre.setText(rs.getString("PRVNOMBRE"));
                txtIdentificacion.setText(rs.getString("PRVIDENTIFICACION"));
                txtDireccion.setText(rs.getString("PRVDIRECCION"));
                txtTelefono.setText(rs.getString("PRVTELEFONO"));
                txtCelular.setText(rs.getString("PRVCELULAR"));
                txtEmail.setText(rs.getString("PRVEMAIL"));
                cmbTipo.setSelectedItem(rs.getString("PRVTIPO"));
                cmbStatus.setSelectedItem(rs.getString("PRVSTATUS"));
                foto = rs.getBytes("FOTO");
                lblFoto.setText("Foto cargada");
            } else {
                JOptionPane.showMessageDialog(null, "Proveedor no encontrado.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al buscar proveedor: " + ex.getMessage());
        }
    }

    public static void actualizarProveedor() {
        if (!validarCampos()) {
            return;
        }

        String codigo = txtCodigo.getText();
        String nombre = txtNombre.getText();
        String identificacion = txtIdentificacion.getText();
        String direccion = txtDireccion.getText();
        String telefono = txtTelefono.getText();
        String celular = txtCelular.getText();
        String email = txtEmail.getText();
        String tipo = cmbTipo.getSelectedItem().toString();
        String status = cmbStatus.getSelectedItem().toString();

        String sql = "UPDATE PROVEEDORES SET PRVNOMBRE=?, PRVIDENTIFICACION=?, PRVDIRECCION=?, PRVTELEFONO=?, PRVCELULAR=?, PRVEMAIL=?, PRVTIPO=?, PRVSTATUS=?, FOTO=? WHERE PRVCODIGO=?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, identificacion);
            stmt.setString(3, direccion);
            stmt.setString(4, telefono);
            stmt.setString(5, celular);
            stmt.setString(6, email);
            stmt.setString(7, tipo);
            stmt.setString(8, status);
            stmt.setBytes(9, foto);
            stmt.setString(10, codigo);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Proveedor actualizado correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "No se actualizó el proveedor. Verifica los datos e intenta nuevamente.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar proveedor: " + ex.getMessage());
        }
    }

    public static void eliminarProveedor() {
        String codigo = txtCodigo.getText();

        String sql = "DELETE FROM PROVEEDORES WHERE PRVCODIGO = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, codigo);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "Proveedor eliminado correctamente");
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(null, "No se eliminó el proveedor. Verifica los datos e intenta nuevamente.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar proveedor: " + ex.getMessage());
        }
    }

    public static void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtIdentificacion.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtCelular.setText("");
        txtEmail.setText("");
        cmbTipo.setSelectedItem(null);
        cmbStatus.setSelectedItem(null);
        lblFoto.setText("Foto:");
        foto = null;
    }
    
     // Métodos getter para los campos
    public static JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public static JTextField getTxtNombre() {
        return txtNombre;
    }

    public static JTextField getTxtIdentificacion() {
        return txtIdentificacion;
    }

    public static JTextField getTxtDireccion() {
        return txtDireccion;
    }

    public static JTextField getTxtTelefono() {
        return txtTelefono;
    }

    public static JTextField getTxtCelular() {
        return txtCelular;
    }

    public static JTextField getTxtEmail() {
        return txtEmail;
    }

    public static JComboBox<String> getCmbTipo() {
        return cmbTipo;
    }

    public static JComboBox<String> getCmbStatus() {
        return cmbStatus;
    }

    public static void setFoto(byte[] foto) {
        ProveedoresApp.foto = foto;
    }

    public static void setLblFotoText(String text) {
        lblFoto.setText(text);
    }
}
