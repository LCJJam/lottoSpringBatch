package com.example.lottospringbatch.repository;

import com.example.lottospringbatch.batch.entity.MyGameDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyGameDetailRepository extends JpaRepository<MyGameDetail, Long> {
}
