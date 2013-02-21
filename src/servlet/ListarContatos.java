package servlet;

import interfaces.IContatoDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Contato;
import modelo.Usuario;
import dao.ContatoDAO;

public class ListarContatos extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private IContatoDAO contatoDAO;
	
	@Override
	public void init() throws ServletException {
		super.init();
		contatoDAO = new ContatoDAO();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String redireciona = "/sistema/listarcontatos.jsp";
		
		int pagina = 1;
		try {
			pagina = Integer.parseInt(req.getParameter("pagina"));
			if (pagina == 0) {
				pagina = 1;
			}
		} catch (Exception e) {
			pagina = 1;
		}
		
		int qtContatosPagina = 10;
		try {
			qtContatosPagina = Integer.parseInt(req.getParameter("qtContatosPagina"));
			if (qtContatosPagina == 0 ) {
				qtContatosPagina = 10;
			}
		} catch (Exception e) {
			qtContatosPagina = 10;
		}
		
		try {
			List<Contato> contatos = null;
			Usuario usuario = null;
			usuario = (Usuario)req.getSession().getAttribute("usuario");
			if (usuario != null) {
				contatos = contatoDAO.getContatosUsuario(pagina, qtContatosPagina, usuario);
				if (contatos != null && contatos.size() > 0) {
					req.setAttribute("contatos", contatos);
					req.setAttribute("pagina", pagina);
					req.setAttribute("paginas", (int)Math.ceil(contatoDAO.getQtContatos(usuario) / (float)qtContatosPagina));
					req.setAttribute("qtContatosPagina", qtContatosPagina);
				} else {
					req.setAttribute("mensagem", "Erro: contatos é nulo.");
				}
			} else {
				req.setAttribute("mensagem", "Página não encontrada (a lista de contatos é nula).");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			req.setAttribute("mensagem", "Erro: uma exceção ocorreu na consulta sql.");
		}
		req.getRequestDispatcher(redireciona).forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {
		doGet(req, resp);
	}
}