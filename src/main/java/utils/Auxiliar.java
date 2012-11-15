package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class Auxiliar {

	
	/*
	 * Funcao que ordena um hashmap
	 */
	public static HashMap<String, Integer> ordenarHashDesc(HashMap<String, Integer> hash) {
	
		
		@SuppressWarnings("unchecked")
		HashMap<String,Integer> aux = (HashMap<String, Integer>) hash.clone();
		
		List<String> mapKeys = new ArrayList<String>(aux.keySet());
		List<Integer> mapValues = new ArrayList<Integer>(aux.values());
		Collections.sort(mapValues);
		Collections.reverse(mapValues);

		Collections.sort(mapKeys);
		Collections.reverse(mapKeys);

		LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();

		Iterator<Integer> valueIt = mapValues.iterator();
		while (valueIt.hasNext()) {
			Object val = valueIt.next();
			Iterator<String> keyIt = mapKeys.iterator();

			while (keyIt.hasNext()) {
				Object key = keyIt.next();
				String comp1 = aux.get(key).toString();
				String comp2 = val.toString();
				if (comp1.equals(comp2)){
					aux.remove(key);
					mapKeys.remove(key);

					sortedMap.put((String)key, (Integer)val);
					break;
				}

			}

		}
		return sortedMap;

	}
	
	public static String juntarDadosArray(List<String> array) {
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<array.size();i++) {
			if(i+1 < array.size())
				sb.append(array.get(i)+"|");
			else
				sb.append(array.get(i));
		}
		return sb.toString();
	}
	
}
