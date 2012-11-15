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
			
			//1 debatecidadeverde 2012-10-02
			String dataInicio = "2012-09-30";
			String dataFim = "2012-10-05";
			
			//2 debatecidadeverde 2012-10-23
//			String dataInicio = "2012-10-20";
//			String dataFim = "2012-10-25";
			
			//1 debatemn 2012-07-13
//			String dataInicio = "2012-07-10";
//			String dataFim = "2012-07-15";
			
			//2 debatemn 2012-09-27
//			String dataInicio = "2012-09-25";
//			String dataFim = "2012-09-30";
			
			//1 debateantena10 2012-09-04
//			String dataInicio = "2012-09-02";
//			String dataFim = "2012-09-06";
			
			//2 debateantena10 2012-10-01
//			String dataInicio = "2012-09-28";
//			String dataFim = "2012-10-03";
			
			
			List<String>words = new ArrayList<String>();
			words.add("firmino");
			words.add("firmino45");
			words.add("firmino filho");
			words.add("firmino_filho");
			words.add("45");
			words.add("psdb");
			
//			words.add("elmano");
//			words.add("elmano_14");
//			words.add("elmano14");
//			words.add("elmano ferrer");
//			words.add("14");
//			words.add("ptb");
			
			String queryCondition = "";
			for(int i=0;i<words.size();i++) {
				if(i+1 < words.size())
					queryCondition = queryCondition.concat(words.get(i)+"|");
				else
					queryCondition = queryCondition.concat(words.get(i));
			}
			
			AnaliseTwittes at = new AnaliseTwittes(hashtags, dataInicio, dataFim);
			
			System.out.println("Analise para: "+Auxiliar.juntarDadosArray(hashtags));
			System.out.println("Periodo: "+dataInicio+" ate "+dataFim);
			System.out.println("Words: "+Auxiliar.juntarDadosArray(words));
			System.out.println("Total twittes que referenciam words: "+at.getNrTwittesContaining(words));
			
//			HashMap<String, Integer> frequencia = at.getFrequenciaUsuarios();
//			Set<String> set = frequencia.keySet();
//			Iterator<String> it = set.iterator();
////			while(it.hasNext()) {
////				String user = it.next();
////				System.out.println(user+" - "+frequencia.get(user));
////			}
//			int totalUsers = frequencia.size();
//			int totalTwittes = at.getNrTwittes();
////			System.out.println("Analise para: "+hashtag);
//			System.out.println("Periodo: "+dataInicio+" ate "+dataFim);
//			System.out.println("Total de usuarios que twittaram: "+totalUsers);
//			System.out.println("Total de twittes: "+totalTwittes);
//			
//			System.out.println("------------------------------------");
//			
//			HashMap<String,Integer> frequenciaOrdenada = Auxiliar.ordenarHashDesc(frequencia);
//			
//			set = frequenciaOrdenada.keySet();
//			it = set.iterator();
//			while(it.hasNext()) {
//				String user = it.next();
//				float percentual = (float)frequenciaOrdenada.get(user) / totalTwittes * 100;
//				
//				System.out.println(user+"\t\t\t"+frequenciaOrdenada.get(user)+"\t\t\t"+percentual+"%");
//			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
