package ch.swaechter.data;

import java.util.ArrayList;
import java.util.List;

public class Person {

    private final String FirstName;

    private final String LastName;

    private boolean isFederalCouncil;

    private final List<ParliamentMember> parliamentMembers;

    public Person(String FirstName, String LastName) {
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.parliamentMembers = new ArrayList<>();
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public boolean isFederalCouncil() {
        return isFederalCouncil;
    }

    public void setFederalCouncil(boolean federalCouncil) {
        isFederalCouncil = federalCouncil;
    }

    public List<ParliamentMember> getParliamentMembers() {
        return parliamentMembers;
    }

    @Override
    public String toString() {
        return "Person{" +
                "FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", isFederalCouncil=" + isFederalCouncil +
                '}';
    }
}
