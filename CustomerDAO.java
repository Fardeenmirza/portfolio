package portfolio.restapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;

 
	public List<Customer> list(){
		String sql = "select * from `customer_table`"; 
		List<Customer> customer = jdbcTemplate.query(sql, 
				BeanPropertyRowMapper.newInstance(Customer.class));
		return customer;
	}
	public void save(Customer customer) {
		SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);
		insertActor.withTableName("customer_table").usingColumns("cust_name","email","subject","message");
		
		BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(customer);
		insertActor.execute(param);

	}
	public Customer get(int cust_id) {
		return null;

	}
	public void update (Customer customer) {

	}
	public CustomerDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	public void delete(Customer customer) {

	}

}
