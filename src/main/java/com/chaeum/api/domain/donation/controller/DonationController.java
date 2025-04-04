package com.chaeum.api.domain.donation.controller;

import com.chaeum.api.domain.donation.service.DonationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/donation")
@RequiredArgsConstructor
@Tag(name = "Donation", description = "기부 관리")
public class DonationController {

    private final DonationService donationService;
}
