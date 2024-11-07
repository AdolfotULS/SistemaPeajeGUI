/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author SheratoD
 */
import java.util.ArrayList;

class Peaje {
    private String nombre;
    private String departamento;
    private int totalRecaudado;
    private ArrayList<Vehiculo> vehiculos;

    public Peaje(String nombre, String departamento) {
        this.nombre = nombre;
        this.departamento = departamento;
        this.vehiculos = new ArrayList<>();
        this.totalRecaudado = 0;
    }

    public void agregarVehiculo(Vehiculo vehiculo) {
        vehiculos.add(vehiculo);
        totalRecaudado += vehiculo.calcularPeaje();
    }

    public String getNombre() {
        return nombre;
    }

    public String getDepartamento() {
        return departamento;
    }

    public int getTotalRecaudado() {
        return totalRecaudado;
    }

    public ArrayList<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void mostrarTotalRecaudado() {
        System.out.println("Estacion de Peaje: " + nombre);
        System.out.println("Departamento: " + departamento);
        System.out.println("Vehiculos registrados:");
        for (Vehiculo v : vehiculos) {
            System.out.println("Placa: " + v.getPlaca() + ", Peaje: $" + v.calcularPeaje());
        }
        System.out.println("Total recaudado: $" + totalRecaudado);
    }
}