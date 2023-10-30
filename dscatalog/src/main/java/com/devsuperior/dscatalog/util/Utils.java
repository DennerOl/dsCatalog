package com.devsuperior.dscatalog.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.projection.ProductProjection;

public class Utils {
	/* classe auxiliar para order a consulta por ordem alfabetica 
	 * que busca os produtos com seu id e  com as repectivas categorias 
	 */
	
	public static List<Product> replace(List<ProductProjection> ordered, List<Product> unordered) {
		//copio todos produtos da lista desordenada 
		Map<Long, Product> map = new HashMap<>();
		for(Product obj : unordered) {
			map.put(obj.getId(), obj);
		}
		
		List<Product> result = new ArrayList<>();
		for (ProductProjection obj : ordered) {
			result.add(map.get(obj.getId()));
		}
		return result;
	}


}
