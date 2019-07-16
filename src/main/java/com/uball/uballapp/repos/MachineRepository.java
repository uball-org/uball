package com.uball.uballapp.repos;



import com.uball.uballapp.models.Machine;
import com.uball.uballapp.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//This repository may end up being unnecessary

@Repository
public interface MachineRepository extends CrudRepository <Machine, Long> {



}
