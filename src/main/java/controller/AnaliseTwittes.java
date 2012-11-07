package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import utils.Conexao;


/**
 * classe que faz analise de twittes de determinada hastag
 * numero de usuarios
 * quais usuarios twittaram mais
 * numero de twittes positivos/negativos de acordo com um pequeno dicionario 
 * @author aers
 *
 */
public class AnaliseTwittes {
	
	private String hashtag;
	private int keywordId;
	
	//Guarda quantos twittes cada usuario postou
	private HashMap<String,Integer> frequenciaUsuarios;
	
	public AnaliseTwittes(String hashtag) throws Exception {
		this.hashtag = hashtag;
		
		Connection connection = Conexao.criarConexao();
		
		Statement st = connection.createStatement();
		String query = "select id from keywords where LOWER(name) = '"+hashtag.toLowerCase()+"'";
		ResultSet rs = st.executeQuery(query);
		rs.next();
		String id = rs.getString("id");
		if(id != null) 
			keywordId = Integer.parseInt(id);
		else
			throw new Exception("Keyword not found");
			
		System.out.println(keywordId);
		
		Conexao.fecharConexao();
		
	}
	
	/*
	 * Pegar os usuarios, iterar sobre eles, nr usuarios, usuario mais frequente.
	 */
	
	
	public HashMap<String,Integer> getFrequenciaUsuarios() throws SQLException {
		
		frequenciaUsuarios = new HashMap<String,Integer>();
		
		Connection connection = Conexao.criarConexao();
		Statement st = connection.createStatement();
		String query = "select from_user from twittes where keyword_id="+keywordId;
		ResultSet rs = st.executeQuery(query);
		String user;
		while(rs.next()) {
			user = rs.getString("from_user");
			if(frequenciaUsuarios.containsKey(user))
				frequenciaUsuarios.put(user, frequenciaUsuarios.get(user) + 1);
			else
				frequenciaUsuarios.put(user, 0);
		}
		
		
		
		return frequenciaUsuarios;
	}
	
	
	
	
	
	
	
	
	
	
	

}
