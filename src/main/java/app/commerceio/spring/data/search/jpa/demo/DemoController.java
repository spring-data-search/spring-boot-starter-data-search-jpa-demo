package app.commerceio.spring.data.search.jpa.demo;

import app.commerceio.spring.data.search.jpa.demo.customer.Customer;
import app.commerceio.spring.data.search.jpa.demo.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
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

    private final CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/customers",
            produces = {"application/json"}
    )
    public ResponseEntity<Page<Customer>> searchCustomers(
            @RequestParam(value = "search", required = false) String search,
            Pageable pageable) {

        Page<Customer> customerDocumentPage = customerRepository.findAll(
                search,
                pageable);

        return ok(customerDocumentPage);
    }

    @Transactional(readOnly = true)
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/customers/{customerId}",
            produces = {"application/json"}
    )
    public ResponseEntity<Customer> getCustomer(@PathVariable String customerId) {

        return customerRepository.findById(customerId)
                .map(ResponseEntity::ok)
                .orElse(notFound().build());
    }
}
