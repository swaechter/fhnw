package ch.fhnw.edu.eaf.springioc;

import ch.fhnw.edu.eaf.springioc.renderer.MessageRenderer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringIocApplication {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"config.xml"});

        MessageRenderer messageRenderer1 = (MessageRenderer) context.getBean("renderer1");
        messageRenderer1.render();

        MessageRenderer messageRenderer2 = (MessageRenderer) context.getBean("renderer2");
        messageRenderer2.render();

        MessageRenderer messageRenderer3 = (MessageRenderer) context.getBean("renderer3");
        messageRenderer3.render();
    }
}
