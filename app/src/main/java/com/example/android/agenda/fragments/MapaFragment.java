package com.example.android.agenda.fragments;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import com.example.android.agenda.dao.AlunoDAO;
import com.example.android.agenda.modelos.Aluno;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        // Centralizar a câmera no endereço passado:
        LatLng posicaoInicial = pegaCoordenadas("Rua Nereu de Morais Coelho - Cristo Redentor, João Pessoa - PB");
        if (posicaoInicial != null) {
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(posicaoInicial, 17);
            googleMap.moveCamera(update);
        } else {
            Toast.makeText(getContext(), "Endereço Null", Toast.LENGTH_SHORT).show();
            // OBS: Implementar um tratamento para cadastro do endereço poderia otimizar a busca
        }

        // Colocar marcador no endereço dos alunos cadastrados:
        AlunoDAO alunoDAO = new AlunoDAO(getContext());
        for (Aluno aluno : alunoDAO.buscaAlunos()) {
            LatLng coordenada = pegaCoordenadas(aluno.getEndereco());
            if (coordenada != null) {
                MarkerOptions marcador = new MarkerOptions();
                marcador.position(coordenada);
                marcador.title(aluno.getNome());
                marcador.snippet(String.valueOf(aluno.getNota()));
                googleMap.addMarker(marcador);
            }
        }
        alunoDAO.close();

        // OBS: parâmetro 'googleMap' dá acesso a funcionalidades e listeners para customizar o mapa.

        // new GPSLocationClient(getContext(), googleMap);
    }

    private LatLng pegaCoordenadas(String endereco) {
        try {
            Geocoder geocoder = new Geocoder (getContext());
            List<Address> resultados =
                    geocoder.getFromLocationName(endereco, 1);
            if (!resultados.isEmpty()) {
                LatLng posicao = new LatLng(resultados.get(0).getLatitude(), resultados.get(0).getLongitude());
                return posicao;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
