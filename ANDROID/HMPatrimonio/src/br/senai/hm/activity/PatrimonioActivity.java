package br.senai.hm.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import br.senai.hm.modelo.Categoria;
import br.senai.hm.modelo.Conferencia;
import br.senai.hm.modelo.Dias;
import br.senai.hm.modelo.Fornecedor;
import br.senai.hm.modelo.Inconsistencia;
import br.senai.hm.modelo.Modelo;
import br.senai.hm.modelo.Patrimonio;
import br.senai.hm.modelo.Permissao;
import br.senai.hm.modelo.Situacao;
import br.senai.hm.modelo.Usuario;
import br.senai.hm.util.WebService;
import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PatrimonioActivity extends Activity {
	
	private ListView lista;
	private List<Patrimonio> listaPatrimonio;
	private AlertDialog dialogOpcoes,dialogManual,dialogEncerrar;
	private Conferencia conferencia;
	private List<Patrimonio> encontrados = new ArrayList<Patrimonio>();
	static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
	private Ambiente ambiente;
	private Usuario usuario;
	private boolean validar = false;
	int qtd,qtdAusente;
	MyCustomAdapter dataAdapter = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.conferencia_patrimonios);
		
		lista = (ListView) findViewById(R.id.listaPatrimonios);
		listaPatrimonio = new ArrayList<Patrimonio>();
		dialogOpcoes = opcoesVerificar();
		dialogManual = opcaoManual();
		dialogEncerrar = dialogEncerrar();
		conferencia = (Conferencia) getIntent().getSerializableExtra("conferencia");
		ambiente = (Ambiente) getIntent().getSerializableExtra("ambiente");
		usuario = (Usuario) getIntent().getSerializableExtra("usuario");
		listaPatrimonio = getPatrimonioAmbiente();
		if (!listaPatrimonio.isEmpty()) {
			criarSpinner();
		}else{
			Toast.makeText(this, R.string.nao_patrimonios, Toast.LENGTH_SHORT).show();
		}
		try {
			if (conferencia.getId() != null) {
				validar = validarData(conferencia);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void criarSpinner(){
		dataAdapter =  new MyCustomAdapter(this	, R.layout.item_listview, listaPatrimonio);
		lista.setAdapter(dataAdapter);
		registerForContextMenu(lista);
	}
		
	public List<Patrimonio> getPatrimonioWS(){
		String url = WebService.URL+"patrimonio/lista";
		String resposta = WebService.makeRequest(url);
		List<Patrimonio> lista = new ArrayList<Patrimonio>();
		try {
			JSONArray array = new JSONArray(resposta);
			for (int i = 0; i < array.length(); i++) 
			{
				JSONObject job = array.getJSONObject(i);
				Patrimonio p = new Patrimonio();
				p.setId(job.getLong("id"));
				Date date = new Date(job.getLong("data_entrada"));
				p.setData_entrada(date);
				p.setSituacao(Situacao.valueOf(job.getString("situacao")));
				p.setStatus(job.getBoolean("status"));
				p.setValor_unitario(job.getDouble("valor_unitario"));
				
				JSONObject jobModelo = job.getJSONObject("modelo");
				Modelo m = new Modelo();
				m.setId(jobModelo.getLong("id"));
				m.setComponente(jobModelo.getString("componente"));
				m.setDescricao(jobModelo.getString("descricao"));
				m.setDetalhe(jobModelo.getString("detalhe"));
				m.setMarca(jobModelo.getString("marca"));
				m.setStatus(jobModelo.getBoolean("status"));
				m.setFoto(jobModelo.getString("foto").getBytes());
				
					JSONObject jobCategoria = jobModelo.getJSONObject("categoria");
					Categoria c = new Categoria();
					c.setId(jobCategoria.getLong("id"));
					c.setDescricao(jobCategoria.getString("descricao"));
					c.setDepreciacao(jobCategoria.getDouble("depreciacao"));
					c.setVida_util(jobCategoria.getInt("vida_util"));
					c.setStatus(jobCategoria.getBoolean("status"));
					
				m.setCategoria(c);
				
					JSONObject jobFornecedor = jobModelo.getJSONObject("fornecedor");
					Fornecedor f = new Fornecedor();
					f.setId(jobFornecedor.getLong("id"));
					f.setCnpj(jobFornecedor.getString("cnpj"));
					f.setEmail(jobFornecedor.getString("email"));
					f.setNome(jobFornecedor.getString("nome"));
					f.setTelefone(jobFornecedor.getString("telefone"));
					f.setStatus(jobFornecedor.getBoolean("status"));
			
				m.setFornecedor(f);
				
				p.setId_modelo(m);
				
				JSONObject jobAmbiente = job.getJSONObject("ambiente");
				Ambiente a = new Ambiente();
				a.setId(jobAmbiente.getLong("id"));
				a.setDescricao(jobAmbiente.getString("descricao"));
				a.setStatus(jobAmbiente.getBoolean("status"));
					
					JSONObject jobUsuario = jobAmbiente.getJSONObject("usuario");
					Usuario u = new Usuario();
					u.setId(jobUsuario.getLong("id"));
					u.setEmail(jobUsuario.getString("email"));
					u.setLogin(jobUsuario.getString("login"));
					u.setNome(jobUsuario.getString("nome"));
					u.setSenha(jobUsuario.getString("senha"));
					u.setPermissao(Permissao.valueOf(jobUsuario.getString("permissao")));
					u.setStatus(jobUsuario.getBoolean("status"));
					
				a.setUsuario(u);
				
				p.setId_ambiente(a);
				
				lista.add(p);
				
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		}
		return lista;
	}
	
	private List<Patrimonio> getPatrimonioAmbiente(){	
		List<Patrimonio> lista = new ArrayList<Patrimonio>();
		qtd = 0;
		for (Patrimonio p : getPatrimonioWS()) {
			if (p.getId_ambiente().getId().equals(ambiente.getId())) {
				lista.add(p);
				qtd++;
			}
		}
		return lista;
	}
	
	public void verificar(View v){
		if (validar) {
			dialogOpcoes.show();
			
		}else{
			Toast.makeText(this, R.string.aviso_agendadas, Toast.LENGTH_SHORT).show();
		}
	}
	
	private void viaQRcode(){
		try {
			 //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);			
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");			
            startActivityForResult(intent, 0);		

		 	} catch (ActivityNotFoundException anfe) {			
            showDialog(PatrimonioActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
		 	 anfe.printStackTrace();
		 	}
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {	
            if (resultCode == RESULT_OK) {	
            	String patrimonio = new String();
            	int end = 0;
                //get the extras that are returned from the intent	
            	String contents = intent.getStringExtra("SCAN_RESULT");	
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");		
                for (int i = 0; i < contents.length();i++) {
                	if (contents.charAt(i) == 'E' && end == 0) {
						end = i;
					}
				}        
                patrimonio = contents.substring(3, end);
                Toast.makeText(this, patrimonio.trim(), Toast.LENGTH_SHORT).show();	
                try {
					 Long id = Long.parseLong(patrimonio.trim(),10);
					 conferirpatrimonio(id);
				} catch (Exception e) {
					Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
				}      
            }	
        }	
	}
	
	public void conferirpatrimonio(Long id){
		boolean contem = false;
		int indice = 0;
			for (Patrimonio p : listaPatrimonio) {	
				if (id.equals(p.getId()) ){
					encontrados.add(p);
						
					contem = true;
					indice = encontrados.indexOf(p);
				}
			}
			if (contem) {
				Toast.makeText(this, R.string.conferido, Toast.LENGTH_SHORT).show();	
			}else{
				Toast.makeText(this, R.string.nao_encontrado, Toast.LENGTH_SHORT).show();	
			}
			criarSpinner();
	}
	
	public void finalizar(View v){
		if (validar) {
			dialogEncerrar.show();
		}else{			
			Toast.makeText(this, R.string.aviso_agendadas, Toast.LENGTH_SHORT).show();
		}
		
	}
	
	private boolean validarData(Conferencia c){
		int x = Integer.parseInt(c.getAgendamento().getLimite().toString());
		Calendar hoje = Calendar.getInstance();
		Calendar dataConferencia= Calendar.getInstance();
		Calendar dataLimite= Calendar.getInstance();
		hoje.getTime();
		dataConferencia.setTime(c.getAgendamento().getData());
		dataLimite.setTime(c.getAgendamento().getData());	
		dataLimite.add(Calendar.DATE, x);
		String h =  new SimpleDateFormat("dd/MM/yyyy").format(hoje.getTime());
		String conf =  new SimpleDateFormat("dd/MM/yyyy").format(dataConferencia.getTime());
		String limite =  new SimpleDateFormat("dd/MM/yyyy").format(dataLimite.getTime());
		if (dataConferencia.after(hoje) && !(conf.equals(h))  ) {                                               
			return false;
		}else if (conf.equals(h) || hoje.before(dataLimite)) {
			return true;
		}
		return false;
	}
	
	private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
		AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
		downloadDialog.setTitle(title);
		downloadDialog.setMessage(message);
		downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialogInterface, int i) {
				Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				try {
					act.startActivity(intent);
				} catch (ActivityNotFoundException anfe) {

				}
			}
		});
		downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialogInterface, int i) {
			}
		});
		return downloadDialog.show();
	}
	
	private AlertDialog opcoesVerificar(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.conferencia);
		builder.setMessage(R.string.opcoes);
		builder.setPositiveButton(R.string.qrcode,new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				viaQRcode();
			}
		});
		
		builder.setNegativeButton(R.string.manual, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialogManual.show();
			}
		});
		
		return builder.create();
	}
	
	private AlertDialog opcaoManual(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.conferencia);
		builder.setMessage(R.string.digite);
		final EditText idManual = new EditText(this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		idManual.setLayoutParams(lp);
		idManual.setInputType(InputType.TYPE_CLASS_NUMBER);
		builder.setView(idManual);
		builder.setPositiveButton(R.string.enviar,new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if (!idManual.getText().toString().isEmpty()) {
					Long id = Long.parseLong(idManual.getText().toString().trim());
					conferirpatrimonio(id);
				}else{
					Toast.makeText(getApplicationContext(),R.string.campo_invalido, Toast.LENGTH_SHORT).show();
				}
				idManual.setText(null);
			}
		});
		
		builder.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialogManual.dismiss();
			}
		});
		
		return builder.create();
	}
	
	public void gerarIncosistencia(List<Patrimonio> lista){
		Inconsistencia i;
		for (Patrimonio p : lista) {
			i = new Inconsistencia();
			i.setPatrimonio(p);
			i.setConferencia(conferencia);
			JSONObject job = new JSONObject();
			try {
				job.put("id", i.getId());
				job.put("motivo", i.getMotivo());
				job.put("limite", i.getMotivo());
				job.put("conferencia", i.getConferencia());
				
					JSONObject jobPatrimonio = new JSONObject();
					jobPatrimonio.put("id", p.getId());
					jobPatrimonio.put("data_entrada", p.getData_entrada().getTime());
					jobPatrimonio.put("situacao", p.getSituacao().name());
					jobPatrimonio.put("valor_unitario", p.getValor_unitario());
					jobPatrimonio.put("status", p.isStatus());
					job.put("id_patrimonio", jobPatrimonio);
					
					JSONObject jobConferencia = new JSONObject();
					jobConferencia.put("id", conferencia.getId());
					job.put("id_conferencia", jobConferencia);
					
				StringEntity se = new StringEntity(job.toString(), HTTP.UTF_8);
				se.setContentType("application/json;charset=UTF-8");
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));
				HttpPost post = new HttpPost(WebService.URL+"inconsistencia/salvar");
				post.setEntity(se);
				HttpClient client = new DefaultHttpClient();
				HttpResponse response = client.execute(post);
				String resposta = EntityUtils.toString(response.getEntity());
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
			}
		}	
	}
	
	public void atualizarConferencia(List<Patrimonio> lista){
			//conferencia.setData(date);
		int x = 0;
			conferencia.setPatrimonio_ausente(lista);
			JSONObject job = new JSONObject();
			try {
				job.put("id", conferencia.getId());
				
				JSONArray jobArrayPatrimonio = new JSONArray();
				
				JSONObject jobPatrimonio ;
				for (Patrimonio p : conferencia.getPatrimonio_ausente()) {
					jobPatrimonio = new JSONObject();
					jobPatrimonio.put("id", conferencia.getPatrimonio_ausente().get(x).getId());
					jobArrayPatrimonio.put(jobPatrimonio);
					x++;
				}
				job.put("patrimonio_ausente",jobArrayPatrimonio);
				
				StringEntity se = new StringEntity(job.toString(), HTTP.UTF_8);
				se.setContentType("application/json;charset=UTF-8");
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));
				HttpPost post = new HttpPost(WebService.URL+"conferencia/salvar");
				post.setEntity(se);
				HttpClient client = new DefaultHttpClient();
				HttpResponse response = client.execute(post);
				String resposta = EntityUtils.toString(response.getEntity());
				//Notifica.notificar("Sucesso", resposta, this);
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
			}
	}
	
	private AlertDialog dialogEncerrar(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.conferencia);
		builder.setMessage(R.string.encerrar_opcao);
		builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				encerrar();
			}
		});
		builder.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialogEncerrar.dismiss();
			}
		});
		return builder.create();
	}
	
	public void encerrar(){
		qtdAusente = 0;
		List<Patrimonio> inconsistencia = new ArrayList<Patrimonio>();
		for (Patrimonio p : listaPatrimonio) {
			if (!encontrados.contains(p)) {
				inconsistencia.add(p);
				qtdAusente++;
			}	
		}
		String texto = Integer.toString(R.string.ausente_patrimonio) ;

		//Toast.makeText(this, texto+qtdAusente, Toast.LENGTH_LONG).show();
		gerarIncosistencia(inconsistencia);
		atualizarConferencia(inconsistencia);	
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		intent.putExtra("usuario", usuario);
		intent.putExtra("conferencia", conferencia);
		startActivity(intent);
		finish();
	}
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	private class MyCustomAdapter extends ArrayAdapter<Patrimonio> {
		
		private List<Patrimonio> listaPatrimonio;

		public MyCustomAdapter(Context context, int textViewResourceId,List<Patrimonio> listaPatrimonio) {
			super(context, textViewResourceId, listaPatrimonio);
			this.listaPatrimonio = new ArrayList<Patrimonio>();
			this.listaPatrimonio.addAll(listaPatrimonio);
			// TODO Auto-generated constructor stub
		}
		
		 private class ViewHolder {
		   TextView id;
		   TextView descricao;
		   TextView modelo;
		   CheckBox check;
		 }
		 
		 public View getView(int position, View convertView, ViewGroup parent){
			 
			  ViewHolder holder = null;
			  Log.v("ConvertView", String.valueOf(position));
			  
			  if (convertView == null) {
				   LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				   convertView = vi.inflate(R.layout.item_listview, null);
			  }
			  
			  holder = new ViewHolder();
			  holder.id = (TextView) convertView.findViewById(R.id.id);
			  holder.descricao = (TextView) convertView.findViewById(R.id.descricao);
			  holder.modelo = (TextView) convertView.findViewById(R.id.modelo);
			  holder.check = (CheckBox) convertView.findViewById(R.id.checkBox1);
			  convertView.setTag(holder);

			  Patrimonio p =  listaPatrimonio.get(position);	  
			  holder.id.setText(p.getId().toString());
			  holder.descricao.setText(p.getDescricao());
			  holder.modelo.setText(p.getComponente());
			  
			  
			  holder.check.setChecked(checado(p));
			  holder.check.setTag(p);

			  
			 return convertView;
		 }
		 
		 public boolean checado(Patrimonio p){
			
			 for (Patrimonio item : encontrados) {
				if (p.equals(item)) {
					return true;
				}
			}
			 return false;
		 }
		
	}
	
}