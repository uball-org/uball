package com.uball.uballapp.repos;



import com.uball.uballapp.models.League;
import com.uball.uballapp.models.Machine;
import com.uball.uballapp.models.Score;
import com.uball.uballapp.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LeagueRepository extends CrudRepository <League, Long> {

    List<League> findAllById(long id);


}
