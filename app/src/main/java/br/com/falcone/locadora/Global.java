package br.com.falcone.locadora;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alfredo on 05/08/2017.
 */

class Global {
    private static final Global ourInstance = new Global();

    private List<Bem> bens;
    static Global getInstance() {
        return ourInstance;
    }

    private Global() {
        bens = new ArrayList<>();

        bens.add(new Bem("Nome1", "genero1", "http://i.imgur.com/DvpvklR.png"));
        bens.add(new Bem("Nome2", "genero2", "http://i.imgur.com/DvpvklR.png"));
        bens.add(new Bem("Nome3", "genero3", "http://i.imgur.com/DvpvklR.png"));
        bens.add(new Bem("Nome4", "genero4", "http://i.imgur.com/DvpvklR.png"));

    }


    public List<Bem> getBens() {
        return bens;
    }

    public void setBens(List<Bem> bens) {
        this.bens = bens;
    }
}
