package servlet;

import java.io.IOException;
import java.util.List;
import interfaces.IContatoDAO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Contato;
import modelo.Usuario;
import dao.ContatoDAO;

public class BuscarContatos extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	private IContatoDAO contatoDAO;
	
	@Override
	public void init() throws ServletException {
		contatoDAO = new ContatoDAO();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pagina = "/sistema/buscarcontatos.jsp";
		if (req.getParameter("nome") != null) {
			String nome = req.getParameter("nome");
			if (!nome.equals("")) {
				try {
					Usuario usuario = (Usuario)req.getSession().getAttribute("usuario");
					List<Contato> contatos = contatoDAO.getContato(nome, usuario);
					if (contatos.size() > 0) {
						req.setAttribute("contatos", contatos);
					} else {
						req.setAttribute("mensagem", "Nenhum contato encontrado.");	
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			} else {
				req.setAttribute("mensagem", "Informe um nome a ser buscado não pode ser em branco.");
			}
		} else {
			req.setAttribute("mensagem", "Informe um nome a ser buscado.");
		}
		req.getRequestDispatcher(pagina).forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {
		doGet(req, resp);
	}
}