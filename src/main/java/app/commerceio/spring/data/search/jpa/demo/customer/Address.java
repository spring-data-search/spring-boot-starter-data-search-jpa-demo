package app.commerceio.spring.data.search.jpa.demo.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String id;
    private String street;
    private String zipCode;
    private String city;
    private String country;
}

