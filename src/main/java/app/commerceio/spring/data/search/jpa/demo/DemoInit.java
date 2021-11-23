package app.commerceio.spring.data.search.jpa.demo;

import app.commerceio.spring.data.search.jpa.demo.customer.Customer;
import app.commerceio.spring.data.search.jpa.demo.customer.CustomerMapper;
import app.commerceio.spring.data.search.jpa.demo.customer.CustomerRepository;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
@RequiredArgsConstructor
public class DemoInit implements ApplicationListener<ApplicationReadyEvent> {

    private final CustomerMapper customerMapper;
    private final DemoProperties demoProperties;
    private final CustomerRepository customerRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (demoProperties.isDataInit()) {
            Faker faker = new Faker();
            customerRepository.deleteAll();
            List<Customer> customers = new ArrayList<>();
            for (long ref = 1; ref <= demoProperties.getDataSize(); ref++) {
                Customer customer = customerDocument(ref, faker);
                customers.add(customer);
            }
            customerRepository.saveAll(customers);
        }
    }

    private Customer customerDocument(long ref, Faker faker) {
        return customerMapper.customer(ref, faker);
    }

}
