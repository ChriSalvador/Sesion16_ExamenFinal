package com.example.sesion16_examenfinal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.sesion16_examenfinal.model.Feriado;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "public_holidays.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "holidays";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_COUNTRY_NAME = "countryCode";
    private static final String COLUMN_LOCAL_NAME = "localName";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_COUNTRY_NAME + " TEXT, " +
                COLUMN_LOCAL_NAME + " TEXT, " +
                //EVITA VALORES DUPLICADOS
                "UNIQUE (" + COLUMN_DATE + ", " + COLUMN_COUNTRY_NAME + "));";
        sqLiteDatabase.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void registrarFeriado(Feriado feriado) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, feriado.getFecha());
        values.put(COLUMN_COUNTRY_NAME, feriado.getPais());
        values.put(COLUMN_LOCAL_NAME, feriado.getNombreFeriado());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<Feriado> listarFeriados() {
        List<Feriado> feriados = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idIndex= cursor.getColumnIndex(COLUMN_ID);
                int fechaIndex = cursor.getColumnIndex(COLUMN_DATE);
                int paisIndex = cursor.getColumnIndex(COLUMN_COUNTRY_NAME);
                int nombreIndex = cursor.getColumnIndex(COLUMN_LOCAL_NAME);

                int id = cursor.getInt(idIndex);
                String fecha = cursor.getString(fechaIndex);
                String pais = cursor.getString(paisIndex);
                String nombre = cursor.getString(nombreIndex);

                Feriado feriado = new Feriado(id, fecha, pais, nombre);
                feriados.add(feriado);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return feriados;
    }
}
