package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import utils.Conexao;

public class RemoverDuplicidade {

	public void execute() {
		
		Connection connection;
		try {
			connection = Conexao.criarConexao();
			int i=0;
			String query = "select id_str, count(*) from twittes group by id_str having count(*) > 1";
			Statement st = connection.createStatement();
			ResultSet result = st.executeQuery(query);
			while(result.next()) {
				String query2 = "delete from twittes where id_str='"+result.getString("id_str")+"'";
				
				Statement st2 = connection.createStatement();
				st2.execute(query2);
				st2.close();
				System.out.println("Removido ["+i+++"]");
			}
			System.out.println("Finalizado(remover duplicidade");
			Conexao.fecharConexao();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
