package br.senai.hm.util;
import org.apache.commons.mail.SimpleEmail;

public class Teste {
	public static void main(String[] args) throws Exception {
		SimpleEmail email = new SimpleEmail();
		System.out.println("liberando porta...");
		email.setHostName("smtp.live.com");
		email.setStartTLSEnabled(true);
		email.setSmtpPort(587);

		email.addTo("wsaboboreira@hotmail.com", "Will");
		// u.getEmail();
		email.setFrom("wsaboboreira@hotmail.com", "William");
		// Adicione um assunto
		email.setSubject("Teste de email");
		// Adicione a mensagem do email
		email.setMsg("primeiro email do java");
		// Para autenticar no servidor é necessário chamar os dois métodos
		// abaixo
		System.out.println("autenticando...");
		email.setAuthentication("wsaboboreira@hotmail.com", "aboboreira333");
		System.out.println("enviando...");
		email.send();
		System.out.println("Email enviado!");
	}

}
