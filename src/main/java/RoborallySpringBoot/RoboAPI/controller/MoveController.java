package RoborallySpringBoot.RoboAPI.controller;

import RoborallySpringBoot.RoboAPI.repository.MoveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/moves")
public class MoveController {

    @Autowired
    private MoveRepository moveRepository;
}
