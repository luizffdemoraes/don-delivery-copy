package com.doncorleone.dondelivery.resources.exceptions;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class UniqueValueValidator implements ConstraintValidator<UniqueValue, Object> {

    private String targetAttribute;
    private Class<?> klass;

    @PersistenceContext
    private EntityManager manager;

    /*
     * Esse método é chamado na instanciacao da classe que vai ser validada.
     * O metodo recebe a anotacao customizada e seta os valores definidos nela
     * @param params o objeto que do tipo da anotacao que contem os campos especificados na annotation
     */
    @Override
    public void initialize(UniqueValue params) { // alteracao do nome do argumento para algo que faz mais sentido
        targetAttribute = params.fieldName();
        klass = params.domainClass();
    }

    /*
     * @param value o objeto que representa o valor do atributo a ser buscado
     * @param constraintValidatorContext
     * @return True se a regra de valicacao der ok, False em caso contrario
     */

    @Override // CI: 3
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        // 1
        Query query = manager.createQuery("select 1 from "+klass.getName()+ " where "+ targetAttribute+"=:pValue");
        query.setParameter("pValue", value);
        List<?> list = query.getResultList();

        return list.isEmpty();

    }
}