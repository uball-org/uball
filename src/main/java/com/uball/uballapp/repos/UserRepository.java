package com.uball.uballapp.repos;



import com.uball.uballapp.models.Machine;
import com.uball.uballapp.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository <User, Long> {

    //------------------------------

    //    Two forms of "findByEmail"

    User findByEmail(String email);

//    @Query("select u from #{#entityName} u where u.email = ?1")
//    User findByEmail(String email);
    //------------------------------

//    Get user by their id

    @Query("from User u where u.id = ?1")
    User getUserById(long id);

//    Find all who are in one league by league id

    @Query("select id, concat(first_name, ' ', last_name) as Name, " +
            "       gender as Gender," +
            "       points as Total_Points " +
            "from users " +
            "where league_id in (" +
            "    select id" +
            "    from leagues" +
            "    where id = ?1" +
            "    )")
    User findByLeague(long id);

//  get the machines a user has played on (SUBQuery)

    @Query("select name from machines " +
            "where id in (" +
            "    select machine_id " +
            "    from scores " +
            "    where user_id in (" +
            "        select id " +
            "        from users " +
            "        where id = ?1 " +
            "    )" +
            ")")
    List<Machine> searchByMachine(@Param("id") long id);


//  Top 4 of all time

    @Query("select u.id, concat(u.first_name, ' ', u.last_name) as Name," +
            "u.gender as Gender, s.score as Score " +
            "from users u " +
            "join scores s on u.id = s.user_id " +
            "order by s.score desc, " +
            "limit, 4")
    List<User> findTop4();

//    Find top scores for machines user has played on (JOIN)
    @Query("")
    List<Machine> usersMachineScore(@Param("id") long id);


//    Example of adding users to joining users_groups table
//    @Query("insert into users_groups (user_id, group_id) VALUES (?1, ?2)")

}
