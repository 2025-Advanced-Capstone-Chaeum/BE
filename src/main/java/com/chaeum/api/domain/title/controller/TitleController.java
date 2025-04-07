package com.chaeum.api.domain.title.controller;

import com.chaeum.api.domain.title.service.TitleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/title")
@RequiredArgsConstructor
@Tag(name = "Title", description = "칭호 관리")
public class TitleController {

    private final TitleService titleService;
}
