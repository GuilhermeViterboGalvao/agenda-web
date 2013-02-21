package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import conexao.FabricaDeConexao;
import modelo.Email;
import modelo.Usuario;
import interfaces.IContatoDAO;
import interfaces.IEmailDAO;

public class EmailDAO implements IEmailDAO {

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
	public void cadastrar(Email email) throws Exception {
		sql = new StringBuilder();
		sql.append("insert into Email  (nmEmail, cdContato)\n");
		sql.append("values (?, ?)");
		try {
			cn = FabricaDeConexao.getConnection();
			stmt = cn.prepareStatement(sql.toString());
			stmt.setString(1, email.getNmEmail());
			stmt.setInt(2, email.getContato().getCdContato());
			System.out.println(stmt);
			stmt.execute();	
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
	}

	@Override
	public void alterar(Email oldEmail, Email newEmail) throws Exception {
		sql = new StringBuilder();
		sql.append("update Email set nmEmail=?, cdContato=?\n");
		sql.append("where nmEmail=? and cdContato=?");
		try {
			cn = FabricaDeConexao.getConnection();
			stmt = cn.prepareStatement(sql.toString());
			stmt.setString(1, newEmail.getNmEmail());
			stmt.setInt(2, newEmail.getContato().getCdContato());
			stmt.setString(3, oldEmail.getNmEmail());
			stmt.setInt(4, oldEmail.getContato().getCdContato());
			System.out.println(stmt);
			stmt.execute();	
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
	}

	@Override
	public void deletar(Email email) throws Exception {
		sql = new StringBuilder();
		sql.append("delete from Email where nmEmail=? and cdContato=?");
		try {
			cn = FabricaDeConexao.getConnection();
			stmt = cn.prepareStatement(sql.toString());
			stmt.setString(1, email.getNmEmail());
			stmt.setInt(2, email.getContato().getCdContato());
			System.out.println(stmt);
			stmt.execute();	
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
	}
	
	@Override
	public void deletar(Email email, Usuario usuario) throws Exception {
		sql = new StringBuilder();
		sql.append("delete e.* from Email e\n");
		sql.append("inner join Contato c\n");
		sql.append("on e.cdContato = c.cdContato\n");
		sql.append("inner join Usuario u\n");
		sql.append("on c.nmLogin = u.nmLogin\n");
		sql.append("where e.nmEmail=? and c.cdContato=? and u.nmLogin=?");
		try {
			cn = FabricaDeConexao.getConnection();
			stmt = cn.prepareStatement(sql.toString());
			stmt.setString(1, email.getNmEmail());
			stmt.setInt(2, email.getContato().getCdContato());
			stmt.setString(3, usuario.getNmLogin());
			System.out.println(stmt);
			stmt.execute();
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
	}

	@Override
	public List<Email> consultar(int inicio, int qtRegistros) throws Exception {
		sql = new StringBuilder();
		sql.append("SELECT * FROM Email LIMIT ?, ?");
		List<Email> emails = null;
		try {
			cn = FabricaDeConexao.getConnection();
			stmt = cn.prepareStatement(sql.toString());
			stmt.setInt(1, inicio);
			stmt.setInt(2, qtRegistros);
			System.out.println(stmt);
			rs = stmt.executeQuery();
			emails = new ArrayList<Email>();
			Email email = null;
			IContatoDAO contatoDAO = new ContatoDAO();
			while (rs.next()) {				
				email = new Email();
				email.setNmEmail(rs.getString("nmEmail"));
				email.setContato(contatoDAO.getContato(rs.getInt("cdContato")));
				emails.add(email);
			}	
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
		return emails;
	}

	@Override
	public List<Email> consultar(String condicao) throws Exception {
		sql = new StringBuilder();
		sql.append("SELECT * FROM Email where ?");
		List<Email> emails = null;
		try {
			cn = FabricaDeConexao.getConnection();
			st = cn.createStatement();
			String sql2 = sql.toString().replace("?", condicao);
			System.out.println(sql2);
			rs = st.executeQuery(sql2);
			emails = new ArrayList<Email>();
			Email email = null;
			IContatoDAO contatoDAO = new ContatoDAO();
			while (rs.next()) {
				email = new Email();
				email.setNmEmail(rs.getString("nmEmail"));
				email.setContato(contatoDAO.getContato(rs.getInt("cdContato")));
				emails.add(email);
			}	
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
		return emails;
	}
	
	@Override
	public List<Email> consultar(String colunas, String condicao) throws Exception {
		sql = new StringBuilder();
		sql.append("select ? from Email where ?");
		List<Email> emails = null;
		try {
			cn = FabricaDeConexao.getConnection();
			st = cn.createStatement();
			String sql2 = sql.toString().replace("?", colunas).replace("?", condicao);
			System.out.println(sql2);
			rs = st.executeQuery(sql2);
			emails = new ArrayList<Email>();
			Email email = null;
			IContatoDAO contatoDAO = new ContatoDAO();
			while (rs.next()) {
				email = new Email();
				email.setNmEmail(rs.getString("nmEmail"));
				email.setContato(contatoDAO.getContato(rs.getInt("cdContato")));
				emails.add(email);
			}	
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
		return emails;
	}

	@Override
	public Email getEmail(String nmEmail) throws Exception {
		sql = new StringBuilder();
		sql.append("select * from Email where nmEmail=?");
		Email email = null;
		try {
			cn = FabricaDeConexao.getConnection();
			stmt = cn.prepareStatement(sql.toString());
			stmt.setString(1, nmEmail);
			System.out.println(stmt);
			rs = stmt.executeQuery();
			IContatoDAO contatoDAO = new ContatoDAO();
			while (rs.next()) {
				email = new Email();
				email.setNmEmail(rs.getString("nmEmail"));
				email.setContato(contatoDAO.getContato(rs.getInt("cdContato")));
			}	
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
		return email;
	}

	@Override
	public List<Email> getEmailsContato(int cdContato, Usuario usuario) throws Exception {
		sql = new StringBuilder();
		sql.append("select e.* from Email e\n");
		sql.append("inner join Contato c\n");
		sql.append("on e.cdContato = c.cdContato\n");
		sql.append("where e.cdContato=? and c.nmLogin=?");
		List<Email> emails = null;
		try {
			cn = FabricaDeConexao.getConnection();
			stmt = cn.prepareStatement(sql.toString());
			stmt.setInt(1, cdContato);
			stmt.setString(2, usuario.getNmLogin());
			System.out.println(stmt);
			rs = stmt.executeQuery();
			emails = new ArrayList<Email>();
			Email email = null;
			while (rs.next()) {
				email = new Email();
				email.setNmEmail(rs.getString("nmEmail"));
				emails.add(email);
			}	
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
		return emails;
	}

	@Override
	public Email getEmail(String nmEmail, int cdContato) throws Exception {
		sql = new StringBuilder();
		sql.append("select * from Email where nmEmail=? and cdContato=?");
		Email email = null;
		try {
			cn = FabricaDeConexao.getConnection();
			stmt = cn.prepareStatement(sql.toString());
			stmt.setString(1, nmEmail);
			stmt.setInt(2, cdContato);
			System.out.println(stmt);
			rs = stmt.executeQuery();
			IContatoDAO contatoDAO = new ContatoDAO();
			while (rs.next()) {
				email = new Email();
				email.setNmEmail(rs.getString("nmEmail"));
				email.setContato(contatoDAO.getContato(rs.getInt("cdContato")));
			}	
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
		return email;
	}

	@Override
	public List<Email> getEmailsContato(int cdContato) throws Exception {
		sql = new StringBuilder();
		sql.append("select * from Email \n");
		sql.append("where cdContato=?");
		List<Email> emails = null;
		try {
			cn = FabricaDeConexao.getConnection();
			stmt = cn.prepareStatement(sql.toString());
			stmt.setInt(1, cdContato);
			System.out.println(stmt);
			rs = stmt.executeQuery();
			emails = new ArrayList<Email>();
			Email email = null;
			while (rs.next()) {
				email = new Email();
				email.setNmEmail(rs.getString("nmEmail"));
				emails.add(email);
			}	
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
		return emails;
	}
}