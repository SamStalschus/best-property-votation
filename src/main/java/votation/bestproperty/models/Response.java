package votation.bestproperty.models;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Response <T>{
    public int quantity;
    public String message;
    public T body;

    public Response(T body){
        this.body = body;
    }

    public Response(T body, String message){
        this.body = body;
        this.message = message;
    }

}
