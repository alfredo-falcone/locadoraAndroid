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

        bens.add(new Bem("Jet Ski Yamaha", "Navegação", "http://i.imgur.com/DvpvklR.png"));
        bens.add(new Bem("Piscina 200L", "Infantil", "http://i.imgur.com/DvpvklR.png"));
        bens.add(new Bem("Bóia Dinossauro", "Infantil", "http://i.imgur.com/DvpvklR.png"));
        bens.add(new Bem("Caiaque azul", "Navegação", "http://i.imgur.com/DvpvklR.png"));
        bens.add(new Bem("Wind Surf", "Navegação", "http://i.imgur.com/DvpvklR.png"));
        bens.add(new Bem("Guarda-sol", "Conforto", "http://i.imgur.com/DvpvklR.png"));
        bens.add(new Bem("Cadeira listrada", "Conforto", "http://i.imgur.com/DvpvklR.png"));
        bens.add(new Bem("Cadeira corpo inteiro", "Conforto", "http://i.imgur.com/DvpvklR.png"));

    }


    public List<Bem> getBens() {
        return bens;
    }

    public void setBens(List<Bem> bens) {
        this.bens = bens;
    }
}
