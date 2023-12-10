package com.vet.khw.application.input;
import com.vet.khw.application.output.BoardViewOutputPort;
import com.vet.khw.application.usecase.BoardViewUseCase;
import com.vet.khw.domain.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class BoardViewInputPort implements BoardViewUseCase {
    private final BoardViewOutputPort boardViewOutputPort;

    @Override
    public List<Board> getBoards(Predicate<Board> filter) {
        var routers = boardViewOutputPort.fetchBoards();
        return Board.retrieveBoard(routers, filter);
    }
}
