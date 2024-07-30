package jpabook.jpashop.api;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable_;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;


@RestController // @controller + @responsebody
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @GetMapping("/api/v1/members")
    public List<Member>membersV1(){
        return memberService.findMembers();
    }
    @GetMapping("/api/v2/members")
    public Result memberV2(){
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());

        return new Result(collect.size(),collect);
    }
    @Data
    @AllArgsConstructor
    static class Result<T>{
        private int count;
        private T data;
    }
    @Data
    @AllArgsConstructor
    static class MemberDto{
        private String name;
    }
    @PostMapping("/api/v1/members") // api를 만들 때에는 파라미터로 받지 말것!
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }
    @PostMapping("/api/v2/members") // 실무에서 사용하는 api 하나의 dto 객체를 만든 뒤 거기서 받아서 api postmapping
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request){
        Member member  = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);

        return new CreateMemberResponse(id);
    }
    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2 (@PathVariable("id") Long id,
                                                @RequestBody @Valid UpdateMemberRequest request){
        memberService.update(id,request.getName());
        Member findMember = memberService.findOne(id); // 커맨드와 쿼리를 분리하여,
        // 멤버를 바로 반환해주는 것이 아닌, 여기에서 쿼리를 찾는다.

        return new UpdateMemberResponse(findMember.getId(),findMember.getName());
    }

    //api 스펙 만들기
    @Data
    static class UpdateMemberRequest{
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse{
        private Long id;
        private String name;
    }
    @Data
    static class CreateMemberRequest{
        private String name;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;
        public CreateMemberResponse(Long id) {

            this.id = id;
        }
}
}
