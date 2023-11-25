package ca.sheridan.najiahm.naji_ahmad_khalil_assignment3.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridan.najiahm.naji_ahmad_khalil_assignment3.dao.DatabaseAccess;
import ca.sheridan.najiahm.naji_ahmad_khalil_assignment3.model.Product;

@Controller
public class controller {
    @Autowired
    private DatabaseAccess pda;
    public ArrayList<Product> prods = new ArrayList<>();


    @GetMapping(value = "/index") // route to index
    public String index() {
        return "/index";
    }

    // ADDING ROUTERS
    // add to model for form binding, then route user to
    // insert_product_input.html
    @GetMapping("/adding_templates/insert_product")
    public String insert_prod(Model model) {
        model.addAttribute("product", new Product());
        return "/adding_templates/insert_product_input";
    }

    @GetMapping("/insert_outcome")
    public String insert_outcome(@ModelAttribute Product product, Model model) {

        String message;
        long number_of_rows = pda.addProducts(product);
        if (number_of_rows > 0) {
            message = "AWESOME SAUCE!";
        } else {
            message = "OH MY, OH MY, OH MY";
        }
        model.addAttribute("message", message);
        return "/adding_templates/insert_outcome";
    }

    // DELETING ROUTING
    @GetMapping("/list_products")
    public String delete_selection(Model model) {
        prods = (ArrayList) pda.selectProducts(0, "");
        model.addAttribute("products", prods); // showing all objects in database that are deletable
        return "/listing_templates/list_all_products";
    }

    // MAPPING FOR SELECTED OBJECT
    @GetMapping("/delete_product_info/{id}")
    @ResponseBody
    public String delete_confirm(Model model, @PathVariable("id") int id) {
        String message = "Are you sure you want to delete product with ID: " + id; // requesting confirmation from user
        String button = "<br/><a href='/delete_product_confirmation/" + id + "'><button>Confirm</button></a>";
        model.addAttribute("id", id);
        return message + button;
    }

    // MAPPING FOR WHEN CONFIRM BUTTON IS PRESSED
    @GetMapping("/delete_product_confirmation/{id}")
    public String delete_outcome(@PathVariable("id") int id, Model model) {
        long outcome = pda.deleteProduct(id);
        String message;
        if (outcome > 0) {
            message = "AWESOME SAUCE!";
        } else {
            message = "YOU'RE A DISAPPOINTMENT!";
        }
        model.addAttribute("message", message);
        return "/listing_templates/deleting_templates/delete_outcome";
    }

    // EDITING PAGES
    /*
     * I used our handy array list here since we know the instance the user clicked
     * on to edit is apart of the list we used to diplay on the page
     * therefore avoiding having to make 3 requests to the Database to update one
     * instance, we only need to make two requests.
     * One of those requests in fact serves the purpose of providing access to both
     * deleting and editing rows.
     */
    @GetMapping("/update_product_info/{id}")
    public String update_info_form(Model model, @PathVariable("id") int id) {
        Product prod = prods.get(id);
        model.addAttribute("prod", prod);// added for form binding
        return "/listing_templates/editing_templates/update_product_info";
    }

    @GetMapping("/update_outcome/{id}")
    public String update_outcome(Model model, @ModelAttribute Product prod, @PathVariable("id") int id) {
        long result = pda.update_row(id, prod);// given new information provided we use the id to send a request to
                                               // database to update instance
        String message;
        if (result > 0) {
            message = "AWESOME SAUCE";
        } else {
            message = "WAH WAH";
        }
        model.addAttribute("message", message);
        return "/listing_templates/editing_templates/update_outcome";
    }

    // LISTING PAGES OH MY GOODNESS IT DOESN'T STOP SEND HELP!!!

    // LISTING BRANDS
    @GetMapping("select_brand")
    public String brand_picker(Model model) {
        ArrayList<Product> brands = new ArrayList<>();
        // TODO: ADD SELECT FUNCTION THAT ADDS ALL UNIQUE BRANDS TO PAGE
        model.addAttribute("brands", brands);
        return "select_brand";
    }

    // @GetMapping(value = "/list_by_brand")
    // public String banding101() {

    //     return "list_by_brand";
    // }

    // LIST QUANTITIES
    @GetMapping("/apply_filters/{filter}")
    public String display_then_filter(Model model, @PathVariable("filter") String filter) {
        ArrayList<Product> prods = new ArrayList<>();
        int inp=0;
        try{
            inp = Integer.parseInt(filter);
            filter = "poopAE";
            System.out.println(filter);
            System.out.println(inp);
            prods = (ArrayList)pda.selectProducts(inp, filter);
            model.addAttribute("products", prods);
        }catch(Exception e){
            prods = (ArrayList)pda.selectProducts(inp, filter);
            model.addAttribute("products", prods);
        }
        return "/listing_templates/list_all_products";
    }

    // @GetMapping("/select_by_quantity/{min}")
    // public String display_filtered(@RequestParam ArrayList<Product> prods) {
    //     // TODO: IMPLEMENT LOGIC FOR FILTERING BY MINIMUM, MAYBE JUST IMPLEMENT IN JS?
    //     return "";
    // }
}
