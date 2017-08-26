package br.com.falcone.locadora.model;

import java.util.Calendar;

/**
 * Created by Alfredo on 24/08/2017.
 */

public class Aluguel {
    private Bem bem;
    private double valorTotal;

    public Aluguel(Bem bem, double valorTotal) {
        this.bem = bem;
        this.valorTotal = valorTotal;
    }

    public Bem getBem() {
        return bem;
    }

    public void setBem(Bem bem) {
        this.bem = bem;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double precoHora) {
        this.valorTotal = precoHora;
    }


}
