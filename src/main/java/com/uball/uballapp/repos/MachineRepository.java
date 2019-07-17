package com.uball.uballapp.repos;



import com.uball.uballapp.models.Machine;
import com.uball.uballapp.models.Score;
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

//    List<Machine> findDistinctTopByScoresAnd_User_Id(long scores_user_id);


}
