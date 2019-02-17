package ch.swaechter;

import ch.swaechter.data.FederalCouncilMember;
import ch.swaechter.data.ParliamentMember;
import ch.swaechter.filter.FederalCouncilFilter;

import java.util.List;

public class Launcher {

    public static void main(String[] args) throws Exception {
        if (args.length == 2) {
            FederalCouncilFilter federalCouncilFilter = new FederalCouncilFilter();

            List<ParliamentMember> parliamentMembers = federalCouncilFilter.readParliamentMembersFromFile(args[0]);
            System.out.println("Parliament members read: " + parliamentMembers.size());

            List<FederalCouncilMember> filteredFederalCouncilList = federalCouncilFilter.getAllFederalCouncilMembers(parliamentMembers);
            System.out.println("Federal council members read: " + filteredFederalCouncilList.size());

            federalCouncilFilter.writeFederalCouncilMembersToCsvFile(args[1], filteredFederalCouncilList);
        } else {
            System.out.println("Usage: java -jar federal-council-reader-1.0.0-jar-with-dependencies.jar <Input CSV> <Output CSV>");
            System.out.println("Example: java -jar federal-council-reader-1.0.0-jar-with-dependencies.jar Ratsmitglieder_1848_DE.csv Bundesraete_1848_DE.csv");
        }
    }
}
