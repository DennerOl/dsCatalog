package com.devsuperior.dscatalog.projection;

public interface IdProjection<E> {
/* essa interface me ajuda a tornar o metodo
 * da copia da lista ordenada generico
 * implemento a classe na entidade Product
 * e extendo na prodProjection
 */
	E getId();
}
