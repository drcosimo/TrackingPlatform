package it.polimi.progettoIngSoft.TrackingPlatform.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/project", produces="application/json" , consumes="application/json")
@CrossOrigin(origins="*")
public class ProjectController {
}
