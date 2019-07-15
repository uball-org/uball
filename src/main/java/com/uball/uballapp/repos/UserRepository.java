package com.uball.uballapp.repos;



import com.uball.uballapp.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository <User, Long> {
    User findByEmail(String email);


}
