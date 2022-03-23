package votation.bestproperty.repository;

import com.mongodb.client.result.UpdateResult;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import votation.bestproperty.models.Group;
import votation.bestproperty.models.History;
import votation.bestproperty.models.Response;
import votation.bestproperty.models.User;
import votation.bestproperty.models.enums.Roles;
import votation.bestproperty.models.exception.DbException;
import votation.bestproperty.services.Utils;
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

    public Response<Group> create(Group obj, User user){
        try{

            if(verifyIfGroupExist(obj.getId())){ return null;}

            History history = new History(user.getUid(), "USER ID: " + user.getId() + "DELETED GROUP", Utils.getTime());
            obj.getHistory().add(history);
            return new Response<>(template.save(obj));

        }catch (RuntimeException e){
            throw new DbException("could not persist group: " + e.getMessage());
        }
    }

    public Response<List<Group>> read(int skip, int limit, User user){
        try{

            Query query = new Query(Criteria.where("deleted").is(false));
            query.with(Sort.by(Sort.Direction.ASC, "id"));
            if(limit > 100) limit = 100;
            query.skip(skip).limit(limit);

            if(user.getRole() == Roles.ACCESS_TO_VOTE){
                user.getGroupIds().forEach(group -> query.addCriteria(Criteria.where("id").is(group)));
            }

            return new Response<>(template.find(query, Group.class));

        }catch (RuntimeException e){
            throw new DbException("could not read group: " + e.getMessage());
        }
    }

    public Response<Group> addProperty(User user, String groupId, String propertyId){
        try{

            History history = new History(user.getUid(), "USER ID: " + user.getId() + "UPDATE GROUP, ADD PROPERTY: " + propertyId , Utils.getTime());
            Query query = new Query(Criteria.where("id").is(groupId));
            Update update = new Update().push("propertiesIds", propertyId).push("history", history);
            UpdateResult result = template.updateFirst(query, update, Group.class);
            if (result.getModifiedCount() > 0){
                return new Response<>(template.findOne(query, Group.class));
            }
            return null;

        }catch (RuntimeException e){
            throw new DbException("could not update group: " + e.getMessage());
        }
    }

    public Response<Group> removeProperty(User user, String groupId, String propertyId){
        try{

            History history = new History(user.getUid(), "USER ID: " + user.getId() + "UPDATE GROUP, REMOVE PROPERTY: " + propertyId , Utils.getTime());
            Query query = new Query(Criteria.where("id").is(groupId));
            Group group = template.findOne(query, Group.class);
            assert group != null;
            group.getPropertiesIds().removeIf(item -> item.equals(propertyId));

            if(!group.getPropertiesIds().contains(groupId)){
                Update update = new Update().set("propertiesIds", group.getPropertiesIds());
                UpdateResult result = template.updateFirst(query, update, Group.class);
                if (result.getModifiedCount() > 0){
                    return new Response<>(template.findOne(query, Group.class));
                }
            }
            return null;

        }catch (RuntimeException e){
            throw new DbException("could not update group: " + e.getMessage());
        }
    }

    public Response<Boolean> delete(String id, User user){
        try{

            if(user.getRole() != Roles.ACCESS_TO_VOTE){
                History history = new History(user.getUid(), "USER ID: " + user.getId() + "DELETED GROUP", Utils.getTime());

                Query query = new Query(Criteria.where("id").is(id).and("deleted").is(false));
                Update update = new Update().set("deleted", true).push("history", history);
                UpdateResult result = template.updateFirst(query, update, Group.class);
                if(result.getModifiedCount() > 0){return new Response<>(true);}
            }

            return new Response<>(false);

        }catch (RuntimeException e){
            throw new DbException("could not delete group: " + e.getMessage());
        }
    }

}
