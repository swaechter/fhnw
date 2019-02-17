package ch.fhnw.edu.eaf.springioc;

import ch.fhnw.edu.eaf.springioc.renderer.MessageRenderer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringIocApplication.class})
public class SpringIocApplicationTests {

    @Test
    public void contextLoads() {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"config.xml"});

        MessageRenderer messageRenderer1 = (MessageRenderer) context.getBean("renderer1");
        Assert.assertEquals("Gruezi!", messageRenderer1.render());

        MessageRenderer messageRenderer2 = (MessageRenderer) context.getBean("renderer2");
        Assert.assertEquals("Hello world!", messageRenderer2.render());

        MessageRenderer messageRenderer3 = (MessageRenderer) context.getBean("renderer3");
        Assert.assertEquals("Herzlich willkommen von den Properties!", messageRenderer3.render());
    }
}
