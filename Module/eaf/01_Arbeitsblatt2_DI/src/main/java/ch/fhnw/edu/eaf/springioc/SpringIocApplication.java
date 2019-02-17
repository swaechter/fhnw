package ch.fhnw.edu.eaf.springioc;

import ch.fhnw.edu.eaf.springioc.renderer.MessageRenderer;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class SpringIocApplication {

    public static void main(String[] args) {
        BeanFactory beanFactory = getBeanFactory();
        MessageRenderer messageRenderer = (MessageRenderer) beanFactory.getBean("renderer");
        messageRenderer.render();
    }

    public static BeanFactory getBeanFactory() {
        return new XmlBeanFactory(new ClassPathResource("/config.xml"));
    }
}
