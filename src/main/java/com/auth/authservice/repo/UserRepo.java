package com.auth.authservice.repo;

import com.auth.authservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.jws.soap.SOAPBinding;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    public User findByUserName(String username);
}
