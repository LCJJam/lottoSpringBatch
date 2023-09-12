package com.example.lottospringbatch.entity;

import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Table(name = "lotto_ball")
public class LottoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numberId;

    private int ballNum1;
    private int ballNum2;
    private int ballNum3;
    private int ballNum4;
    private int ballNum5;
    private int ballNum6;

    private int numberWeight;

    public LottoEntity(int ballNum1, int ballNum2, int ballNum3,
                       int ballNum4, int ballNum5, int ballNum6) {
        this.ballNum1 = ballNum1;
        this.ballNum2 = ballNum2;
        this.ballNum3 = ballNum3;
        this.ballNum4 = ballNum4;
        this.ballNum5 = ballNum5;
        this.ballNum6 = ballNum6;
    }

    public LottoEntity() {

    }
}
