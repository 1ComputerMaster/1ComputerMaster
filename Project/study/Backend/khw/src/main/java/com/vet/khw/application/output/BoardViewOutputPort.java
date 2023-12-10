package com.vet.khw.application.output;
import com.vet.khw.domain.Board;
import org.springframework.stereotype.Component;

import java.util.List;

public interface BoardViewOutputPort {

    List<Board> fetchBoards();
}