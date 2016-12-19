package br.senai.hm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class AutorizaInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		System.out.println(uri);
		/*Liberação da url que contenhas tais valores*/
		if (uri.contains("acessar")|| uri.contains("acesso") || uri.contains("resources")
				|| uri.contains("home") || uri.contains("erro")) {
			return true;
		}

		/*verifica o usuário que está "pendurado" na sessão*/
		Object usuario = request.getSession().getAttribute("usuario");

	

		
		

	
		/*esse return vale para tudo que não esteja validado acima*/
		return true;
	}
}
