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
        sqLiteDatabase.execSQL("Create Table bens(id INTEGER PRIMARY KEY, nome TEXT, genero TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int versaoAnterior, int versaoAtual) {

    }


}
