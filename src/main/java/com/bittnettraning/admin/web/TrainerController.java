package com.bittnettraning.admin.web;

import com.bittnettraning.admin.entities.Trainer;
import com.bittnettraning.admin.entities.User;
import com.bittnettraning.admin.services.TrainerService;
import com.bittnettraning.admin.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

import static com.bittnettraning.admin.constants.Admin.Constants.KEYWORD;
import static com.bittnettraning.admin.constants.Admin.Constants.LIST_TRAINERS;


@Controller
@RequestMapping("/trainers")
public class TrainerController {

    @Autowired
    private TrainerService trainerService;
    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public String trainers(Model model, @RequestParam(name = KEYWORD, defaultValue = "") String keyword) {
        List<Trainer> trainers = trainerService.findTrainerByName(keyword);
        model.addAttribute(LIST_TRAINERS, trainers);
        model.addAttribute(KEYWORD, keyword);
        return "trainer-views/trainers";
    }
    @GetMapping("/delete")
    public String deleteTrainer(Long trainerId, String keyword){
        trainerService.removeTrainer(trainerId);
        return "redirect:/trainers/index?keyword=" + keyword;
    }
    @GetMapping("/formUpdate")
    public String updateTrainer(Model model, Long trainerId){
        Trainer trainer = trainerService.findTrainerById(trainerId);
        model.addAttribute("trainer", trainer);
        return "trainer-views/formUpdate";
    }
    @PostMapping("/update")
    public String update(Trainer trainer){
        trainerService.updateTrainer(trainer);
        return "redirect:/trainers/index";
    }
    @GetMapping("/formCreate")
    public String formTrainers(Model model){
        model.addAttribute("trainer", new Trainer());
        return "trainer-views/formCreate";
    }
    @PostMapping("/save")
    public String save(@Valid Trainer trainer, BindingResult bindingResult){
        User user = userService.findUserByEmail(trainer.getUser().getEmail());
        if (user!=null) bindingResult.rejectValue
                ("user.email",null, "There is already an account register with that email!");
        if (bindingResult.hasErrors()) return "trainer-views/formCreate";
        trainerService.createTrainer(trainer.getFirstName(), trainer.getLastName(), trainer.getSummary(),
                trainer.getUser().getEmail(), trainer.getUser().getPassword());
        return "redirect:/trainers/index";

    }
}
