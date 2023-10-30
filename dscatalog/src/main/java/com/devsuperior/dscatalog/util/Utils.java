package com.devsuperior.dscatalog.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.devsuperior.dscatalog.projection.IdProjection;

public class Utils {
	/* classe auxiliar para order a consulta por ordem alfabetica 
	 * que busca os produtos com seu id e  com as repectivas categorias 
	 */
	
	public static <ID> List<? extends IdProjection<ID>> replace(List<? extends IdProjection<ID>> ordered, List<? extends IdProjection<ID>> unordered) {
		//copio todos produtos da lista desordenada 
		Map<ID, IdProjection<ID>>  map = new HashMap<>();
		for(IdProjection<ID> obj : unordered) {
			map.put(obj.getId(), obj);
		}
		
		List<IdProjection<ID>> result = new ArrayList<>();
		for (IdProjection<ID> obj : ordered) {
			result.add(map.get(obj.getId()));
		}
		return result;
	}


}
