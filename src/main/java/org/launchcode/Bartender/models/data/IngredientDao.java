package org.launchcode.Bartender.models.data;

import org.launchcode.Bartender.models.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface IngredientDao extends CrudRepository<Ingredient, Integer> {

    boolean existsByName(String name);

}