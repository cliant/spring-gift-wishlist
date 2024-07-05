package gift.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import gift.domain.Product;

import java.util.List;

@Repository
public class ProductDao {
    
    private final JdbcTemplate jdbcTemplate;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> findAll(){
        var sql = "select id, name, price, imageUrl from product";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new Product(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("price"),
                        resultSet.getString("imageUrl"))
                );
    }

    public Product findOne(Long id){
        try{
            var sql = "select id, name, price, imageUrl from product where id = ?";
            return jdbcTemplate.queryForObject(
                    sql,
                    (resultSet, rowNum) -> new Product(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("price"),
                        resultSet.getString("imageUrl")),
                    id
            );
        }catch(EmptyResultDataAccessException e){
            return null;  
        }
    }

    public void insertProduct(Product product) {

        var sql = "INSERT INTO product (id, name, price, imageUrl) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, product.id(), product.name(), product.price(), product.imageUrl());

    }

    public void updateProduct(Product product) {

        var sql = "UPDATE product SET name = ?, price = ?, imageUrl = ? WHERE id = ?";
        jdbcTemplate.update(sql, product.name(), product.price(), product.imageUrl(), product.id());

    }

    public void deleteProduct(Long id) {

        var sql = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(sql, id);

    }
}