package com.bittnettraning.admin.web;

import com.bittnettraning.admin.entities.Trainer;
import com.bittnettraning.admin.services.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequestMapping("/trainers")
public class TrainerController {

    @Autowired
    private TrainerService trainerService;

    @GetMapping("/index")
    public String trainers(Model model, @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        List<Trainer> trainers = trainerService.findTrainerByName(keyword);
        model.addAttribute("listTrainers", trainers);
        model.addAttribute("keyword", keyword);
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
}
