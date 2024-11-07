/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author SheratoD
 */
class Camion extends Vehiculo {
    private int numeroEjes;

    public Camion(String placa, int numeroEjes) {
        super(placa);
        this.numeroEjes = numeroEjes;
    }

    public int getNumeroEjes() {
        return numeroEjes;
    }

    public int calcularPeaje() {
        return numeroEjes * 5000;
    }
}
