package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import conexao.FabricaDeConexao;
import modelo.Usuario;
import interfaces.IUsuarioDAO;

public class UsuarioDAO implements IUsuarioDAO{

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
	public void cadastrar(Usuario usuario) throws Exception {
		sql = new StringBuilder();
		sql.append("insert into Usuario (nmLogin, nmSenha, nmUsuario, nmEmail)\n");
		sql.append("values (?, ?, ?, ?)");
		try {
			cn = FabricaDeConexao.getConnection();
			stmt = cn.prepareStatement(sql.toString());
			stmt.setString(1, usuario.getNmLogin());
			stmt.setString(2, usuario.getNmSenha());
			stmt.setString(3, usuario.getNmUsuario());
			stmt.setString(4, usuario.getNmEmail());
			System.out.println(stmt);
			stmt.execute();
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}		
	}

	@Override
	public void alterar(Usuario oldUsuario, Usuario newUsuario) throws Exception {
		sql = new StringBuilder();
		sql.append("update Usuario set\n");
		sql.append("nmSenha=?,\n");
		sql.append("nmUsuario=?,\n");
		sql.append("nmEmail=?t\n");
		sql.append("where nmLogin=?");
		try {
			cn = FabricaDeConexao.getConnection();
			stmt = cn.prepareStatement(sql.toString());		
			stmt.setString(1, newUsuario.getNmSenha());
			stmt.setString(2, newUsuario.getNmUsuario());
			stmt.setString(3, newUsuario.getNmEmail());
			stmt.setString(4, oldUsuario.getNmLogin());
			System.out.println(stmt);
			stmt.execute();	
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}				
	}

	@Override
	public void deletar(Usuario usuario) throws Exception {
		sql = new StringBuilder();
		sql.append("delete from Usuario \n");
		sql.append("where nmLogin=?");
		try {
			cn = FabricaDeConexao.getConnection();
			stmt = cn.prepareStatement(sql.toString());		
			stmt.setString(1, usuario.getNmLogin());
			System.out.println(stmt);
			stmt.execute();	
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
	}

	@Override
	public List<Usuario> consultar(int inicio, int qtRegistros) throws Exception {
		sql = new StringBuilder();
		sql.append("SELECT * FROM Usuario\n");
		sql.append("LIMIT ?, ?");
		List<Usuario> usuarios = null;
		try {
			cn = FabricaDeConexao.getConnection();
			stmt = cn.prepareStatement(sql.toString());
			stmt.setInt(1, inicio);
			stmt.setInt(2, qtRegistros);
			System.out.println(stmt);
			rs = stmt.executeQuery();
			usuarios = new ArrayList<Usuario>();
			Usuario usuario = null;
			while (rs.next()) {
				usuario = new Usuario();
				usuario.setNmLogin(rs.getString("nmLogin"));
				usuario.setNmSenha(rs.getString("nmSenha"));
				usuario.setNmUsuario(rs.getString("nmUsuario"));
				usuario.setNmEmail(rs.getString("nmEmail"));
				usuarios.add(usuario);
			}	
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
		return usuarios;
	}

	@Override
	public List<Usuario> consultar(String condicao) throws Exception {
		sql = new StringBuilder();
		sql.append("SELECT * FROM Usuario\n");
		sql.append("where ?");
		List<Usuario> usuarios = null;
		try {
			cn = FabricaDeConexao.getConnection();
			st = cn.createStatement();
			String sql2 = sql.toString().replace("?", condicao);
			System.out.println(sql2);
			rs = st.executeQuery(sql2);
			usuarios = new ArrayList<Usuario>();
			Usuario usuario = null;
			while (rs.next()) {
				usuario = new Usuario();
				usuario.setNmLogin(rs.getString("nmLogin"));
				usuario.setNmSenha(rs.getString("nmSenha"));
				usuario.setNmUsuario(rs.getString("nmUsuario"));
				usuario.setNmEmail(rs.getString("nmEmail"));
				usuarios.add(usuario);
			}	
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
		return usuarios;
	}
	
	@Override
	public List<Usuario> consultar(String colunas, String condicao)	throws Exception {
		sql = new StringBuilder();
		sql.append("select ? from Usuario where ?");
		List<Usuario> usuarios = null;
		try {
			cn = FabricaDeConexao.getConnection();
			st = cn.createStatement();
			String sql2 = sql.toString().replace("?", colunas).replace("?", condicao);
			System.out.println(sql2);
			rs = st.executeQuery(sql2);
			usuarios = new ArrayList<Usuario>();
			Usuario usuario = null;
			while (rs.next()) {
				usuario = new Usuario();
				usuario.setNmLogin(rs.getString("nmLogin"));
				usuario.setNmSenha(rs.getString("nmSenha"));
				usuario.setNmUsuario(rs.getString("nmUsuario"));
				usuario.setNmEmail(rs.getString("nmEmail"));
				usuarios.add(usuario);
			}	
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
		return usuarios;
	}

	@Override
	public List<Usuario> getUsuario(String nmLogin)throws Exception {
		sql = new StringBuilder();
		sql.append("select * from Usuario where nmLogin like ?");
		List<Usuario> usuarios = null;
		try {
			cn = FabricaDeConexao.getConnection();
			stmt = cn.prepareStatement(sql.toString());
			stmt.setString(1, nmLogin + "%");
			System.out.println(stmt);
			rs = stmt.executeQuery();
			usuarios = new ArrayList<Usuario>();
			Usuario usuario = null;
			while (rs.next()) {
				usuario = new Usuario();
				usuario.setNmLogin(rs.getString("nmLogin"));
				usuario.setNmSenha(rs.getString("nmSenha"));
				usuario.setNmEmail(rs.getString("nmEmail"));
				usuario.setNmUsuario(rs.getString("nmUsuario"));
				usuarios.add(usuario);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
		return usuarios;
	}

	@Override
	public Usuario getUsuario(String nmLogin, String nmSenha) throws Exception {
		sql = new StringBuilder();
		sql.append("select * from Usuario where nmLogin=? and nmSenha=?");
		Usuario usuario = null;
		try {
			cn = FabricaDeConexao.getConnection();
			stmt = cn.prepareStatement(sql.toString());
			stmt.setString(1, nmLogin);
			stmt.setString(2, nmSenha);
			System.out.println(stmt);
			rs = stmt.executeQuery();			
			while (rs.next()) {
				usuario = new Usuario();
				usuario.setNmLogin(rs.getString("nmLogin"));
				usuario.setNmSenha(rs.getString("nmSenha"));
				usuario.setNmEmail(rs.getString("nmEmail"));
				usuario.setNmUsuario(rs.getString("nmUsuario"));
			}	
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
		return usuario;
	}
}