package controller;

import model.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import service.CustomerService;

import java.io.File;
import java.io.IOException;

@Controller
public class HomeController {
    CustomerService customerService = new CustomerService();

    @GetMapping("/customer")
    public ModelAndView show() {
        ModelAndView modelAndView = new ModelAndView("show");
        modelAndView.addObject("customers", customerService.list);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreate() {
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("customer", new Customer());
        return modelAndView;
    }

    @PostMapping("/create")
    public String create(@RequestParam MultipartFile uppFile, @ModelAttribute Customer customer) {
        String nameImg = uppFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(uppFile.getBytes(), new File("D:\\Module 4\\3. Views & Thymeleaf\\CRUD-Thymelaf\\src\\main\\webapp\\WEB-INF\\image\\" + nameImg));
            String urlImg = "/img/" + nameImg;
            customer.setImg(urlImg);
        } catch (IOException e) {
            System.err.println("chưa uppload file");
        }
        customerService.save(customer);
        return "redirect:/customer";
    }

    @PostMapping("/uppFile")
    public String uppFile(@RequestParam MultipartFile uppFile) {
        String fileName = uppFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(uppFile.getBytes(), new File("D:\\Module 4\\3. Views & Thymeleaf\\CRUD-Thymelaf\\src\\main\\webapp\\WEB-INF\\image\\" + fileName));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "redirect:/customer";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditCustomer(@PathVariable int id){
        ModelAndView modelAndView = new ModelAndView("/editCustomer");
        modelAndView.addObject("customer", customerService.list.get(customerService.findIndexById(id)));
        return modelAndView;
    }

    @PostMapping ("/edit")
    public String editCustomer(@RequestParam MultipartFile uppFile, @ModelAttribute Customer customer){
        String nameImg = uppFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(uppFile.getBytes(), new File("D:\\Module 4\\3. Views & Thymeleaf\\CRUD-Thymelaf\\src\\main\\webapp\\WEB-INF\\image\\" + nameImg));
            String urlImg = "/img/" + nameImg;
            customer.setImg(urlImg);
        } catch (IOException e) {
            System.err.println("chưa uppload file");
        }
        customerService.edit(customerService.getCustomer(customer.getId()), customer);
        return "redirect:/customer";
    }

    @GetMapping("/delete")
    public ModelAndView delete(@RequestParam int id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/customer");
        customerService.delete(customerService.findIndexById(id));
        return modelAndView;
    }

}