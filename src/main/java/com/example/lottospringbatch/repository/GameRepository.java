package com.example.lottospringbatch.repository;

import com.example.lottospringbatch.batch.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {
    List<Game> findByRound(int id);
}
