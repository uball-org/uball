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

    @Query("select name from machines " +
            "where id in (" +
            "    select machine_id " +
            "    from scores " +
            "    where user_id in (" +
            "        select id " +
            "        from users " +
            "        where id = 3 " +
            "    )" +
            ")")
    List<Machine> searchByMachine(@Param("id") long id);

    @Query("from User u where u.id = ?1")
    User getUserById(long id);

    @Query("select u from #{#entityName} u where u.email = ?1")
    List<User> findByEmail(String email);


    @Query("select u.id, concat(u.first_name, ' ', u.last_name) as Name," +
            "u.gender as Gender, s.score as Score " +
            "from users u " +
            "join scores s on u.id = s.user_id " +
            "order by s.score desc, " +
            "limit, 4")
    List<User> findTop4();

}
