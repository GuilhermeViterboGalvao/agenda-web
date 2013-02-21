package modelo;

import java.util.ArrayList;
import java.util.List;

public class Contato {
	private int cdContato;
	private String nmLogin;
	private String nmContato;
	private String nmCaminhoFoto;
	private List<Telefone> telefones;
	private List<Email> emails;
	public Contato(){
		telefones = new ArrayList<Telefone>();
		emails = new ArrayList<Email>();
	}
	public List<Telefone> getTelefones() {
		return telefones;
	}
	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}
	public List<Email> getEmails() {
		return emails;
	}
	public void setEmails(List<Email> emails) {
		this.emails = emails;
	}
	public int getCdContato() {
		return cdContato;
	}
	public void setCdContato(int cdContato) {
		this.cdContato = cdContato;
	}
	public String getNmLogin() {
		return nmLogin;
	}
	public void setNmLogin(String nmLogin) {
		this.nmLogin = nmLogin;
	}
	public String getNmContato() {
		return nmContato;
	}
	public void setNmContato(String nmContato) {
		this.nmContato = nmContato;
	}
	public String getNmCaminhoFoto() {
		return nmCaminhoFoto;
	}
	public void setNmCaminhoFoto(String nmCaminhoFoto) {
		this.nmCaminhoFoto = nmCaminhoFoto;
	}
}