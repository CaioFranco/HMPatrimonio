package br.senai.hm.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.senai.hm.R;
import br.senai.hm.modelo.Agendamento;
import br.senai.hm.modelo.Ambiente;
import br.senai.hm.modelo.Conferencia;
import br.senai.hm.modelo.Dias;
import br.senai.hm.modelo.Permissao;
import br.senai.hm.modelo.Usuario;
import br.senai.hm.util.WebService;
import android.app.Fragment;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class FragRealizadas extends Fragment {
	
	private ListView lista;
	private ArrayAdapter<Conferencia> adapterRealizada;
	private List<Conferencia> listaConferencia;
	private Ambiente ambiente;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.realizadas, container, false);
        lista = (ListView) rootView.findViewById(R.id.listaRealizadas);
        ambiente = (Ambiente) getActivity().getIntent().getSerializableExtra("ambiente");
        listaConferencia = getRealizadas();
        if (!listaConferencia.isEmpty()) {
            criarSpinner();
		}else{
			Toast.makeText(getActivity(), R.string.aviso_conferencia, Toast.LENGTH_SHORT).show();
		}
        return rootView;
 }
	
	public void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		super.onCreate(savedInstanceState);
	}
	 
	public void criarSpinner(){
		Collections.reverse(listaConferencia);
		adapterRealizada = new ArrayAdapter<Conferencia>(getActivity()  , android.R.layout.simple_list_item_1,listaConferencia);
		lista.setAdapter(adapterRealizada);
		registerForContextMenu(lista);
	 }
	
	public List<Conferencia> getConferenciaWS(){
		String url = WebService.URL+"conferencia/lista";
		String resposta = WebService.makeRequest(url);
		List<Conferencia> lista = new ArrayList<Conferencia>();
		try {
			JSONArray array = new JSONArray(resposta);
			for (int i = 0; i < array.length(); i++) {
				JSONObject job = array.getJSONObject(i);
				Conferencia c = new Conferencia();
				c.setId(job.getLong("id"));
				if (!job.isNull("data")) {
					Date date = new Date(job.getLong("data"));
					c.setData(date);
				}

					JSONObject jobAgendamento = job.getJSONObject("agendamento");
					Agendamento ag = new Agendamento();
					ag.setId(jobAgendamento.getLong("id"));
					Date dataAg = new Date(jobAgendamento.getLong("data"));
					ag.setData(dataAg);
					ag.setLimite(Dias.valueOf(jobAgendamento.getString("limite")));
					
						JSONObject jobAmbiente = jobAgendamento.getJSONObject("ambiente");
						Ambiente ab = new Ambiente();
						ab.setId(jobAmbiente.getLong("id"));
						ab.setDescricao(jobAmbiente.getString("descricao"));
						ab.setStatus(jobAmbiente.getBoolean("status"));
						
							JSONObject jobUsuario = jobAmbiente.getJSONObject("usuario");
							Usuario u = new Usuario();
							u.setId(jobUsuario.getLong("id"));
							u.setEmail(jobUsuario.getString("email"));
							u.setLogin(jobUsuario.getString("login"));
							u.setNome(jobUsuario.getString("nome"));
							u.setSenha(jobUsuario.getString("senha"));
							u.setPermissao(Permissao.valueOf(jobUsuario.getString("permissao")));
							u.setStatus(jobUsuario.getBoolean("status"));
						
						ab.setUsuario(u);			
					ag.setAmbiente(ab);	
				c.setAgendamento(ag);
				//c.setPatrimonio_ausente(patrimonio_ausente);
				lista.add(c);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			//Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		}
		return lista;
	}

	public List<Conferencia> getRealizadas(){
		List<Conferencia> realizadas = new ArrayList<Conferencia>();
		for (Conferencia c : getConferenciaWS()) {
			if (c.getData() != null && c.getAgendamento().getAmbiente().getId().equals(ambiente.getId())) {
				realizadas.add(c);
			}
		}
		return realizadas;
	}
	
	
	
}
