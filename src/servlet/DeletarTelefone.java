package servlet;

import interfaces.ITelefoneDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Telefone;
import modelo.Usuario;
import dao.TelefoneDAO;

public class DeletarTelefone extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ITelefoneDAO telefoneDAO;
	
	@Override
	public void init() throws ServletException {
		super.init();
		telefoneDAO = new TelefoneDAO();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pagina = "/sistema/deletartelefone.jsp";
		if (req.getParameter("idContato") != null) {
			try {
				int idContato = Integer.parseInt(req.getParameter("idContato"));
				if (idContato > 0) {
					if (req.getParameter("numero") != null) {
						String numero = req.getParameter("numero");
						if (!numero.equals("")) {
							try {
								Telefone telefone = telefoneDAO.getTelefone(numero, idContato); 

								if (telefone != null) {
									telefoneDAO.deletar(telefone, (Usuario)req.getSession().getAttribute("usuario"));
									req.setAttribute("mensagem", "Telefone removido com sucesso.");									
								} else {
									req.setAttribute("mensagem", "O contato " + idContato + " não possui esse telefone. Verifique se você digitou o número corretamente.");	
								}
							} catch (Exception e) {

							}
						} else {
							req.setAttribute("mensagem", "O número do telefone não pode ser em branco.");	
						}
					} else {
						req.setAttribute("mensagem", "Informe o número do telefone.");	
					}
				} else {
					req.setAttribute("mensagem", "O código do contato dever maior que zero.");	
				}
			} catch (Exception e) {
				req.setAttribute("mensagem", "O código do contato dever um número do tipo inteiro.");
			}
		} else {
			req.setAttribute("mensagem", "Informe o código do contato.");
		}
		req.getRequestDispatcher(pagina).forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}