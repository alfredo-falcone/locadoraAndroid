package br.com.falcone.locadora;

/**
 * Created by Alfredo on 22/07/2017.
 */

public class Bem {
    private String nome;
    private String genero;
    private String site;

    public Bem(String nome, String genero, String site) {
        this.setNome(nome);
        this.setGenero(genero);
        this.setSite(site);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
