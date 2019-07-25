package com.rybickim.javaquiz.data;

import com.rybickim.javaquiz.domain.BaseClass;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public interface GenericCrudRepository<T extends BaseClass, IdType extends Serializable>
        extends CrudRepository<T, IdType> {
}
