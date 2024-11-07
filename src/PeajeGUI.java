/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author SheratoD
 */
import javax.swing.*;
import java.awt.*; 
import java.awt.event.*; 
import javax.swing.border.*; 
import java.util.ArrayList;

public class PeajeGUI extends JFrame implements ActionListener {
    // Componentes para entrada de datos
    private JComboBox<String> tipoVehiculoCombo; // Lista desplegable para tipo de vehiculo
    private JComboBox<Integer> numEjesCombo; // Lista desplegable para numero de ejes
    private JTextField placaField; // Campo de texto para la placa
    private JTextField totalField; // Campo de texto para mostrar el total
    private JTextArea resumenArea; // Area de texto para el resumen

    // Botones
    private JButton pagarButton; // Boton para procesar el pago
    private JButton cancelarButton; // Boton para cancelar la operacion
    private JButton cerrarPeajeButton; // Boton para cerrar el peaje
    private JButton reabrirPeajeButton; // Boton para reabrir el peaje

    // Objeto del peaje
    private Peaje peaje;

    /**
     * Constructor de la clase
     * Inicializa el peaje y configura la interfaz grafica
     */
    public PeajeGUI() {
        // Crear una nueva instancia de Peaje
        peaje = new Peaje("Peaje Las Cardas", "La Serena");
        // Configurar la interfaz grafica
        setupUI();
    }

    private void setupUI() {
        // ======== CONFIGURACION BASICA DE LA VENTANA ========
        setTitle("Sistema Plaza de Peaje");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cerrar programa al cerrar ventana
        setLayout(new BorderLayout(10, 10)); // Layout con espaciado

        // ======== PANEL PRINCIPAL ========
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Margen exterior

        // ======== PANEL DE ENTRADA DE DATOS ========
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5)); // 5 filas, 2 columnas

        // Configuracion del selector de tipo de vehiculo
        inputPanel.add(new JLabel("Tipo de Vehiculo:"));
        String[] tipos = { "Seleccione Tipo", "Carro", "Moto", "Camion" };
        tipoVehiculoCombo = new JComboBox<>(tipos);
        tipoVehiculoCombo.addActionListener(this); // Agregar listener para eventos
        inputPanel.add(tipoVehiculoCombo);

        // Configuracion del selector de numero de ejes
        inputPanel.add(new JLabel("Cantidad Ejes:"));
        Integer[] ejes = { 2, 3, 4, 5, 6 };
        numEjesCombo = new JComboBox<>(ejes);
        numEjesCombo.setEnabled(false); // Inicialmente deshabilitado
        numEjesCombo.addActionListener(this);
        inputPanel.add(numEjesCombo);

        // Configuracion del campo de placa
        inputPanel.add(new JLabel("Placa:"));
        placaField = new JTextField();
        inputPanel.add(placaField);

        // Configuracion del campo de total
        inputPanel.add(new JLabel("Total Peaje:"));
        totalField = new JTextField();
        totalField.setEditable(false); // Campo solo de lectura
        inputPanel.add(totalField);

        // ======== PANEL DE BOTONES ========
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Configuracion del boton Pagar
        pagarButton = new JButton("Pagar");
        pagarButton.addActionListener(this);
        buttonPanel.add(pagarButton);

        // Configuracion del boton Cancelar
        cancelarButton = new JButton("Cancelar");
        cancelarButton.addActionListener(this);
        buttonPanel.add(cancelarButton);

        // Configuracion del boton Cerrar Peaje
        cerrarPeajeButton = new JButton("Cerrar Peaje");
        cerrarPeajeButton.addActionListener(this);
        buttonPanel.add(cerrarPeajeButton);

        // Configuracion del boton Reabrir Peaje
        reabrirPeajeButton = new JButton("Re-abrir Peaje");
        reabrirPeajeButton.addActionListener(this);
        reabrirPeajeButton.setEnabled(false); // Inicialmente deshabilitado
        buttonPanel.add(reabrirPeajeButton);

        // ======== PANEL DE RESUMEN ========
        JPanel resumenPanel = new JPanel(new BorderLayout());
        resumenPanel.setBorder(BorderFactory.createTitledBorder("Resumen Peaje"));
        resumenArea = new JTextArea(15, 30); // 15 filas, 30 columnas
        resumenArea.setEditable(false); // Area solo de lectura
        JScrollPane scrollPane = new JScrollPane(resumenArea); // Agregar scroll
        resumenPanel.add(scrollPane);

        // ======== ENSAMBLAJE FINAL DE PANELES ========
        mainPanel.add(inputPanel, BorderLayout.NORTH); // Panel de entrada arriba
        mainPanel.add(buttonPanel, BorderLayout.CENTER); // Botones en el centro
        add(mainPanel, BorderLayout.WEST); // Panel principal a la izquierda
        add(resumenPanel, BorderLayout.EAST); // Resumen a la derecha

        // ======== CONFIGURACION FINAL DE LA VENTANA ========
        pack(); // Ajustar tamano de la ventana al contenido
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        actualizarResumen(); // Mostrar el resumen inicial
    }

    public void actionPerformed(ActionEvent e) {
        // ======== EVENTOS DE SELECCION DE VEHICULO ========
        if (e.getSource() == tipoVehiculoCombo) {
            // Verificar si se selecciono un camion
            boolean esCamion = tipoVehiculoCombo.getSelectedItem().equals("Camion");
            numEjesCombo.setEnabled(esCamion); // Habilitar selector de ejes solo para camiones
            actualizarTotal(); // Actualizar el monto a pagar
        }
        // ======== EVENTOS DE CAMBIO DE EJES ========
        else if (e.getSource() == numEjesCombo) {
            actualizarTotal(); // Actualizar el monto cuando cambia el numero de ejes
        }
        // ======== EVENTOS DE BOTONES ========
        else if (e.getSource() == pagarButton) {
            procesarPago(); // Procesar el pago del peaje
        } else if (e.getSource() == cancelarButton) {
            limpiarCampos(); // Limpiar todos los campos
        } else if (e.getSource() == cerrarPeajeButton) {
            mostrarResumenFinal(); // Mostrar ventana con resumen final
            reabrirPeajeButton.setEnabled(true); // Habilitar boton de reapertura
            cancelarButton.setEnabled(false); // Deshabilitar boton de cancelar
        } else if (e.getSource() == reabrirPeajeButton) {
            reabrirPeaje(); // Reabrir el peaje
        }
    }

    /**
     * Calcula y actualiza el total a pagar segun el tipo de vehiculo
     * Los montos son:
     * - Carro: $10,000
     * - Moto: $5,000
     * - Camion: $5,000 por eje
     */
    private void actualizarTotal() {
        String tipo = (String) tipoVehiculoCombo.getSelectedItem();
        int total = 0;

        // Calcular total segun tipo de vehiculo
        switch (tipo) {
            case "Carro":
                total = 10000;
                break;
            case "Moto":
                total = 5000;
                break;
            case "Camion":
                int ejes = (Integer) numEjesCombo.getSelectedItem();
                total = ejes * 5000; // $5000 por cada eje
                break;
        }

        totalField.setText("$" + total);
    }

    /**
     * Procesa el pago del peaje validando los datos y registrando el vehiculo
     */
    private void procesarPago() {
        // Obtener datos ingresados
        String placa = placaField.getText();
        String tipo = (String) tipoVehiculoCombo.getSelectedItem();

        // Validar que los campos esten completos
        if (placa.isEmpty() || tipo.equals("Seleccione Tipo")) {
            JOptionPane.showMessageDialog(this,
                    "Por favor complete todos los campos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear el vehiculo segun su tipo
        Vehiculo vehiculo = null;
        switch (tipo) {
            case "Carro":
                vehiculo = new Carro(placa);
                break;
            case "Moto":
                vehiculo = new Moto(placa);
                break;
            case "Camion":
                int ejes = (Integer) numEjesCombo.getSelectedItem();
                vehiculo = new Camion(placa, ejes);
                break;
        }

        // Registrar el vehiculo y actualizar la interfaz
        if (vehiculo != null) {
            peaje.agregarVehiculo(vehiculo); // Agregar vehiculo al registro
            actualizarResumen(); // Actualizar panel de resumen
            limpiarCampos(); // Limpiar campos para nuevo ingreso
        }
    }

    /**
     * Maneja la reapertura del peaje, preguntando si se desean mantener los datos
     */
    private void reabrirPeaje() {
        // Preguntar al usuario si desea borrar los datos actuales
        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Desea borrar los datos actuales?",
                "Reabrir Peaje",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        // Si el usuario acepta, crear un nuevo peaje (borra datos)
        if (opcion == JOptionPane.YES_OPTION) {
            peaje = new Peaje(peaje.getNombre(), peaje.getDepartamento());
        }

        // Rehabilitar todos los controles
        tipoVehiculoCombo.setEnabled(true);
        numEjesCombo.setEnabled(false); // Se habilitara solo para camiones
        placaField.setEnabled(true);
        pagarButton.setEnabled(true);
        cerrarPeajeButton.setEnabled(true);
        reabrirPeajeButton.setEnabled(false);
        cancelarButton.setEnabled(true);

        // Limpiar campos y actualizar visualizacion
        limpiarCampos();
        actualizarResumen();
    }

    /**
     * Muestra una ventana con el resumen final detallado del peaje
     * Incluye lista de vehiculos y totales
     */
    private void mostrarResumenFinal() {
        // Crear nueva ventana para el resumen
        JFrame resumenFrame = new JFrame("Resumen Final del Peaje");
        resumenFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Configurar area de texto con fuente monoespaciada para mejor formato
        JTextArea resumenFinalArea = new JTextArea(20, 40);
        resumenFinalArea.setEditable(false);
        resumenFinalArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        // Construir el contenido del resumen
        StringBuilder resumen = new StringBuilder();
        resumen.append("Peaje: ").append(peaje.getNombre()).append("\n");
        resumen.append("Departamento: ").append(peaje.getDepartamento()).append("\n\n");
        resumen.append("LISTA DE VEHICULOS Y PATENTES:\n");
        resumen.append("================================\n\n");

        // Contadores para el resumen
        int carros = 0, motos = 0, camiones = 0;
        int totalVehiculos = 0;
        int totalRecaudado = 0;

        // Generar lista detallada de vehiculos
        for (Vehiculo v : peaje.getVehiculos()) {
            if (v instanceof Carro) {
                // Formato: Tipo | Placa | Peaje
                resumen.append(String.format("%-8s | Placa: %-10s | Peaje: $%d\n",
                        "Carro", v.getPlaca(), v.calcularPeaje()));
                carros++;
            } else if (v instanceof Moto) {
                resumen.append(String.format("%-8s | Placa: %-10s | Peaje: $%d\n",
                        "Moto", v.getPlaca(), v.calcularPeaje()));
                motos++;
            } else if (v instanceof Camion) {
                Camion c = (Camion) v;
                // Para camiones se incluye el numero de ejes
                resumen.append(String.format("%-8s | Placa: %-10s | Ejes: %d | Peaje: $%d\n",
                        "Camion", v.getPlaca(), c.getNumeroEjes(), v.calcularPeaje()));
                camiones++;
            }
            totalVehiculos++;
            totalRecaudado += v.calcularPeaje();
        }

        // Agregar resumen estadistico
        resumen.append("\n================================\n");
        resumen.append("\nRESUMEN FINAL:\n");
        resumen.append("Vehiculos registrados:\n");
        resumen.append("Automoviles: ").append(carros).append("\n");
        resumen.append("Motocicletas: ").append(motos).append("\n");
        resumen.append("Camiones: ").append(camiones).append("\n");
        resumen.append("Total Vehiculos: ").append(totalVehiculos).append("\n\n");
        resumen.append("TOTAL RECAUDADO: $").append(totalRecaudado);

        // Mostrar el resumen en la ventana
        resumenFinalArea.setText(resumen.toString());

        // Agregar scroll en caso de que el contenido sea muy largo
        JScrollPane scrollPane = new JScrollPane(resumenFinalArea);
        resumenFrame.add(scrollPane);

        // Configurar y mostrar la ventana
        resumenFrame.pack();
        resumenFrame.setLocationRelativeTo(this);
        resumenFrame.setVisible(true);

        // Deshabilitar controles principales
        tipoVehiculoCombo.setEnabled(false);
        numEjesCombo.setEnabled(false);
        placaField.setEnabled(false);
        pagarButton.setEnabled(false);
        cerrarPeajeButton.setEnabled(false);
    }

    /**
     * Limpia todos los campos de entrada para un nuevo registro
     */
    private void limpiarCampos() {
        tipoVehiculoCombo.setSelectedIndex(0); // Volver a "Seleccione Tipo"
        placaField.setText(""); // Limpiar campo de placa
        totalField.setText(""); // Limpiar campo de total
        numEjesCombo.setEnabled(false); // Deshabilitar selector de ejes
    }

    /**
     * Actualiza el panel de resumen con la informacion actual del peaje
     */
    private void actualizarResumen() {
        StringBuilder resumen = new StringBuilder();
        resumen.append("Peaje: ").append(peaje.getNombre()).append("\n");
        resumen.append("Departamento: ").append(peaje.getDepartamento()).append("\n\n");
        resumen.append("LISTA DE VEHICULOS:\n");
        resumen.append("------------------\n");

        // Contadores para el resumen
        int carros = 0, motos = 0, camiones = 0;
        int totalVehiculos = 0;
        int totalRecaudado = 0;

        // Procesar cada vehiculo
        for (Vehiculo v : peaje.getVehiculos()) {
            if (v instanceof Carro) {
                resumen.append("Carro - Placa: ").append(v.getPlaca());
                carros++;
            } else if (v instanceof Moto) {
                resumen.append("Moto - Placa: ").append(v.getPlaca());
                motos++;
            } else if (v instanceof Camion) {
                Camion c = (Camion) v;
                resumen.append("Camion - Placa: ").append(v.getPlaca())
                        .append(" (").append(c.getNumeroEjes()).append(" ejes)");
                camiones++;
            }
            resumen.append(" - Peaje: $").append(v.calcularPeaje()).append("\n");
            totalVehiculos++;
            totalRecaudado += v.calcularPeaje();
        }

        // Agregar resumen estadistico
        resumen.append("\nVehiculos registrados:\n");
        resumen.append("Automoviles: ").append(carros).append("\n");
        resumen.append("Motocicletas: ").append(motos).append("\n");
        resumen.append("Camiones: ").append(camiones).append("\n");
        resumen.append("Total Vehiculos: ").append(totalVehiculos).append("\n\n");
        resumen.append("Dinero Total: $").append(totalRecaudado);

        // Actualizar el area de texto
        resumenArea.setText(resumen.toString());
    }

    /**
     * Metodo principal que inicia la aplicacion
     */
    public static void main(String[] args) {
        // Ejecutar la creacion de la GUI en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            new PeajeGUI().setVisible(true);
        });
    }
}