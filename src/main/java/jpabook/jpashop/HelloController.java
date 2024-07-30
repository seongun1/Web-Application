package jpabook.jpashop;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller // 이걸 적어줘야 동적 페이지 기능 가능.
public class HelloController {
    @GetMapping("hello")
    public String hello (Model model){
        model.addAttribute("data","hello!!!");
        return "hello";
    }
}
