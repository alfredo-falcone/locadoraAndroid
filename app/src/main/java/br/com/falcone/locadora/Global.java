package br.com.falcone.locadora;

import android.accessibilityservice.FingerprintGestureController;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Alfredo on 05/08/2017.
 */

class Global {
    public static final int ID_NOTIFICACAO_DEVOLUCAO = 100000;

    private static final Global ourInstance = new Global();

    private List<Bem> bens;


    private Hashtable<Integer, Bem> alugueis;

    static Global getInstance() {
        return ourInstance;
    }

    private int ultimaChave;
    private Global() {
        bens = new ArrayList<>();

        bens.add(new Bem("Jet Ski Yamaha", "Navegação", "http://i.imgur.com/DvpvklR.png"));
        bens.add(new Bem("Piscina 200L", "Infantil", "http://i.imgur.com/DvpvklR.png"));
        bens.add(new Bem("Bóia Dinossauro", "Infantil", "http://i.imgur.com/DvpvklR.png"));
        bens.add(new Bem("Caiaque azul", "Navegação", "http://i.imgur.com/DvpvklR.png"));
        bens.add(new Bem("Wind Surf", "Navegação", "http://i.imgur.com/DvpvklR.png"));
        bens.add(new Bem("Guarda-sol", "Conforto", "http://i.imgur.com/DvpvklR.png"));
        bens.add(new Bem("Cadeira listrada", "Conforto", "http://i.imgur.com/DvpvklR.png"));
        bens.add(new Bem("Cadeira corpo inteiro", "Conforto", "http://i.imgur.com/DvpvklR.png"));

        alugueis = new Hashtable<>();
        ultimaChave = 0;

    }


    public List<Bem> getBens() {
        return bens;
    }

    public int getProximaChave()
    {
        return ++ultimaChave;
    }


    public void setBens(List<Bem> bens) {
        this.bens = bens;
    }

    public Hashtable<Integer, Bem> getAlugueis() {
        return alugueis;
    }

    public boolean IsBemAlugado(Bem bem)
    {
        return getAlugueis().contains(bem);
    }

    public void RemoverAluguel(Integer id){
        this.getAlugueis().remove(id);
    }

    public void InserirAluguel(Bem bem){
        this.getAlugueis().put(getProximaChave(), bem);
    }


    public Integer GetIdAlarme(Bem bem)
    {
        Iterator<Integer> chaves = getAlugueis().keySet().iterator();
        Integer idAlarmeRetorno = null;
        while(chaves.hasNext()){
            Integer idAlarmeAtual = chaves.next();
            if(getAlugueis().get(idAlarmeAtual).equals(bem))
                idAlarmeRetorno = idAlarmeAtual;
        }

        return idAlarmeRetorno;
    }

}
