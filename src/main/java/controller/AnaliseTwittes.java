package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
	
	private List<String> hashtags;
	private List<Integer> keywordIds;
	
	private String dataInicioString;
	private String dataFimString;
	
	//Guarda quantos twittes cada usuario postou
	private HashMap<String,Integer> frequenciaUsuarios;
	
	public AnaliseTwittes(List<String> hashtags,String dataInicioString, String dataFimString) throws Exception {
		this.hashtags = hashtags;
		
		// TODO: Verificar estes dois campos
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dataInicio = sdf.parse(dataInicioString);
		Date dataFim = sdf.parse(dataFimString);
		if(dataInicio.after(dataFim))
			throw new Exception("DataInicio nao pode ser depois da DataFim");
		this.dataFimString = dataFimString;
		this.dataInicioString = dataInicioString;
		
		Connection connection = Conexao.criarConexao();
		
		Statement st;
		String query;
		ResultSet rs;
		String id;
		keywordIds = new ArrayList<Integer>();
		for(String hashtag : hashtags) {
			st = connection.createStatement();
			query = "select id from keywords where LOWER(name) = '"+hashtag.toLowerCase()+"'";
			rs = st.executeQuery(query);
			if(rs.next()) {
				id = rs.getString("id");
				if(id != null) 
					keywordIds.add(Integer.parseInt(id));
				else
					throw new Exception("Keyword '"+hashtag+"' not found, ocorreu um erro...");
			} else 
				throw new Exception("Keyword '"+hashtag+"' not found");
			
			st.close();
			rs.close();
		}
		
		
		Conexao.fecharConexao();
		
	}
	
	/*
	 * Pegar os usuarios, iterar sobre eles, nr usuarios, usuario mais frequente.
	 */
	
	
	public HashMap<String,Integer> getFrequenciaUsuarios() throws SQLException {
		
		frequenciaUsuarios = new HashMap<String,Integer>();
		
		Connection connection = Conexao.criarConexao();
		
		Statement st;
		String query;
		ResultSet rs;
		String user;
		for(Integer keywordId : keywordIds) {
			st = connection.createStatement();
			query = "select from_user from twittes where keyword_id="+keywordId+addIntervaloData();
			rs = st.executeQuery(query);
			
			while(rs.next()) {
				user = rs.getString("from_user");
				if(frequenciaUsuarios.containsKey(user))
					frequenciaUsuarios.put(user, frequenciaUsuarios.get(user) + 1);
				else
					frequenciaUsuarios.put(user, 1);
			}
			
			rs.close();
			st.close();
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
		
		int nrTwittes=0;
		
		Statement stCountTwittes;
		ResultSet rsCountTwittes;
		String queryCountTwittes;
		for(Integer keywordId : keywordIds) {
			stCountTwittes = connection.createStatement();
			queryCountTwittes = "select count(*) from twittes where keyword_id='"+keywordId+"'"+addIntervaloData();
			rsCountTwittes = stCountTwittes.executeQuery(queryCountTwittes);
			if(rsCountTwittes.next())
				nrTwittes += Integer.parseInt(rsCountTwittes.getString("count"));
			
			rsCountTwittes.close();
			stCountTwittes.close();
		}
		
		
		Conexao.fecharConexao();
		return nrTwittes;
	}
	
	
	
	/**
	 * Metodo que faz uma busca de acordo com a lista de params
	 * em todas as hashtags passadas por param
	 * @throws SQLException 
	 */
	public int getNrTwittesContaining(List<String> words) throws SQLException {
		
		Connection connection = Conexao.criarConexao();
		
		//montar string para a query sql
		//ex: word1|word2|word3|...|word4
		String queryCondition = "";
		for(int i=0;i<words.size();i++) {
			if(i+1 < words.size())
				queryCondition = queryCondition.concat(words.get(i)+"|");
			else
				queryCondition = queryCondition.concat(words.get(i));
		}
		
		Statement st;
		String query;
		ResultSet rs;
		
		int nrTwittes=0;
		for(Integer keywordId : keywordIds) {
			st = connection.createStatement();
//			select count(*) from twittes where ((keyword_id=1) AND (lower(text) similar to '%(elmano ferrer|elmano14|elmano|ptb|14)%'));
			query = "select count(*) from twittes where ((keyword_id="+keywordId+") AND (lower(text) similar to '%("+queryCondition+")%') )";
			rs = st.executeQuery(query);
			
			if(rs.next()) {
				nrTwittes += Integer.parseInt(rs.getString("count"));
			}
			
			st.close();
			rs.close();
		}
		
		
		
		Conexao.fecharConexao();
		
		return nrTwittes;
	}
	
	
	
	
	
	
	
	
	

}
