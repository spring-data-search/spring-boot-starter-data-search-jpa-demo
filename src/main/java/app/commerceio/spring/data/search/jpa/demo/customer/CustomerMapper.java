package app.commerceio.spring.data.search.jpa.demo.customer;

import com.github.javafaker.Faker;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

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

    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "ref", source = "ref")
    @Mapping(target = "title", expression = "java(faker.name().prefix())")
    @Mapping(target = "firstName", expression = "java(faker.name().firstName())")
    @Mapping(target = "lastName", expression = "java(faker.name().lastName())")
    @Mapping(target = "emailAddress", expression = "java(faker.internet().emailAddress())")
    @Mapping(target = "emailAddressVerified", expression = "java(faker.bool().bool())")
    @Mapping(target = "coins", expression = "java(faker.number().randomDouble(6, 0, 100))")
    Customer customer(Long ref, Faker faker);

    @AfterMapping
    default Customer customerAfterMapping(Long ref, Faker faker, @MappingTarget Customer customer) {
        String emailAddress = MessageFormat.format("{0}.{1}@{2}",
                customer.getFirstName().replaceAll("[^\\x00-\\x7F]", "").replaceAll("[^a-zA-Z]", ""),
                customer.getLastName().replaceAll("[^\\x00-\\x7F]", "").replaceAll("[^a-zA-Z]", ""),
                customer.getEmailAddress().split("@")[1]).toLowerCase();
        customer.setEmailAddress(emailAddress);

        LocalDate birthDate = faker.date().birthday(26, 84).toInstant().atOffset(ZoneOffset.UTC).toLocalDate();
        customer.setBirthDate(birthDate);

        Random random = new Random();
        List<Address> addresses = new ArrayList<>();
        IntStream.rangeClosed(0, random.nextInt(4) + 1)
                .forEach(id -> addresses.add(address(customer, faker)));
        customer.setAddresses(addresses);

        LocalDateTime to = LocalDateTime.now(ZoneOffset.UTC);
        LocalDateTime from = to.minus(1, ChronoUnit.YEARS);
        OffsetDateTime createdDate = faker.date().between(Date.from(from.toInstant(ZoneOffset.UTC)), Date.from(to.toInstant(ZoneOffset.UTC))).toInstant().atOffset(ZoneOffset.UTC);
        customer.setCreatedDate(createdDate);
        return customer;
    }

    private Address address(Customer customer, Faker faker) {
        Address address = address(faker.address());
        address.setCustomer(customer);
        address.setCountry(new Locale("", address.getCountryCode()).getDisplayCountry());
        return address;
    }

    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "streetAddress", expression = "java(address.streetAddress())")
    @Mapping(target = "postalCode", expression = "java(address.zipCode())")
    @Mapping(target = "city", expression = "java(address.city())")
    @Mapping(target = "countryCode", expression = "java(address.countryCode())")
    Address address(com.github.javafaker.Address address);
}
