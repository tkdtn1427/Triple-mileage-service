package trip.milliage.entity.member;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/point")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity findMemberPoint(@RequestParam(value = "user-id") UUID userId){
        return memberService.findUserPoint(userId);
    }
}
