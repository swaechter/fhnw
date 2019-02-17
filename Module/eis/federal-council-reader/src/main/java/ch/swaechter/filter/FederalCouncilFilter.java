package ch.swaechter.filter;

import ch.swaechter.data.FederalCouncilMember;
import ch.swaechter.data.ParliamentMember;
import ch.swaechter.data.Person;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FederalCouncilFilter {

    private static final String[] HEADER_KEYS = new String[]{"FirstName", "LastName", "GenderAsString", "CantonName", "PartyName", "DateNationalCouncilJoining", "DateNationalCouncilLeaving", "DateStateCouncilJoining", "DateStateCouncilLeaving", "DateFederalCouncilJoining", "DateFederalCouncilLeaving"};

    public List<ParliamentMember> readParliamentMembersFromFile(String filePath) throws Exception {
        try (
                Reader reader = Files.newBufferedReader(Paths.get(filePath));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())
        ) {
            List<ParliamentMember> parliamentMembers = new ArrayList<>();
            for (CSVRecord csvRecord : csvParser) {
                ParliamentMember parliamentMember = new ParliamentMember();
                parliamentMember.setActive(csvRecord.get(0));
                parliamentMember.setFirstName(csvRecord.get(1));
                parliamentMember.setLastName(csvRecord.get(2));
                parliamentMember.setGenderAsString(csvRecord.get(3));
                parliamentMember.setCantonName(csvRecord.get(4));
                parliamentMember.setCantonAbbreviation(csvRecord.get(5));
                parliamentMember.setCouncilName(csvRecord.get(6));
                parliamentMember.setParlGroupName(csvRecord.get(7));
                parliamentMember.setParlGroupAbbreviation(csvRecord.get(8));
                parliamentMember.setPartyName(csvRecord.get(9));
                parliamentMember.setPartyAbbreviation(csvRecord.get(10));
                parliamentMember.setMaritalStatusText(csvRecord.get(11));
                parliamentMember.setBirthPlace_City(csvRecord.get(12));
                parliamentMember.setBirthPlace_Canton(csvRecord.get(13));
                parliamentMember.setMandates(csvRecord.get(14));
                parliamentMember.setDateJoining(csvRecord.get(15));
                parliamentMember.setDateLeaving(csvRecord.get(16));
                parliamentMember.setCitizenship(csvRecord.get(17));
                parliamentMember.setDateOfBirth(csvRecord.get(18));
                parliamentMember.setDateOfDeath(csvRecord.get(19));
                parliamentMembers.add(parliamentMember);
            }
            return parliamentMembers;
        }
    }

    public void writeFederalCouncilMembersToCsvFile(String filePath, List<FederalCouncilMember> federalCouncilMembers) throws Exception {
        try (
                BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath));
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(HEADER_KEYS))
        ) {
            for (FederalCouncilMember federalCouncilMember : federalCouncilMembers) {
                List<String> data = new ArrayList<>();
                data.add(federalCouncilMember.getFirstName());
                data.add(federalCouncilMember.getLastName());
                data.add(federalCouncilMember.getGenderAsString());
                data.add(federalCouncilMember.getCantonName());
                data.add(federalCouncilMember.getPartyName());
                data.add(federalCouncilMember.getDateNationalCouncilJoining());
                data.add(federalCouncilMember.getDateNationalCouncilLeaving());
                data.add(federalCouncilMember.getDateStateCouncilJoining());
                data.add(federalCouncilMember.getDateStateCouncilLeaving());
                data.add(federalCouncilMember.getDateFederalCouncilJoining());
                data.add(federalCouncilMember.getDateFederalCouncilLeaving());
                csvPrinter.printRecord(data);
            }
            csvPrinter.flush();
        }
    }

    public List<FederalCouncilMember> getAllFederalCouncilMembers(List<ParliamentMember> parliamentMembers) {
        List<Person> parliamentPeople = getPeopleListFromParliamenetMembers(parliamentMembers);
        List<FederalCouncilMember> federalCouncilMembers = getFederalCouncilMembersFromPeopleList(parliamentPeople);
        sortFederalCouncilMembers(federalCouncilMembers);
        return federalCouncilMembers;
    }

    private List<Person> getPeopleListFromParliamenetMembers(List<ParliamentMember> parliamentMembers) {
        List<Person> people = new ArrayList<>();

        for (ParliamentMember parliamentMember : parliamentMembers) {
            // Get the first and last name
            String firstName = parliamentMember.getFirstName();
            String lastName = parliamentMember.getLastName();

            // Find the people entry for the current parliament member
            Person person = people.stream().filter(p -> p.getFirstName().equalsIgnoreCase(firstName) && p.getLastName().equalsIgnoreCase(lastName)).findFirst().orElse(null);

            // If no person was found, add a new one
            if (person == null) {
                person = new Person(firstName, lastName);
                people.add(person);
            }

            // Add the new parliament member to the person
            person.getParliamentMembers().add(parliamentMember);

            // Check if the parliament member is a member of the federal council
            if ("Bundesrat".equalsIgnoreCase(parliamentMember.getCouncilName())) {
                person.setFederalCouncil(true);
            }
        }
        return people;
    }

    private List<FederalCouncilMember> getFederalCouncilMembersFromPeopleList(List<Person> people) {
        List<FederalCouncilMember> federalCouncilMembers = new ArrayList<>();
        for (Person person : people) {
            if (person.isFederalCouncil()) {
                federalCouncilMembers.add(getFederalCouncilMember(person));
            }
        }
        return federalCouncilMembers;
    }

    private FederalCouncilMember getFederalCouncilMember(Person person) {
        // Sort the parliament member events by join date
        List<ParliamentMember> parliamentMembers = person.getParliamentMembers();
        sortParliamentMembersByDate(parliamentMembers);

        // Create the federal council and set the names
        FederalCouncilMember federalCouncilMember = new FederalCouncilMember();
        federalCouncilMember.setFirstName(person.getFirstName());
        federalCouncilMember.setLastName(person.getLastName());

        // Iterate over the parliamenet member events
        for (ParliamentMember parliamentMember : parliamentMembers) {
            if ("Nationalrat".equalsIgnoreCase(parliamentMember.getCouncilName())) {
                // Set the join date if not already set or outdated
                if (federalCouncilMember.getDateNationalCouncilJoining() == null || isDateBefore(parliamentMember.getDateJoining(), federalCouncilMember.getDateNationalCouncilJoining())) {
                    federalCouncilMember.setDateNationalCouncilJoining(parliamentMember.getDateJoining());
                }

                // Set the leave date if not already set or outdated
                if (federalCouncilMember.getDateNationalCouncilLeaving() == null || isDateAfter(parliamentMember.getDateLeaving(), federalCouncilMember.getDateNationalCouncilLeaving())) {
                    federalCouncilMember.setDateNationalCouncilLeaving(parliamentMember.getDateLeaving());
                }
            } else if ("Ständerat".equalsIgnoreCase(parliamentMember.getCouncilName())) {
                // Set the join date if not already set or outdated
                if (federalCouncilMember.getDateStateCouncilJoining() == null || isDateBefore(parliamentMember.getDateJoining(), federalCouncilMember.getDateStateCouncilJoining())) {
                    federalCouncilMember.setDateStateCouncilJoining(parliamentMember.getDateJoining());
                }

                // Set the leave date if not already set or outdated
                if (federalCouncilMember.getDateStateCouncilLeaving() == null || isDateAfter(parliamentMember.getDateLeaving(), federalCouncilMember.getDateStateCouncilLeaving())) {
                    federalCouncilMember.setDateStateCouncilLeaving(parliamentMember.getDateLeaving());
                }
            } else if ("Bundesrat".equalsIgnoreCase(parliamentMember.getCouncilName())) {
                // Set the join date if not already set or outdated
                if (federalCouncilMember.getDateFederalCouncilJoining() == null || isDateBefore(parliamentMember.getDateJoining(), federalCouncilMember.getDateFederalCouncilJoining())) {
                    federalCouncilMember.setDateFederalCouncilJoining(parliamentMember.getDateJoining());
                }

                // Set the leave date if not already set or outdated
                if (federalCouncilMember.getDateFederalCouncilLeaving() == null || isDateAfter(parliamentMember.getDateLeaving(), federalCouncilMember.getDateFederalCouncilLeaving())) {
                    federalCouncilMember.setDateFederalCouncilLeaving(parliamentMember.getDateLeaving());
                }
            }

            // Update the gender (I guess this never happened and also won't happen that soon.....)
            federalCouncilMember.setGenderAsString(parliamentMember.getGenderAsString());

            // Always use the latest canton/party
            federalCouncilMember.setCantonName(parliamentMember.getCantonName());
            federalCouncilMember.setPartyName(parliamentMember.getPartyName());
        }
        return federalCouncilMember;
    }

    private void sortFederalCouncilMembers(List<FederalCouncilMember> federalCouncilMembers) {
        federalCouncilMembers.sort((firstMember, secondMember) -> {
            String firstFullName = firstMember.getFirstName() + " " + firstMember.getLastName();
            String secondFullname = secondMember.getFirstName() + " " + secondMember.getLastName();
            return firstFullName.compareTo(secondFullname);
        });
    }

    private void sortParliamentMembersByDate(List<ParliamentMember> parliamentMembers) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/d/yyyy");
        parliamentMembers.sort((firstMember, secondMember) -> {
            try {
                Date firstDate = simpleDateFormat.parse(firstMember.getDateJoining());
                Date secondDate = simpleDateFormat.parse(secondMember.getDateJoining());
                return firstDate.compareTo(secondDate);
            } catch (Exception exception) {
                throw new RuntimeException("Invalid data: " + exception.getMessage(), exception);
            }
        });
    }

    private boolean isDateBefore(String firstDate, String secondDate) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return simpleDateFormat.parse(firstDate).before(simpleDateFormat.parse(secondDate));
        } catch (ParseException exception) {
            return false;
        }
    }

    private boolean isDateAfter(String firstDate, String secondDate) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return simpleDateFormat.parse(firstDate).after(simpleDateFormat.parse(secondDate));
        } catch (ParseException exception) {
            return false;
        }
    }
}
