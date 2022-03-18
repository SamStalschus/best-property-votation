package votation.bestproperty.security;

import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import votation.bestproperty.models.User;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RequestFilter extends OncePerRequestFilter {

    Firebase firebase;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            User user = firebase.getUserByToken(request.getHeader("Authorization"));
            filterChain.doFilter(request, response);
        }catch (RuntimeException | FirebaseAuthException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
