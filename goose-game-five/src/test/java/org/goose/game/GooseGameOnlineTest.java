package org.goose.game;

import net.sf.corn.httpclient.HttpForm;
import net.sf.corn.httpclient.HttpResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class GooseGameOnlineTest {
    private ApplicationServer server = new ApplicationServer(4567, new GooseGameWrapperServlet());

    @Before
    public void setUp() throws Exception {
        server.start();
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    /**
     * /players/add     name=pippo
     * /players/{name}/rolls    dice1=5   dice2=4       (opzionali)
     */
    @Test
    public void testAddPlayer() throws URISyntaxException, IOException {
        HttpForm form = new HttpForm(new URI("http://localhost:4567/players/add"));
        form.putFieldValue("name", "Pippo");
        HttpResponse response = form.doPost();

        assertThat(response.getData(), is("players: Pippo"));
    }

    @Test
    public void testRollsPippo() throws URISyntaxException, IOException {
        HttpForm form = new HttpForm(new URI("http://localhost:4567/players/add"));
        form.putFieldValue("name", "Pippo");
        HttpResponse response = form.doPost();

        form = new HttpForm(new URI("http://localhost:4567/players/Pippo/rolls"));
        form.putFieldValue("dice1", "5");
        form.putFieldValue("dice2", "4");
        response = form.doPost();

        assertThat(response.getData(), is("Pippo rools 5, 4. Pippo moves from Start to 9"));
    }

    @Test
    public void testRollsPluto() throws URISyntaxException, IOException {
        HttpForm form = new HttpForm(new URI("http://localhost:4567/players/add"));
        form.putFieldValue("name", "Pluto");
        HttpResponse response = form.doPost();

        form = new HttpForm(new URI("http://localhost:4567/players/Pluto/rolls"));
        form.putFieldValue("dice1", "6");
        form.putFieldValue("dice2", "2");
        response = form.doPost();

        assertThat(response.getData(), is("Pluto rools 6, 2. Pluto moves from Start to 8"));
    }

    @Test
    public void testRollsPlutoAndPippoAndRool() throws URISyntaxException, IOException {
        HttpForm form = new HttpForm(new URI("http://localhost:4567/players/add"));
        form.putFieldValue("name", "Pluto");
        HttpResponse response = form.doPost();
        form.putFieldValue("name", "Pippo");
        response = form.doPost();

        form = new HttpForm(new URI("http://localhost:4567/players/Pluto/rolls"));
        form.putFieldValue("dice1", "6");
        form.putFieldValue("dice2", "2");
        response = form.doPost();
        assertThat(response.getData(), is("Pluto rools 6, 2. Pluto moves from Start to 8"));

        form = new HttpForm(new URI("http://localhost:4567/players/Pippo/rolls"));
        form.putFieldValue("dice1", "5");
        form.putFieldValue("dice2", "4");
        response = form.doPost();
        assertThat(response.getData(), is("Pippo rools 5, 4. Pippo moves from Start to 9"));
    }

    @Test
    public void testRollsPlutoAndPippo() throws URISyntaxException, IOException {
        HttpForm form = new HttpForm(new URI("http://localhost:4567/players/add"));
        form.putFieldValue("name", "Pippo");
        HttpResponse response = form.doPost();
        form.putFieldValue("name", "Pluto");
        response = form.doPost();
        assertThat(response.getData(), is("players: Pippo, Pluto"));
    }
}