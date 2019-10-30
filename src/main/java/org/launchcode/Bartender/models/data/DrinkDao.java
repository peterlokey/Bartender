package org.launchcode.Bartender.models.data;

import org.launchcode.Bartender.models.Drink;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface DrinkDao extends CrudRepository<Drink, Integer> {

}