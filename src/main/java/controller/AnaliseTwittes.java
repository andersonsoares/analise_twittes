package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
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
	
	private String dataInicioString;
	private String dataFimString;
	
	//Guarda quantos twittes cada usuario postou
	private HashMap<String,Integer> frequenciaUsuarios;
	
	public AnaliseTwittes(String hashtag,String dataInicioString, String dataFimString) throws Exception {
		this.hashtag = hashtag;
		
		// TODO: Verificar estes dois campos
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dataInicio = sdf.parse(dataInicioString);
		Date dataFim = sdf.parse(dataFimString);
		if(dataInicio.after(dataFim))
			throw new Exception("DataInicio nao pode ser depois da DataFim");
		this.dataFimString = dataFimString;
		this.dataInicioString = dataInicioString;
		
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
		
		Conexao.fecharConexao();
		
	}
	
	/*
	 * Pegar os usuarios, iterar sobre eles, nr usuarios, usuario mais frequente.
	 */
	
	
	public HashMap<String,Integer> getFrequenciaUsuarios() throws SQLException {
		
		frequenciaUsuarios = new HashMap<String,Integer>();
		
		Connection connection = Conexao.criarConexao();
		Statement st = connection.createStatement();
		String query = "select from_user from twittes where keyword_id="+keywordId+addIntervaloData();
		ResultSet rs = st.executeQuery(query);
		String user;
		while(rs.next()) {
			user = rs.getString("from_user");
			if(frequenciaUsuarios.containsKey(user))
				frequenciaUsuarios.put(user, frequenciaUsuarios.get(user) + 1);
			else
				frequenciaUsuarios.put(user, 1);
		}
		
		Conexao.fecharConexao();
		
		return frequenciaUsuarios;
	}
	
	
	/*
	 * caso um dos campos das datas estiverem nulos ou vazios, a pesquisa vai ser em torno de todos os twittes
	 */
	private String addIntervaloData() {
		
		if(dataInicioString == null || dataFimString == null || dataFimString.isEmpty() || dataInicioString.isEmpty())
			return "";
		return " and date >= '"+dataInicioString+"' and date <= '"+dataFimString+"'";
	}

	public int getNrTwittes() throws SQLException {
		Connection connection = Conexao.criarConexao();
		
		String queryCountTwittes = "select count(*) from twittes where keyword_id='"+keywordId+"'"+addIntervaloData();
		Statement stCountTwittes = connection.createStatement();
		ResultSet rsCountTwittes = stCountTwittes.executeQuery(queryCountTwittes);
		rsCountTwittes.next();
		int nrTwittes = Integer.parseInt(rsCountTwittes.getString("count"));
		
		
		Conexao.fecharConexao();
		return nrTwittes;
	}
	
	
	
	
	
	
	
	
	
	

}
