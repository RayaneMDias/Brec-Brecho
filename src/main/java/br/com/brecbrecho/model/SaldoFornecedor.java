package br.com.brecbrecho.model;

import java.math.BigDecimal; 

public class SaldoFornecedor {

    private int idSaldo;
    private int idFornecedor;
    private BigDecimal saldoDisponivel;
    private BigDecimal saldoPendente;

   
    public SaldoFornecedor() {
        this.saldoDisponivel = BigDecimal.ZERO;
        this.saldoPendente = BigDecimal.ZERO;
    }

    
    public int getIdSaldo() {
        return idSaldo;
    }

    public void setIdSaldo(int idSaldo) {
        this.idSaldo = idSaldo;
    }

    public int getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(int idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public BigDecimal getSaldoDisponivel() {
        return saldoDisponivel;
    }

    public void setSaldoDisponivel(BigDecimal saldoDisponivel) {
        this.saldoDisponivel = saldoDisponivel;
    }

    public BigDecimal getSaldoPendente() {
        return saldoPendente;
    }

    public void setSaldoPendente(BigDecimal saldoPendente) {
        this.saldoPendente = saldoPendente;
    }
}