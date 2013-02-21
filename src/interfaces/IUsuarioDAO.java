package interfaces;

import java.util.List;
import modelo.Usuario;

public interface IUsuarioDAO extends IDAO<Usuario> {

	public List<Usuario> getUsuario(String nmLogin) throws Exception;
	public Usuario getUsuario(String nmLogin, String nmSenha) throws Exception; 
}
