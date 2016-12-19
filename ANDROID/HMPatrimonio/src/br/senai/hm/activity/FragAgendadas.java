package br.senai.hm.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import br.senai.hm.R;
import br.senai.hm.modelo.Agendamento;
import br.senai.hm.modelo.Ambiente;
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

public class FragAgendadas extends Fragment {
	
	private ListView lista;
	private ArrayAdapter<Agendamento> adapterAgendamento;
	private List<Agendamento> listaAgendamento;
	private Ambiente ambiente;
	
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {		
        View rootView = inflater.inflate(R.layout.agendadas, container, false);       
        lista = (ListView) rootView.findViewById(R.id.listaAgendadas);
        ambiente = (Ambiente) getActivity().getIntent().getSerializableExtra("ambiente");
	    listaAgendamento = getAgendamentoUsuario(ambiente);
	    if (!listaAgendamento.isEmpty()) {
        	criarSpinner();
		}else{
			Toast.makeText(getActivity(), R.string.aviso_agendamento, Toast.LENGTH_SHORT).show();
		}
        return rootView;
	 }
	 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		super.onCreate(savedInstanceState);

	}
	 
	public void criarSpinner(){
		Collections.reverse(listaAgendamento);;
		adapterAgendamento = new ArrayAdapter<Agendamento>(getActivity()  , android.R.layout.simple_list_item_1,listaAgendamento);
		lista.setAdapter(adapterAgendamento);
		registerForContextMenu(lista);
	 }
	
	 public List<Agendamento> getAgendamentoWS(){
		 String url = WebService.URL+"agendamento/lista";
			String resposta = WebService.makeRequest(url);
			List<Agendamento> lista = new ArrayList<Agendamento>();
			try {
				JSONArray array = new JSONArray(resposta);
				for (int i = 0; i < array.length(); i++) 
				{
					JSONObject jobAgendamento = array.getJSONObject(i);
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
					lista.add(ag);	
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return lista;
	 }

	 private List<Agendamento> getAgendamentoUsuario(Ambiente amb){
		 List<Agendamento> lista = new ArrayList<Agendamento>();
		 for (Agendamento a : getAgendamentoWS()) {
			if (a.getAmbiente().getId().equals(amb.getId())) {
				lista.add(a);
			}
		}
		 return lista;
	 }
}