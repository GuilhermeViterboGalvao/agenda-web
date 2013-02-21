package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;
import conexao.FabricaDeConexao;
import modelo.Contato;
import modelo.Usuario;
import interfaces.IContatoDAO;
import interfaces.IEmailDAO;
import interfaces.ITelefoneDAO;

public class ContatoDAO implements IContatoDAO{
	
	private StringBuilder sql;
	private Connection cn;
	private PreparedStatement stmt;
	private Statement st;
	private ResultSet rs;
	
	private void fecharConexoes(){
		if (cn != null) {
			try {
				cn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			} finally {
				cn = null;
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			} finally {
				stmt = null;
			}
		}
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			} finally {
				st = null;
			}
		}
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			} finally {
				rs = null;
			}
		}
		sql.setLength(0);
		sql = null;
	}
	
	@Override
	public void cadastrar(Contato contato) throws Exception {
		sql = new StringBuilder();
		sql.append("insert into Contato (nmContato, nmCaminhoFoto, nmLogin)\n");
		sql.append("values (?, ?, ?)");
		try {
			cn = FabricaDeConexao.getConnection();
			stmt = cn.prepareStatement(sql.toString());
			stmt.setString(1, contato.getNmContato());
			stmt.setString(2, contato.getNmCaminhoFoto());
			stmt.setString(3, contato.getNmLogin());
			System.out.println(stmt);
			stmt.execute();
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
	}

	@Override
	public void alterar(Contato oldContato, Contato newContato) throws Exception {
		sql = new StringBuilder();
		sql.append("update Contato set\n");
		sql.append("nmContato=?,\n");
		sql.append("nmCaminhoFoto=?,\n");
		sql.append("nmLogin=?\n");
		sql.append("where cdContato=? and nmLogin=?");
		try {
			cn = FabricaDeConexao.getConnection();
			stmt = cn.prepareStatement(sql.toString());
			stmt.setString(1, newContato.getNmContato());
			stmt.setString(2, newContato.getNmCaminhoFoto());
			stmt.setString(3, newContato.getNmLogin());
			stmt.setInt(4, oldContato.getCdContato());
			stmt.setString(5, oldContato.getNmLogin());
			System.out.println(stmt);
			stmt.execute();	
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
	}

	@Override
	public void deletar(Contato contato) throws Exception {
		sql = new StringBuilder();
		sql.append("delete from Contato \n");
		sql.append("where cdContato=? and nmLogin=?");
		try {
			cn = FabricaDeConexao.getConnection();
			stmt = cn.prepareStatement(sql.toString());
			stmt.setInt(1, contato.getCdContato());
			stmt.setString(2, contato.getNmLogin());
			System.out.println(stmt);
			stmt.execute();	
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}		
	}

	@Override
	public List<Contato> consultar(int inicio, int qtRegistros) throws Exception {
		sql = new StringBuilder();
		sql.append("SELECT * FROM Contato");
		sql.append("LIMIT ?, ?");
		List<Contato> contatos = null;		
		try {
			cn = FabricaDeConexao.getConnection();
			stmt = cn.prepareStatement(sql.toString());
			stmt.setInt(1, inicio);
			stmt.setInt(2, qtRegistros);
			System.out.println(stmt);
			rs = stmt.executeQuery();
			contatos = new ArrayList<Contato>();
			Contato contato = null;
			ITelefoneDAO telefoneDAO = new TelefoneDAO();
			IEmailDAO emailDAO = new EmailDAO();
			while (rs.next()) {
				contato = new Contato();
				contato.setCdContato(rs.getInt("cdContato"));
				contato.setNmContato(rs.getString("nmContato"));
				contato.setNmCaminhoFoto(rs.getString("nmCaminhoFoto"));
				contato.setNmLogin(rs.getString("nmLogin"));
				contato.setEmails(emailDAO.getEmailsContato(contato.getCdContato()));
				contato.setTelefones(telefoneDAO.getTelefonesContato(contato.getCdContato()));
				contatos.add(contato);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
		return contatos;
	}

	@Override
	public List<Contato> consultar(String condicao) throws Exception {
		sql = new StringBuilder();
		sql.append("SELECT * FROM Contato where ?");
		List<Contato> contatos = null;
		try {
			cn = FabricaDeConexao.getConnection();
		    st = cn.createStatement();
		    String sql2 = sql.toString().replace("?", condicao);
		    System.out.println(sql2);
			rs = st.executeQuery(sql2);
			contatos = new ArrayList<Contato>();
			Contato contato = null;
			ITelefoneDAO telefoneDAO = new TelefoneDAO();
			IEmailDAO emailDAO = new EmailDAO();
			while (rs.next()) {
				contato = new Contato();
				contato.setCdContato(rs.getInt("cdContato"));
				contato.setNmContato(rs.getString("nmContato"));
				contato.setNmCaminhoFoto(rs.getString("nmCaminhoFoto"));
				contato.setNmLogin(rs.getString("nmLogin"));
				contato.setEmails(emailDAO.getEmailsContato(contato.getCdContato()));
				contato.setTelefones(telefoneDAO.getTelefonesContato(contato.getCdContato()));
				contatos.add(contato);
			}	
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
		return contatos;
	}
	
	@Override
	public List<Contato> consultar(String colunas, String condicao)	throws Exception {
		sql = new StringBuilder();
		sql.append("select ? from Contato where ?");
		List<Contato> contatos = null;
		try {
			cn = FabricaDeConexao.getConnection();
		    st = cn.createStatement();
		    String sql2 = sql.toString().replace("?", colunas).replace("?", condicao);
		    System.out.println(sql2);
			rs = st.executeQuery(sql2);
			contatos = new ArrayList<Contato>();
			Contato contato = null;
			ITelefoneDAO telefoneDAO = new TelefoneDAO();
			IEmailDAO emailDAO = new EmailDAO();
			while (rs.next()) {
				contato = new Contato();
				contato.setCdContato(rs.getInt("cdContato"));
				contato.setNmContato(rs.getString("nmContato"));
				contato.setNmCaminhoFoto(rs.getString("nmCaminhoFoto"));
				contato.setNmLogin(rs.getString("nmLogin"));
				contato.setEmails(emailDAO.getEmailsContato(contato.getCdContato()));
				contato.setTelefones(telefoneDAO.getTelefonesContato(contato.getCdContato()));
				contatos.add(contato);
			}	
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
		return contatos;
	}	

	@Override
	public Contato getContato(int cdContato, Usuario usuario) throws Exception {
		sql = new StringBuilder();
		sql.append("select * from Contato where cdContato=? and nmLogin=?");
		Contato contato = null;
		try {
			cn = FabricaDeConexao.getConnection();
			stmt = cn.prepareStatement(sql.toString());
			stmt.setInt(1, cdContato);
			stmt.setString(2, usuario.getNmLogin());
			System.out.println(stmt);
			rs = stmt.executeQuery();
			ITelefoneDAO telefoneDAO = new TelefoneDAO();
			IEmailDAO emailDAO = new EmailDAO();
			while (rs.next()) {
				contato = new Contato();
				contato.setCdContato(rs.getInt("cdContato"));
				contato.setNmContato(rs.getString("nmContato"));
				contato.setNmCaminhoFoto(rs.getString("nmCaminhoFoto"));
				contato.setNmLogin(rs.getString("nmLogin"));
				contato.setEmails(emailDAO.getEmailsContato(contato.getCdContato()));
				contato.setTelefones(telefoneDAO.getTelefonesContato(contato.getCdContato()));
			}	
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
		return contato;
	}
	
	@Override
	public List<Contato> getContato(String nmContato, Usuario usuario) throws Exception {
		sql = new StringBuilder();
		sql.append("select * from Contato where nmContato like ? and nmLogin=?");
		List<Contato> contatos = null;
		try {
			cn = FabricaDeConexao.getConnection();
			stmt = cn.prepareStatement(sql.toString());
			stmt.setString(1, nmContato + "%");
			stmt.setString(2, usuario.getNmLogin());
			System.out.println(stmt);
			rs = stmt.executeQuery();
			contatos = new ArrayList<Contato>();
			Contato contato = null;
			ITelefoneDAO telefoneDAO = new TelefoneDAO();
			IEmailDAO emailDAO = new EmailDAO();
			while (rs.next()) {
				contato = new Contato();
				contato.setCdContato(rs.getInt("cdContato"));
				contato.setNmContato(rs.getString("nmContato"));
				contato.setNmCaminhoFoto(rs.getString("nmCaminhoFoto"));
				contato.setNmLogin(rs.getString("nmLogin"));
				contato.setEmails(emailDAO.getEmailsContato(contato.getCdContato()));
				contato.setTelefones(telefoneDAO.getTelefonesContato(contato.getCdContato()));
				contatos.add(contato);
			}	
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
		return contatos;
	}

	@Override
	public List<Contato> getContatosUsuario(Usuario usuario) throws Exception {
		sql = new StringBuilder();
		sql.append("select * from Contato where nmLogin=?");
		List<Contato> contatos = null;
		try {
			cn = FabricaDeConexao.getConnection();
			stmt = cn.prepareStatement(sql.toString());
			stmt.setString(1, usuario.getNmLogin());
			System.out.println(stmt);
			rs = stmt.executeQuery();
			contatos = new ArrayList<Contato>();
			Contato contato = null;
			ITelefoneDAO telefoneDAO = new TelefoneDAO();
			IEmailDAO emailDAO = new EmailDAO();
			while (rs.next()) {
				contato = new Contato();
				contato.setCdContato(rs.getInt("cdContato"));
				contato.setNmContato(rs.getString("nmContato"));
				contato.setNmCaminhoFoto(rs.getString("nmCaminhoFoto"));
				contato.setNmLogin(rs.getString("nmLogin"));
				contato.setEmails(emailDAO.getEmailsContato(contato.getCdContato()));
				contato.setTelefones(telefoneDAO.getTelefonesContato(contato.getCdContato()));
				contatos.add(contato);
			}	
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
		return contatos;
	}

	@Override
	public List<Contato> getContatosUsuario(int pagina, int qtContatosPagina, Usuario usuario) throws Exception {
		sql = new StringBuilder();
		sql.append("select * from Contato where nmLogin like ? limit ?, ?");
		int inicio = (pagina - 1) * qtContatosPagina;
		List<Contato> contatos = null;
		try {
			cn = FabricaDeConexao.getConnection();
			stmt = cn.prepareStatement(sql.toString());
			stmt.setString(1, usuario.getNmLogin());
			stmt.setInt(2, inicio);
			stmt.setInt(3, qtContatosPagina);
			System.out.println(stmt);
			rs = stmt.executeQuery();
			contatos = new ArrayList<Contato>();
			Contato contato = null;
			ITelefoneDAO telefoneDAO = new TelefoneDAO();
			IEmailDAO emailDAO = new EmailDAO();
			while (rs.next()) {
				contato = new Contato();
				contato.setCdContato(rs.getInt("cdContato"));
				contato.setNmContato(rs.getString("nmContato"));
				contato.setNmCaminhoFoto(rs.getString("nmCaminhoFoto"));
				contato.setNmLogin(rs.getString("nmLogin"));
				contato.setEmails(emailDAO.getEmailsContato(contato.getCdContato()));
				contato.setTelefones(telefoneDAO.getTelefonesContato(contato.getCdContato()));
				contatos.add(contato);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
		return contatos;
	}

	@Override
	public int getQtContatos(Usuario usuario) throws Exception {
		sql = new StringBuilder();
		sql.append("SELECT count(*) as quantidade  FROM Contato where nmLogin like ?");
		int quantidade = 0;
		try {
			cn = FabricaDeConexao.getConnection();
			stmt = cn.prepareStatement(sql.toString());
			stmt.setString(1, usuario.getNmLogin());
			System.out.println(stmt);
			rs = stmt.executeQuery();			
			if (rs.next()) {
				quantidade = rs.getInt("quantidade");
			}			
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
		return quantidade;
	}

	@Override
	public Contato getContato(int cdContato) throws Exception {
		sql = new StringBuilder();
		sql.append("select * from Contato where cdContato=?");
		Contato contato = null;
		try {
			cn = FabricaDeConexao.getConnection();
			stmt = cn.prepareStatement(sql.toString());
			stmt.setInt(1, cdContato);
			System.out.println(stmt);
			rs = stmt.executeQuery();	
			ITelefoneDAO telefoneDAO = new TelefoneDAO();
			IEmailDAO emailDAO = new EmailDAO();
			while (rs.next()) {
				contato = new Contato();
				contato.setCdContato(rs.getInt("cdContato"));
				contato.setNmContato(rs.getString("nmContato"));
				contato.setNmCaminhoFoto(rs.getString("nmCaminhoFoto"));
				contato.setNmLogin(rs.getString("nmLogin"));
				contato.setEmails(emailDAO.getEmailsContato(contato.getCdContato()));
				contato.setTelefones(telefoneDAO.getTelefonesContato(contato.getCdContato()));
			}	
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
		return contato;
	}
}