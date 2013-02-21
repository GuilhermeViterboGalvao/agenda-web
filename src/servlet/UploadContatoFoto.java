package servlet;

import interfaces.IContatoDAO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.Contato;
import modelo.Usuario;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import dao.ContatoDAO;

public class UploadContatoFoto extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final long CEM_KB = 100000;
	private IContatoDAO contatoDAO;
	
	@Override
	public void init() throws ServletException {
		super.init();
		contatoDAO = new ContatoDAO();
	}
	
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());        
    	Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
    	List<FileItem> items = null;
    	Contato newContato = null;
    	Contato oldContato = null;
    	int idContato = 0;
    	
    	if (request.getParameter("idContato") != null) {    		
    		idContato = Integer.parseInt(request.getParameter("idContato"));
    		
        	try {        		
        		newContato = contatoDAO.getContato(idContato, usuario);
    			oldContato = contatoDAO.getContato(idContato, usuario);
    			
            	try {
            		
                    items = upload.parseRequest(request);
        	        Iterator<FileItem> iterator = items.iterator();
        	        
        	        while (iterator.hasNext()) {
        	        	
        	            FileItem item = (FileItem)iterator.next();
        	            
        	            if (!item.isFormField()) {
        	            	
        	            	if (item.getContentType().equals("image/jpeg") || item.getContentType().equals("image/png")) {
        	            		
        	            		if (item.getSize() <  CEM_KB) {
        	            			
            	            		if (newContato != null && oldContato != null) {
            	            			
            		                	String caminho = getServletContext().getRealPath(File.separator) + "imagens/" + usuario.getNmLogin().replaceAll(" ", "") + "/" + newContato.getNmContato().replaceAll(" ", "") + "/";
            		                	File diretorio = new File(caminho);        		                	
            		                	System.out.println(diretorio.getPath());
            		                	
            		                	if (!diretorio.exists()) {
            		                		diretorio.mkdirs();
            		    				}            	
            		                	
            		                    File file = new File(diretorio, "imagem.jpg");
            		                    FileOutputStream output = new FileOutputStream(file);
            		                    InputStream is = item.getInputStream();
            		                    byte[] buffer = new byte[2048];
            		                    int nLidos = 0;
            		                    
            		                    while ((nLidos = is.read(buffer)) >= 0) {
            		                    	output.write(buffer, 0, nLidos);
            		                    }
            		                    
            		                    is.close();
            		                    output.close();
            		                    newContato.setNmCaminhoFoto(caminho);
            		                    
            		                    try {
            		                    	contatoDAO.alterar(oldContato, newContato);	
            							} catch (Exception e) {
            								System.out.println(e.getMessage());
            								request.setAttribute("mensagem", "Ocorreu um erro ao consultar a base da dados.");
            								break;
            							}	            
            		                    
            		                    request.setAttribute("mensagem", "Arquivo recebido com sucesso.");
            						} else {
            							request.setAttribute("mensagem", "O usuário " + usuario.getNmLogin() + " não possui nenhum contato com o código " + idContato);
            							break;
            						}	
								} else {
									request.setAttribute("mensagem", "A imagem deve ser menor que 100 KB.");
									break;
								}
        					} else {
        						request.setAttribute("mensagem", "Só pode ser enviado ao servidor arquivos no formato jpg ou png.");
        						break;
        					}
        	            }
        	        }
                } catch (FileUploadException e) {
                    System.out.println(e.getMessage());
                    request.setAttribute("mensagem", "Ocorreu um erro no envio do arquivo.");
                }
    		} catch (Exception e) {
    			System.out.println(e.getMessage());
    			request.setAttribute("mensagem", "Ocorreu um erro ao consultar a base da dados.");
    		}    			
		} else {
			request.setAttribute("mensagem", "O id do contato não poder ser nulo ou estar em branco!");
		}
        request.getRequestDispatcher("/sistema/uploadcontatofoto.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}