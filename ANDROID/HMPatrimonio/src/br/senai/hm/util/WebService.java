package br.senai.hm.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class WebService {

	public static final String URL = "http://10.84.135.24/HMPatrimonioWS/services/";
	/**
	 * 10.84.135.22
	 * Metodo para nviar requisição à URL e retornar resposta
	 * @param url do webservice
	 * @return conteudo lido no websevice
	 */
	public static String makeRequest(String url){
		HttpURLConnection conexao = null;
		URL endereco = null;
		String retorno = null;
		try {
			endereco = new URL(url);
			conexao = (HttpURLConnection) endereco.openConnection();
			retorno = LerDados(conexao.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(conexao != null)
			{
				// desconecta
				conexao.disconnect();
			}
		}
		// retorna o conteudo lido requisição
		return retorno;
		
	}
	/**
	 * Método para ler um InputStream e retornar seu resultado
	 * @param is com os dados a serem lidos
	 * @return dados em forma de string
	 */
	private static String LerDados(InputStream is){
		//criar uma referencia para BufferedReader
		BufferedReader reader = null;
		//criar um StringBuilder
		StringBuilder builder = new StringBuilder();
		try {
			//instanciar o reader com o InputStream recebido no metodo
			reader = new BufferedReader(new InputStreamReader(is));
			// criar uma string para a linha ser lida
			String linha = null;
			// enquanto houver linhas, guarda o conteúdo na String linha
			while ((linha = reader.readLine()) != null ) 
			{
				// acrescentar  a linha ao builder
				builder.append(linha+"\n");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (reader != null) 
			{
				try {
					reader.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		//retorna o conteúdo lido
		return builder.toString();
	}
	
	public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected())
            return true;
        else
            return false;
 }
}
