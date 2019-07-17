package com.uball.uballapp.repos;



import com.uball.uballapp.models.Machine;
import com.uball.uballapp.models.Score;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreRepository extends CrudRepository <Score, Long> {

    List <Score> findDistinctTopByMachineAndUser_Id(Iterable<Machine> machine, long user_id);

    List<Score> findAllByUser_Id(long user_id);

}
