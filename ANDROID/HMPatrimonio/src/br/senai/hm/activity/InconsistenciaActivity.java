package br.senai.hm.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.senai.hm.R;
import br.senai.hm.modelo.Ambiente;
import br.senai.hm.modelo.Conferencia;
import br.senai.hm.modelo.Inconsistencia;
import br.senai.hm.modelo.Modelo;
import br.senai.hm.modelo.Patrimonio;
import br.senai.hm.modelo.Situacao;
import br.senai.hm.util.WebService;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class InconsistenciaActivity extends Activity {
	
	private ListView lista;
	private List<Inconsistencia> listaInconsistencia;
	private AlertDialog dialogMotivo;
	private Inconsistencia inconsistencia;
	private Conferencia conferencia;
	private int item;
	static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
	
	MyCustomAdapter dataAdapter = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inconsistencia);
		
		lista = (ListView) findViewById(R.id.listaInconsistencia);
		listaInconsistencia = new ArrayList<Inconsistencia>();
		conferencia = (Conferencia) getIntent().getSerializableExtra("conferencia");
		listaInconsistencia = getInconsistenciaConferencia();
		if (listaInconsistencia.isEmpty()) {
			Toast.makeText(this, R.string.nao_patrimonios, Toast.LENGTH_SHORT).show();
		}else{
			criarSpinner();
		}
			
		dialogMotivo = inserirMotivo();
		//criarSpinner();
	}
	
	public void criarSpinner(){
		dataAdapter = new MyCustomAdapter(this, android.R.layout.simple_list_item_1, listaInconsistencia);
		lista.setAdapter(dataAdapter);
		lista.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				item = position;
				inconsistencia = (Inconsistencia) parent.getItemAtPosition(position);
				if (inconsistencia.getMotivo() == null) {
					dialogMotivo.show();
				}else{
					Toast.makeText(getApplicationContext(), R.string.aviso_reporte, Toast.LENGTH_SHORT).show();
				}			
			}		
		});
		registerForContextMenu(lista);
	}
	
	private List<Inconsistencia> getInconsistenciaWS(){
		String url = WebService.URL+"inconsistencia/lista";
		String resposta = WebService.makeRequest(url);
		List<Inconsistencia> lista = new ArrayList<Inconsistencia>();
		try {
			JSONArray array = new JSONArray(resposta);
			for (int i = 0; i < array.length(); i++) {
				JSONObject job = array.getJSONObject(i);
				Inconsistencia in = new Inconsistencia();
				in.setId(job.getLong("id"));
				if (!job.isNull("motivo")) {
					in.setMotivo(job.getString("motivo"));
				}		
					JSONObject jobPatrimonio = job.getJSONObject("patrimonio");
					Patrimonio p = new Patrimonio();
					p.setId(jobPatrimonio.getLong("id"));
					Date date = new Date(jobPatrimonio.getLong("data_entrada"));
					p.setData_entrada(date);
					p.setSituacao(Situacao.valueOf(jobPatrimonio.getString("situacao")));
					p.setStatus(jobPatrimonio.getBoolean("status"));
					p.setValor_unitario(jobPatrimonio.getDouble("valor_unitario"));
					
						JSONObject jobModelo = jobPatrimonio.getJSONObject("modelo");
						Modelo m = new Modelo();
						m.setId(jobModelo.getLong("id"));
						m.setDescricao(jobModelo.getString("descricao"));
						m.setComponente(jobModelo.getString("componente"));
						
					p.setId_modelo(m);
				in.setPatrimonio(p);
				
					JSONObject jobConferencia = job.getJSONObject("conferencia");
					Conferencia c = new Conferencia();
					c.setId(jobConferencia.getLong("id"));
					
				in.setConferencia(c);
				
				lista.add(in);				
			}
		} catch (JSONException e) {
			e.printStackTrace();
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		}
		return lista;
	}
	
	private List<Inconsistencia> getInconsistenciaConferencia(){
		List<Inconsistencia> lista = new ArrayList<Inconsistencia>();
		for (Inconsistencia i : getInconsistenciaWS()) {
			if (i.getConferencia().getId().equals(conferencia.getId())) {
				lista.add(i);
			}
		}
		return lista;
	}
	
	private  AlertDialog inserirMotivo() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.reporta);
		builder.setMessage(R.string.digite);
		final EditText motivo = new EditText(this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
		motivo.setLayoutParams(lp);
		motivo.setHint(R.string.digite_motivo);
		motivo.setInputType(InputType.TYPE_CLASS_TEXT);
	
		builder.setView(motivo);	
		
		builder.setPositiveButton(R.string.enviar, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if (!motivo.getText().toString().isEmpty()) {
					atualizarInconsistencia(motivo.getText().toString());
				}else{
					Toast.makeText(getApplicationContext(), R.string.campo_invalido, Toast.LENGTH_SHORT).show();
				}
				//criarSpinner();
				motivo.setText(null);
			}
		});
		builder.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialogMotivo.dismiss();
			}
		});
		return builder.create();
	}
		
	public void atualizarInconsistencia(String motivo){
		Inconsistencia i = inconsistencia;
		inconsistencia.setMotivo(motivo);
		i.setMotivo(motivo);
			JSONObject job = new JSONObject();
			
			try {
				job.put("id", i.getId());
				job.put("motivo", i.getMotivo());
	
				JSONObject jobPatrimonio = new JSONObject();
				jobPatrimonio.put("id", i.getPatrimonio().getId());
				jobPatrimonio.put("data_entrada",  i.getPatrimonio().getData_entrada().getTime());
				jobPatrimonio.put("situacao",  i.getPatrimonio().getSituacao().name());
				jobPatrimonio.put("valor_unitario",  i.getPatrimonio().getValor_unitario());
				jobPatrimonio.put("status",  i.getPatrimonio().isStatus());
				job.put("id_patrimonio", jobPatrimonio);
				
				JSONObject jobConferencia = new JSONObject();
				jobConferencia.put("id", i.getConferencia().getId());
				job.put("id_conferencia", jobConferencia);
					
				StringEntity se = new StringEntity(job.toString(), HTTP.UTF_8);
				se.setContentType("application/json;charset=UTF-8");
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));
				HttpPost post = new HttpPost(WebService.URL+"inconsistencia/alterar");
				post.setEntity(se);
				HttpClient client = new DefaultHttpClient();
				HttpResponse response = client.execute(post);
				String resposta = EntityUtils.toString(response.getEntity());
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
			}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	private class MyCustomAdapter extends ArrayAdapter<Inconsistencia> {
		
	private List<Inconsistencia> listaInconsistencia;

		public MyCustomAdapter(Context context, int textViewResourceId,List<Inconsistencia> listaInconsistencia) {
			super(context, textViewResourceId, listaInconsistencia);
			this.listaInconsistencia = new ArrayList<Inconsistencia>();
			this.listaInconsistencia.addAll(listaInconsistencia);
			// TODO Auto-generated constructor stub
		}
		
		 private class ViewHolder {
		   TextView id;
		   TextView descricao;
		   TextView modelo;
		   TextView motivo;
		   TextView labelMotivo;
		 }
		 
		 public View getView(int position, View convertView, ViewGroup parent){
			 
			  ViewHolder holder = null;
			  Log.v("ConvertView", String.valueOf(position));
			  
			  if (convertView == null) {
				   LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				   convertView = vi.inflate(R.layout.item2_listview, null);
			  }
			  
			  holder = new ViewHolder();
			  holder.id = (TextView) convertView.findViewById(R.id.id);
			  holder.descricao = (TextView) convertView.findViewById(R.id.descricao);
			  holder.modelo = (TextView) convertView.findViewById(R.id.modelo);
			  holder.motivo = (TextView) convertView.findViewById(R.id.motivo);
			  holder.labelMotivo = (TextView)convertView.findViewById(R.id.labelMotivo);
			  convertView.setTag(holder);
			
			  Inconsistencia i =  listaInconsistencia.get(position);	  
			  holder.id.setText(i.getPatrimonio().getId().toString());
			  holder.descricao.setText(i.getPatrimonio().getDescricao());
			  holder.modelo.setText(i.getPatrimonio().getComponente());
			  if (i.getMotivo() != null) {
				holder.labelMotivo.setVisibility(android.view.View.VISIBLE);
				holder.motivo.setVisibility(android.view.View.VISIBLE);
				holder.motivo.setText(i.getMotivo());
			  }else{
				holder.labelMotivo.setVisibility(android.view.View.INVISIBLE);
				holder.motivo.setVisibility(android.view.View.INVISIBLE);
				holder.motivo.setText(null);
			  }
				  
			 return convertView;
		 }
		 
	
	}

}
