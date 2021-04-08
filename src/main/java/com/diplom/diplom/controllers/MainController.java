package com.diplom.diplom.controllers;

import com.diplom.diplom.models.Question;
import com.diplom.diplom.models.Test;
import com.diplom.diplom.models.User;
import com.diplom.diplom.repo.QuestionRepo;
import com.diplom.diplom.repo.TestRepo;
import com.diplom.diplom.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Controller

public class MainController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    QuestionRepo qr;

    @Autowired
    TestRepo tr;

    @GetMapping("/start")
    public void backup() {
        List<User> users = (List<User>) userRepo.findAll();
        for (int i = 0; i < users.size(); i++) {
            try {

                File file = new File("C:\\file.xml");
                JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                jaxbMarshaller.marshal(users.get(i), file);
                jaxbMarshaller.marshal(users.get(i), System.out);

            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }
    }


    @GetMapping("/test/add")
    public String testAdd() {
        return "test-add";
    }


    @PostMapping("/test/add")
    public String testAdd(@RequestParam String name) {

        if (tr.findAllByName(name).size() >= 1) {
            return "redirect:/test/view/" + tr.findAllByName(name).get(0).getId();
        } else {
            Test t = new Test(name);
            tr.save(t);
            /*return "test";*/
        }

        return "redirect:/test/view/"+tr.findByName(name).getId()+"/add";
    }

    @GetMapping("/test/view")
    public String viewAllTests(Model model) {
        List<Test> l = (List<Test>) tr.findAll();
        model.addAttribute("tests", l);
        return "tests-view";
    }


    @GetMapping("/test/view/{id}")
    public String viewTest(@PathVariable long id, Model model) {

        Test t = tr.findById(id);
        model.addAttribute("questions", tr.findById(id).getQuestions());
        model.addAttribute("test", t);
        return "test-view";

    }

    @GetMapping("test/view/{id}/question/{questionId}")
    public String editQuestion(@PathVariable long id, @PathVariable long questionId, Model model) {
        Optional<Question> q = qr.findById(questionId);
        List l = q.get().variants;
        model.addAttribute("myquestion", q.get());
        model.addAttribute("variants", l);

        model.addAttribute("id", id);
        model.addAttribute("questionId", questionId);
        model.addAttribute("test", tr.findById(id));
        return "question-edit";
    }

    @PostMapping("test/view/{id}/question/{questionId}/edit")
    public String editQuestion(@PathVariable long id,
                               @PathVariable long questionId

    ) {

        return "redirect:/test/view/" + id;
    }

    @GetMapping("test/view/{id}/add")
    public String questionAdd(Model model, @PathVariable long id) {
        Test t = tr.findById(id);
        model.addAttribute("test", t);
        return "question-add";
    }


    @PostMapping("test/view/{id}/add")
    public String questionAdd(@PathVariable long id,
                              @RequestParam String question,
                              @RequestParam String var1,
                              @RequestParam String var2,
                              @RequestParam String var3,
                              @RequestParam String var4,
                              @RequestParam String answer) {
        Question q = new Question();
        System.out.println(question);
        System.out.println(var1);
        System.out.println(var2);
        System.out.println(var3);
        System.out.println(var4);
        System.out.println(answer);


        q.setQuestion(question);
        q.setAnswer(answer);
        q.addVariant(var1);
        q.addVariant(var2);
        q.addVariant(var3);
        q.addVariant(var4);

        Test t = tr.findById(id);
        t.addQuestion(q);
        q.setTest(t);
        tr.save(t);
        qr.save(q);


        return "redirect:/test/view/" + id+"";
    }
}
