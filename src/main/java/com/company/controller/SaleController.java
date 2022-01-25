package com.company.controller;

import com.company.dto.*;
import com.company.manager.SaleManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SaleController {
    private final SaleManager manager;

    @PostMapping("/register")
    public SaleRegisterResponseDTO register(@RequestBody SaleRegisterRequestDTO requestDTO) {
        return manager.register(requestDTO);
    }

    @GetMapping("/getAll")
    public SaleGetAllResponseDTO getAll() {
        return manager.getAll();
    }

    @GetMapping("/getById")
    public SaleGetByIdResponseDTO getById(@RequestParam long id) {
        return manager.getById(id);
    }
}
