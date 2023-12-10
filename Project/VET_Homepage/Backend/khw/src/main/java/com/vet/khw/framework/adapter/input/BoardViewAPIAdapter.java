package com.vet.khw.framework.adapter.input;

import com.vet.khw.application.usecase.BoardViewUseCase;
import com.vet.khw.domain.Board;
import com.vet.khw.domain.BoardType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequiredArgsConstructor
public class BoardViewAPIAdapter {

    private final BoardViewUseCase boardViewUseCase;
    @GetMapping("/board/list/{type}")
    public List<Board> obtainRelatedBoards(@PathVariable("type") String type) {
        return boardViewUseCase.getBoards(
                Board.filterRouterByType(BoardType.valueOf(type)));
    }
}
