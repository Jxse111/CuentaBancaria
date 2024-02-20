/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cuentabancaria;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author José
 */
public class CuentaBancaria {

//Atributos estáticos y Constantes
    private static double sumaSaldoGlobal;
    private static int numeroCuentasEmbargadas;
    private static LocalDate fechaCreacionCuentaMasModerna;
    public static final double MAX_DESCUBIERTO = -2000.00;
    public static final double MAX_SALDO = 50000000.00;
    public static final int MIN_YEAR = LocalDate.of(1900, 1, 1).getYear();
    public static final double MAX_EMBARGO = 100.0;
    public static final double MIN_EMBARGO = 0.0;
    public static final double DEFAULT_SALDO = 0.00;
    public static final double DEFAULT_MAX_DESCUBIERTO = 0.00;
    public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/YYYY");
//Atributos de clase mutables
    private double saldoActual;
    private double porcentajeEmbargo;
    private double cantidadtotalEurosIngresados;
    private double saldoMaximo;
    private static int identificador = 0;
//Atributos de clase inmutables
    private final int id;
    private final LocalDate fechaCreacion;
    private final double limiteDescubierto;
//Constructor/es

    /**
     * Constructor para crear una cuenta bancaria con saldo inicial, fecha de
     * creación y límite de descubierto.
     *
     * @param saldoInicial El saldo inicial de la cuenta.
     * @param fechaCreacion La fecha de creación de la cuenta.
     * @param limiteDescubierto El límite de descubierto permitido para la
     * cuenta.
     * @throws IllegalArgumentException Si los parámetros proporcionados son
     * inválidos.
     */
    public CuentaBancaria(double saldoInicial, LocalDate fechaCreacion, double limiteDescubierto) throws IllegalArgumentException {
        if (saldoInicial < 0.0 || saldoInicial > MAX_SALDO) {
            throw new IllegalArgumentException(String.format("Error: Parámetros de creación de la cuenta inválidos. Saldo inicial: %.2f\n",
                    saldoInicial));
        } else if (fechaCreacion == null || fechaCreacion.isAfter(LocalDate.now()) || fechaCreacion.getYear() < MIN_YEAR) {
            throw new IllegalArgumentException(String.format("Error: Parámetros de creación de la cuenta inválidos. Fecha de creación: %s\n",
                    fechaCreacion.format(FORMATO_FECHA)));
        } else if (limiteDescubierto > 0 || limiteDescubierto < MAX_DESCUBIERTO) {
            throw new IllegalArgumentException(String.format("Error: Parámetros de creación de la cuenta inválidos. Máximo descubierto: %.2f\n",
                    limiteDescubierto));
        }
        this.saldoActual = saldoInicial;
        this.fechaCreacion = fechaCreacion;
        this.limiteDescubierto = limiteDescubierto;
        this.id = identificador++;
    }

    /**
     * Constructor para crear una cuenta bancaria con saldo inicial y fecha de
     * creación.
     *
     * @param saldoInicial El saldo inicial de la cuenta.
     * @param fechaCreacion La fecha de creación de la cuenta.
     * @throws IllegalArgumentException Si los parámetros proporcionados son
     * inválidos.
     */
    public CuentaBancaria(double saldoInicial, LocalDate fechaCreacion) throws IllegalArgumentException {
        this(saldoInicial, fechaCreacion, DEFAULT_MAX_DESCUBIERTO);
    }

    /**
     * Constructor para crear una cuenta bancaria con saldo inicial y fecha
     * actual como fecha de creación.
     *
     * @param saldoIncial El saldo inicial de la cuenta.
     * @throws IllegalArgumentException Si los parámetros proporcionados son
     * inválidos.
     */
    public CuentaBancaria(double saldoIncial) throws IllegalArgumentException {
        this(saldoIncial, LocalDate.now(), MAX_DESCUBIERTO);
    }

    /**
     * Constructor para crear una cuenta bancaria con saldo inicial 0.0 y fecha
     * actual como fecha de creación.
     */
    public CuentaBancaria() {
        this(DEFAULT_SALDO, LocalDate.now(), DEFAULT_MAX_DESCUBIERTO);
    }

    /**
     * Obtiene el saldo actual de la cuenta.
     *
     * @return El saldo actual de la cuenta.
     */
    public double getSaldo() {
        return this.saldoActual;
    }

    /**
     * Obtiene el porcentaje de embargo de la cuenta.
     *
     * @return El porcentaje de embargo de la cuenta.
     */
    public double getPorcentajeEmbargo() {
        if (this.porcentajeEmbargo >= MIN_EMBARGO && this.porcentajeEmbargo <= MAX_EMBARGO) {
            return this.porcentajeEmbargo;
        }
        return this.porcentajeEmbargo;
    }

    /**
     * Verifica si la cuenta está en descubierto.
     *
     * @return true si la cuenta está en descubierto, false en caso contrario.
     */
    public boolean isDescubierta() {
        boolean descubierto = false;
        if (limiteDescubierto >= MAX_DESCUBIERTO && limiteDescubierto <= 0) {
            descubierto = true;
        }
        return descubierto;
    }

    /**
     * Verifica si la cuenta está embargada.
     *
     * @return true si la cuenta está embargada, false en caso contrario.
     */
    public boolean isEmbargada() {
        boolean embargado = false;
        if (porcentajeEmbargo >= MIN_EMBARGO && porcentajeEmbargo <= MAX_EMBARGO) {
            embargado = true;
        }
        return embargado;
    }

    /**
     * Embarga la cuenta con un porcentaje dado.
     *
     * @param porcentaje El porcentaje de embargo.
     * @throws IllegalArgumentException Si el porcentaje de embargo no es
     * válido.
     * @throws IllegalStateException Si la cuenta ya está embargada.
     */
    public void embargar(double porcentaje) throws IllegalArgumentException, IllegalStateException {
        if (porcentaje <= MIN_EMBARGO || porcentaje > MAX_EMBARGO) {
            throw new IllegalArgumentException(String.format("Error: Porcentaje de embargo no válido: %.2f\n",
                    porcentaje));
        }

        if (isEmbargada()) {
            throw new IllegalStateException("Error: La cuenta ya está embargada\n");
        }

        this.porcentajeEmbargo = porcentaje;
    }

    /**
     * Desembarga la cuenta.
     *
     * @return true si la cuenta estaba embargada y se desembargó, false si la
     * cuenta no estaba embargada.
     */
    public boolean desembargar() {
        if (isEmbargada()) {
            this.porcentajeEmbargo = 0;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Calcula los días de antigüedad de la cuenta.
     *
     * @return El número de días de antigüedad de la cuenta.
     */
    public int fetDiasCuenta() {
        int diasAntiguedad;
        diasAntiguedad = LocalDate.now().getDayOfYear() - fechaCreacion.getDayOfYear();
        return diasAntiguedad;
    }

    /**
     * Realiza un ingreso en la cuenta.
     *
     * @param cantidad La cantidad a ingresar.
     * @throws IllegalArgumentException Si la cantidad de ingreso no es válida.
     * @throws IllegalStateException Si se supera el saldo máximo con el
     * ingreso.
     */
    public void ingresar(double cantidad) throws IllegalArgumentException, IllegalStateException {
        if (cantidad < 0) {
            throw new IllegalArgumentException(String.format("Error: cantidad de ingreso no válida: %.2f",
                    cantidad));
        }

        double ingresoEfectivo = cantidad - porcentajeEmbargo / 100;
        double saldoDespuesIngreso = this.saldoActual + ingresoEfectivo;

        if (saldoDespuesIngreso > MAX_SALDO) {
            throw new IllegalStateException(String.format("Error: saldo máximo superado con este ingreso: %.2f",
                    saldoDespuesIngreso));
        }

        this.saldoActual = saldoDespuesIngreso;
        sumaSaldoGlobal += ingresoEfectivo;
        if (this.saldoActual > this.saldoMaximo) {
            this.saldoMaximo = this.saldoActual;
            fechaCreacionCuentaMasModerna = LocalDate.now();
        }
    }

    /**
     * Obtiene la cantidad total ingresada en la cuenta.
     *
     * @return La cantidad total ingresada en la cuenta.
     */
    public double getTotalIngresado() {
        return this.saldoActual - DEFAULT_SALDO;
    }

    /**
     * Realiza una extracción de dinero de la cuenta.
     *
     * @param cantidad La cantidad a extraer.
     * @throws IllegalArgumentException Si la cantidad de extracción no es
     * válida.
     * @throws IllegalStateException Si se supera el límite de descubierto con
     * la extracción.
     */
    public void extraer(double cantidad) throws IllegalArgumentException, IllegalStateException {
        if (cantidad < 0) {
            throw new IllegalArgumentException(String.format("Error: cantidad de extracción no válida: %.2f",
                    cantidad));
        }

        double saldoDespuesExtraccion = this.saldoActual - cantidad;

        if (saldoDespuesExtraccion < MAX_DESCUBIERTO) {
            throw new IllegalStateException(String.format("Error: descubierto máximo superado con esta extracción: %.2f",
                    saldoDespuesExtraccion));
        }

        this.saldoActual = saldoDespuesExtraccion;
    }

    /**
     * Transfiere una cantidad de dinero desde esta cuenta a otra cuenta
     * bancaria.
     *
     * @param cantidad La cantidad a transferir.
     * @param cuentaDestino La cuenta de destino de la transferencia.
     * @throws IllegalArgumentException Si la cuenta de destino no es válida o
     * la cantidad a transferir no es válida.
     * @throws IllegalStateException Si no hay suficientes fondos en la cuenta
     * de origen, se supera el límite de descubierto, o se supera el saldo
     * máximo en la cuenta de destino.
     */
    public void transferir(double cantidad, CuentaBancaria cuentaDestino) throws IllegalArgumentException, IllegalStateException {
        if (cuentaDestino == null) {
            throw new IllegalArgumentException("Error: cuenta de destino no válida");
        }

        if (cantidad < 0) {
            throw new IllegalArgumentException(String.format("Error: cantidad a transferir no válida: %.2f",
                    cantidad));
        }

        double saldoEfectivoOrigen = this.saldoActual
                - (this.saldoActual * (porcentajeEmbargo / 100));

        if (saldoEfectivoOrigen < cantidad || saldoEfectivoOrigen < MAX_DESCUBIERTO) {
            throw new IllegalStateException(String.format("Error: cantidad no disponible en cuenta origen: %.2f",
                    cantidad));
        }

        double saldoEfectivoDestino = cuentaDestino.saldoActual
                - (cuentaDestino.saldoActual
                * (cuentaDestino.porcentajeEmbargo / 100));

        double incrementoDestino = cantidad - (cantidad
                * (cuentaDestino.porcentajeEmbargo / 100));

        if (saldoEfectivoDestino + incrementoDestino > MAX_SALDO) {
            throw new IllegalStateException(String.format("Error: saldo máximo de cuenta destino superado con esta transferencia: %.2f",
                    saldoEfectivoDestino + incrementoDestino));
        }

        this.saldoActual -= cantidad;
        cuentaDestino.saldoActual += incrementoDestino;

    }

    /**
     * Transfiere todo el saldo de esta cuenta a otra cuenta bancaria.
     *
     * @param cuentaDestino La cuenta de destino de la transferencia.
     * @throws IllegalArgumentException Si la cuenta de destino no es válida.
     * @throws IllegalStateException Si no hay suficientes fondos en la cuenta
     * de origen o se supera el límite de descubierto.
     */
    public void transferir(CuentaBancaria cuentaDestino)
            throws IllegalArgumentException, IllegalStateException {
        double saldoCompleto = this.saldoActual;
        if (this.saldoActual > 0) {
            transferir(saldoCompleto, cuentaDestino);
        } else {
            transferir(0.0, cuentaDestino);
        }
    }

    /**
     * Obtiene el saldo máximo registrado en la cuenta.
     *
     * @return El saldo máximo registrado en la cuenta.
     */
    public double getSaldoMaximo() {
        return this.saldoMaximo;
    }

    /**
     * Obtiene el identificador único de la cuenta.
     *
     * @return El identificador único de la cuenta.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Obtiene la fecha de creación de la cuenta.
     *
     * @return La fecha de creación de la cuenta.
     */
    public LocalDate getFechaCreacion() {
        return this.fechaCreacion;
    }

    /**
     * Obtiene el límite de descubierto permitido para la cuenta.
     *
     * @return El límite de descubierto permitido para la cuenta.
     */
    public double getLimiteDescubierto() {
        return this.limiteDescubierto;
    }

    /**
     * Obtiene la suma total del saldo de todas las cuentas creadas.
     *
     * @return La suma total del saldo de todas las cuentas creadas.
     */
    public static double getSumaSaldoGlobal() {
        return sumaSaldoGlobal;
    }

    /**
     * Obtiene el número total de cuentas embargadas.
     *
     * @return El número total de cuentas embargadas.
     */
    public static int getNumeroCuentasEmbargadas() {
        return numeroCuentasEmbargadas;
    }

    /**
     * Obtiene la fecha de creación de la cuenta más moderna.
     *
     * @return La fecha de creación de la cuenta más moderna.
     */
    public static LocalDate getFechaCreacionCuentaMasModerna() {
        return fechaCreacionCuentaMasModerna;
    }

    /**
     * Devuelve una cadena que representa el estado actual de la cuenta
     * bancaria. El formato de la cadena incluye el identificador de la cuenta,
     * el saldo actual y la indicación de si la cuenta está embargada junto con
     * el porcentaje de embargo. Si la cuenta no está embargada, no se incluye
     * información sobre el porcentaje de embargo.
     *
     * @return Una cadena que representa el estado actual de la cuenta bancaria.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(this.id).append(" - Saldo: ");

        // Formatear el saldo con dos decimales y anchura fija
        sb.append(String.format("%,14.2f", this.saldoActual).replace(',', ' '));

        sb.append(" - Embargada: ");
        if (isEmbargada()) {
            // Si la cuenta está embargada, añadir el porcentaje de embargo
            sb.append("sí ").append(String.format("%5.1f%%", this.porcentajeEmbargo));
        } else {
            sb.append("no");
        }

        return sb.toString();
    }
}
