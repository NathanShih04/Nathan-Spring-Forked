package com.nighthawk.spring_portfolio.mvc.person;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.format.annotation.DateTimeFormat;

import com.vladmihalcea.hibernate.type.json.JsonType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/*
Person is a POJO, Plain Old Java Object.
First set of annotations add functionality to POJO
--- @Setter @Getter @ToString @NoArgsConstructor @RequiredArgsConstructor
The last annotation connect to database
--- @Entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@TypeDef(name="json", typeClass = JsonType.class)
public class Person {
    
    // automatic unique identifier for Person record
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // email, password, roles are key attributes to login and authentication
    @NotEmpty
    @Size(min=5)
    @Column(unique=true)
    @Email
    private String email;

    @NotEmpty
    private String password;

    // @NonNull, etc placed in params of constructor: "@NonNull @Size(min = 2, max = 30, message = "Name (2 to 30 chars)") String name"
    @NonNull
    @Size(min = 2, max = 30, message = "Name (2 to 30 chars)")
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;
    
    @Positive
    private Integer height;

    @Positive
    private Integer weight;

    @Positive
    private Integer goalStep;

    /* HashMap is used to store JSON for daily "stats"
    "stats": {
        "2022-11-13": {
            "calories": 2200,
            "steps": 8000
        }
    }
    */

    @Type(type="json")
    @Column(columnDefinition = "jsonb")
    private Map<String,Map<String, Object>> stats = new HashMap<>(); 
    

    // Constructor used when building object from an API
    public Person(String email, String password, String name, Date dob, int height, int weight) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.dob = dob;
        this.height = height; 
        this.weight = weight;
        this.goalStep = (height/2 + weight)*70;
    }

    // A custom getter to return age from dob attribute
    public int getAge() {
        if (this.dob != null) {
            LocalDate birthDay = this.dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return Period.between(birthDay, LocalDate.now()).getYears(); }
        return -1;
    }

    public String toString() {
        return ( "{ \"name\": "  +this.name+  ", " + "\"email\": "  +this.email+  ", " + "\"password\": "  +this.password+  ", " + "\"dateOfBirth\": "  +this.dob+  ", " + "\"age\": "  +this.getAge()+ ", " + "\"height(cm)\": "  +this.height+ ", " + "\"weight(kg)\": "  +this.weight+ ", " + "\"stepgoal\": "  +this.goalStep+ " }" );
    }

    public static void main(String[] args) {
        Person p0 = new Person();
        Person p1 = new Person("Nathanshih04@gmail.com", "turtles", "Nathan Shih", new java.util.GregorianCalendar(2004,12,11).getTime(), 67, 140);
        System.out.println(p0);
        System.out.println(p1);
    }
}