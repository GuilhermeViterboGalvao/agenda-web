package interfaces;

import java.util.List;
import modelo.Email;
import modelo.Usuario;

public interface IEmailDAO extends IDAO<Email>{
	
	public Email getEmail(String nmEmail) throws Exception;
	public Email getEmail(String nmEmail, int cdContato) throws Exception;
	public List<Email> getEmailsContato(int cdContato, Usuario usuario) throws Exception;
	public List<Email> getEmailsContato(int cdContato) throws Exception;
	public void deletar(Email email, Usuario usuario) throws Exception;
}