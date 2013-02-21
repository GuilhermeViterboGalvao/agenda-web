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

public class ContatoFoto extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private IContatoDAO contatoDAO;
	
	@Override
	public void init() throws ServletException {
		super.init();
		contatoDAO = new ContatoDAO();
	}
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("idContato") != null) {
			try {
				int idContato = Integer.parseInt(request.getParameter("idContato"));
				if (idContato > 0) {
					Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
					try {
						Contato contato = contatoDAO.getContato(idContato, usuario);
						if (contato != null) {
							String caminho = "../imagens/" + usuario.getNmLogin().replaceAll(" ", "") + "/" + contato.getNmContato().replaceAll(" ", "") + "/imagem.jpg";
							System.out.println(caminho);
							request.getRequestDispatcher(caminho).forward(request, response);							
						} else {
							response.sendError(HttpServletResponse.SC_NOT_FOUND);	
						}
					} catch (Exception e) {
						response.sendError(HttpServletResponse.SC_NOT_FOUND);
					}
				} else {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);	
				}
			} catch (NumberFormatException e) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}			
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}