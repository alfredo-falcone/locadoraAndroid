package br.com.falcone.locadora.model;

/**
 * Created by Alfredo on 22/07/2017.
 */

public class Bem {
    private long id;
    private String nome;
    private String genero;

    private Double precoHora;

    public Bem(long id, String nome, String genero, Double precoHora) {
        this.id = id;
        this.setNome(nome);
        this.setGenero(genero);
        //this.setSite(site);
        this.precoHora = precoHora;
    }

    /*public Bem(){
        this(0, null, null, 0.0);
    }*/

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

    /*public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }*/

    public Double getPrecoHora() {
        return precoHora;
    }

    public void setPrecoHora(Double precoHora) {
        this.precoHora = precoHora;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object instancia) {
        return instancia != null && instancia instanceof Bem
                && ((Bem)instancia).getId() == this.getId();

    }
}
