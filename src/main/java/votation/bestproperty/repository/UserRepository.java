package votation.bestproperty.repository;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import votation.bestproperty.models.Response;
import votation.bestproperty.models.User;
import votation.bestproperty.models.exception.DbException;

@Service
public class UserRepository {

    private final MongoTemplate template;

    public UserRepository(MongoTemplate template){
        this.template = template;
    }

    public Response<User> create(User user){
        try{
            var result = template.save(user);
            return new Response<>(result);
        }catch (RuntimeException e){
            throw new DbException("could not create user: " + e.getMessage());
        }
    }



}
