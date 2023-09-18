package com.example.lottospringbatch.batch.entity;

import com.example.lottospringbatch.batch.dto.MyGameDetailId;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "my_game_detail")
public class MyGameDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "my_game_detail_id")
    private long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private int round;

    @Column(nullable = false)
    private int gameNum;

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
