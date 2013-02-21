package servlet;

import java.io.IOException;

import interfaces.IContatoDAO;
import interfaces.IEmailDAO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Email;
import dao.ContatoDAO;
import dao.EmailDAO;

public class AlterarEmail extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private IEmailDAO emailDAO;
	private IContatoDAO contatoDAO;
	
	@Override
	public void init() throws ServletException {
		super.init();
		emailDAO = new EmailDAO();
		contatoDAO = new ContatoDAO();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pagina = "/sistema/alteraremail.jsp";
		
		if (req.getParameter("idContato") != null) {
			try {
				int idContato = Integer.parseInt(req.getParameter("idContato"));
				if (idContato > 0) {
					if (req.getParameter("emailNovo") != null) {
						String emailNovo = req.getParameter("emailNovo");
						if (!emailNovo.equals("")) {
							if (req.getParameter("emailAntigo") != null) {
								String emailAntigo = req.getParameter("emailAntigo");
								if (!emailAntigo.equals("")) {
									try {
										Email oldEmail = emailDAO.getEmail(emailAntigo);
										
										Email newEmail = new Email();
										newEmail.setContato(contatoDAO.getContato(idContato));
										newEmail.setNmEmail(emailNovo);
										
										emailDAO.alterar(oldEmail, newEmail);
										req.setAttribute("email", newEmail);										
									} catch (Exception e) {
										System.out.println(e.getMessage());
										req.setAttribute("mensagem", "Houve algum problema ao alterar o email " + emailAntigo + ".\nTente mais tarde.");
									}
								} else {
									req.setAttribute("mensagem", "O e-mail atual não poder ser em branco.");	
								}
							} else {
								req.setAttribute("mensagem", "Informe o e-mail atual.");	
							}
						} else {
							req.setAttribute("mensagem", "O valor do novo e-mail não pode ser em branco.");	
						}
					} else {
						req.setAttribute("mensagem", "Informe o novo e-mail.");	
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