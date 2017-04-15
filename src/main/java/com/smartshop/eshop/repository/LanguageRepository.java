package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.Language;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Language entity.
 */
@SuppressWarnings("unused")
public interface LanguageRepository extends JpaRepository<Language,Long> {

    @Query("select distinct language from Language language left join fetch language.stores")
    List<Language> findAllWithEagerRelationships();

    @Query("select language from Language language left join fetch language.stores where language.id =:id")
    Language findOneWithEagerRelationships(@Param("id") Long id);

}
