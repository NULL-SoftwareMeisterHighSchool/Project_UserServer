package com.project.backend.user.repository;

import com.project.backend.user.entity.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository <User, Integer> {
    User findUserByEmail(String email);
    User getUserByUserIdx(int idx);
    int countByWithdrawedFalse();
    User findUserByUserIdx(int idx);

    List<User> findUserByWithdrawed(String withdrawedYn, PageRequest pageRequest);
}
