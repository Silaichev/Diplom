package com.diplom.controllers;

import com.diplom.models.File;
import com.diplom.models.Test;
import com.diplom.models.TestResult;
import com.diplom.models.questions.OpenQuestion;
import com.diplom.models.questions.TestQuestion;
import com.diplom.repo.FileRepo;
import com.diplom.repo.TestRepo;
import com.diplom.repo.TestResultRepo;
import com.diplom.repo.UserRepo;
import com.diplom.repo.question.OpenQuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepo userRepo;

    @Autowired
    OpenQuestionRepo qr;

    @Autowired
    TestRepo testRepo;

    @Autowired
    TestResultRepo testResultRepo;

    @Autowired
    FileRepo fileRepo;

    String username;
    Test nowTest;
    int numQuestion;
    List<TestQuestion> testQuestions = new ArrayList<>();
    List<OpenQuestion> openQuestions = new ArrayList<>();

    int questionsCount;
    int score;


    @GetMapping(value = "/image/{imageId}")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Long imageId) {
        File file = fileRepo.findById(imageId).get();

        byte[] imageBytes = file.getImage();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/main")
    public String main() {
        return "redirect:/user/tests";
    }

    @GetMapping("tests")
    public String viewTests(Model model) {
        model.addAttribute("tests", testRepo.findAll());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        username = auth.getName();
        model.addAttribute("username", username);
        return "user/viewTests";
    }

    @PostMapping("tests/{id}")
    public String startTest(@PathVariable long id, Model model) {
        Test testById = testRepo.findById(id);
        testQuestions = testById.getTestQuestions();
        openQuestions = testById.getOpenQuestions();
        questionsCount = 0;
        numQuestion = 0;
        score = 0;
        nowTest = testById;
        if(!openQuestions.isEmpty()){
            questionsCount+= openQuestions.size()-1;
        }
        if(!testQuestions.isEmpty()){
            questionsCount+= testQuestions.size()-1;
        }
        model.addAttribute("question", testQuestions.get(0));

        return "redirect:/user/test/0";
    }


    @GetMapping("test/{idQ}")
    public String doTest(@PathVariable int idQ, Model model) {
        if (idQ <= testQuestions.size() - 1) {
            TestQuestion question = testQuestions.get(idQ);
            List<String> variants = new ArrayList<>(question.getVariants());
            variants.add(question.getAnswer());
            Collections.shuffle(variants);
            model.addAttribute("variants", variants);
            model.addAttribute("question", question.getQuestion());
            model.addAttribute("numOfQuestion", numQuestion);
        } else {
            OpenQuestion question = openQuestions.get(idQ - testQuestions.size() - 1);
            model.addAttribute("question", question.getQuestion());
        }
        return "user/makeTest";
    }

    @PostMapping("test/{idQ}")
    public String doTestPost(@PathVariable int idQ, @RequestParam String var, Model model) {


        if (testQuestions.get(idQ).answer.equals(var)) {
            score += 1;
        }

        if (idQ == questionsCount) {
            model.addAttribute("score", score);
            model.addAttribute("all", testQuestions.size());
            TestResult tr = new TestResult(score, nowTest.getTestQuestions().size(),
                    nowTest.name, userRepo.findByUsername(username));
            testResultRepo.save(tr);
            score = 0;
            nowTest = null;
            return "testEnd";
        } else {
            model.addAttribute("question", testQuestions.get(idQ));

            return "redirect:/user/test/" + (idQ + 1);
        }
    }

}
