package servlet;

import interfaces.IContatoDAO;
import interfaces.ITelefoneDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Telefone;
import modelo.Usuario;
import dao.ContatoDAO;
import dao.TelefoneDAO;

public class AlterarTelefone extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ITelefoneDAO telefoneDAO;
	public IContatoDAO contatoDAO;
	
	@Override
	public void init() throws ServletException {
		super.init();
		telefoneDAO = new TelefoneDAO();
		contatoDAO = new ContatoDAO();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pagina = "/sistema/alterartelefone.jsp";		
		
		if (req.getParameter("idContato") != null) {
			try {
				int idContato = Integer.parseInt(req.getParameter("idContato"));
				if (idContato > 0) {
					if (req.getParameter("idTipoTelefone") != null) {
						try {
							int idTipoTelefone = Integer.parseInt(req.getParameter("idTipoTelefone"));
							if (idTipoTelefone > 0 && idTipoTelefone < 7) {
								if (req.getParameter("numeroAntigo") != null) {
									String numeroAntigo = req.getParameter("numeroAntigo");
									if (!numeroAntigo.equals("")) {
										if (req.getParameter("numeroNovo") != null) {
											String numeroNovo = req.getParameter("numeroNovo");
											if (!numeroNovo.equals("")) {
												try {
													Usuario usuario = (Usuario)req.getSession().getAttribute("usuario");
													
													List<Telefone> telefones = telefoneDAO.getTelefonesContato(idContato, usuario);
													
													boolean telefoneCadastrado = false;
													
													for (int i = 0; i < telefones.size(); i++) {
														if (telefones.get(i).getNmTelefone().equals(numeroNovo)) {
															telefoneCadastrado = true;
															break;
														}
													}
													
													Telefone oldTelefone = telefoneDAO.getTelefone(numeroAntigo);
													
													if (!telefoneCadastrado) {													
														Telefone newTelefone = new Telefone();
														newTelefone.setContato(contatoDAO.getContato(idContato));
														newTelefone.setNmTelefone(numeroNovo);
														newTelefone.setCdTipo(idTipoTelefone);
														
														telefoneDAO.alterar(oldTelefone, newTelefone);
														req.setAttribute("telefone", newTelefone);	
													} else {
														req.setAttribute("mensagem", "O contato " + idContato + " já possui o telefone " + numeroNovo);
													}
												} catch (Exception e) {
													System.out.println(e.getMessage());
													req.setAttribute("mensagem", "Ocorreu um erro ao alterar o telefone " + numeroNovo + ". Tente novamente mais tarde.");
												}	
											} else {
												req.setAttribute("mensagem", "O novo núemro do telefone não pode ser em branco.");
											}
										} else {
											req.setAttribute("mensagem", "Informe o novo núemro do telefone.");	
										}
									} else {
										req.setAttribute("mensagem", "O núemro do telefone não pode ser em branco.");	
									}
								} else {
									req.setAttribute("mensagem", "Informe o núemro do telefone.");	
								}
							} else {
								req.setAttribute("mensagem", "O código do tipo de telefone deve ser maior que zero e menor que sete.");	
							}
						} catch (Exception e) {
							req.setAttribute("mensagem", "O código do tipo de telefone deve ser um número do tipo inteiro.");
						}
					} else {
						req.setAttribute("mensagem", "Informe o código do tipo de telefone.");			
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