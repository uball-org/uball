package com.uball.uballapp.repos;



import com.uball.uballapp.models.League;
import com.uball.uballapp.models.Machine;
import com.uball.uballapp.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository <User, Long> {

    //------------------------------
    //          Two forms of "findByEmail"      \\

    User findByEmail(String email);

//    @Query("from users where email = ?1")
//    User findByEmail(String email);
    //------------------------------


    //    Get user by their id
//    @Query("from User u where u.id = ?1")
    User getUserById(long id);

    //    Find all who are in one league by league id
    @Query(value = "select id, concat(first_name, ' ', last_name) as Name, " +
            "       gender as Gender," +
            "       points as Total_Points " +
            "from users " +
            "where league_id in (" +
            "    select id" +
            "    from leagues" +
            "    where id = ?1" +
            "    )", nativeQuery = true)
    User findByLeague(long id);

    //    Spring made method
    User findAllByLeague(League league);

    //  get all the machines a user has played on (SUBQuery)
    @Query(value = "select name from machines " +
            "where id in (" +
            "    select machine_id " +
            "    from scores " +
            "    where user_id in (" +
            "        select id " +
            "        from users " +
            "        where id = ?1 " +
            "    )" +
            ")", nativeQuery = true)
    List<Machine> searchByMachine(long id);

    //  Top 4 of all time
    @Query(value = "select u.id, concat(u.first_name, ' ', u.last_name) as Name," +
            "u.gender as Gender, s.score as Score " +
            "from users u " +
            "join scores s on u.id = s.user_id " +
            "order by s.score desc " +
            "limit 4", nativeQuery = true)
    List<User> findTop4();

    //  4 random machines
    @Query(value = "SELECT * FROM machines ORDER BY RAND() LIMIT 4", nativeQuery = true)
    List<Machine> randoMachines();

    //  update total points for user (ADDING)
    @Query(value = "update users set points = points + ?2 where id = ?1", nativeQuery = true)
    User updatePointsAdd(long id, long points);

    //  update total points for user (Subtracting)
    @Query(value = "update users set points = points - ?2 where id = ?1", nativeQuery = true)
    User updatePointsSub(long id, long points);

    //    set admin
    @Query(value = "update users set is_admin = true where id = ?1", nativeQuery = true)
    User isNewAdmin (long id);

    //    set NON admin
    @Query(value = "update users set is_admin = false where id = ?1", nativeQuery = true)
    User isNewNonAdmin (long id);


//                      NOT DONE                 \\


    //    Find top scores for machines user has played on (JOIN)

                    //    {THIS DOES NOT WORK}    \\

    @Query(value = "select m.name as Machine, s.date as Date, s.score as Score" +
            " from users u" +
            " join scores s on u.id = s.user_id" +
            " join machines m on s.machine_id = m.id" +
            " where (select MAX(score))" +
            " group by Machine", nativeQuery = true)
    List<Machine> usersMachineScore(long id);


    //    Example of adding users to joining users_groups table
    //    @Query("insert into users_groups (user_id, group_id) VALUES (?1, ?2)")

}
