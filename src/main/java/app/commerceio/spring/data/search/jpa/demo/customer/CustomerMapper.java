package app.commerceio.spring.data.search.jpa.demo.customer;

import app.commerceio.spring.data.search.jpa.demo.customer.data.AddressEntity;
import app.commerceio.spring.data.search.jpa.demo.customer.data.CustomerEntity;
import com.github.javafaker.Faker;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.IntStream;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(target = "name", source = "firstName")
    @Mapping(target = "surname", source = "lastName")
    @Mapping(target = "email", source = "emailAddress")
    @Mapping(target = "emailVerified", source = "emailAddressVerified")
    @Mapping(target = "addresses", source = "addressEntities")
    Customer customer(CustomerEntity customerEntity);

    List<Customer> customers(List<CustomerEntity> customerEntities);

    @Mapping(target = "street", source = "streetAddress")
    @Mapping(target = "zipCode", source = "postalCode")
    @Mapping(target = "country", source = "countryCode")
    Address address(AddressEntity addressEntity);

    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "ref", source = "ref")
    @Mapping(target = "title", expression = "java(faker.name().prefix())")
    @Mapping(target = "firstName", expression = "java(faker.name().firstName())")
    @Mapping(target = "lastName", expression = "java(faker.name().lastName())")
    @Mapping(target = "emailAddress", expression = "java(faker.internet().emailAddress())")
    @Mapping(target = "emailAddressVerified", expression = "java(faker.bool().bool())")
    @Mapping(target = "coins", expression = "java(faker.number().randomDouble(6, 0, 100))")
    CustomerEntity customerEntity(Long ref, Faker faker);

    @AfterMapping
    default CustomerEntity customerEntityAfterMapping(Long ref, Faker faker, @MappingTarget CustomerEntity customer) {
        String emailAddress = MessageFormat.format("{0}.{1}@{2}",
                customer.getFirstName().replaceAll("[^\\x00-\\x7F]", "").replaceAll("[^a-zA-Z]", ""),
                customer.getLastName().replaceAll("[^\\x00-\\x7F]", "").replaceAll("[^a-zA-Z]", ""),
                customer.getEmailAddress().split("@")[1]).toLowerCase();
        customer.setEmailAddress(emailAddress);

        LocalDate birthDate = faker.date().birthday(26, 84).toInstant().atOffset(ZoneOffset.UTC).toLocalDate();
        customer.setBirthDate(birthDate);

        Random random = new Random();
        List<AddressEntity> addresses = new ArrayList<>();
        IntStream.rangeClosed(0, random.nextInt(4) + 1)
                .forEach(id -> addresses.add(addressEntity(customer, faker)));
        customer.setAddressEntities(addresses);

        LocalDateTime to = LocalDateTime.now(ZoneOffset.UTC);
        LocalDateTime from = to.minus(1, ChronoUnit.YEARS);
        OffsetDateTime createdDate = faker.date().between(Date.from(from.toInstant(ZoneOffset.UTC)), Date.from(to.toInstant(ZoneOffset.UTC))).toInstant().atOffset(ZoneOffset.UTC);
        customer.setCreatedDate(createdDate);
        return customer;
    }

    private AddressEntity addressEntity(CustomerEntity customer, Faker faker) {
        AddressEntity address = addressEntity(faker.address());
        address.setCustomer(customer);
        address.setCountry(new Locale("", address.getCountryCode()).getDisplayCountry());
        return address;
    }

    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "streetAddress", expression = "java(address.streetAddress())")
    @Mapping(target = "postalCode", expression = "java(address.zipCode())")
    @Mapping(target = "city", expression = "java(address.city())")
    @Mapping(target = "countryCode", expression = "java(address.countryCode())")
    AddressEntity addressEntity(com.github.javafaker.Address address);
}
