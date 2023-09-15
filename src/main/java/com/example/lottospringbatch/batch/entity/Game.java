package com.example.lottospringbatch.batch.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int round;

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

    @Column(nullable = false)
    private int bonusNum;

    @Column
    private String yyyy;

    @Column
    private String drwNoDate;

    @Column
    private long firstAccumamnt;

    @Column
    private int firstPrzwnerCo;

    @Column
    private int secondAccumamnt;

    @Column
    private int secondPrzwnerCo;

    @Column
    private int thirdAccumamnt;

    @Column
    private int thirdPrzwnerCo;

    @Column
    private int fourthAccumamnt;

    @Column
    private int fourthPrzwnerCo;

    @Column
    private int fifthAccumamnt;

    @Column
    private int fifthPrzwnerCo;

    @Column
    private String etc;

}
