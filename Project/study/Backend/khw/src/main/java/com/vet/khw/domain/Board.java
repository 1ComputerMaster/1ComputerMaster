package com.vet.khw.domain;

import com.fasterxml.jackson.annotation.JsonTypeId;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
@Data
@Builder
public class Board {
    @JsonTypeId
    private int id;
    private String title;
    private BoardType boardType;
    private String textContent;

    public static Predicate<Board> filterRouterByType(BoardType boardType){
        return boardType.equals(BoardType.USER)
                ? isUser() :
                isAdmin();
    }
    private static Predicate<Board> isUser(){
        return p -> p.getBoardType() == BoardType.USER;
    }

    private static Predicate<Board> isAdmin(){
        return p -> p.getBoardType() == BoardType.ADMIN;
    }

    public static List<Board> retrieveBoard(List<Board> boards, Predicate<Board> predicate){
        return boards.stream()
                .filter(predicate)
                .collect(Collectors.<Board>toList());
    }
}
