package servlet;

import interfaces.IContatoDAO;
import interfaces.IEmailDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Email;
import dao.ContatoDAO;
import dao.EmailDAO;

public class CadastrarEmail extends HttpServlet {

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
		String pagina = "/sistema/cadastraremail.jsp";
		if (req.getParameter("idContato") != null) {
			try {
				int idContato = Integer.parseInt(req.getParameter("idContato"));
				if (idContato > 0) {
					if (req.getParameter("email") != null) {
						String nmEmail = req.getParameter("email");
						if (!nmEmail.equals("")) {
							try {
								Email email = new Email();
								email.setContato(contatoDAO.getContato(idContato));
								email.setNmEmail(nmEmail);
								
								emailDAO.cadastrar(email);					
								req.setAttribute("email", email);
							} catch (Exception e) {
								System.out.println(e.getMessage());
								req.setAttribute("mensagem", "Ocorreu um erro durante o cadastro do e-mail " + nmEmail);
							}
						} else {
							req.setAttribute("mensagem", "O e-mail não poder ser em branco.");	
						}
					} else {
						req.setAttribute("mensagem", "Infome o e-mail.");	
					}
				} else {
					req.setAttribute("mensagem", "O código do contato deve ser maior que zero.");	
				}
			} catch (Exception e) {
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