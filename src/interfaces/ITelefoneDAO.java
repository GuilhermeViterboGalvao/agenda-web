package interfaces;

import java.util.List;
import modelo.Telefone;
import modelo.Usuario;

public interface ITelefoneDAO extends IDAO<Telefone>{
	
	public Telefone getTelefone(String nmTelefone) throws Exception;
	public Telefone getTelefone(String nmTelefone, int cdContato) throws Exception;
	public List<Telefone> getTelefonesContato(int cdContato, Usuario usuario) throws Exception;
	public List<Telefone> getTelefonesContato(int cdContato) throws Exception;
	public void deletar(Telefone telefone, Usuario usuario) throws Exception;
}
