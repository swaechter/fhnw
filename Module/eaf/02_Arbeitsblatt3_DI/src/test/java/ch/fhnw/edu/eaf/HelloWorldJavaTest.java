package ch.fhnw.edu.eaf;

import ch.fhnw.edu.eaf.app.BeanConfiguration;
import ch.fhnw.edu.eaf.app.domain.MessageProvider;
import ch.fhnw.edu.eaf.app.domain.MessageRenderer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = BeanConfiguration.class)
public class HelloWorldJavaTest {

    @Autowired
    private MessageProvider messageProvider;

    @Autowired
    private MessageRenderer messageRenderer;

    @Test
    public void testGetMessage() {
        assertEquals("Herzlich willkommen!", messageProvider.getMessage());
    }

    @Test
    public void testGetMessageProvider() {
        assertNotNull(messageRenderer.getMessageProvider());
    }
}
