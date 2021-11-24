package app.commerceio.spring.data.search.jpa.demo.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    private String id;
    private long ref;
    private String title;
    private String name;
    private String surname;
    private String email;
    private boolean emailVerified;
    private LocalDate birthDate;
    private List<Address> addresses;
    private double coins;
    private OffsetDateTime createdDate;
}
