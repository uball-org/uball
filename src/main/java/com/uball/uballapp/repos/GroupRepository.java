package com.uball.uballapp.repos;



import com.uball.uballapp.models.Group;
import com.uball.uballapp.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface GroupRepository extends CrudRepository <Group, Long> {

    List<Group> findAllByUsers(List<User> users);

    @Query(value = "select distinct g.id, g.name from `groups` g " +
            "join users_groups ug on g.id = ug.group_id " +
            "join users u on u.id = ug.user_id " +
            "join scores s on s.user_id = u.id " +
            "  where s.addedscoredate = ?1 " +
            "      and s.score != 0 ", nativeQuery = true)
    List<Group> findAllInGroup(LocalDate now);

//    @Query(value = "insert into users_groups (user_id, group_id) values (?1, ?2)", nativeQuery = true)
//    List<Group> addToUserGroupsTable();

}
