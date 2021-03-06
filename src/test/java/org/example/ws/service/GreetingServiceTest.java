package org.example.ws.service;

import java.util.Collection;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;

import org.example.ws.AbstractTest;
import org.example.ws.model.Greeting;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

// E' meglio creare le classi dei test @Transactional perchè
// perchè ogni azione sul database eseguita da ogni @Test viene rollbeccata quando il test termina
@Transactional
public class GreetingServiceTest extends AbstractTest {

    @Autowired
    private GreetingService service;

    // Esegue della logica prima di ogni @Test della classe ( E' un Before Each
    // per intendersi )
    @Before
    public void setUp() {

        // serve perchè l'ordine di esecuzione dei test non è predicibile,
        // dato che c'è un sistema di caching attivo è meglio pulire ad ogni
        // test
        // perchè sia attendibile.
        service.evictCache();
    }

    // Esegue logica dopo ogni @Test
    @After
    public void tearDown() {
        // clean up after each test method
    }

    @Test
    public void testFindAll() {
        Collection<Greeting> list = service.findAll();
        Assert.assertNotNull("failure - expected not null", list);
        Assert.assertEquals("failure - expected size", 2, list.size());
    }

    @Test
    public void testFindOne() {
        Long id = new Long(1);

        Greeting entity = service.findOne(id);

        Assert.assertNotNull("failure - expected not null", entity);
        Assert.assertEquals("failure - expected id attribute match", id, entity.getId());
    }

    @Test
    public void testFindOneNotFound() {
        Long id = Long.MAX_VALUE;

        Greeting entity = service.findOne(id);

        Assert.assertNull("failure - expected null", entity);

    }

    @Test
    public void testCreate() {
        Greeting entity = new Greeting();
        String greetingText = "Buongiorno!!!";

        entity.setText(greetingText);

        Greeting createdEntity = service.create(entity);

        Assert.assertNotNull("failure - expected not null", createdEntity);
        Assert.assertNotNull("failure - expected id not null", createdEntity.getId());
        Assert.assertEquals("failure - expected match", greetingText, createdEntity.getText());

        Collection<Greeting> list = service.findAll();

        Assert.assertEquals("failure - expected size", 3, list.size());
    }

    @Test
    public void testCreateWithId() {
        Exception exception = null;

        Greeting entity = new Greeting();

        entity.setId(Long.MAX_VALUE);
        entity.setText("test");

        try {
            service.create(entity);
        } catch (Exception ex) {
            exception = ex;
        }

        Assert.assertNotNull("failure - expected exception", exception);
        Assert.assertTrue("failure - expected instance of EntityExistsException",
                exception instanceof EntityExistsException);
    }

    @Test
    public void testUpdate() {

        Long id = new Long(1);

        Greeting entity = service.findOne(id);

        Assert.assertNotNull("failure - expected not null", entity);

        String updatedText = "testttt";

        entity.setText(updatedText);

        Greeting updatedGreeting = service.update(entity);

        Assert.assertNotNull("failure - expected not null", updatedGreeting);
        Assert.assertEquals("failure - expected match", updatedText, updatedGreeting.getText());
        Assert.assertEquals("failure - expected greeting id not change", id, updatedGreeting.getId());
    }

    @Test
    public void testUpdateNotFound() {
        Exception exception = null;

        Long id = Long.MAX_VALUE;

        Greeting entity = new Greeting();
        entity.setId(id);
        entity.setText("test");

        try {
            service.update(entity);
        } catch (Exception ex) {
            exception = ex;
        }

        Assert.assertNotNull("failure - expected not null", exception);
        Assert.assertTrue("failure - expected exception to be instance of NoResultException",
                exception instanceof NoResultException);
    }

    @Test
    public void testDelete() {
        Long id = new Long(1);

        Greeting entity = service.findOne(id);

        Assert.assertNotNull("failure - expected not null", entity);

        service.delete(id);

        Collection<Greeting> list = service.findAll();

        Assert.assertEquals("failure - expected size", 1, list.size());

        Greeting deletedGreeting = service.findOne(id);

        Assert.assertNull("failure - expected null", deletedGreeting);
    }
}
