package servlet;

import interfaces.IUsuarioDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.UsuarioDAO;
import modelo.Usuario;

public class Login extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private IUsuarioDAO usuarioDAO;
	
	@Override
	public void init() throws ServletException {
		super.init();
		usuarioDAO = new UsuarioDAO();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pagina = "index.jsp";
		if (req.getParameter("login") != null) {
			String login = req.getParameter("login");
			if (!login.equals("")) {
				if (req.getParameter("senha") != null) {
					String senha = req.getParameter("senha");
					if (!senha.equals("")) {
						try {
							Usuario usuario = usuarioDAO.getUsuario(login, senha);
							if (usuario != null) {
								req.getSession().setAttribute("usuario", usuario);
								req.setAttribute("mensagem", "Logado com sucesso!");				
								pagina = "sistema/login.jsp";
							} else {
								req.setAttribute("mensagem", "Login/senha inválidos!");
							}
						} catch (Exception e) {
							System.out.println(e.getMessage());
							req.setAttribute("mensagem", "Erro ao consultar dados. Tente novamente mais tarde.");
						}
					} else {
						req.setAttribute("mensagem", "A senha não pode ser em branco.");
					}
				} else {
					req.setAttribute("mensagem", "Informe a senha.");		
				}
			} else {
				req.setAttribute("mensagem", "O login não pode ser em branco.");	
			}
		} else {
			req.setAttribute("mensagem", "Informe o login.");
		}
		req.getRequestDispatcher(pagina).forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {
		doGet(req, resp);
	}
}