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

public class CadastrarContato extends HttpServlet {

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
		String pagina = "/sistema/cadastrarcontato.jsp";
		if (req.getParameter("nome") != null) {
			String nmContato = req.getParameter("nome");
			if (!nmContato.equals("")) {
				try {
					Usuario usuario = (Usuario)req.getSession().getAttribute("usuario");
					String condicao = "nmContato='" + limparString(nmContato) + "' and nmLogin='" + limparString(usuario.getNmLogin()) + "'";
					if (contatoDAO.consultar(condicao).size() <= 0) {
						Contato contato = new Contato();
						contato.setNmContato(nmContato);
						contato.setNmLogin(usuario.getNmLogin());
						contato.setNmCaminhoFoto("");
						
						contatoDAO.cadastrar(contato);
						contato = contatoDAO.getContato(nmContato, usuario).get(0);
						req.setAttribute("contato", contato);
					} else {
						req.setAttribute("mensagem", "O usuário " + usuario.getNmUsuario() + " já possui um contato com o nome " + nmContato);
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
					req.setAttribute("mensagem", "Houve algum problema ao inserir o contato " + nmContato + ".\nTente mais tarde.");
				}
			} else {
				req.setAttribute("mensagem", "O nome do contato não poder ser em branco.");
			}
		} else {
			req.setAttribute("mensagem", "Informe o nome do contato.");
		}
		req.getRequestDispatcher(pagina).forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {
		doGet(req, resp);
	}
	
	private String limparString(String s){
		return s.replaceAll("'", "\'").replace("\\", "");
	}
}