package models;

public class Estudiante implements Comparable<Estudiante> {
    private final String codigo;
    private final String nombre;
    private final int edad;
    private final double nota;
    private final String carrera;

    public Estudiante(String codigo, String nombre, int edad, double nota, String carrera) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.edad = edad;
        this.nota = nota;
        this.carrera = carrera;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public double getNota() {
        return nota;
    }

    public String getCarrera() {
        return carrera;
    }

    @Override
    public int compareTo(Estudiante otro) {
        return this.codigo.compareTo(otro.codigo);
    }

    @Override
    public String toString() {
        return String.format("%-5s | %-8s | %2d | %4.1f | %s", codigo, nombre, edad, nota, carrera);
    }
}