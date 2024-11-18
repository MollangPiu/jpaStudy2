package kr.soft.campus.api;

import kr.soft.campus.domain.Member;
import kr.soft.campus.repository.MemberRepository;
import kr.soft.campus.util.ResponseData;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;


@RestController
@RequestMapping("/api/member")
public class MemberController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberRepository memberRepository;

    @PostMapping("/list")
    public ResponseEntity<?> list() {
        ResponseData data = new ResponseData();

        List<Member> members = memberRepository.findAllWithArea();
        List<MemberListRes> list = members.stream()
                .map(m -> new MemberListRes(m))
                .collect(toList());

        data.setData(list);
        return ResponseEntity.ok(data);
    }

    @PostMapping("/find")
    public ResponseEntity<?> find(@RequestBody String id) {
        ResponseData data = new ResponseData();
        logger.info("find: {}", id);
        Member m = memberRepository.findByUserId(id);
        String pw = m.getUserPw();

        logger.info("★★★★★ pw: {}", pw);

        return ResponseEntity.ok(data);
    }

    @GetMapping("/test")
    public void test(Test test) {
        logger.info("/test ★★★★★★★★★★★★★★");
        logger.info("test: {}", test.toString());
    }


    @Data
    static class Test{
        private String name;
        private String id;
    }

    @Data
    static class MemberListRes {
        private long idx;
        private String id;
        private String name;
        private long areaIdx;
        private String areaName;
        public MemberListRes(Member member) {
            this.idx = member.getIdx();
            this.id = member.getUserId();
            this.name = member.getUserName();
            this.areaIdx = member.getArea().getIdx();
            this.areaName = member.getArea().getAreaName();
        }
    }


}
