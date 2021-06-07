package com.diplom.controllers;

import com.diplom.models.Question;
import com.diplom.models.Test;
import com.diplom.models.User;
import com.diplom.models.UserAuthorities;
import com.diplom.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    QuestionRepo qr;

    @Autowired
    TestRepo tr;

    @Autowired
    TestResultRepo testResultRepo;

    @Autowired
    private UserAuthoritiesRepo userAuthoritiesRepo;

    @GetMapping("/main")
    public String main(){
        return "redirect:/admin/test/view";
    }

    @GetMapping("/user/view")
    public String viewUsers(Model model){
        ArrayList<User> users = (ArrayList<User>) userRepo.findAll();
        model.addAttribute("list",users);
        return "list";
    }
    @GetMapping("/deleteUserByUsername/{username}")
    public void deleteUserByUsername(@PathVariable String username){
        UserAuthorities ua = userAuthoritiesRepo.findUserAuthoritiesByAuthority("ROLE_USER");
        ua.deleteUserByUser(userRepo.findByUsername(username));
        userRepo.deleteByUsername(username);
    }
    @GetMapping("/delete/user/{id}")
    public String deleteUserById(@PathVariable long id,Model model){
        userRepo.deleteById(id);
        return "redirect:/admin/user/view";
    }
    
    //Работа с тестами
    @GetMapping("/test/view")
    public String viewAllTests(Model model) {
        List<Test> l = (List<Test>) tr.findAll();
        model.addAttribute("tests", l);
        return "tests-view";
    }
    @GetMapping("/test/add")
    public String testAdd() {
        return "test-add";
    }

    @PostMapping("/test/add")
    public String testAdd(@RequestParam String name) {

        if (tr.findAllByName(name).size() >= 1) {
            return "redirect:/admin/test/view/" + tr.findAllByName(name).get(0).getId();
        } else {
            Test t = new Test(name);
            tr.save(t);
        }
        return "redirect:/admin/test/view/";
    }

    @GetMapping("/test/delete/{id}")
    public String testDelete(@PathVariable long id){
        Test t = tr.findById(id);
        t.getQuestions().stream().forEach(q->qr.deleteById(q.getId()));

        tr.deleteById(id);
        return "redirect:/admin/test/view";
    }

    @GetMapping("/test/view/{id}")
    public String viewTest(@PathVariable long id, Model model) {

        Test t = tr.findById(id);
        model.addAttribute("questions", t.getQuestions());
        System.out.println(tr.findById(id).getQuestions().size());
        model.addAttribute("test", t);
        return "test-view";
    }
    //Результаты
    @GetMapping("/results/view/{id}")
    public String resultsView(Model model,@PathVariable long id){
        model.addAttribute("list",testResultRepo.findTestResultsByTest(tr.findById(id).name));

        return "results";
    }
    @GetMapping("/results/delete/{id}")
    public String resultsDeleteById(Model model,@PathVariable long id){
        testResultRepo.deleteById(id);

        return "redirect:/admin/test/view/";
    }
    @GetMapping("/results/view/user/{id}")
    public String resultsOfUser(@PathVariable long id, Model model){
        model.addAttribute("results",userRepo.findById(id).get().getResults());
        return "testResultsOfUser";
    }

    //Вопросы
    @GetMapping("/test/view/{id}/question/add")
    public String addQuestion(@PathVariable long id, Model model){
        model.addAttribute("id",id);

        model.addAttribute("question",new Question());
        return "question-add";
    }

    @PostMapping("/test/view/{id}/question/add")
    public String addQuestionPost(@PathVariable long id,
                                  @RequestParam String myQuestion,
                                  @RequestParam String var1,
                                  @RequestParam String var2,
                                  @RequestParam String var3,
                                  @RequestParam String var4,
                                  @ModelAttribute Question question){

        question.setQuestion(myQuestion);
        question.addVariant(var1);
        question.addVariant(var2);
        question.addVariant(var3);
        question.addVariant(var4);
        Test test = tr.findById(id);
        test.addQuestion(question);
        tr.save(test);
        question.setTest(test);
        qr.save(question);
        return"redirect:/admin/test/view/";
    }

    @GetMapping("/test/view/{id}/question/{idQ}/edit")
    public String questionEdit(@PathVariable long id,
                               @PathVariable long idQ,
                               Model model){
        Question q = qr.findById(idQ).get();

        model.addAttribute("idQ",q.getId());
        model.addAttribute("question",q.getQuestion());
        model.addAttribute("var1",q.getVariants().get(0));
        model.addAttribute("var2",q.getVariants().get(1));
        model.addAttribute("var3",q.getVariants().get(2));
        model.addAttribute("var4",q.getVariants().get(3));
        model.addAttribute("answer",q.getAnswer());
        return "question-edit";
    }

    @PostMapping("/test/view/{id}/question/{idQ}/edit")
    public String questionEditPost(@PathVariable long id,
                                   @PathVariable long idQ,
                                   @RequestParam String question,
                                   @RequestParam String var1,
                                   @RequestParam String var2,
                                   @RequestParam String var3,
                                   @RequestParam String var4,
                                   @RequestParam String answer,
                                   Model model){
        Question q = qr.findById(idQ).get();
        q.setQuestion(question);
        q.setAnswer(answer);
        q.deleteAllVariants();
        q.addVariant(var1);
        q.addVariant(var2);
        q.addVariant(var3);
        q.addVariant(var4);
        qr.save(q);
        return "redirect:/admin/test/view/"+id;
    }
    @GetMapping("question/{id}/delete")
    public String questionDelete(@PathVariable long id){
        qr.deleteById(id);
        return "redirect:/admin/test/view";
    }


}
