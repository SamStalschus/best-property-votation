package votation.bestproperty.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import votation.bestproperty.models.*;
import votation.bestproperty.models.enums.Roles;
import votation.bestproperty.models.exception.DbException;
import votation.bestproperty.services.Utils;
import java.util.List;

@Service
public class PropertyRepository {

    private final MongoTemplate template;
    public PropertyRepository(MongoTemplate template){
        this.template = template;
    }

    private boolean verifyPropertyExist(String id){
        return template.findOne(new Query(Criteria.where("id").is(id)), Property.class) != null;
    }

    public Response<Property> create(Property obj, User user){
        try{

            if(verifyPropertyExist(obj.getId())){ return null;}

            History history = new History(user.getUid(), "USER ID: " + user.getId() + "DELETED GROUP", Utils.getTime());
            obj.getHistory().add(history);
            return new Response<>(template.save(obj));

        }catch (RuntimeException e){
            throw new DbException("could not delete property: " + e.getMessage());
        }
    }

    public Response<List<Property>> read(String groupId, int skip, int limit, User user){
        try{

            Query query = new Query(Criteria.where("deleted").is(false));
            query.with(Sort.by(Sort.Direction.ASC, "id"));
            if(limit > 100) limit = 100;
            query.skip(skip).limit(limit);
            if(user.getRole() == Roles.ACCESS_TO_VOTE){
                query.addCriteria(Criteria.where("id").is(groupId));
            }

            return new Response<>(template.find(query, Property.class));

        }catch (RuntimeException e){
            throw new DbException("could not read property: " + e.getMessage());
        }
    }

    public Response<Property> update(Property obj, User user){
        try{
            return null;
        }catch (RuntimeException e){
            throw new DbException("could not delete property: " + e.getMessage());
        }
    }

    public Response<Boolean> delete(String id, User user){
        try{
            return null;
        }catch (RuntimeException e){
            throw new DbException("could not delete property: " + e.getMessage());
        }
    }

}
