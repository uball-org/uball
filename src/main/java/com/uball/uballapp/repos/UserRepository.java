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
    @Query("select id, concat(first_name, ' ', last_name) as Name," +
            "gender as Gender, points as Points from users" +
            "where league_id in ( select id from leagues where " +
            "id = 1)")
    User findByLeague(String email);

    @Query("select name from machines\n" +
            "where id in (\n" +
            "    select machine_id\n" +
            "    from scores\n" +
            "    where user_id in (\n" +
            "        select id\n" +
            "        from users\n" +
            "        where id = 3\n" +
            "    )" +
            ")")
    List<Machine> searchByMachine(@Param("id") long id);

    @Query("from User u where u.id = ?1")
    User getUserById(long id);

    @Query("select u from #{#entityName} u where u.email = ?1")
    List<User> findByEmail(String email);

}
