package servlet;

import interfaces.IEmailDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Email;
import modelo.Usuario;
import dao.EmailDAO;

public class DeletarEmail extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IEmailDAO emailDAO;
	
	@Override
	public void init() throws ServletException {
		super.init();
		emailDAO = new EmailDAO();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pagina = "/sistema/deletaremail.jsp";
		
		if (req.getParameter("idContato") != null) {
			try {
				int idContato = Integer.parseInt(req.getParameter("idContato"));
				if (idContato > 0) {
					if (req.getParameter("email") != null) {
						String nmEmail = req.getParameter("email");
						if (!nmEmail.equals("")) {
							try {
								Email email = emailDAO.getEmail(nmEmail, idContato);
								
								if (email != null) {
									emailDAO.deletar(email, (Usuario)req.getSession().getAttribute("usuario"));
									req.setAttribute("mensagem", "E-mail removido com sucesso.");	
								} else {
									req.setAttribute("mensagem", "O contato " + idContato + " não possui esse e-mail. Verifique se você o digitou corretamente.");									
								}
							} catch (Exception e) {
								System.out.println(e.getMessage());
								req.setAttribute("mensagem", "Ocorreu um erro durante a remoção do e-mail " + nmEmail);
							}
						} else {
							req.setAttribute("mensagem", "O e-mail não poder ser em branco.");	
						}
					} else {
						req.setAttribute("mensagem", "Inforem o e-mail.");	
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
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}