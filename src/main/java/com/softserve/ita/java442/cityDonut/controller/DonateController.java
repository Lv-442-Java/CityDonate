package com.softserve.ita.java442.cityDonut.controller;

import com.google.gson.Gson;
import com.softserve.ita.java442.cityDonut.dto.donateDto.DonatesForProjectDto;
import com.softserve.ita.java442.cityDonut.model.DonatedUserProject;
import com.softserve.ita.java442.cityDonut.repository.DonatedUserProjectRepository;
import com.softserve.ita.java442.cityDonut.repository.UserRepository;
import com.softserve.ita.java442.cityDonut.service.DonateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/donate")

public class DonateController {

    @Autowired
    private DonateService donateService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DonatedUserProjectRepository donatedUserProjectRepository;

    @GetMapping("/project/{id}")
    public ResponseEntity<List<DonatesForProjectDto>> getProjectDonates(@PathVariable long id) {
        return new ResponseEntity<>(donateService.getProjectDonates(id), HttpStatus.OK);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<List<DonateDto>> getUserDonatedProject(@PathVariable long id) {
//        User user = userRepository.findById(id).orElseThrow();
//        List<DonateDto> donateDtos = new LinkedList<>();
//        List<Donate> donates = user.getDonates();
//        for (Donate donate: donates) {
//            donateDtos.add(new DonateMapper().convertToDto(donate));
//        }
////        List<Project> projects = new LinkedList<>();
////        for (Donate donate: donates) {
////            projects.add(donate.getProject());
////        }
//        System.out.println(donateDtos);
//        return new ResponseEntity<>(donateDtos, HttpStatus.OK);
//    }

    @GetMapping("/donated/{id}")
    public ResponseEntity<String> getDonatedUserProject(@PathVariable long id) throws IOException {

        List<DonatedUserProject> list = donatedUserProjectRepository.findDonatedUserProject(id);
        Gson gson = new Gson();
        String s = gson.toJson(list);
        return new  ResponseEntity<>(s, HttpStatus.OK);
    }
}
