package interfaces;

import java.util.List;

public interface IDAO<T> {
	public void cadastrar(T objeto) throws Exception;
	public void alterar(T oldObjeto, T newObjeto) throws Exception;
	public void deletar(T objeto) throws Exception;
	public List<T> consultar(int inicio, int qtResitros) throws Exception;	
	public List<T> consultar(String condicao) throws Exception;
	public List<T> consultar(String colunas, String condicao) throws Exception;
}