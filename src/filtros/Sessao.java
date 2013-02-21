package filtros;

import interfaces.IUsuarioDAO;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dao.UsuarioDAO;

import modelo.Usuario;

public class Sessao implements Filter {

	private IUsuarioDAO usuarioDAO;
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		usuarioDAO = new UsuarioDAO();
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,	FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req; 
        HttpSession sessao = request.getSession(false);
        Usuario usuario = null;
        if (sessao != null) {
			usuario = (Usuario)sessao.getAttribute("usuario");
			if (usuario != null) {
				chain.doFilter(req, resp);
			} else {
				if (recuperarSessao(request)) {
					chain.doFilter(req, resp);	
				} else {
					request.setAttribute("mensagem", "Permissão negada! Forneça login/senha");
					request.getRequestDispatcher("/index.jsp").forward(req, resp);					
				}
			}
		} else {
			if (recuperarSessao(request)) {
				chain.doFilter(req, resp);	
			} else {
				request.setAttribute("mensagem", "Permissão negada! Forneça login/senha");
				request.getRequestDispatcher("/index.jsp").forward(req, resp);					
			}
		}        
	}
	
	private boolean recuperarSessao(HttpServletRequest request){
		boolean sessaoRecuperada = false;		
		Cookie[] cookies = request.getCookies();
		Cookie cookie = null;
		String login = null;
		String senha = null;
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				cookie = cookies[i];
				if (cookie.getName().equals("login")) {
					login = cookie.getValue();
				} else if (cookie.getName().equals("senha")) {
					senha = cookie.getValue();
				}
			}
			if (login != null && senha != null) {
				try {
					Usuario usuario = usuarioDAO.getUsuario(login, senha);
					if (usuario != null) {
						HttpSession sessao = request.getSession(false);
						if (sessao == null) {
							sessao = request.getSession();
						}
						sessao.setAttribute("usuario", usuario);
						sessaoRecuperada = true;
					}
				} catch (Exception e) {						
					System.out.println(e.getMessage());
				}					
			}	
		}		
		return sessaoRecuperada;
	}

	@Override
	public void destroy() {

	}
}