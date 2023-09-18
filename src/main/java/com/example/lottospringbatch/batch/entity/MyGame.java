package com.example.lottospringbatch.batch.entity;


import com.example.lottospringbatch.batch.dto.MyGameId;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "my_game")
public class MyGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "my_game_id")
    private long id;

    @Column
    private String email;

    @Column
    private int round;

    @Column
    private int firstGameGrade;
    @Column
    private int secondGameGrade;
    @Column
    private int thirdGameGrade;
    @Column
    private int fourthGameGrade;
    @Column
    private int fifthGameGrade;

    @Column
    private long totalWinnings;

    @Column
    private String drwNoDate;
}