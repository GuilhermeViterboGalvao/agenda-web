package modelo;

public class Telefone {
	private String nmTelefone;
	private Contato contato;
	private int cdTipo;
	public Telefone(){
		contato = new Contato();
	}
	public String getNmTelefone() {
		return nmTelefone;
	}
	public void setNmTelefone(String nmTelefone) {
		this.nmTelefone = nmTelefone;
	}
	public Contato getContato() {
		return contato;
	}
	public void setContato(Contato contato) {
		this.contato = contato;
	}
	public int getCdTipo() {
		return cdTipo;
	}
	public void setCdTipo(int cdTipo) {
		this.cdTipo = cdTipo;
	}
}