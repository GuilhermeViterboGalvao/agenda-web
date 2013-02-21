package servlet;

import interfaces.IContatoDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Contato;
import modelo.Usuario;
import dao.ContatoDAO;

public class DeletarContato extends HttpServlet {

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
		String pagina = "/sistema/deletarcontato.jsp";
		if (req.getParameter("idContato") != null) {
			try {
				int idContato = Integer.parseInt(req.getParameter("idContato"));
				Usuario ususario = (Usuario)req.getSession().getAttribute("usuario");
				if (idContato > 0) {
					try {
						Contato contato = contatoDAO.getContato(idContato, ususario);
						if (contato != null) {
							contatoDAO.deletar(contato);
							req.setAttribute("mensagem", "Contato removido com sucesso.");
						} else {
							req.setAttribute("mensagem", "O usuário " + ususario.getNmUsuario() + " não possui nenhum contato com o código " + idContato + ".");
						}
					} catch (Exception e) {
						req.setAttribute("mensagem", "Não foi possível remover o contato. Tente novamente mais tarde.");
					}		
				} else {
					req.setAttribute("mensagem", "O código do contato deve ser maior que zero.");	
				}
			} catch (NumberFormatException e) {
				req.setAttribute("mensagem", "O código do contato deve ser um número do tipo inteiro.");
			}
		} else {
			req.setAttribute("mensagem", "Informe o código do contato.");
		}
		req.getRequestDispatcher(pagina).forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {
		doGet(req, resp);
	}
}