package ch.swaechter.data;

public class FederalCouncilMember {

    private String FirstName;

    private String LastName;

    private String GenderAsString;

    private String CantonName;

    private String PartyName;

    private String DateNationalCouncilJoining;

    private String DateNationalCouncilLeaving;

    private String DateStateCouncilJoining;

    private String DateStateCouncilLeaving;

    private String DateFederalCouncilJoining;

    private String DateFederalCouncilLeaving;

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getGenderAsString() {
        return GenderAsString;
    }

    public void setGenderAsString(String genderAsString) {
        GenderAsString = genderAsString;
    }

    public String getCantonName() {
        return CantonName;
    }

    public void setCantonName(String cantonName) {
        CantonName = cantonName;
    }

    public String getPartyName() {
        return PartyName;
    }

    public void setPartyName(String partyName) {
        PartyName = partyName;
    }

    public String getDateNationalCouncilJoining() {
        return DateNationalCouncilJoining;
    }

    public void setDateNationalCouncilJoining(String dateNationalCouncilJoining) {
        DateNationalCouncilJoining = dateNationalCouncilJoining;
    }

    public String getDateNationalCouncilLeaving() {
        return DateNationalCouncilLeaving;
    }

    public void setDateNationalCouncilLeaving(String dateNationalCouncilLeaving) {
        DateNationalCouncilLeaving = dateNationalCouncilLeaving;
    }

    public String getDateStateCouncilJoining() {
        return DateStateCouncilJoining;
    }

    public void setDateStateCouncilJoining(String dateStateCouncilJoining) {
        DateStateCouncilJoining = dateStateCouncilJoining;
    }

    public String getDateStateCouncilLeaving() {
        return DateStateCouncilLeaving;
    }

    public void setDateStateCouncilLeaving(String dateStateCouncilLeaving) {
        DateStateCouncilLeaving = dateStateCouncilLeaving;
    }

    public String getDateFederalCouncilJoining() {
        return DateFederalCouncilJoining;
    }

    public void setDateFederalCouncilJoining(String dateFederalCouncilJoining) {
        DateFederalCouncilJoining = dateFederalCouncilJoining;
    }

    public String getDateFederalCouncilLeaving() {
        return DateFederalCouncilLeaving;
    }

    public void setDateFederalCouncilLeaving(String dateFederalCouncilLeaving) {
        DateFederalCouncilLeaving = dateFederalCouncilLeaving;
    }

    @Override
    public String toString() {
        return "FederalCouncilMember{" +
                "FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", GenderAsString='" + GenderAsString + '\'' +
                ", CantonName='" + CantonName + '\'' +
                ", PartyName='" + PartyName + '\'' +
                ", DateNationalCouncilJoining='" + DateNationalCouncilJoining + '\'' +
                ", DateNationalCouncilLeaving='" + DateNationalCouncilLeaving + '\'' +
                ", DateStateCouncilJoining='" + DateStateCouncilJoining + '\'' +
                ", DateStateCouncilLeaving='" + DateStateCouncilLeaving + '\'' +
                ", DateFederalCouncilJoining='" + DateFederalCouncilJoining + '\'' +
                ", DateFederalCouncilLeaving='" + DateFederalCouncilLeaving + '\'' +
                '}';
    }
}
