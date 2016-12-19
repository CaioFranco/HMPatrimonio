package br.senai.hm.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import br.senai.hm.R;
import br.senai.hm.modelo.Permissao;
import br.senai.hm.modelo.Usuario;
import br.senai.hm.util.WebService;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	private EditText login,senha;
	private List<Usuario> listaU;
	private Usuario usuario;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		login = (EditText) findViewById(R.id.et01);
		senha = (EditText) findViewById(R.id.et02);
		try {
			listaU = getUsuarioWS1();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void enviar(View v){
		if (login.getText().toString().isEmpty()) {
			Toast.makeText(this, R.string.aviso_nome, Toast.LENGTH_SHORT).show();
		}else if(senha.getText().toString().isEmpty()){
			Toast.makeText(this, R.string.aviso_senha, Toast.LENGTH_SHORT).show();
		}else if (validarUsuario(login.getText().toString(),senha.getText().toString())) {
			logar(usuario);	
		}else{
			Toast.makeText(this, R.string.aviso_logar, Toast.LENGTH_SHORT).show();
		}
	}
	
	public boolean validarUsuario(String login,String senha){
		List<Usuario> lista = new ArrayList<Usuario>(listaU);
		for (Usuario u : lista) {
			if (login.equals(u.getLogin()) && senha.equals(u.getSenha()) ) {
				usuario = u;
				return true;
			}
		}
		return false;
	}
	
	public List<Usuario> getUsuarioWS(){
		// cria String com URL completa
		String url = WebService.URL+"usuario/lista";
		//passar a url para o metodo makeRequest e obter a resposta
		String resposta = WebService.makeRequest(url);
		//cria uma instancia de Gson
		Gson gson = new Gson();
		//criar um tipo
		Type tipoLista = new TypeToken<List<Usuario>>(){}.getType();
		// Converte a resposta em lista de genero
		List<Usuario> listaCompleta = gson.fromJson(resposta, tipoLista);
		//retorna lista
		List<Usuario> lista = new ArrayList<Usuario>();
		for (Usuario u : listaCompleta) {
			if (u.getPermissao() == Permissao.GESTOR && u.isStatus()) {
				lista.add(u);
			}
		}
		if (lista.isEmpty()) {
			Toast.makeText(this, R.string.lista_vazia, Toast.LENGTH_SHORT).show();
			return null;
		}
		return lista;
	}
	
	public List<Usuario> getUsuarioWS1(){
		String url = WebService.URL+"usuario/lista";
		String resposta = WebService.makeRequest(url);
		List<Usuario> lista = new ArrayList<Usuario>();
		try {
			JSONArray array = new JSONArray(resposta);
			for (int i = 0; i < array.length(); i++){
				JSONObject job = array.getJSONObject(i);
				Usuario u = new Usuario();
				u.setId(job.getLong("id"));
				u.setNome(job.getString("nome"));
				u.setLogin(job.getString("login"));
				u.setSenha(job.getString("senha"));
				u.setEmail(job.getString("email"));
				u.setPermissao(Permissao.valueOf(job.getString("permissao")));
				u.setStatus(job.getBoolean("status"));
				lista.add(u);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Usuario> listaC = new ArrayList<Usuario>();
		for (Usuario u : lista) {
			if (u.isStatus() && u.getPermissao() == Permissao.GESTOR ) {
				listaC.add(u);
			}
		}
		if (listaC.isEmpty()) {
			Toast.makeText(this, R.string.lista_vazia, Toast.LENGTH_SHORT).show();
			return null;
		}
		return listaC;
	}
	
	public void logar(Usuario u){
		Intent intent = new Intent(this,MainActivity.class);
		intent.putExtra("usuario", usuario);
		startActivity(intent);
		finish();
	}
	
	@Override
	protected void onDestroy() {
		
		super.onDestroy();
	}
}
