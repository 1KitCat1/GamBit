package com.gambit.GamBit.repository;

import com.gambit.GamBit.model.Game;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GameRepository extends CrudRepository<Game,Long> {

}
