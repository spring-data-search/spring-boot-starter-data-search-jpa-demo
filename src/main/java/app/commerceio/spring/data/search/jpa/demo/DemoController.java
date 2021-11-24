package app.commerceio.spring.data.search.jpa.demo;

import app.commerceio.spring.data.search.Mapper;
import app.commerceio.spring.data.search.jpa.demo.customer.Customer;
import app.commerceio.spring.data.search.jpa.demo.customer.CustomerMapper;
import app.commerceio.spring.data.search.jpa.demo.customer.data.CustomerEntity;
import app.commerceio.spring.data.search.jpa.demo.customer.data.CustomerEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@Log4j2
@RestController
@RequiredArgsConstructor
public class DemoController {

    private final CustomerMapper customerMapper;
    private final CustomerEntityRepository customerRepository;

    @Transactional(readOnly = true)
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/customers",
            produces = {"application/json"}
    )
    public ResponseEntity<Page<Customer>> searchCustomers(
            @RequestParam(value = "search", required = false) String search,
            Pageable pageable) {

        Mapper addressMapper = Mapper.flatMapper()
                .mapping("street", "streetAddress")
                .mapping("zipCode", "postalCode")
                .mapping("country", "countryCode")
                .build();
        Mapper mapper = Mapper.mapper()
                .mapping("name", "firstName")
                .mapping("surname", "lastName")
                .mapping("email", "emailAddress")
                .mapping("emailVerified", "emailAddressVerified")
                .mapping("addresses", "addressEntities", addressMapper)
                .build();

        Page<CustomerEntity> page = customerRepository.findAll(
                search,
                mapper.map(pageable),
                mapper);

        PageImpl<Customer> customerPage = new PageImpl<>(customerMapper.customers(page.getContent()), page.getPageable(), page.getTotalElements());
        return ok(customerPage);
    }

    @Transactional(readOnly = true)
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/customers/{customerId}",
            produces = {"application/json"}
    )
    public ResponseEntity<CustomerEntity> getCustomer(@PathVariable String customerId) {

        return customerRepository.findById(customerId)
                .map(ResponseEntity::ok)
                .orElse(notFound().build());
    }
}
