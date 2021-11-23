package app.commerceio.spring.data.search.jpa.demo.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address {

    @Id
    private String id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    private String streetAddress;
    private String postalCode;
    private String city;
    private String country;
    private String countryCode;
}

