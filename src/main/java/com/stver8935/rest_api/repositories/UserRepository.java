package com.stver8935.rest_api.repositories;

import com.stver8935.rest_api.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 유저 관련 Repository Class
 *
 * @author : hangihyeong
 * @packageName : com.stver8935.rest_api.repositories
 * @fileName : UserRepository
 * @since : 24. 8. 14.
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserName(String userName);


    @Transactional
    @Modifying
    @Query("UPDATE User A SET A.loginFailCount = A.loginFailCount + 1 WHERE A.userName = :userName")
    int increaseLoginFailCount(@Param("userName") String userName);

    @Query("SELECT loginFailCount FROM User A WHERE A.userName = :userName")
    int findLoginFailCountByUserName(@Param("userName") String userName);

    @Query(" UPDATE User A SET A.refreshToken = :refreshToken")
    void updateRefreshToken(@Param("refreshToken") String refreshToken);



}
