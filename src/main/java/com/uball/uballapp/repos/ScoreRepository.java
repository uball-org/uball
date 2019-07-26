package com.uball.uballapp.repos;



import com.uball.uballapp.models.Machine;
import com.uball.uballapp.models.Score;
import com.uball.uballapp.models.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface ScoreRepository extends CrudRepository <Score, Long> {

    List <Score> findDistinctTopByMachineOrderByScore(Machine machine);

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
    @Query(value = "select s.* " +
            "from  scores s " +
            "join users u on u.id = s.user_id " +
            "where league_id in (select id from leagues where id = ?1 ) " +
            "order by s.score desc " +
            " limit 4", nativeQuery = true)
    List<Score> Top4ScoresByLeague(long id);

 /** working on League page data**/

    List<Score> findAllByUser_Id(long user_id);


    List<Score> findDistinctByAddedscoredateAndScore( LocalDate addedscoredate, long score);

    Score findByUserAndMachine(User user, Machine machine);

    @Transactional
    @Modifying
    @Query(value = "UPDATE scores s SET s.score = ?1 WHERE machine_id = ?2 AND user_id = ?3 AND s.score = 0", nativeQuery = true)
    void updateScore( long score, Machine machineId, User userId);

    @Query(value = "select * from scores s " +
            "join users u on s.user_id = u.id " +
            "join machines m on s.machine_id = m.id " +
            "join users_groups ug on u.id = ug.user_id " +
            "join `groups` g on ug.group_id = g.id " +
            "where ug.group_id = ?1 " +
            "  and s.addedscoredate = ?2 " +
            "      and s.score != 0 order BY s.machine_id, s.score desc", nativeQuery = true)
    List<Score> findAllInGroup(long id, LocalDate now);

    @Query(value = "select DISTINCT m.name, s.* from machines m " +
            "join scores s on m.id = s.machine_id " +
            "join users u on s.user_id = u.id " +
            "where u.id = ?1  " +
            "order by s.score desc", nativeQuery = true)
    List<Score> findTopScoresOnMachines(long id);

}
