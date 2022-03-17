package votation.bestproperty.models;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import votation.bestproperty.models.enums.Roles;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Document("users")
@Data
@NoArgsConstructor
public class User {

    @Id
    private String id;
    @Email
    @NotNull
    @NotBlank
    private String email;
    @NotNull
    @NotBlank
    private String uid;
    @NotNull
    @NotBlank
    private Roles role;

}
