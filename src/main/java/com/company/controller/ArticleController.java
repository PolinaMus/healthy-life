package com.company.controller;

import com.company.dto.ArticleGetAllResponseDTO;
import com.company.dto.ArticleGetByIdResponseDTO;
import com.company.dto.ArticleSaveRequestDTO;
import com.company.dto.ArticleSaveResponseDTO;
import com.company.manager.ArticleManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleManager manager;

    @GetMapping("/getAll")
    public ArticleGetAllResponseDTO getAll() {
        return manager.getAll();
    }

    @GetMapping("/getById")
    public ArticleGetByIdResponseDTO getById(@RequestParam long id) {
        return manager.getById(id);
    }

    @PostMapping("/save")
    public ArticleSaveResponseDTO save(@RequestBody ArticleSaveRequestDTO requestDTO){
        return manager.save(requestDTO);
    }

    @PostMapping("/removeById/{id}")
    public void removeByIdFromPath(@PathVariable long id){
        manager.removeById(id);
    }

    @PostMapping("/restoreById/{id}")
    public void restoreByIdFromPath(@PathVariable long id){
        manager.restoreById(id);
    }
}
