package com.diplom.controllers;

import com.diplom.models.Question;
import com.diplom.models.Test;
import com.diplom.models.TestResult;
import com.diplom.repo.QuestionRepo;
import com.diplom.repo.TestRepo;
import com.diplom.repo.TestResultRepo;
import com.diplom.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepo userRepo;

    @Autowired
    QuestionRepo qr;

    @Autowired
    TestRepo testRepo;

    @Autowired
    TestResultRepo testResultRepo;
    String username;
    Test nowTest;
    int numQuestion = 0;
    List<Question> questions = new ArrayList<>();
    int countTrueAnswers = 0;
    int score = 0;

    @GetMapping("/main")
    public String main(){
        return "redirect:/user/tests";
    }

    @GetMapping("tests")
    public String viewTests(Model model){
        model.addAttribute("tests", testRepo.findAll());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        username = auth.getName();
        model.addAttribute("username",username);


        return "user/viewTests";
    }
    @PostMapping("tests/{id}")
    public String startTest(@PathVariable long id, Model model){
        questions = testRepo.findById(id).getQuestions();
        numQuestion = 0;
        countTrueAnswers = 0;
        nowTest = testRepo.findById(id);

        model.addAttribute("question",questions.get(0));

        return "redirect:/user/test/0";
    }


    @GetMapping("test/{idQ}")
    public String doTest(@PathVariable int idQ,Model model){

        model.addAttribute("question",questions.get(idQ));
        model.addAttribute("numOfQuestion",numQuestion);


        return "user/makeTest";
    }

    @PostMapping("test/{idQ}")
    public String doTestPost(@PathVariable int idQ, @RequestParam String var, Model model){
        System.out.println(var);
        if(questions.get(idQ).answer.equals(var)){
            score+=1;
        }
        if(idQ==(questions.size()-1)){
            model.addAttribute("score",score);
            model.addAttribute("all",questions.size());
            TestResult tr = new TestResult(score,nowTest.getQuestions().size(), nowTest.name, userRepo.findByUsername(username));


            testResultRepo.save(tr);
            score=0;
            nowTest = null;
            return "testEnd";
        }else{
            model.addAttribute("question",questions.get(idQ));

            return "redirect:/user/test/"+(idQ+1);
        }

    }

}
