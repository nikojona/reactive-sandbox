package guru.springframework.reactiveexamples;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    
    public String firstName;
    public String lastName;

    public String sayMyName(){

        return "My name is " + firstName + " " + lastName;
    };
}
