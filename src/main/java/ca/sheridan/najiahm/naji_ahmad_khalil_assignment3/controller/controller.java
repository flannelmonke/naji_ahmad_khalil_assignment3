package ca.sheridan.najiahm.naji_ahmad_khalil_assignment3.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import ca.sheridan.najiahm.naji_ahmad_khalil_assignment3.model.Product;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class controller {
    @GetMapping(value="/index")//route to index
    public String index() {
        return "/index";
    }
    
    //ADDING ROUTERS
    @GetMapping("insert_product")//add to model for form binding, then route user to insert_product_input.html
    public String insert_prod(Model model){
        model.addAttribute("product", new Product());
        return "insert_product_input";
    }
    @GetMapping("/insert_outcome")
    public String insert_outcome(){
        // TODO: database access code
        return "insert_outcome";
    }


    //DELETING ROUTING
    @GetMapping("delect_product_info")
    public String delete_selection(Model model){
        ArrayList<Product> prods = new ArrayList<>();        
        //TODO: add code to SELECT ALL FROM DATABASE that are deletable
        model.addAttribute("Prodcuts", prods); //showing all objects in database that are deletable
        return "delete_product_info";
    }

    //MAPPING FOR SELECTED OBJECT
    @GetMapping("/delete_product_info/{id}")
    @ResponseBody
    public String delete_confirm(Model model, @PathVariable("id") String id){
        String message = "Are you sure you want to delete product with ID: " + id; //requesting confirmation from user
        model.addAttribute("message", message);
        return "";
    }
    //MAPPING FOR WHEN CONFIRM BUTTON IS PRESSED
    @GetMapping("/delete_product_confirmation")
    public String delete_outcome(){
        //TODO: ADD DELETING FUNCTION FOR SPECIFIED ID
        return "delete_product_confirmation";
    }

    //EDITING PAGES
    @GetMapping("list_of_courses")
    public String update_select(Model model){
        ArrayList<Product> prods = new ArrayList<>();        
        //TODO: add code to SELECT ALL FROM DATABASE that are editable
        model.addAttribute("Prodcuts", prods); //showing all objects in database that are deletable        
        return "list_of_courses";
    }
    @GetMapping("/update_product_info/{id}")
    public String update_info_form(Model model, @PathVariable("id") String id){
        Product prod = new Product(); //TODO: CHANGE THIS TO GET STATEMENT RETURN VALUE(Product)
        model.addAttribute("prod", prod);//added for form binding
        return "update_product_info";
    }
    @GetMapping("update_outcome")
    public String update_outcome(){
        //TODO: send update query to SQL with new prod 
        return "update_outcome";
    }



    //LISTING PAGES OH MY GOODNESS IT DOESN'T STOP SEND HELP!!!
    
    //LISTING BRANDS
    @GetMapping("select_brand")
    public String brand_picker(Model model){
        ArrayList<Product> brands = new ArrayList<>();        
        //TODO: ADD SELECT FUNCTION THAT ADDS ALL UNIQUE BRANDS TO PAGE
        model.addAttribute("brands", brands);
        return "select_brand";
    }
    @GetMapping(value="/list_by_brand")
    public String banding101() {
         
        return "list_by_brand";
    }
    
    //LIST QUANTITIES
    @GetMapping("select_by_quantity")
    public String display_then_filter(Model model){
        ArrayList<Product> prods = new ArrayList<>();        
        //TODO: ADD SELECT QUERY TO RETURN ALL PRODUCTS UNTIL FILTER IS APPLIED
        model.addAttribute("prods", prods);
        return "select_by_quantity";
    }
    @GetMapping("/select_by_quantity/{min}")
    public String display_filtered(@RequestParam ArrayList<Product> prods){
        //TODO: IMPLEMENT LOGIC FOR FILTERING BY MINIMUM, MAYBE JUST IMPLEMENT IN JS?
        return "";
    }
}