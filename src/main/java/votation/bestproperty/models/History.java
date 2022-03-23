package votation.bestproperty.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class History {
    @NotNull
    @NotBlank
    private String uid;
    @NotNull
    @NotBlank
    private String message;
    private Date date;
}
