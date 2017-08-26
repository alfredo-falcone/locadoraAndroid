package br.com.falcone.locadora;

import android.content.Context;
import android.location.Location;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import br.com.falcone.locadora.Util.OkHttpUtil;
import br.com.falcone.locadora.model.Aluguel;
import br.com.falcone.locadora.model.Bem;
import br.com.falcone.locadora.model.MeuOpenHelper;
import br.com.falcone.locadora.model.RepositorioBensSQLite;

/**
 * Created by Alfredo on 05/08/2017.
 */

class Global {
    public static final int ID_NOTIFICACAO_DEVOLUCAO = 100000;

    private static Global ourInstance = null;

    private MeuOpenHelper db;

    private RepositorioBensSQLite repositorioBens;

    private List<Bem> bens = null;
    private Location location;
    private int ultimaChave;

    private Context contexto;
    private Hashtable<Integer, Aluguel> alugueis;

    public static synchronized Global getInstance(Context contexto) {
        if(ourInstance == null)
            ourInstance = new Global(contexto);
        return ourInstance;
    }


    private Global(Context contexto) {

        db = new MeuOpenHelper(contexto.getApplicationContext());
        repositorioBens = new RepositorioBensSQLite(db);

        this.contexto = contexto.getApplicationContext();
        bens = repositorioBens.getBens();
        if(bens.size() == 0) {
            repositorioBens.inserir(new Bem(0, "Jet Ski Yamaha", "Navegação", 1.1));
            repositorioBens.inserir(new Bem(0, "Piscina 200L", "Infantil", 2.1));
            repositorioBens.inserir(new Bem(0, "Bóia Dinossauro", "Infantil",  3.1));
            repositorioBens.inserir(new Bem(0, "Caiaque azul", "Navegação",  4.1));
            repositorioBens.inserir(new Bem(0, "Wind Surf", "Navegação", 5.2));
            repositorioBens.inserir(new Bem(0, "Guarda-sol", "Conforto",  6.3));
            repositorioBens.inserir(new Bem(0, "Cadeira listrada", "Conforto", 7.4));
            repositorioBens.inserir(new Bem(0, "Cadeira corpo inteiro", "Conforto",  8.5));
            bens = repositorioBens.getBens();
        }
        alugueis = new Hashtable<>();
        ultimaChave = 0;

    }


    public List<Bem> getBens() {
        return bens.sort();
    }

    public Bem getBemPorId(long id){
        Bem bemProcurar = new Bem(id, null, null, 0.0);
        Bem retorno = null;
        if(bens.contains(bemProcurar))
            retorno = bens.get(bens.indexOf(bemProcurar));
        return retorno;
    }

    public int getProximaChave()
    {
        return ++ultimaChave;
    }


    /*public void setBens(List<Bem> bens) {
        this.bens = bens;
    }*/

    public Hashtable<Integer, Aluguel> getAlugueis() {
        return alugueis;
    }

    public boolean IsBemAlugado(Bem bem)
    {
        return (GetIdAlarme(bem) != null);
    }

    /*public void RemoverAluguel(Integer id){
        this.getAlugueis().remove(id);
    }

    public void InserirAluguel(Bem bem){
        this.getAlugueis().put(getProximaChave(), bem);
    }
*/
    public Location getLocation() {
        if(this.location == null){
             // Recife
            //api.openweathermap.org/data/2.5/weather?lat=-8.05&lon=-34.9&appid=28fe37b6966dc1a90cd10825e168a8ab
            this.location = new Location("");
            this.location.setLatitude(-8.05);
            this.location.setLongitude(-34.9);
        }
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Integer GetIdAlarme(Bem bem)
    {
        Iterator<Integer> chaves = getAlugueis().keySet().iterator();
        Integer idAlarmeRetorno = null;
        while(chaves.hasNext()){
            Integer idAlarmeAtual = chaves.next();
            if(getAlugueis().get(idAlarmeAtual).getBem().equals(bem))
                idAlarmeRetorno = idAlarmeAtual;
        }

        return idAlarmeRetorno;
    }

    public MeuOpenHelper getDb() {
        return db;
    }

    public void InserirBem(Bem bem){
        repositorioBens.inserir(bem);
        this.getBens().add(bem);
    }

    public void ExcluirBem(Bem bem){
        repositorioBens.excluir(bem);
        this.getBens().remove(bem);
    }

    public void AtualizarBem(Bem bem){
        repositorioBens.atualizar(bem);
    }

    public boolean IsConnected(){
        return OkHttpUtil.IsConnected(this.contexto);
    }

    public Double CalcularPercentualAjustePreco(){
        return OkHttpUtil.CalcularPercentualAjustePreco(getLocation());
    }
}
