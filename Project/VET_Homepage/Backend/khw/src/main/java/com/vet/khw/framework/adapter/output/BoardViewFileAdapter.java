package com.vet.khw.framework.adapter.output;

import com.vet.khw.application.output.BoardViewOutputPort;
import com.vet.khw.domain.Board;
import com.vet.khw.domain.BoardType;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
public class BoardViewFileAdapter implements BoardViewOutputPort {
    private static List<Board> readFileAsString() {
        List<Board> boards = new ArrayList<>();
        try (Stream<String> stream = new BufferedReader(
                new InputStreamReader(
                        BoardViewFileAdapter.class.getClassLoader().
                                getResourceAsStream("boards.txt"))).lines()) {
                stream.forEach(line ->{
                String[] boardEntry = line.split(";");
                var type = boardEntry[0];
                var title = boardEntry[1];
                var textContent = boardEntry[2];
                Board board = Board.builder()
                .boardType(BoardType.valueOf(type)).title(title).textContent(textContent).build();
                boards.add(board);
            });
        } catch (Exception e){
            e.printStackTrace();
        }
        return boards;
    }
    @Override
    public List<Board> fetchBoards() {
        return readFileAsString();
    }
}