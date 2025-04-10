package com.chaeum.api.domain.member.controller;

import com.chaeum.api.domain.member.dto.request.MemberUpdateRequest;
import com.chaeum.api.domain.member.dto.response.MemberMyPageResponse;
import com.chaeum.api.domain.member.service.MemberService;
import com.chaeum.api.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
@Tag(name = "Member", description = "회원 관리")
public class MemberController {

    private final MemberService memberService;

    @Operation(
            summary = "마이페이지",
            description = """
                    [모든 Role 가능]<br>
                    기부자: 회원 정보와 누적 기부 금액, 기부 내역을 보여줍니다.<br>
                    수혜자: 회원 정보와 진행 중인 펀딩 및 완료된 펀딩을 보여줍니다.
                    """
    )
    @PreAuthorize("hasRole('DONOR')")
    @GetMapping("")
    public ApiResponse<MemberMyPageResponse> getMemberMyPage() {
        return ApiResponse.success(memberService.getMemberMyPage());
    }

    @Operation(summary = "내 포인트 조회", description = "[모든 Role 가능]")
    @PreAuthorize("hasRole('DONOR')")
    @GetMapping("/point")
    public ApiResponse<BigDecimal> getMyPoint() {
        return ApiResponse.success(memberService.getMyPoint());
    }

    @Operation(
            summary = "회원 수정",
            description = "[모든 Role 가능] 회원의 이름과 프로필 이미지를 수정할 수 있습니다."
    )
    @PreAuthorize("hasRole('DONOR')")
    @PatchMapping("")
    public ApiResponse<Long> update(
            @RequestParam(name = "memberId") Long memberId,
            @Valid @RequestBody MemberUpdateRequest memberUpdateRequest
    ) {
        Long id = memberService.update(memberId, memberUpdateRequest);
        return ApiResponse.success(id);
    }

    @Operation(
            summary = "회원 삭제",
            description = "[모든 Role 가능]"
    )
    @PreAuthorize("hasRole('DONOR')")
    @DeleteMapping("/{memberId}")
    public ApiResponse<Long> delete(
            @PathVariable(name = "memberId") Long memberId
    ) {
        Long id = memberService.delete(memberId);
        return ApiResponse.success(id);
    }
}
