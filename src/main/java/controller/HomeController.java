package controller;

import model.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import service.CustomerService;


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
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView create(@ModelAttribute Customer customer) {
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("redirect:/customer");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditCustomer(@PathVariable int id){
        ModelAndView modelAndView = new ModelAndView("/editCustomer");
        modelAndView.addObject("customer", customerService.list.get(customerService.findIndexById(id)));
        return modelAndView;
    }

    @PostMapping ("/edit")
    public ModelAndView editCustomer(@ModelAttribute Customer customer){
        int index = (customerService.findIndexById(customer.getId())) ;
        customerService.edit(index, customer);
        ModelAndView modelAndView = new ModelAndView("redirect:/customer");
        return modelAndView;
    }

    @GetMapping("/delete")
    public ModelAndView delete(@RequestParam int id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/customer");
        customerService.delete(customerService.findIndexById(id));
        return modelAndView;
    }

}