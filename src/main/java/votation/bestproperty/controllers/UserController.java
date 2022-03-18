package votation.bestproperty.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import votation.bestproperty.models.Response;
import votation.bestproperty.models.User;
import votation.bestproperty.models.enums.Roles;
import votation.bestproperty.repository.UserRepository;
import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/api/user/")
public class UserController {

    private final UserRepository userRepository;
    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @PostMapping("vote")
    public ResponseEntity<Response<User>> create(@Valid @RequestBody User user, BindingResult validationResult){
        try{
            if(validationResult.hasErrors()){
                return new ResponseEntity<>(new Response<>(
                        null,
                        Objects.requireNonNull(validationResult.getFieldError()).toString()),
                        HttpStatus.CONFLICT);
            }else{
                if(user.getRole() != Roles.ACCESS_TO_VOTE){
                    user.setRole(Roles.ACCESS_TO_VOTE);
                }
                Response<User> response = userRepository.create(user);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

        }catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
