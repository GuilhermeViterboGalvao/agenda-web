package filtros;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import modelo.Usuario;

public class LoginFilter implements Filter {

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,	FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req; 
        HttpSession sessao = request.getSession(false);

        if (sessao != null) {
			Usuario usuario = (Usuario)sessao.getAttribute("usuario");
			if (usuario != null) {
				req.setAttribute("mensagem", "Você já está logado. Para se logar com outro usuário faça o logout.");
				RequestDispatcher rd = req.getRequestDispatcher("/index.jsp");
				rd.forward(req, resp);
			} else {
				chain.doFilter(req, resp);
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
}