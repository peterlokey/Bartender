package org.launchcode.Bartender.models.data;

import org.launchcode.Bartender.models.IngredientType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface IngredientTypeDao extends CrudRepository<IngredientType, Integer> {
}
