package servlet;

import interfaces.IContatoDAO;
import interfaces.ITelefoneDAO;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.Contato;
import modelo.Telefone;
import modelo.Usuario;
import dao.ContatoDAO;
import dao.TelefoneDAO;

public class CadastrarTelefone extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ITelefoneDAO telefoneDAO;
	public IContatoDAO contatoDAO;
	
	@Override
	public void init() throws ServletException {
		super.init();
		telefoneDAO = new TelefoneDAO();
		contatoDAO = new ContatoDAO();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pagina = "/sistema/cadastrartelefone.jsp";
		if (req.getParameter("idContato") != null) {
			try {
				int idContato = Integer.parseInt(req.getParameter("idContato"));
				if (idContato > 0) {
					if (req.getParameter("idTipoTelefone") != null) {
						try {
							int idTipoTelefone = Integer.parseInt(req.getParameter("idTipoTelefone"));
							if (idTipoTelefone > 0 && idTipoTelefone < 7) {
								if (req.getParameter("numero") != null) {
									String numero = req.getParameter("numero");
									if (!numero.equals("")) {
										try {
											Usuario usuario = (Usuario)req.getSession().getAttribute("usuario");
											
											List<Telefone> telefones = telefoneDAO.getTelefonesContato(idContato, usuario);
											
											boolean telefoneCadastrado = false;
											
											for (int i = 0; i < telefones.size(); i++) {
												if (telefones.get(i).getNmTelefone().equals(numero)) {
													telefoneCadastrado = true;
													break;
												}
											}
											
											Contato contato = contatoDAO.getContato(idContato);
											
											if (!telefoneCadastrado) {
												try {
													Telefone telefone = new Telefone();
													telefone.setContato(contato);
													telefone.setNmTelefone(numero);
													telefone.setCdTipo(idTipoTelefone);
													
													telefoneDAO.cadastrar(telefone);
													req.setAttribute("telefone", telefone);
												} catch (Exception e) {
													System.out.println(e.getMessage());
													req.setAttribute("mensagem", "Ocorreu um erro ao cadastrar o telefone " + numero + ".");	
												}	
											} else {
												req.setAttribute("mensagem", "O contato " + idContato + " já possui o telefone " + numero);
											}
										} catch (Exception e) {

										}
									} else {
										req.setAttribute("mensagem", "O número do telefone não poder ser em branco.");	
									}
								} else {
									req.setAttribute("mensagem", "Informe o número do telefone.");
								}
							} else {
								req.setAttribute("mensagem", "O código do tipo do telefone deve ser maior que zero e menor que sete.");	
							}
						} catch (Exception e) {
							req.setAttribute("mensagem", "O código do tipo do telefone deve ser um número do tipo inteiro.");
						}
					} else {
						req.setAttribute("mensagem", "Informe o código do tipo do telefone.");			
					}
				} else {
					req.setAttribute("mensagem", "O código do contato deve ser maior que zero.");	
				}
			} catch (NumberFormatException e) {
				req.setAttribute("mensagem", "O código do contato de ser um número do tipo inteiro.");	
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