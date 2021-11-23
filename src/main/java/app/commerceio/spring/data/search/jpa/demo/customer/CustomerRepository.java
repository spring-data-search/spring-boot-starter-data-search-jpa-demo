package app.commerceio.spring.data.search.jpa.demo.customer;

import app.commerceio.spring.data.search.jpa.SearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends SearchRepository<Customer, String> {
}
