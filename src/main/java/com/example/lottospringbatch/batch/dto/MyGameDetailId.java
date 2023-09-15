package com.example.lottospringbatch.batch.dto;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class MyGameDetailId implements Serializable {
    private String email;
    private int round;
    private int gameNum;
}
