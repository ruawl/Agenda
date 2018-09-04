package com.example.android.agenda.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.android.agenda.converter.AlunoConverter;
import com.example.android.agenda.dao.AlunoDAO;
import com.example.android.agenda.modelos.Aluno;
import com.example.android.agenda.web.WebClient;

import java.util.List;

public class EnviaAlunosTask extends AsyncTask <Void, Void, String> {

    private Context context;
    private ProgressDialog dialog;

    public EnviaAlunosTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, "Aguarde", "Enviando dados...", true, true);
        // Considerado 'Deprecated' a partir da API 26 do Android por questões de Usabilidade
        // Sugestão de substituto: 'ProgressBar' - (Gera alterações no layout)
    }

    @Override
    protected String doInBackground(Void... params) {
        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();

        AlunoConverter conversor = new AlunoConverter();
        String json = conversor.converteParaJSON(alunos);

        WebClient client = new WebClient();
        String resposta = client.post(json);
        return resposta;
    }

    @Override
    protected void onPostExecute(String resposta) {
        dialog.dismiss();
        Toast.makeText(context, resposta, Toast.LENGTH_LONG).show();
    }
}
