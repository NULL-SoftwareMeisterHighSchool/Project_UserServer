package com.project.backend.user.repository;


import com.project.backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByUserIdx(int userIdx);

    User findUserByEmail(String email);

    int countByWithdrawed(String n);
}