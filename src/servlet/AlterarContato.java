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

public class AlterarContato extends HttpServlet {

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
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {		
		String pagina = "/sistema/alterarcontato.jsp";		
		if (req.getParameter("idContato") != null) {
			int id = 0;			
			try {
				id = Integer.parseInt(req.getParameter("idContato"));				
				if (id > 0) {				
					if (req.getParameter("nome") != null) {
						String nmContato = req.getParameter("nome");
						if (!nmContato.equals("")) {							
							Usuario ususario = (Usuario)req.getSession().getAttribute("usuario");
							try {
								Contato oldContato = contatoDAO.getContato(id);
								
								Contato newContato = new Contato();
								newContato.setCdContato(id);
								newContato.setNmContato(nmContato);
								newContato.setNmLogin(ususario.getNmLogin());
								newContato.setNmCaminhoFoto("");
								
								contatoDAO.alterar(oldContato, newContato);
								req.setAttribute("contato", newContato);
							} catch (Exception e) {
								System.out.println(e.getMessage());
								req.setAttribute("mensagem", "Houve algum problema ao alterar o contato " + nmContato + ".\nTente mais tarde.");
							}							
						} else {
							req.setAttribute("mensagem", "O nome do contato não poder estar em branco.");	
						}
					} else {
						req.setAttribute("mensagem", "Informe o nome do contato.");
					}		
				} else {
					req.setAttribute("mensagem", "O código do contato tem que ser maior que zero.");		
				}
			} catch (NumberFormatException e) {
				req.setAttribute("mensagem", "O código do contato dever ser um número inteiro.");
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