package com.uball.uballapp.repos;



import com.uball.uballapp.models.League;
import com.uball.uballapp.models.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends CrudRepository <User, Long> {


    User findByUsername(String username);

    //    Get a User by their ID
    User getUserById(long id);

    //    Find all who are in one league by league id
    @Query(value = "select * from users where league_id in (" +
            "    select id from leagues where id = ?1)", nativeQuery = true)
    List<User> findByLeague(long id);

    //    Spring made method
    User findAllByLeague(League league);

    //  Top 4 of all time
    @Query(value = "select  u.*, m.name as Game, s.score as Score " +
            "from users u " +
            "join scores s on u.id = s.user_id join machines m on s.machine_id = m.id " +
            "order by s.score desc " +
            "limit 4", nativeQuery = true)
    List<User> findTop4();

    //  Top 4 of all time users
    @Query(value = "select distinct u.id, u.*" +
            " from users u" +
            " join scores s on u.id = s.user_id" +
            " order by u.points desc" +
            " limit 4", nativeQuery = true)
    List<User> findTop4ScoringUsers();

    //  Top 4 of users by league(id)
    @Query(value = "select u.*" +
            " from users u" +
            " join scores s on u.id = s.user_id" +
            " join machines m on s.machine_id = m.id" +
            " where league_id in (select id from leagues where id = ?1)" +
            " order by s.score desc " +
            " limit 4", nativeQuery = true)
    List<User> Top4ScoringUserByLeague(long id);

    //  update total points for user (ADDING)
    @Transactional
    @Modifying
    @Query(value = "update users set points = points + ?1 where id = ?2", nativeQuery = true)
    void updatePointsAdd(Long points, Long id);

    //  update total points for user (Subtracting)
    @Transactional
    @Modifying
    @Query(value = "update users set points = points - ?1 where id = ?2", nativeQuery = true)
    User updatePointsSub(long points, long id);

    //    set admin
    @Transactional
    @Modifying
    @Query(value = "update users set is_admin = true where id = ?1", nativeQuery = true)
    void isNewAdmin (long id);

    //    set NON admin
    @Transactional
    @Modifying
    @Query(value = "update users set is_admin = false where id = ?1", nativeQuery = true)
    void isNewNonAdmin (long id);


    @Query(value = "select DISTINCT s.user_id as id, u.username from users u join scores s on u.id = s.user_id where s.score = 0", nativeQuery = true)
    List<User> findAllByUserId();

    @Query(value = "select * from scores s " +
            "join users u on s.user_id = u.id " +
            "join machines m on s.machine_id = m.id " +
            "join users_groups ug on u.id = ug.user_id " +
            "join `groups` g on ug.group_id = g.id " +
            "where ug.group_id = ?1 " +
            "  and s.addedscoredate = ?2 " +
            "      and s.score != 0 order by s.score DESC", nativeQuery = true)
    List<User> findAllInGroup(long id, LocalDate now);

}
