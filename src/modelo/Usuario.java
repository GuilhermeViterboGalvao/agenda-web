package modelo;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
	private String nmLogin;
	private String nmSenha;
	private String nmUsuario;
	private String nmEmail;
	private List<Contato> contatos;
	public Usuario(){
		contatos = new ArrayList<Contato>();
	}
	public List<Contato> getContatos() {
		return contatos;
	}
	public void setContatos(List<Contato> contatos) {
		this.contatos = contatos;
	}
	public String getNmLogin() {
		return nmLogin;
	}
	public void setNmLogin(String nmLogin) {
		this.nmLogin = nmLogin;
	}
	public String getNmSenha() {
		return nmSenha;
	}
	public void setNmSenha(String nmSenha) {
		this.nmSenha = nmSenha;
	}
	public String getNmUsuario() {
		return nmUsuario;
	}
	public void setNmUsuario(String nmUsuario) {
		this.nmUsuario = nmUsuario;
	}
	public String getNmEmail() {
		return nmEmail;
	}
	public void setNmEmail(String nmEmail) {
		this.nmEmail = nmEmail;
	}
}
