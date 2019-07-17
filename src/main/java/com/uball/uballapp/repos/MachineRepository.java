package com.uball.uballapp.repos;



import com.uball.uballapp.models.Machine;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

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

}
