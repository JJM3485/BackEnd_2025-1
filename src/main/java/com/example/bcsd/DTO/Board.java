package com.example.bcsd.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Board {

    private Long id;
    private String name;

    public Board(String name) {
        this.name = name;
    }
}