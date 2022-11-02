package com.gambit.GamBit.repository;

import com.gambit.GamBit.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByName(String name);
}
