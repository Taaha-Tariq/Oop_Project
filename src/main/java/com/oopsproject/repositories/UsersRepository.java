package com.oopsproject.repositories;

import com.oopsproject.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Defining the repository that will act as a store for our users table
@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
}
