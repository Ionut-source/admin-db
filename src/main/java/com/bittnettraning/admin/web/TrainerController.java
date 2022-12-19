package com.bittnettraning.admin.web;

import com.bittnettraning.admin.entities.Trainer;
import com.bittnettraning.admin.entities.User;
import com.bittnettraning.admin.services.TrainerService;
import com.bittnettraning.admin.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static com.bittnettraning.admin.constants.Constants.*;


@Controller
@RequestMapping("/trainers")
public class TrainerController {

    @Autowired
    private TrainerService trainerService;
    @Autowired
    private UserService userService;

    @GetMapping("/index")
    @PreAuthorize("hasAuthority('Admin')")
    public String trainers(Model model, @RequestParam(name = KEYWORD, defaultValue = "") String keyword) {
        List<Trainer> trainers = trainerService.findTrainerByName(keyword);
        model.addAttribute(LIST_TRAINERS, trainers);
        model.addAttribute(KEYWORD, keyword);
        return "trainer-views/trainers";
    }

    @GetMapping("/delete")
    @PreAuthorize("hasAuthority('Admin')")
    public String deleteTrainer(Long trainerId, String keyword) {
        trainerService.removeTrainer(trainerId);
        return "redirect:/trainers/index?keyword=" + keyword;
    }

    @GetMapping("/formUpdate")
    @PreAuthorize("hasAuthority('Trainer')")
    public String updateTrainer(Model model, Principal principal) {
        Trainer trainer = trainerService.findTrainerByEmail(principal.getName());
        model.addAttribute(TRAINER, trainer);
        return "trainer-views/formUpdate";
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('Trainer')")
    public String update(Trainer trainer) {
        trainerService.updateTrainer(trainer);
        return "redirect:/courses/index/trainer";
    }

    @GetMapping("/formCreate")
    @PreAuthorize("hasAuthority('Admin')")
    public String formTrainers(Model model) {
        model.addAttribute(TRAINER, new Trainer());
        return "trainer-views/formCreate";
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('Admin')")
    public String save(@Valid Trainer trainer, BindingResult bindingResult) {
        User user = userService.findUserByEmail(trainer.getUser().getEmail());
        if (user != null) bindingResult.rejectValue
                ("user.email", null, "There is already an account registered with this email!");
        if (bindingResult.hasErrors()) return "trainer-views/formCreate";
        trainerService.createTrainer(trainer.getFirstName(), trainer.getLastName(), trainer.getSummary(),
                trainer.getUser().getEmail(), trainer.getUser().getPassword());
        return "redirect:/trainers/index";

    }
}
