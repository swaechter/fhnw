package ch.fhnw.swa.osgi.firstbundle.client;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import ch.fhnw.swa.osgi.firstbundle.service.SimpleService;

public class Activator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		SimpleService simpleservice = new SimpleService();
		simpleservice.writeString("Client Version 2.0.0");		
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// Do nothing
	}
}
