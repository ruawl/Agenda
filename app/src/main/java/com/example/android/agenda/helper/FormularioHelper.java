package com.example.android.agenda.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.example.android.agenda.R;
import com.example.android.agenda.activities.FormularioActivity;
import com.example.android.agenda.modelos.Aluno;

/**
 * Created by Ruawl on 24/03/2018.
 */

public class FormularioHelper {

    private EditText campoNome;
    private EditText campoEmail;
    private EditText campoEndereco;
    private EditText campoTelefone;
    private EditText campoSite;
    private RatingBar campoNota;
    private ImageView campoFoto;

    private Aluno aluno;


    public FormularioHelper(FormularioActivity activity) {
        campoNome = activity.findViewById(R.id.fomulario_nome);
        campoEmail = activity.findViewById(R.id.fomulario_email);
        campoEndereco = activity.findViewById(R.id.fomulario_endereco);
        campoTelefone = activity.findViewById(R.id.fomulario_telefone);
        campoSite = activity.findViewById(R.id.fomulario_site);
        campoNota = activity.findViewById(R.id.fomulario_nota);
        campoFoto = activity.findViewById(R.id.formulario_foto);
        aluno = new Aluno();
    }

    public Aluno pegaAluno() {
        aluno.setNome(campoNome.getText().toString());
        aluno.setEmail(campoEmail.getText().toString());
        aluno.setEndereco(campoEndereco.getText().toString());
        aluno.setTelefone(campoTelefone.getText().toString());
        aluno.setSite(campoSite.getText().toString());
        aluno.setNota(Double.valueOf(campoNota.getProgress()));
        aluno.setCaminhoFoto((String) campoFoto.getTag());
        return aluno;
    }

    public void preencheFormulario(Aluno aluno) {
        campoNome.setText(aluno.getNome());
        campoEmail.setText(aluno.getEmail());
        campoEndereco.setText(aluno.getEndereco());
        campoTelefone.setText(aluno.getTelefone());
        campoSite.setText(aluno.getSite());
        campoNota.setProgress(aluno.getNota().intValue());
        carregaFoto(aluno.getCaminhoFoto());
        this.aluno = aluno;
    }

    public void carregaFoto(String caminhoFoto) {
        if(caminhoFoto != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
            campoFoto.setImageBitmap(bitmapReduzido);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
            campoFoto.setTag(caminhoFoto);
        }
    }
}
