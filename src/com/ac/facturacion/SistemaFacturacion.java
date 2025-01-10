package com.ac.facturacion;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// Modelo de Cliente
class Cliente {
    String cedula, nombres, apellidos, direccion, telefono, email;

    public Cliente(String cedula, String nombres, String apellidos, String direccion, String telefono, String email) {
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }

    @Override
    public String toString() {
        return cedula + " - " + nombres + " " + apellidos;
    }
}

// Modelo de Producto
class Producto {
    String codigo, nombre;
    double precio;
    int stock;

    public Producto(String codigo, String nombre, double precio, int stock) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    @Override
    public String toString() {
        return codigo + " - " + nombre;
    }
}

// Modelo de Factura
class Factura {
    String numero;
    String fecha;
    Cliente cliente;
    List<Producto> productos;
    double subtotal, iva, total;

    public Factura(String numero, String fecha, Cliente cliente) {
        this.numero = numero;
        this.fecha = fecha;
        this.cliente = cliente;
        this.productos = new ArrayList<>();
        this.subtotal = 0;
        this.iva = 0;
        this.total = 0;
    }

    public void agregarProducto(Producto producto, int cantidad) {
        if (producto.stock >= cantidad) {
            producto.stock -= cantidad;
            productos.add(producto);
            double costo = producto.precio * cantidad;
            subtotal += costo;
            iva += costo * 0.12;
            total = subtotal + iva;
        } else {
            JOptionPane.showMessageDialog(null, "Stock insuficiente para " + producto.nombre);
        }
    }
}

public class SistemaFacturacion {

    private static List<Cliente> clientes = new ArrayList<>();
    private static List<Producto> productos = new ArrayList<>();
    private static List<Factura> facturas = new ArrayList<>();

    public static void main(String[] args) {
        JFrame ventanaPrincipal = new JFrame("Sistema de Facturación v2.0.0");
        ventanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaPrincipal.setSize(800, 600);

        JDesktopPane desktopPane = new JDesktopPane();
        ventanaPrincipal.add(desktopPane);

        JMenuBar menuBar = new JMenuBar();

        JMenu menuGestion = new JMenu("Gestión");
        JMenuItem menuClientes = new JMenuItem("Clientes");
        JMenuItem menuProductos = new JMenuItem("Productos");
        menuGestion.add(menuClientes);
        menuGestion.add(menuProductos);

        JMenu menuFacturacion = new JMenu("Facturación");
        JMenuItem menuNuevaFactura = new JMenuItem("Nueva Factura");
        menuFacturacion.add(menuNuevaFactura);

        menuBar.add(menuGestion);
        menuBar.add(menuFacturacion);
        ventanaPrincipal.setJMenuBar(menuBar);

        menuClientes.addActionListener(e -> abrirModuloClientes(desktopPane));
        menuProductos.addActionListener(e -> abrirModuloProductos(desktopPane));
        menuNuevaFactura.addActionListener(e -> abrirModuloFacturacion(desktopPane));

        ventanaPrincipal.setVisible(true);
    }

    private static void abrirModuloClientes(JDesktopPane desktopPane) {
        JInternalFrame frameClientes = new JInternalFrame("Gestión de Clientes", true, true, true, true);
        frameClientes.setSize(600, 400);
        frameClientes.setLayout(new BorderLayout());

        DefaultListModel<Cliente> modeloClientes = new DefaultListModel<>();
        JList<Cliente> listaClientes = new JList<>(modeloClientes);
        JScrollPane scrollPane = new JScrollPane(listaClientes);

        JPanel panelBotones = new JPanel();
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        btnAgregar.addActionListener(e -> {
            JTextField txtCedula = new JTextField();
            JTextField txtNombres = new JTextField();
            JTextField txtApellidos = new JTextField();
            JTextField txtDireccion = new JTextField();
            JTextField txtTelefono = new JTextField();
            JTextField txtEmail = new JTextField();

            Object[] campos = {
                "Cédula:", txtCedula,
                "Nombres:", txtNombres,
                "Apellidos:", txtApellidos,
                "Dirección:", txtDireccion,
                "Teléfono:", txtTelefono,
                "Email:", txtEmail
            };

            int opcion = JOptionPane.showConfirmDialog(null, campos, "Agregar Cliente", JOptionPane.OK_CANCEL_OPTION);
            if (opcion == JOptionPane.OK_OPTION) {
                Cliente cliente = new Cliente(txtCedula.getText(), txtNombres.getText(), txtApellidos.getText(),
                        txtDireccion.getText(), txtTelefono.getText(), txtEmail.getText());
                clientes.add(cliente);
                modeloClientes.addElement(cliente);
            }
        });

        btnEditar.addActionListener(e -> {
            Cliente seleccionado = listaClientes.getSelectedValue();
            if (seleccionado != null) {
                JTextField txtCedula = new JTextField(seleccionado.cedula);
                JTextField txtNombres = new JTextField(seleccionado.nombres);
                JTextField txtApellidos = new JTextField(seleccionado.apellidos);
                JTextField txtDireccion = new JTextField(seleccionado.direccion);
                JTextField txtTelefono = new JTextField(seleccionado.telefono);
                JTextField txtEmail = new JTextField(seleccionado.email);

                Object[] campos = {
                    "Cédula:", txtCedula,
                    "Nombres:", txtNombres,
                    "Apellidos:", txtApellidos,
                    "Dirección:", txtDireccion,
                    "Teléfono:", txtTelefono,
                    "Email:", txtEmail
                };

                int opcion = JOptionPane.showConfirmDialog(null, campos, "Editar Cliente", JOptionPane.OK_CANCEL_OPTION);
                if (opcion == JOptionPane.OK_OPTION) {
                    seleccionado.cedula = txtCedula.getText();
                    seleccionado.nombres = txtNombres.getText();
                    seleccionado.apellidos = txtApellidos.getText();
                    seleccionado.direccion = txtDireccion.getText();
                    seleccionado.telefono = txtTelefono.getText();
                    seleccionado.email = txtEmail.getText();

                    listaClientes.repaint();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un cliente para editar.");
            }
        });

        frameClientes.add(scrollPane, BorderLayout.CENTER);
        frameClientes.add(panelBotones, BorderLayout.SOUTH);
        frameClientes.setVisible(true);
        desktopPane.add(frameClientes);
    }

    private static void abrirModuloProductos(JDesktopPane desktopPane) {
        JInternalFrame frameProductos = new JInternalFrame("Gestión de Productos", true, true, true, true);
        frameProductos.setSize(600, 400);
        frameProductos.setLayout(new BorderLayout());

        DefaultListModel<Producto> modeloProductos = new DefaultListModel<>();
        JList<Producto> listaProductos = new JList<>(modeloProductos);
        JScrollPane scrollPane = new JScrollPane(listaProductos);

        JPanel panelBotones = new JPanel();
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        btnAgregar.addActionListener(e -> {
            JTextField txtCodigo = new JTextField();
            JTextField txtNombre = new JTextField();
            JTextField txtPrecio = new JTextField();
            JTextField txtStock = new JTextField();

            Object[] campos = {
                "Código:", txtCodigo,
                "Nombre:", txtNombre,
                "Precio:", txtPrecio,
                "Stock:", txtStock
            };

            int opcion = JOptionPane.showConfirmDialog(null, campos, "Agregar Producto", JOptionPane.OK_CANCEL_OPTION);
            if (opcion == JOptionPane.OK_OPTION) {
                Producto producto = new Producto(txtCodigo.getText(), txtNombre.getText(),
                        Double.parseDouble(txtPrecio.getText()), Integer.parseInt(txtStock.getText()));
                productos.add(producto);
                modeloProductos.addElement(producto);
            }
        });

        frameProductos.add(scrollPane, BorderLayout.CENTER);
        frameProductos.add(panelBotones, BorderLayout.SOUTH);
        frameProductos.setVisible(true);
        desktopPane.add(frameProductos);
    }

    private static void abrirModuloFacturacion(JDesktopPane desktopPane) {
        JInternalFrame frameFacturacion = new JInternalFrame("Gestión de Facturación", true, true, true, true);
        frameFacturacion.setSize(600, 400);
        frameFacturacion.setLayout(new BorderLayout());

        JPanel panelContenido = new JPanel(new BorderLayout());
        JTextArea areaFactura = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(areaFactura);
        panelContenido.add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        JButton btnCrearFactura = new JButton("Crear Factura");
        panelBotones.add(btnCrearFactura);

        btnCrearFactura.addActionListener(e -> {
            if (clientes.isEmpty() || productos.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe agregar clientes y productos antes de facturar.");
                return;
            }

            Cliente cliente = (Cliente) JOptionPane.showInputDialog(null, "Seleccione Cliente", "Cliente",
                    JOptionPane.QUESTION_MESSAGE, null, clientes.toArray(), null);

            if (cliente != null) {
                Factura factura = new Factura("F-" + (facturas.size() + 1), "2025-01-10", cliente);

                boolean agregarProductos = true;
                while (agregarProductos) {
                    Producto producto = (Producto) JOptionPane.showInputDialog(null, "Seleccione Producto", "Producto",
                            JOptionPane.QUESTION_MESSAGE, null, productos.toArray(), null);

                    if (producto != null) {
                        String cantidadStr = JOptionPane.showInputDialog("Ingrese la cantidad para " + producto.nombre);
                        int cantidad = Integer.parseInt(cantidadStr);
                        factura.agregarProducto(producto, cantidad);
                    }

                    int opcion = JOptionPane.showConfirmDialog(null, "¿Desea agregar otro producto?", "Continuar",
                            JOptionPane.YES_NO_OPTION);
                    agregarProductos = (opcion == JOptionPane.YES_OPTION);
                }

                facturas.add(factura);
                areaFactura.setText("Factura creada:\n" + factura.toString());
            }
        });

        frameFacturacion.add(panelContenido, BorderLayout.CENTER);
        frameFacturacion.add(panelBotones, BorderLayout.SOUTH);
        frameFacturacion.setVisible(true);
        desktopPane.add(frameFacturacion);
    }
}
