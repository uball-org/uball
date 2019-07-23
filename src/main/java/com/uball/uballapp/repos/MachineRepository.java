package com.uball.uballapp.repos;



import com.uball.uballapp.models.Machine;
import com.uball.uballapp.models.Score;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.crypto.Mac;
import java.time.LocalDate;
import java.util.List;


@Repository
public interface MachineRepository extends CrudRepository <Machine, Long> {

    List<Machine> findMachinesById(long id);

    List<Machine> findDistinctTopBy();

    //  4 random machines
    @Query(value = "SELECT * FROM machines ORDER BY RAND() LIMIT 4", nativeQuery = true)
    List<Machine> randoMachines();

    //  get all the machines a user has played on
    @Query(value = "select name from machines where id in ( select machine_id from scores " +
            "where user_id in ( select id from users where id = ?1 ))", nativeQuery = true)
    List<Machine> searchByMachine(long id);

    @Query(value = "select u.id, concat(u.first_name, ' ', u.last_name) as Name," +
            "u.gender as Gender, m.name as Game, s.score as Score " +
            "from users u " +
            "join scores s on u.id = s.user_id join machines m on s.machine_id = m.id " +
            "order by s.score desc " +
            "limit 4", nativeQuery = true)
    List<Machine> findTop4();


/**ToDo: Start here
 * working on League page data**/

    /**  Top 4 all times #*/
//  Top 4 of all time machines
    @Query(value = "select m.*" +
            " from machines m" +
            " join scores s  on s.machine_id = m.id" +
            " order by s.score desc " +
            " limit 4 ", nativeQuery = true)
    List<Machine> findTop4ScoringMachines();
    /**Top All time scorers by league(id)*/
// Top 4 of machines by league(id)

    @Query(value = "select * " +
            "from machines m " +
            "join scores s on s.machine_id = m.id " +
            "join users u  on u.id = s.user_id " +
            "where u.league_id = ?1 " +
            "order by s.score desc " +
            "limit 4;", nativeQuery = true)
    List<Machine> Top4ScoringMachinesByLeague(long id);

/**ToDo: End here
 * working on League page data**/

@Query(value = "select DISTINCT m.id from machines m join scores s on m.id = s.machine_id where s.score = ?1", nativeQuery = true)
List<Machine> findAllByMachine_Id(long score);

    @Query(value = "select distinct m.name, m.id from machines m " +
            "join scores s on s.machine_id = m.id " +
            "join users u on s.user_id = u.id " +
            "join users_groups ug on u.id = ug.user_id " +
            "join `groups` g on ug.group_id = g.id " +
            "where ug.group_id = ?1 " +
            "  and s.addedscoredate = ?2 " +
            "      and s.score != 0 ", nativeQuery = true)
    List<Machine> findAllInGroup(long id, LocalDate now);

}
