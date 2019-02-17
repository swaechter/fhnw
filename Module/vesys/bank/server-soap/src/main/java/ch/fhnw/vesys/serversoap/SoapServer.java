package ch.fhnw.vesys.serversoap;

import javax.xml.ws.Endpoint;

class SoapServer {

    private SoapServer(int port) throws Exception {
        Endpoint.publish("http://localhost:" + port + "/bank", new BankServiceImpl());
    }

    public static void main(String[] args) throws Exception {
        new SoapServer(1234);
    }
}
