package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import utils.Auxiliar;


public class RunAnalise {

	public static void main(String args[]) {
//		new RemoverDuplicidade().execute();
		try {
			
			List<String> hashtags = new ArrayList<String>();
			hashtags.add("#debatemn");
			hashtags.add("#debatecidadeverde");
			hashtags.add("#debateantena10");
//			String hashtag = "#debatemn";
			String dataInicio = "2012-09-25";
			String dataFim = "2012-09-29";
			
			
			AnaliseTwittes at = new AnaliseTwittes(hashtags, dataInicio, dataFim);
			HashMap<String, Integer> frequencia = at.getFrequenciaUsuarios();
			Set<String> set = frequencia.keySet();
			Iterator<String> it = set.iterator();
//			while(it.hasNext()) {
//				String user = it.next();
//				System.out.println(user+" - "+frequencia.get(user));
//			}
			int totalUsers = frequencia.size();
			int totalTwittes = at.getNrTwittes();
//			System.out.println("Analise para: "+hashtag);
			System.out.println("Periodo: "+dataInicio+" ate "+dataFim);
			System.out.println("Total de usuarios que twittaram: "+totalUsers);
			System.out.println("Total de twittes: "+totalTwittes);
			
			System.out.println("------------------------------------");
			
			HashMap<String,Integer> frequenciaOrdenada = Auxiliar.ordenarHashDesc(frequencia);
			
			set = frequenciaOrdenada.keySet();
			it = set.iterator();
			while(it.hasNext()) {
				String user = it.next();
				float percentual = (float)frequenciaOrdenada.get(user) / totalTwittes * 100;
				
				System.out.println(user+"\t\t\t"+frequenciaOrdenada.get(user)+"\t\t\t"+percentual+"%");
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
