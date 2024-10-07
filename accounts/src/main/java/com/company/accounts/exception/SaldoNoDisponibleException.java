package com.company.accounts.exception;

public class SaldoNoDisponibleException extends RuntimeException {

    public SaldoNoDisponibleException() {
        super("Aviso: Saldo insuficiente para realizar el movimiento");
    }

    public SaldoNoDisponibleException(String message) {
        super(message);
    }

}
