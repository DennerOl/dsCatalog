package com.devsuperior.dscatalog.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.projection.ProductProjection;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	/*
	 * consulta que realiza o caso de uso O usuário informa: ⦁ trecho do nome do
	 * produto (opcional) ⦁ categorias de produto desejadas (opcional) ⦁ número da
	 * página desejada ⦁ quantidade de itens por página
	 * 
	 * o sistemas irá retornar O sistema informa uma listagem paginada dos produtos
	 * com suas respectivas categorias, conforme os critérios de consulta, ordenados
	 * por nome
	 */
	@Query(nativeQuery = true, value = """
			SELECT DISTINCT tb_product.id,
				tb_product.name
			FROM
				tb_product
			INNER
				JOIN tb_product_category
				on tb_product.id=
				tb_product_category.product_id
			WHERE (:categoryIds IS NULL OR tb_product_category.category_id IN :categoryIds)

			AND LOWER (tb_product.name) LIKE LOWER( CONCAT('%',:name,'%'))
			ORDER BY tb_product.name
""", 
					countQuery = """
		SELECT COUNT(*) FROM (					
			SELECT DISTINCT tb_product.id,
				tb_product.name
			FROM
				tb_product
			INNER
				JOIN tb_product_category
				on tb_product.id=
				tb_product_category.product_id
			WHERE (:categoryIds IS NULL OR tb_product_category.category_id IN :categoryIds)
			AND LOWER (tb_product.name) LIKE LOWER( CONCAT('%',:name,'%'))
			ORDER BY tb_product.name
)	AS tb_result		
							""")
	Page<ProductProjection> searchProducts(List<Long> categoryIds, String name, Pageable pageable);

}
