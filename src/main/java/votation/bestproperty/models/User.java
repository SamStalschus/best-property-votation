package votation.bestproperty.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import votation.bestproperty.models.enums.Roles;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

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
    private String userName;
    private int points;
    @NotNull
    @NotBlank
    private String uid;
    private List<String> groupIds;
    @NotNull
    @NotBlank
    private Roles role;
    @JsonIgnore
    private List<History> histories;
    private boolean deleted;

}
