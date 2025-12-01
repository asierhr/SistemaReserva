package com.asier.SistemaReservas.comment.controller;

import com.asier.SistemaReservas.comment.domain.DTO.CommentDTO;
import com.asier.SistemaReservas.comment.service.CommentService;
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
