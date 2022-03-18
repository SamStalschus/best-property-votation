package votation.bestproperty.repository;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class PropertyRepository {

    private final MongoTemplate template;
    public PropertyRepository(MongoTemplate template){
        this.template = template;
    }

}
