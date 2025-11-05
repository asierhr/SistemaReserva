package com.asier.SistemaReservas.controller;

import com.asier.SistemaReservas.domain.dto.CommentDTO;
import com.asier.SistemaReservas.servicies.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping(path = "/hotels/{id}/comments")
    public CommentDTO createDTO(@PathVariable Long id, @RequestBody CommentDTO comment){
        return commentService.createComment(id, comment);
    }

    @GetMapping(path = "/hotels/{id}/comments")
    public List<CommentDTO> getAllComments(@PathVariable Long id){
        return commentService.getComments(id);
    }
}
