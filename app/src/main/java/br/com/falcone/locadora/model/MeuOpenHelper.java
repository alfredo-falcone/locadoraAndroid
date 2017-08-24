package br.com.falcone.locadora.model;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Alfredo on 11/08/2017.
 */

public class MeuOpenHelper extends SQLiteOpenHelper {

    private static final  int DATABASE_VERSION = 1;
    private static final  String DATABASE_NAME = "locadora";

    public MeuOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public MeuOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MeuOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        for(int i = 1; i <= DATABASE_VERSION; i++){
            ExecutarScriptVersao(sqLiteDatabase, i);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int versaoAnterior, int versaoAtual) {
        for(int i = versaoAnterior + 1; i <= versaoAtual; i++){
            ExecutarScriptVersao(sqLiteDatabase, i);
        }

    }

    private void ExecutarScriptVersao(SQLiteDatabase sqLiteDatabase, int versao){
        switch(versao){
            case 1:
                sqLiteDatabase.execSQL(String.format("Create Table %s(%s INTEGER PRIMARY KEY, %s TEXT NOT NULL, %s TEXT NOT NULL, %s REAL NOT NULL);",
                        Metadados.TabelaBens.NOME, Metadados.TabelaBens.COLUNA_ID, Metadados.TabelaBens.COLUNA_NOME,
                        Metadados.TabelaBens.COLUNA_GENERO, Metadados.TabelaBens.COLUNA_PRECO));
                break;
        }
    }


    public static final class Metadados{
         public static final class TabelaBens{
             public static final String NOME = "bens";
             public static final String COLUNA_ID = "id";
             public static final String COLUNA_NOME = "nome";
             public static final String COLUNA_GENERO = "genero";
             public static final String COLUNA_PRECO = "precoHora";


         }

    }


}
