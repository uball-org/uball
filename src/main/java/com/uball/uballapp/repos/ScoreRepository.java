package com.uball.uballapp.repos;



import com.uball.uballapp.models.Machine;
import com.uball.uballapp.models.Score;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface ScoreRepository extends CrudRepository <Score, Long> {

    List <Score> findDistinctTopByMachineOrderByScore(Machine machine);
    /**ToDo: Start here
     * working on League page data**/
    /**  Top 4 all times #*/

//  Top 4 of all time scores
    @Query(value = "select s.* " +
            " from users u" +
            " join scores s on u.id = s.user_id" +
            " order by s.score desc" +
            " limit 4", nativeQuery = true)
    List<Score> findTop4Scores();

    /**Top All time scorers by league(id)*/
// Top 4 of scores by league(id)
    @Query(value = "select s.*" +
            "from  scores s" +
            " join users u on u.id = s.user_id" +
            "  join machines m on s.machine_id = m.id" +
            " where league_id in (select id from leagues where id = ?1 )" +
            "order by s.score desc" +
            " limit 4", nativeQuery = true)
    List<Score> Top4ScoresByLeague(long id);

/**ToDo: End here
 * working on League page data**/


    List<Score> findAllByUser_Id(long user_id);


    List<Score> findDistinctByAddedscoredateAndScore( LocalDate addedscoredate, long score);

    @Query(value = "update scores SET score = ?1 WHERE machine_id = ?2 AND user_id = ?3 AND score = 0", nativeQuery = true)
    Score updateScore( Long score, Long machinIed, Long userId);

}
