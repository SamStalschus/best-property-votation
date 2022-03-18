package votation.bestproperty.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import votation.bestproperty.models.Group;
import votation.bestproperty.models.Response;
import votation.bestproperty.models.User;
import votation.bestproperty.models.exception.DbException;

import java.util.List;

@Service
public class GroupRepository {

    private final MongoTemplate template;
    public GroupRepository(MongoTemplate template){
        this.template = template;
    }

    private boolean verifyIfGroupExist(String id){
        return template.findOne(new Query(Criteria.where("id").is(id)), Group.class) != null;
    }

    public Response<Group> create(Group obj){
        try{
            if(verifyIfGroupExist(obj.getId())){
                return null;
            }

            return new Response<>(template.save(obj));

        }catch (RuntimeException e){
            throw new DbException("could not persist data: " + e.getMessage());
        }
    }

    public Response<List<Group>> read(int skip, int limit, User user){
        try{

            Query query = new Query();
            query.with(Sort.by(Sort.Direction.ASC, "id"));
            query.skip(skip).limit(limit);
            return new Response<>(template.find(query, Group.class));

        }catch (RuntimeException e){
            throw new DbException("could not read data: " + e.getMessage());
        }
    }

    public Response<Group> readYourself(String uid){
        try{

            Query query = new Query(Criteria.where("uid").is(uid));
            return new Response<>(template.findOne(query, Group.class));

        }catch (RuntimeException e){
            throw new DbException("could not read data: " + e.getMessage());
        }
    }

    public Response<Group> update(Group obj){
        try{
            return null;
        }catch (RuntimeException e){
            throw new DbException("could not update data: " + e.getMessage());
        }
    }

    public Response<Group> delete(String id){
        try{
            return null;
        }catch (RuntimeException e){
            throw new DbException("could not delete data: " + e.getMessage());
        }
    }

}
