package votation.bestproperty.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("properties-group")
@Data
@NoArgsConstructor
public class Group {

    @Id
    private String id;
    private List<String> propertiesIds;
    private List<History> history;
    private boolean deleted;

}
