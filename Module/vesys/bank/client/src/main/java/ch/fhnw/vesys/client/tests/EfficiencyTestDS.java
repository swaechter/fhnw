package ch.fhnw.vesys.client.tests;

public class EfficiencyTestDS extends EfficiencyTest {

    final static int NUMBER_OF_EFF_TESTS = 1000;

    public EfficiencyTestDS() {
        super(NUMBER_OF_EFF_TESTS);
    }

    @Override
    public String getName() {
        return "Efficiency Test (DS)";
    }
}
