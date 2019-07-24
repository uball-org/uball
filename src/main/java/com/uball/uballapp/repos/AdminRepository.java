package com.uball.uballapp.repos;



import com.uball.uballapp.models.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

//This repository may end up being unnecessary

@Repository
public interface AdminRepository extends CrudRepository <User, Long> {

    @Transactional
    @Modifying
    @Query(value = "truncate table users_groups", nativeQuery = true)
    void eliminateTableUG();


}
