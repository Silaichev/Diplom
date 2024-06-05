package com.diplom.controllers;

import com.diplom.models.*;
import com.diplom.models.questions.OpenQuestion;
import com.diplom.models.questions.TestQuestion;
import com.diplom.repo.*;
import com.diplom.repo.question.OpenQuestionRepo;
import com.diplom.repo.question.TestQuestionRepo;
import com.diplom.sevices.FileService;
import com.diplom.util.Saver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    OpenQuestionRepo openQuestionRepo;

    @Autowired
    TestQuestionRepo testQuestionRepo;

    @Autowired
    TestRepo tr;

    @Autowired
    TestResultRepo testResultRepo;

    @Autowired
    private UserAuthoritiesRepo userAuthoritiesRepo;

    @Autowired
    private FileService fileService;

    private Saver saver;

    private static final String IMAGE = "/image/";

    @Autowired
    public AdminController(FileService fileService) {
        this.fileService = fileService;
        this.saver = new Saver(fileService);
    }

    @GetMapping(value = "/show", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public String show(Model model) {
        model.addAttribute("file", fileService.getAllFiles().get(0));
        model.addAttribute("bytes", fileService.getAllFiles().get(0).getImageAsString());
        return "test/show";
    }

    @GetMapping(value = "/image/{imageId}")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Long imageId) {
        File file = fileService.getAllFiles().get(0);

        byte[] imageBytes = file.getImage();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/save")
    public String postSaveFile() {
        return "test/save";
    }

    @PostMapping("/save")
    public String saveFile(@RequestParam("image") MultipartFile image) throws IOException {

        fileService.save(new com.diplom.models.File(image.getOriginalFilename(), image));
        return "redirect:/admin/save";
    }

    @GetMapping("/main")
    public String main() {
        return "redirect:/admin/test/view";
    }

    @GetMapping("/user/view")
    public String viewUsers(Model model) {
        ArrayList<User> users = (ArrayList<User>) userRepo.findAll();
        model.addAttribute("list", users);
        return "list";
    }

    @GetMapping("/deleteUserByUsername/{username}")
    public void deleteUserByUsername(@PathVariable String username) {
        UserAuthorities ua = userAuthoritiesRepo.findUserAuthoritiesByAuthority("ROLE_USER");
        ua.deleteUserByUser(userRepo.findByUsername(username));
        userRepo.deleteByUsername(username);
    }

    @GetMapping("/delete/user/{id}")
    public String deleteUserById(@PathVariable long id, Model model) {
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
    public String testDelete(@PathVariable long id) {
        Test t = tr.findById(id);
        t.getTestQuestions().stream().forEach(q -> testQuestionRepo.deleteById(q.getId()));

        tr.deleteById(id);
        return "redirect:/admin/test/view";
    }

    @GetMapping("/test/view/{id}")
    public String viewTest(@PathVariable long id, Model model) {

        Test t = tr.findById(id);
        model.addAttribute("questions", t.getTestQuestions());
        System.out.println(tr.findById(id).getTestQuestions().size());
        model.addAttribute("test", t);
        return "test-view";
    }

    //Результаты
    @GetMapping("/results/view/{id}")
    public String resultsView(Model model, @PathVariable long id) {
        model.addAttribute("list", testResultRepo.findTestResultsByTest(tr.findById(id).name));

        return "results";
    }

    @GetMapping("/results/delete/{id}")
    public String resultsDeleteById(Model model, @PathVariable long id) {
        testResultRepo.deleteById(id);

        return "redirect:/admin/test/view/";
    }

    @GetMapping("/results/view/user/{id}")
    public String resultsOfUser(@PathVariable long id, Model model) {
        model.addAttribute("results", userRepo.findById(id).get().getResults());
        return "testResultsOfUser";
    }

    //Вопросы
    @GetMapping("/test/view/{id}/question/add")
    public String addQuestion(@PathVariable long id, Model model) {
        model.addAttribute("id", id);

        return "question-add";
    }

    //Da
    @PostMapping("/test/view/{id}/question/add")
    public String addQuestionPost(@PathVariable long id,
                                  @RequestParam(required = false) String textQuestion,
                                  @RequestParam(required = false) MultipartFile multipartQuestion,
                                  @RequestParam(required = false) List<String> textVariants,
                                  @RequestParam(required = false) List<MultipartFile> multipartVariants,
                                  @RequestParam(required = false) String textAnswer,
                                  @RequestParam(required = false) MultipartFile multipartAnswer,
                                  @RequestParam String type) {
        Test test = tr.findById(id);

        String question;
        if (Objects.isNull(textQuestion) || textQuestion.isEmpty()) {
            File resultFile = fileService.save(new com.diplom.models.File(multipartQuestion.getOriginalFilename(), multipartQuestion));
            question = IMAGE + resultFile.getId();
        } else {
            question = textQuestion;
        }

        String answer;
        if ((Objects.isNull(textAnswer) || textAnswer.isEmpty()) && Objects.nonNull(multipartAnswer)) {
            File resultFile = fileService.save(new com.diplom.models.File(multipartAnswer.getOriginalFilename(), multipartAnswer));
            answer = IMAGE + resultFile.getId();
        } else {
            answer = textAnswer;
        }

        List<String> finVariants = new ArrayList<>();
        if (Objects.nonNull(textVariants) && !textVariants.isEmpty()) {
            finVariants.addAll(textVariants);
        }
        for (MultipartFile file : multipartVariants) {
            File resultFile = fileService.save(new com.diplom.models.File(file.getOriginalFilename(), file));
            finVariants.add(IMAGE + resultFile.getId());
        }

        if (Objects.equals(type, "test")) {
            TestQuestion testQuestion = new TestQuestion();
            testQuestion.setQuestion(question);
            testQuestion.setVariants(finVariants);
            testQuestion.setAnswer(answer);
            test.addTestQuestion(testQuestion);
            tr.save(test);
            testQuestion.setTest(test);
            testQuestionRepo.save(testQuestion);
        } else if (Objects.equals(type, "open")) {
            OpenQuestion openQuestion = new OpenQuestion();
            openQuestion.setTest(test);
            openQuestion.setQuestion(question);
            test.addOpenQuestion(openQuestion);
            tr.save(test);
            openQuestionRepo.save(openQuestion);
        }


        return "redirect:/admin/test/view/";
    }

    @GetMapping("/test/view/{id}/question/{idQ}/edit")
    public String questionEdit(@PathVariable long id,
                               @PathVariable long idQ,
                               Model model) {
        TestQuestion q = testQuestionRepo.findById(idQ).get();

        model.addAttribute("idQ", q.getId());
        model.addAttribute("question", q.getQuestion());
        model.addAttribute("var1", q.getVariants().get(0));
        model.addAttribute("var2", q.getVariants().get(1));
        model.addAttribute("var3", q.getVariants().get(2));
        model.addAttribute("var4", q.getVariants().get(3));
        model.addAttribute("answer", q.getAnswer());
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
                                   Model model) {
        TestQuestion q = testQuestionRepo.findById(idQ).get();
        q.setQuestion(question);
        q.setAnswer(answer);
        q.setVariants(Arrays.asList(var1, var2, var3, var4));
        testQuestionRepo.save(q);
        return "redirect:/admin/test/view/" + id;
    }

    @GetMapping("question/{id}/delete")
    public String questionDelete(@PathVariable long id) {
        testQuestionRepo.deleteById(id);
        return "redirect:/admin/test/view";
    }


}
