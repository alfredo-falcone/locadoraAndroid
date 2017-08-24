package br.com.falcone.locadora.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alfredo on 23/08/2017.
 */

public class RepositorioBensSQLite {

    private MeuOpenHelper helper = null;

    public RepositorioBensSQLite(MeuOpenHelper helper){
        this.helper = helper;
    }
    public List<Bem> getBens(){

        final SQLiteDatabase database = helper.getReadableDatabase();
        final List<Bem> bens = new ArrayList<>();

        try {
            final Cursor cursor = database.rawQuery(String.format("Select %s, %s, %s, %s From %s",
                    MeuOpenHelper.Metadados.TabelaBens.COLUNA_ID,
                    MeuOpenHelper.Metadados.TabelaBens.COLUNA_NOME,
                    MeuOpenHelper.Metadados.TabelaBens.COLUNA_GENERO,
                    MeuOpenHelper.Metadados.TabelaBens.COLUNA_PRECO,
                    MeuOpenHelper.Metadados.TabelaBens.NOME), new String[]{});

            for (int i = 0, size = cursor.getCount(); i < size; i++) {
                cursor.moveToPosition(i);

                bens.add(fromCursor(cursor));
            }

            cursor.close();

            return bens;
        } finally {
            database.close();
        }
    }

    public void inserir(Bem bem){
        final SQLiteDatabase database = helper.getWritableDatabase();

        try {

            ContentValues valores = new ContentValues();
            valores.put(MeuOpenHelper.Metadados.TabelaBens.COLUNA_NOME, bem.getNome());
            valores.put(MeuOpenHelper.Metadados.TabelaBens.COLUNA_GENERO, bem.getGenero());
            valores.put(MeuOpenHelper.Metadados.TabelaBens.COLUNA_PRECO, bem.getPrecoHora());
            database.beginTransaction();
            long id = database.insert(MeuOpenHelper.Metadados.TabelaBens.NOME, null, valores);
            bem.setId(id);
            database.setTransactionSuccessful();
        } catch(Exception e){
            e.printStackTrace();
        }
        finally {
            database.endTransaction();
            database.close();
        }

    }

    public void atualizar(Bem bem){
        final SQLiteDatabase database = helper.getWritableDatabase();

        try {

            String where = MeuOpenHelper.Metadados.TabelaBens.COLUNA_ID +"=?";
            String[] whereArgs = new String[] {String.valueOf(bem.getId())};

            ContentValues valores = new ContentValues();
            valores.put(MeuOpenHelper.Metadados.TabelaBens.COLUNA_NOME, bem.getNome());
            valores.put(MeuOpenHelper.Metadados.TabelaBens.COLUNA_GENERO, bem.getGenero());
            valores.put(MeuOpenHelper.Metadados.TabelaBens.COLUNA_PRECO, bem.getPrecoHora());
            database.beginTransaction();
            database.update(MeuOpenHelper.Metadados.TabelaBens.NOME, valores, where, whereArgs);
            database.setTransactionSuccessful();
        } catch(Exception e){
            e.printStackTrace();
        }
        finally {
            database.endTransaction();
            database.close();
        }

    }

    public void excluir(Bem bem){
        final SQLiteDatabase database = helper.getWritableDatabase();

        try {

            String where = MeuOpenHelper.Metadados.TabelaBens.COLUNA_ID +"=?";
            String[] whereArgs = new String[] {String.valueOf(bem.getId())};

            database.beginTransaction();
            database.delete(MeuOpenHelper.Metadados.TabelaBens.NOME, where, whereArgs);
            database.setTransactionSuccessful();
        } catch(Exception e){
            e.printStackTrace();
        }
        finally {
            database.endTransaction();
            database.close();
        }

    }


    private Bem fromCursor(Cursor cursor){
        int id = cursor.getInt(cursor.getColumnIndex(MeuOpenHelper.Metadados.TabelaBens.COLUNA_ID));
        String nome = cursor.getString(cursor.getColumnIndex(MeuOpenHelper.Metadados.TabelaBens.COLUNA_NOME));
        String genero = cursor.getString(cursor.getColumnIndex(MeuOpenHelper.Metadados.TabelaBens.COLUNA_GENERO));
        Double preco = cursor.getDouble(cursor.getColumnIndex(MeuOpenHelper.Metadados.TabelaBens.COLUNA_PRECO));
        Bem retorno = new Bem(id, nome, genero, preco);
        return retorno;
    }


}
