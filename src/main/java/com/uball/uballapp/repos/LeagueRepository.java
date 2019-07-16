package com.uball.uballapp.repos;



import com.uball.uballapp.models.League;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LeagueRepository extends CrudRepository <League, Long> {

    List<League> findAllById(long id);


}
