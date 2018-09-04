package com.example.android.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.example.android.agenda.modelos.Aluno;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ruawl on 25/03/2018.
 */

public class AlunoDAO extends SQLiteOpenHelper {

    public AlunoDAO(Context context) {
        // Versão 2: modificação para incluir foto de perfil no cadastro
        super(context, "Agenda", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Alunos ( id INTEGER PRIMARY KEY, " +
                                            "nome TEXT NOT NULL, " +
                                            "email TEXT, " +
                                            "endereco TEXT, " +
                                            "telefone TEXT, " +
                                            "site TEXT, " +
                                            "nota REAL, " +
                                            "caminhoFoto TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "";
        switch (oldVersion) {
            case 1: sql = "ALTER TABLE Alunos ADD COLUMN caminhoFoto TEXT";
                    db.execSQL(sql);
                    // Sem o 'break;' para atualizar todas as eventuais diferentes versões
        }
    }

    public void insere(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = getContentValuesAluno(aluno);
        db.insert("Alunos", null, dados);
    }

    public void altera(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = getContentValuesAluno(aluno);
        String[] params ={String.valueOf(aluno.getId())};
        db.update("Alunos", dados, "id = ?", params);
    }

    public void deleta(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();

        String[] params ={String.valueOf(aluno.getId())};
        db.delete("Alunos", "id = ?", params);
    }

    public List<Aluno> buscaAlunos() {
        String sql = "SELECT * FROM Alunos;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Aluno> alunos = new ArrayList<Aluno>();

        while (c.moveToNext()) {
            Aluno aluno = new Aluno();

            aluno.setId(c.getLong(c.getColumnIndex("id")));
            aluno.setNome(c.getString(c.getColumnIndex("nome")));
            aluno.setEmail(c.getString(c.getColumnIndex("email")));
            aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
            aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
            aluno.setSite(c.getString(c.getColumnIndex("site")));
            aluno.setNota(c.getDouble(c.getColumnIndex("nota")));
            aluno.setCaminhoFoto((c.getString(c.getColumnIndex("caminhoFoto"))));

            alunos.add(aluno);
        }

        c.close();

        return alunos;
    }

    @NonNull
    private ContentValues getContentValuesAluno(Aluno aluno) {
        ContentValues dados = new ContentValues();
        dados.put("nome", aluno.getNome());
        dados.put("email", aluno.getEmail());
        dados.put("endereco", aluno.getEndereco());
        dados.put("telefone", aluno.getTelefone());
        dados.put("site", aluno.getSite());
        dados.put("nota", aluno.getNota());
        dados.put("caminhoFoto", aluno.getCaminhoFoto());
        return dados;
    }

    public boolean isAluno(String telefone) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Alunos WHERE telefone = ?", new String[] {telefone});
        int resultados = c.getCount();
        c.close();
        return resultados > 0;
    }
}
