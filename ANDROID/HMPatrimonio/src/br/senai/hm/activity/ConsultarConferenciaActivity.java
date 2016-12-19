package br.senai.hm.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import br.senai.hm.R;
import br.senai.hm.modelo.Agendamento;
import br.senai.hm.modelo.Ambiente;
import br.senai.hm.modelo.Dias;
import br.senai.hm.modelo.Permissao;
import br.senai.hm.modelo.Usuario;
import br.senai.hm.util.TabListener;
import br.senai.hm.util.WebService;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ConsultarConferenciaActivity extends Activity{
	
	ActionBar.Tab tabAgendadas,tabRealizadas;
	Fragment fragAgendadas = new FragAgendadas();
	Fragment fragRealizadas =  new FragRealizadas();
	
	private ListView lista;
	private ArrayAdapter<Agendamento> adapterAgendamento;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.consultar_conferencia);
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		tabAgendadas = actionBar.newTab().setText("Agendadas");
		tabRealizadas = actionBar.newTab().setText("Realizadas");
		
		
		
		tabAgendadas.setTabListener(new TabListener(fragAgendadas));
		tabRealizadas.setTabListener(new TabListener(fragRealizadas));
		
		actionBar.addTab(tabAgendadas);
		actionBar.addTab(tabRealizadas);
		
		
		// lista = (ListView) lista.findViewById(R.id.listaAgendadas);
		 // criarSpinner();
		
	}
	
	 public void criarSpinner(){
		 adapterAgendamento = new ArrayAdapter<Agendamento>(this , android.R.layout.simple_list_item_1, getAgendamentoWS());
		 lista.setAdapter(adapterAgendamento);
		 registerForContextMenu(lista);
	 }
	 
	
	/* private List<Agendamento> getAgendamentoWS(){
			// cria String com URL completa
			String url = WebService.URL+"agendamento/lista";
			//passar a url para o metodo makeRequest e obter a resposta
			String resposta = WebService.makeRequest(url);
			//cria uma instancia de Gson
			Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
			
			 //gson=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			//criar um tipo
			Type tipoLista = new TypeToken<List<Agendamento>>(){}.getType();
			// Converte a resposta em lista de genero
			List<Agendamento> lista = gson.fromJson(resposta, tipoLista);
			//retorna lista
			return lista;
		 }*/

	 public List<Agendamento> getAgendamentoWS(){
		 String url = WebService.URL+"agendamento/lista";
			String resposta = WebService.makeRequest(url);
			List<Agendamento> lista = new ArrayList<Agendamento>();
			try {
				JSONArray array = new JSONArray(resposta);
				for (int i = 0; i < array.length(); i++) 
				{
					//Toast.makeText(this, "Entrou ==="+i, Toast.LENGTH_SHORT).show();
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
				//Toast.makeText(this, "Chegou aqui !!!!!!!", Toast.LENGTH_LONG).show();
			}
			return lista;
	 }
}