package modelo;

public class Email {
	private String nmEmail;
	private Contato contato; 
	public Email(){
		contato = new Contato();
	}
	public String getNmEmail() {
		return nmEmail;
	}
	public void setNmEmail(String nmEmail) {
		this.nmEmail = nmEmail;
	}
	public Contato getContato() {
		return contato;
	}
	public void setContato(Contato contato) {
		this.contato = contato;
	}
}