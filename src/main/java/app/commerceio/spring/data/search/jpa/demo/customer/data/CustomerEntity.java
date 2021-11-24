package app.commerceio.spring.data.search.jpa.demo.customer.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CustomerEntity {

    @Id
    private String id;
    private long ref;
    private String title;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private boolean emailAddressVerified;
    private LocalDate birthDate;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<AddressEntity> addressEntities;
    private double coins;
    private OffsetDateTime createdDate;
}
