package org.hassen.hopital.web;


import lombok.AllArgsConstructor;
import org.hassen.hopital.entities.Patient;
import org.hassen.hopital.repositories.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Date;

@Controller
@AllArgsConstructor
public class PatientController {
    PatientRepository patientRepository;
    @GetMapping(path="/user/index")
    public String index(Model model,
                           @RequestParam(name="page",defaultValue = "0") int page,
                           @RequestParam(name="size",defaultValue = "5") int size,
                           @RequestParam(name="keyword",defaultValue ="") String keyword){
        Page<Patient> pagePatient=patientRepository.findByNomContains(keyword, PageRequest.of(page,size));
        model.addAttribute("listePatient",pagePatient);
        model.addAttribute("pages",new int [pagePatient.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
        return "patients";
    }
    @GetMapping("/admin/delete")
    public String delete( @RequestParam(name="id") Long id,
                         String keyword,int page){
        patientRepository.deleteById(id);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    };
    @GetMapping("/admin/formPatients")
    public String formPatients(Model model){
        Patient patient=new Patient();
        model.addAttribute("patient",patient);
        return "formPatients";
    };
    @PostMapping( "/admin/save")
    public String save(Model model, @Valid Patient patient,
                       BindingResult bindingResult,
                       @RequestParam(defaultValue ="0") int page,
                       @RequestParam(defaultValue = "")String keyword){
        if(bindingResult.hasErrors()) return "formPatients";
        patient.setDateNaissance(new Date());
        patientRepository.save(patient);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    };
    @GetMapping("/admin/editPatient")
    public String editPatient(Model model,
                              Long id,
                              String keyword ,
                              int page){
        Patient patient= patientRepository.findById(id).orElse(null);
        if(patient== null) throw new RuntimeException("patient introuvable");
        model.addAttribute("keyword",keyword);
        model.addAttribute("page",page);
        model.addAttribute("patient",patient);
        return "editPatient";
    };
    @GetMapping("/")
    public String home(){
        return "home";
    };
}
