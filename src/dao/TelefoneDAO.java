package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import conexao.FabricaDeConexao;
import modelo.Telefone;
import modelo.Usuario;
import interfaces.IContatoDAO;
import interfaces.ITelefoneDAO;

public class TelefoneDAO implements ITelefoneDAO{

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
	public void cadastrar(Telefone telefone) throws Exception {
		sql = new StringBuilder();
		sql.append("insert into Telefone (nmTelefone, cdContato, cdTipoTelefone)\n");
		sql.append("values (?, ?, ?)");
		try {
			cn = FabricaDeConexao.getConnection();
			stmt = cn.prepareStatement(sql.toString());
			stmt.setString(1, telefone.getNmTelefone());
			stmt.setInt(2, telefone.getContato().getCdContato());
			stmt.setInt(3, telefone.getCdTipo());
			System.out.println(stmt);
			stmt.execute();	
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
	}

	@Override
	public void alterar(Telefone oldTelefone, Telefone newTelefone) throws Exception {
		sql = new StringBuilder();
		sql.append("update Telefone set\n");
		sql.append("nmTelefone=?,\n");
		sql.append("cdContato=?,\n");
		sql.append("cdTipoTelefone=?\n");
		sql.append("where\n");
		sql.append("nmTelefone=? and cdContato=?");
		try {
			cn = FabricaDeConexao.getConnection();
			stmt = cn.prepareStatement(sql.toString());
			stmt.setString(1, newTelefone.getNmTelefone());
			stmt.setInt(2, newTelefone.getContato().getCdContato());
			stmt.setInt(3, newTelefone.getCdTipo());
			stmt.setString(4, oldTelefone.getNmTelefone());
			stmt.setInt(5, oldTelefone.getContato().getCdContato());
			System.out.println(stmt);
			stmt.execute();	
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
	}

	@Override
	public void deletar(Telefone telefone) throws Exception {
		sql = new StringBuilder();
		sql.append("delete from Telefone\n");
		sql.append("where nmTelefone=? and cdContato=?");
		try {
			cn = FabricaDeConexao.getConnection();
			stmt = cn.prepareStatement(sql.toString());
			stmt.setString(1, telefone.getNmTelefone());
			stmt.setInt(2, telefone.getContato().getCdContato());
			System.out.println(stmt);
			stmt.execute();	
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
	}
	
	@Override
	public void deletar(Telefone telefone, Usuario usuario) throws Exception {
		sql = new StringBuilder();
		sql.append("delete t.* from Telefone t\n");
		sql.append("inner join Contato c\n");
		sql.append("on t.cdContato = c.cdContato\n");
		sql.append("inner join Usuario u\n");
		sql.append("on c.nmLogin = u.nmLogin\n");
		sql.append("where t.nmTelefone=? and c.cdContato=? and u.nmLogin=?");
		try {
			cn = FabricaDeConexao.getConnection();
			stmt = cn.prepareStatement(sql.toString());
			stmt.setString(1, telefone.getNmTelefone());
			stmt.setInt(2, telefone.getContato().getCdContato());
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
	public List<Telefone> consultar(int inicio, int qtRegistros) throws Exception {
		sql = new StringBuilder();
		sql.append("SELECT * FROM Telefone LIMIT ?, ?");
		List<Telefone> telefones = null;
		try {
			cn = FabricaDeConexao.getConnection();
			stmt = cn.prepareStatement(sql.toString());
			stmt.setInt(1, inicio);
			stmt.setInt(2, qtRegistros);
			System.out.println(stmt);
			rs = stmt.executeQuery();
			telefones = new ArrayList<Telefone>();
			Telefone telefone = null;
			IContatoDAO contatoDAO = new ContatoDAO();
			while (rs.next()) {				
				telefone = new Telefone();
				telefone.setNmTelefone(rs.getString("nmTelefone"));
				telefone.setContato(contatoDAO.getContato(rs.getInt("cdContato")));
				telefone.setCdTipo(rs.getInt("cdTipoTelefone"));
				telefones.add(telefone);
			}	
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
		return telefones;
	}

	@Override
	public List<Telefone> consultar(String condicao) throws Exception {
		sql = new StringBuilder();
		sql.append("SELECT * FROM Telefone where ?");
		List<Telefone> telefones = null;
		try {
			cn = FabricaDeConexao.getConnection();
			st = cn.createStatement();
			String sql2 = sql.toString().replace("?", condicao);
			System.out.println(sql2);
			rs = st.executeQuery(sql2);
			telefones = new ArrayList<Telefone>();
			Telefone telefone = null;
			IContatoDAO contatoDAO = new ContatoDAO();
			while (rs.next()) {
				telefone = new Telefone();
				telefone.setNmTelefone(rs.getString("nmTelefone"));
				telefone.setContato(contatoDAO.getContato(rs.getInt("cdContato")));
				telefone.setCdTipo(rs.getInt("cdTipoTelefone"));
				telefones.add(telefone);
			}	
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
		return telefones;
	}
	
	@Override
	public List<Telefone> consultar(String colunas, String condicao) throws Exception {
		sql = new StringBuilder();
		sql.append("select ? from Telefone where ?");
		List<Telefone> telefones = null;
		try {
			cn = FabricaDeConexao.getConnection();
			st = cn.createStatement();
			String sql2 = sql.toString().replace("?", colunas).replace("?", condicao);
			System.out.println(sql2);
			rs = st.executeQuery(sql2);
			telefones = new ArrayList<Telefone>();
			Telefone telefone = null;
			IContatoDAO contatoDAO = new ContatoDAO();
			while (rs.next()) {
				telefone = new Telefone();
				telefone.setNmTelefone(rs.getString("nmTelefone"));
				telefone.setContato(contatoDAO.getContato(rs.getInt("cdContato")));
				telefone.setCdTipo(rs.getInt("cdTipoTelefone"));
				telefones.add(telefone);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
		return telefones;
	}

	@Override
	public Telefone getTelefone(String nmTelefone) throws Exception {
		sql = new StringBuilder();
		sql.append("select * from Telefone where nmTelefone=?");
		Telefone telefone = null;
		try {
			cn = FabricaDeConexao.getConnection();
			stmt = cn.prepareStatement(sql.toString());
			stmt.setString(1, nmTelefone);
			System.out.println(stmt);
			rs = stmt.executeQuery();
			IContatoDAO contatoDAO = new ContatoDAO();
			while (rs.next()) {
				telefone = new Telefone();
				telefone.setNmTelefone(rs.getString("nmTelefone"));
				telefone.setContato(contatoDAO.getContato(rs.getInt("cdContato")));
				telefone.setCdTipo(rs.getInt("cdTipoTelefone"));
			}	
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
		return telefone;
	}

	@Override
	public List<Telefone> getTelefonesContato(int cdContato, Usuario usuario) throws Exception {
		sql = new StringBuilder();
		sql.append("select t.* from Telefone t\n");
		sql.append("inner join Contato c\n");
		sql.append("on t.cdContato = c.cdContato\n");
		sql.append("where t.cdContato=? and c.nmLogin=?");
		List<Telefone> telefones = null;
		try {
			cn = FabricaDeConexao.getConnection();
			stmt = cn.prepareStatement(sql.toString());
			stmt.setInt(1, cdContato);
			stmt.setString(2, usuario.getNmLogin());
			rs = stmt.executeQuery();
			telefones = new ArrayList<Telefone>();
			Telefone telefone = null;
			while (rs.next()) {				
				telefone = new Telefone();
				telefone.setNmTelefone(rs.getString("nmTelefone"));
				telefone.setCdTipo(rs.getInt("cdTipoTelefone"));
				telefones.add(telefone);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
		return telefones;
	}

	@Override
	public Telefone getTelefone(String nmTelefone, int cdContato) throws Exception {
		sql = new StringBuilder();
		sql.append("select * from Telefone where nmTelefone=? and cdContato=?");
		Telefone telefone = null;
		try {
			cn = FabricaDeConexao.getConnection();
			stmt = cn.prepareStatement(sql.toString());
			stmt.setString(1, nmTelefone);
			stmt.setInt(2, cdContato);
			System.out.println(stmt);
			rs = stmt.executeQuery();
			IContatoDAO contatoDAO = new ContatoDAO();
			while (rs.next()) {				
				telefone = new Telefone();
				telefone.setNmTelefone(rs.getString("nmTelefone"));
				telefone.setContato(contatoDAO.getContato(rs.getInt("cdContato")));
				telefone.setCdTipo(rs.getInt("cdTipoTelefone"));
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
		return telefone;
	}

	@Override
	public List<Telefone> getTelefonesContato(int cdContato) throws Exception {
		sql = new StringBuilder();
		sql.append("select * from Telefone \n");
		sql.append("where cdContato=?");
		List<Telefone> telefones = null;
		try {
			cn = FabricaDeConexao.getConnection();
			stmt = cn.prepareStatement(sql.toString());
			stmt.setInt(1, cdContato);
			rs = stmt.executeQuery();
			telefones = new ArrayList<Telefone>();
			Telefone telefone = null;
			while (rs.next()) {				
				telefone = new Telefone();
				telefone.setNmTelefone(rs.getString("nmTelefone"));
				telefone.setCdTipo(rs.getInt("cdTipoTelefone"));
				telefones.add(telefone);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
		return telefones;
	}
}