package interfaces;

import java.util.List;
import modelo.Contato;
import modelo.Usuario;

public interface IContatoDAO extends IDAO<Contato>{

	public Contato getContato(int cdContato) throws Exception;
	public Contato getContato(int cdContato, Usuario usuario) throws Exception;
	public List<Contato> getContato(String nmContato, Usuario usuario) throws Exception;
	public int getQtContatos(Usuario usuario) throws Exception;
	public List<Contato> getContatosUsuario(Usuario usuario) throws Exception;
	public List<Contato> getContatosUsuario(int pagina, int qtContatosPagina, Usuario usuario) throws Exception;	
}
