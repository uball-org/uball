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

    /**ToDo: Start here
     * working on League page data**/

    /**  Top 4 all times #*/
    //  Top 4 of all time leagues
    @Query(value = "select l.* from machines m" +
            "                  join scores s  on s.machine_id = m.id" +
            "                  join users u  on  s.user_id =  u.id" +
            "                  join leagues l  on l.id = u.league_id" +
            "where s.machine_id = m.id order by s.score desc limit 4", nativeQuery = true)
    List<League> findTop4ScoringLeagues();

    /**ToDo: End here
     * working on League page data**/


}
