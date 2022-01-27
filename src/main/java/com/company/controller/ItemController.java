package com.company.controller;

import com.company.dto.*;
import com.company.manager.ItemManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemManager manager;

    @GetMapping("/getAll")
    public ItemGetAllResponseDTO getAll() {
        return manager.getAll();
    }

    @GetMapping("/getById")
    public ItemGetByIdResponseDTO getById(@RequestParam long id) {
        return manager.getById(id);
    }

    @PostMapping("/save")
    public ItemSaveResponseDTO save(@RequestBody ItemSaveRequestDTO requestDTO) {
        return manager.save(requestDTO);
    }

    @PostMapping("/removeById/{id}")
    public void removeByIdFromPath(@PathVariable long id) {
        manager.removeById(id);
    }

    @PostMapping("/restoreById/{id}")
    public void restoreByIdFromPath(@PathVariable long id) {
        manager.restoreById(id);
    }

    @GetMapping("/getPopular")
    public ItemGetPopularResponseDTO getPopular() {
        return manager.getPopular();
    }
}
