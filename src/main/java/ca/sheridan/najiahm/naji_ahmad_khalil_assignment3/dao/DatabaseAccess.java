package ca.sheridan.najiahm.naji_ahmad_khalil_assignment3.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.sheridan.najiahm.naji_ahmad_khalil_assignment3.model.Product;

@Repository
public class DatabaseAccess {
    @Autowired
    protected NamedParameterJdbcTemplate jdbc;
    //add product to db method
    public long addProducts(Product prod) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String insert= "INSERT INTO product_catalog (product_code, product_brand, unit_price, quantity) VALUES (:product_code, :product_brand, :price, :quantity);";
        
        namedParameters.addValue("product_code", prod.getProduct_code());
        namedParameters.addValue("product_brand", prod.getBrand());
        namedParameters.addValue("price", prod.getUnit_price());
        namedParameters.addValue("quantity", prod.getQuantity());
        int rowsAffected = jdbc.update(insert, namedParameters);
        
        return rowsAffected;
    }
    //select all method
    public List<Product> selectProducts(int threshhold, String brand) {
        MapSqlParameterSource namedParameters =
        new MapSqlParameterSource();
        String query = "SELECT * FROM products";
        if(threshhold != 0){
            query = "SELECT * FROM product_catalog WHERE quantity >= "+threshhold;
        }else if(brand != ""){
            query = "SELECT * FROM product_catalog WHERE product_brand IS '" + brand+"'";
        }
        List<Product> products_list = jdbc.query(query,
        namedParameters, new
        BeanPropertyRowMapper<Product>(Product.class));
        return products_list;
    }
}