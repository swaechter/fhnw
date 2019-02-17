package ch.swaechter.data;

public class ParliamentMember {

    private String Active;

    private String FirstName;

    private String LastName;

    private String GenderAsString;

    private String CantonName;

    private String CantonAbbreviation;

    private String CouncilName;

    private String ParlGroupName;

    private String ParlGroupAbbreviation;

    private String PartyName;

    private String PartyAbbreviation;

    private String MaritalStatusText;

    private String BirthPlace_City;

    private String BirthPlace_Canton;

    private String Mandates;

    private String DateJoining;

    private String DateLeaving;

    private String Citizenship;

    private String DateOfBirth;

    private String DateOfDeath;

    public String getActive() {
        return Active;
    }

    public void setActive(String active) {
        Active = active;
    }

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

    public String getCantonAbbreviation() {
        return CantonAbbreviation;
    }

    public void setCantonAbbreviation(String cantonAbbreviation) {
        CantonAbbreviation = cantonAbbreviation;
    }

    public String getCouncilName() {
        return CouncilName;
    }

    public void setCouncilName(String councilName) {
        CouncilName = councilName;
    }

    public String getParlGroupName() {
        return ParlGroupName;
    }

    public void setParlGroupName(String parlGroupName) {
        ParlGroupName = parlGroupName;
    }

    public String getParlGroupAbbreviation() {
        return ParlGroupAbbreviation;
    }

    public void setParlGroupAbbreviation(String parlGroupAbbreviation) {
        ParlGroupAbbreviation = parlGroupAbbreviation;
    }

    public String getPartyName() {
        return PartyName;
    }

    public void setPartyName(String partyName) {
        PartyName = partyName;
    }

    public String getPartyAbbreviation() {
        return PartyAbbreviation;
    }

    public void setPartyAbbreviation(String partyAbbreviation) {
        PartyAbbreviation = partyAbbreviation;
    }

    public String getMaritalStatusText() {
        return MaritalStatusText;
    }

    public void setMaritalStatusText(String maritalStatusText) {
        MaritalStatusText = maritalStatusText;
    }

    public String getBirthPlace_City() {
        return BirthPlace_City;
    }

    public void setBirthPlace_City(String birthPlace_City) {
        BirthPlace_City = birthPlace_City;
    }

    public String getBirthPlace_Canton() {
        return BirthPlace_Canton;
    }

    public void setBirthPlace_Canton(String birthPlace_Canton) {
        BirthPlace_Canton = birthPlace_Canton;
    }

    public String getMandates() {
        return Mandates;
    }

    public void setMandates(String mandates) {
        Mandates = mandates;
    }

    public String getDateJoining() {
        return DateJoining;
    }

    public void setDateJoining(String dateJoining) {
        DateJoining = dateJoining;
    }

    public String getDateLeaving() {
        return DateLeaving;
    }

    public void setDateLeaving(String dateLeaving) {
        DateLeaving = dateLeaving;
    }

    public String getCitizenship() {
        return Citizenship;
    }

    public void setCitizenship(String citizenship) {
        Citizenship = citizenship;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getDateOfDeath() {
        return DateOfDeath;
    }

    public void setDateOfDeath(String dateOfDeath) {
        DateOfDeath = dateOfDeath;
    }

    @Override
    public String toString() {
        return "ParliamentMember{" +
                "Active='" + Active + '\'' +
                ", FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", GenderAsString='" + GenderAsString + '\'' +
                ", CantonName='" + CantonName + '\'' +
                ", CantonAbbreviation='" + CantonAbbreviation + '\'' +
                ", CouncilName='" + CouncilName + '\'' +
                ", ParlGroupName='" + ParlGroupName + '\'' +
                ", ParlGroupAbbreviation='" + ParlGroupAbbreviation + '\'' +
                ", PartyName='" + PartyName + '\'' +
                ", PartyAbbreviation='" + PartyAbbreviation + '\'' +
                ", MaritalStatusText='" + MaritalStatusText + '\'' +
                ", BirthPlace_City='" + BirthPlace_City + '\'' +
                ", BirthPlace_Canton='" + BirthPlace_Canton + '\'' +
                ", Mandates='" + Mandates + '\'' +
                ", DateJoining='" + DateJoining + '\'' +
                ", DateLeaving='" + DateLeaving + '\'' +
                ", Citizenship='" + Citizenship + '\'' +
                ", DateOfBirth='" + DateOfBirth + '\'' +
                ", DateOfDeath='" + DateOfDeath + '\'' +
                '}';
    }
}
