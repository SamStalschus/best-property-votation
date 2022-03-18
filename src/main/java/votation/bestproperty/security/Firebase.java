package votation.bestproperty.security;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import votation.bestproperty.models.User;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;

@Service
public class Firebase {

    private FirebaseAuth auth;
    private final MongoTemplate template;

    public Firebase(MongoTemplate template){
        this.template = template;
    }

    @PostConstruct
    public void initialization(){

        FileInputStream serviceAccount;

        try {

            serviceAccount = new FileInputStream("firebase-key.json");

            @SuppressWarnings("deprecation")
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getUidByIdToken(String token) throws FirebaseAuthException {
        return auth.verifyIdToken(token).getUid();
    }

    public User getUserByToken(String token) throws FirebaseAuthException {
        String uid = getUidByIdToken(token);
        Query query = new Query();
        query.addCriteria(Criteria.where("uid").is(uid));
        return template.findOne(query, User.class);
    }

}
