package com.example.lottospringbatch.batch.entity;

import com.example.lottospringbatch.batch.dto.MyGameDetailId;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "my_game_detail")
public class MyGameDetail {

    @EmbeddedId
    private MyGameDetailId id;

    @Column(nullable = false)
    private int ballNum1;

    @Column(nullable = false)
    private int ballNum2;

    @Column(nullable = false)
    private int ballNum3;

    @Column(nullable = false)
    private int ballNum4;

    @Column(nullable = false)
    private int ballNum5;

    @Column(nullable = false)
    private int ballNum6;

    @Column
    private int gameGrade;

    @Column
    private long gameWinnings;
}
