package com.uball.uballapp.repos;



import com.uball.uballapp.models.Group;
import com.uball.uballapp.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GroupRepository extends CrudRepository <Group, Long> {

    List<Group> findAllByUsers(List<User> users);

//    @Query(value = "insert into users_groups (user_id, group_id) values (?1, ?2)", nativeQuery = true)
//    List<Group> addToUserGroupsTable();

}
