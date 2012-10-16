package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import utils.Conexao;


/**
 * Classe que recebe o nome da tabela que vai ser salva em arquivo como parametro
 * Caso nenhuma tabela seja passada, o sistema grava todos os dados no arquivo
 * @author aers
 *
 */
public class SalvarEmArquivo {

	public SalvarEmArquivo() {}
	
	public void execute() throws SQLException, IOException {
		Connection connection = Conexao.criarConexao();
		
		
		//---Recuperar todas as keywords
		String queryListKeywords = "select * from keywords";
		Statement stListKeywords = connection.createStatement();
		ResultSet rsListKeywords = stListKeywords.executeQuery(queryListKeywords);
		
		String keyword = null;
		String keywordId = null;
		String queryCountTwittes = null;
		Statement stCountTwittes = null;
		ResultSet rsCountTwittes = null;
		
		int nrTwittes;
		int limit = 1000;
		int offset;
		
		String queryLoopTwittes = null;
		Statement stLoopTwittes = null;
		ResultSet rsLoopTwittes = null;
		
		File file = null;
		Writer out = null;
		
		File fileFull = new File("todos.txt");
		Writer outFull = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileFull), "UTF-8"));
		while(rsListKeywords.next()) {
			
			keyword = rsListKeywords.getString("name");
			keywordId = rsListKeywords.getString("id");
			
			//iniciar o writer
			file = new File("twittes_"+keywordId.toString()+".txt");
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
			
			
			//countar quantos twittes tem em cada keyword
			queryCountTwittes = "select count(*) from twittes where keyword_id='"+keywordId+"'";
			stCountTwittes = connection.createStatement();
			rsCountTwittes = stCountTwittes.executeQuery(queryCountTwittes);
			rsCountTwittes.next();
			nrTwittes = Integer.parseInt(rsCountTwittes.getString("count"));
			System.out.println("["+nrTwittes+"] twittes for "+keyword);
			
			offset = 0;
			//loop em cima dos twittes, recuperando eles de pouquinho
			while(offset < nrTwittes) {
				queryLoopTwittes = "select * from twittes where keyword_id='"+keywordId+"' offset "+offset+" limit "+limit;
				stLoopTwittes = connection.createStatement();
				rsLoopTwittes = stLoopTwittes.executeQuery(queryLoopTwittes);
				
				while(rsLoopTwittes.next()) {
					
					String texto = rsLoopTwittes.getString("text");
					texto = texto.replace("\n"," ");
					out.append(texto+"\n");
					
					outFull.append(texto+"\n");
				}
				
				
				stLoopTwittes.close();
				offset+= 1000;
			}
			System.out.println("Finalizando");
			out.close();
			stCountTwittes.close();
			
			
		}
		
		outFull.close();
		Conexao.fecharConexao();
		
	}
}
