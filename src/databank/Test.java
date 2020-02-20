package databank;

import javax.persistence.*;

@Entity
@Table(name = "test")
public class Test {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int testid;
    private String naam;

    protected Test(){}

    public Test(String name){
        setNaam(name);
    }

    public void setNaam(String naam) {
    }
}
