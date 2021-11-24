package app.commerceio.spring.data.search.jpa.demo.customer.data;

import app.commerceio.spring.data.search.jpa.SearchRepository;
import app.commerceio.spring.data.search.jpa.demo.customer.data.CustomerEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerEntityRepository extends SearchRepository<CustomerEntity, String> {
}
