package votation.bestproperty.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
}
