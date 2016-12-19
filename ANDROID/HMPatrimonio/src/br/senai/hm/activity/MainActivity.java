package br.senai.hm.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import br.senai.hm.R;
import br.senai.hm.modelo.Agendamento;
import br.senai.hm.modelo.Ambiente;
import br.senai.hm.modelo.Conferencia;
import br.senai.hm.modelo.Dias;
import br.senai.hm.modelo.Patrimonio;
import br.senai.hm.modelo.Permissao;
import br.senai.hm.modelo.Usuario;
import br.senai.hm.util.WebService;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity implements OnItemClickListener {
	
	private ListView listOpcoes;
	private TextView  tvGestor ; 
	private List<Conferencia> listaConferencia;
	private Conferencia conferencia;
	private Usuario usuario;
	private Ambiente ambiente;
	private AlertDialog dialogSaida;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menuprincipal);
		tvGestor = (TextView) findViewById(R.id.tvGestor);
		listOpcoes = (ListView) findViewById(R.id.listOpcoes);
		criarMenu();
		usuario = (Usuario)getIntent().getSerializableExtra("usuario");
		conferencia = (Conferencia)getIntent().getSerializableExtra("conferencia");
		listaConferencia = getConferenciaWS();
		ambiente = getAmbienteUsuario(usuario);
		dialogSaida = criarDialog();
		tvGestor.setText(usuario.getNome());
		if (ambiente != null) {
			if (conferencia == null) {
				conferencia = GetConfereciaUsuario(ambiente);
			}
			
		}else{
			Toast.makeText(this, R.string.aviso_ambiente, Toast.LENGTH_SHORT).show();
		}
		if (conferencia != null) {
			//Toast.makeText(this, R.string.conferencia_marcada, Toast.LENGTH_SHORT).show();
		}
	}
	
	private void criarMenu(){
		String[] de = {"imagem","texto"};
		int[] para = {R.id.imageOpcao,R.id.textOpcao};	
		SimpleAdapter adapter = new SimpleAdapter(this, listarOpcoes(), R.layout.lista_opcao, de, para);
		listOpcoes.setAdapter(adapter);
		listOpcoes.setOnItemClickListener(this);	
	}
	
	private List<Map<String, Object>> listarOpcoes(){
		List lista = new ArrayList<Map<String, Object>>();
		Map<String,Object> item = new HashMap<String, Object>();
		
		item.put("imagem", R.drawable.patrimonio_conferencia);
		item.put("texto", getString(R.string.listar));
		lista.add(item);
		
		item = new HashMap<String, Object>();
		item.put("imagem", R.drawable.inconsistencia_reportar);
		item.put("texto", getString(R.string.inconsistencia));
		lista.add(item);
		
		item = new HashMap<String, Object>();
		item.put("imagem", R.drawable.agendamento);
		item.put("texto", getString(R.string.agendadas_realizadas));
		lista.add(item);
		/*
		item = new HashMap<String, Object>();
		item.put("imagem", R.drawable.configuracoes);
		item.put("texto", getString(R.string.configuracoes));
		lista.add(item);*/
		item = new HashMap<String, Object>();
		item.put("imagem", R.drawable.encerrar);
		item.put("texto", getString(R.string.sair));
		lista.add(item);
		
		return lista;
	}
	
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		switch (position) {
		case 0:
			listar();
			break;
		case 1:
			inconsistencia();
			break;
		case 2:
			conferencia();
			break;
		case 3:
			dialogSaida.show();
			break;
		default:
			break;
		}
	}
	
	private AlertDialog criarDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.aviso_sair);
		builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {

				finish();
			}
		});
		builder.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {	
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialogSaida.dismiss();
				
			}
		});
		
		return builder.create();
	}
	
	public void listar (){
		Intent intent = new Intent(this, PatrimonioActivity.class);	
		if (ambiente != null) {
			intent.putExtra("conferencia", conferencia);
			intent.putExtra("ambiente", ambiente);
			intent.putExtra("usuario", usuario);
			startActivity(intent);
			finish();
		}else{
			Toast.makeText(this, R.string.aviso_ambiente, Toast.LENGTH_SHORT).show();
		}
	}
	
	public void conferencia (){
		Intent intent = new Intent(this, ConsultarConferenciaActivity.class);
		if (ambiente != null) {
			intent.putExtra("ambiente", ambiente);
			startActivity(intent);
		}else{
			Toast.makeText(this, R.string.aviso_ambiente, Toast.LENGTH_SHORT).show();
		}
	}
	
	public void inconsistencia (){
		Intent intent = new Intent(this, InconsistenciaActivity.class);		
		if (conferencia != null) {
			intent.putExtra("conferencia", conferencia);
			startActivity(intent);
		}else{
			if (ambiente != null ) {
				conferencia = getConferenciaRealizada(ambiente);
				if (conferencia.getId() != null) {
					intent.putExtra("conferencia", conferencia);
					startActivity(intent);
				}else{
						Toast.makeText(this, R.string.aviso_conferencia, Toast.LENGTH_SHORT).show();
				}
			}else{
				Toast.makeText(this, R.string.aviso_ambiente, Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	public List<Conferencia> getConferenciaWS(){
		String url = WebService.URL+"conferencia/lista";
		String resposta = WebService.makeRequest(url);
		List<Conferencia> lista = new ArrayList<Conferencia>();
		try {
			JSONArray array = new JSONArray(resposta);	
			for (int i = 0; i < array.length(); i++){
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
							
				if (!job.isNull("patrimonio_ausente")){
					List<Patrimonio> ausentes = new ArrayList<Patrimonio>();
					Patrimonio p;
					JSONArray jobArrayPatrimonio = (JSONArray) new JSONTokener(job.getJSONArray("patrimonio_ausente").toString()).nextValue();
					final int tamanho = jobArrayPatrimonio.length();
						for (int x = 0; x < tamanho; x++) {
							JSONObject jobPatrimonio = jobArrayPatrimonio.getJSONObject(x); 
							p = new Patrimonio();
							p.setId(jobPatrimonio.getLong("id"));
							ausentes.add(p);
						}
					c.setPatrimonio_ausente(ausentes);
				}
				lista.add(c);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return lista;
	}

	private Ambiente getAmbienteUsuario(Usuario u){
		String url = WebService.URL+"ambiente/lista";
		String resposta = WebService.makeRequest(url);
		Gson gson = new Gson();
		Type tipoLista = new TypeToken<List<Ambiente>>(){}.getType();
		List<Ambiente> lista = gson.fromJson(resposta, tipoLista);
		for (Ambiente a : lista) {
			if (a.getUsuario().getId().equals(u.getId()) && a.isStatus() ) {
				return a;
			}
		}
		return null;
	}
	
	public Conferencia GetConfereciaUsuario(Ambiente a){
		for (Conferencia c : listaConferencia) {
			if (c.getAgendamento().getAmbiente().getId().equals(a.getId()) && c.getData() == null   )  {
				return c;
			}
		}	
		return null;
	}
	
	private Conferencia getConferenciaRealizada(Ambiente a){
		Conferencia con = new Conferencia();
		for (Conferencia c : listaConferencia) {
			if (c.getAgendamento().getAmbiente().getId().equals(a.getId()) && c.getData() != null  )  {
				con = c;
			}
		}	
		return con;
	}
	
	protected void onDestroy() {
		super.onDestroy();
	}


	
}
