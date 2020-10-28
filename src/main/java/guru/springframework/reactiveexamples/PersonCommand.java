package guru.springframework.reactiveexamples;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonCommand {

    public PersonCommand(Person person) {
        this.firstName = person.firstName;
        this.lastName = person.lastName;
    }

    private String firstName;
    private String lastName;

    public String sayMyName() {

        return "My name is " + firstName + " " + lastName + ".";
    }
}
