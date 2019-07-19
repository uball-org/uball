package com.uball.uballapp.repos;



import com.uball.uballapp.models.League;
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
    @Query(value = "select l.*" +
            "from leagues l" +
            " join users u  on  u.league_id =  l.id" +
            " join scores s on u.id = s.user_id" +
            " order by s.score desc " +
            " limit 4 ", nativeQuery = true)
    List<League> findTop4ScoringLeagues();

/**ToDo: End here
 * working on League page data**/



}
