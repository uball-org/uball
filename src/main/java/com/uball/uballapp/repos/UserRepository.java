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
public interface UserRepository extends CrudRepository <User, Long> {

    //------------------------------

    User findByUsername(String username);

//    @Query("from users where username = ?1")
//    User findByUsername(String username);
    //------------------------------


    //    Get a User by their ID
    User getUserById(long id);

    //    Find all who are in one league by league id
    @Query(value = "select id, concat(first_name, ' ', last_name) as Name, " +
            "       gender as Gender, points as Total_Points from users where league_id in (" +
            "    select id from leagues where id = ?1)", nativeQuery = true)
    User findByLeague(long id);

    //    Spring made method
    User findAllByLeague(League league);

    //  Top 4 of all time
    @Query(value = "select  u.*, m.name as Game, s.score as Score " +
            "from users u " +
            "join scores s on u.id = s.user_id join machines m on s.machine_id = m.id " +
            "order by s.score desc " +
            "limit 4", nativeQuery = true)
    List<User> findTop4();
  @Query(value = "select s.* " +
            "from users u " +
            "join scores s on u.id = s.user_id join machines m on s.machine_id = m.id " +
            "order by s.score desc " +
            "limit 4", nativeQuery = true)
    List<Score> findTopScores();

    /**
     * //    select u.*, m.name as Game,  s.score as Score, s.date as Date, l.name as League
     * //    from users u
     * //    join scores s on s.user_id = u.id
     * //    join leagues l on l.id = u.league_id
     * //    join scores s  on s.user_id =  u.id
     * //    join machines m on  m.id = s.machine_id
     * //    order by s.score desc limit 4;
     * */
    //  Top 4 of all time machines
    @Query(value = "select  m.* " +
            "from users u " +
            "join scores s on u.id = s.user_id join machines m on s.machine_id = m.id " +
            "order by s.score desc " +
            "limit 4", nativeQuery = true)
    List<Machine> findTop4Machines();


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


    //    Example of adding users to joining users_groups table
    //    @Query("insert into users_groups (user_id, group_id) VALUES (?1, ?2)")


}
