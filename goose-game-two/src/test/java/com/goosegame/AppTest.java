package com.goosegame;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AppTest {
    @Mock
    Request req;

    @Mock
    Response res;


    @Test
    public void name() {
        List<String> uuids = new ArrayList<String>();

        App app = new App();

        when(req.body()).thenReturn("{ \"name\": \"Paolo\"}");
        String out = app.createPlayer(req, res);
        System.out.println(out);
        uuids.add(out.substring(8, 44));

        when(req.body()).thenReturn("{ \"name\": \"Piero\"}");
        out = app.createPlayer(req, res);
        System.out.println(out);
        uuids.add(out.substring(8, 44));

        when(req.body()).thenReturn("{ \"name\": \"Ivo\"}");
        out = app.createPlayer(req, res);
        System.out.println(out);
        uuids.add(out.substring(8, 44));

        when(req.body()).thenReturn("{ \"name\": \"Manuela\"}");
        out = app.createPlayer(req, res);
        System.out.println(out);
        uuids.add(out.substring(8, 44));

        vittoria:
        while (true) {
            for (String uuid : uuids) {
                when(req.params(anyString())).thenReturn(uuid);
                String roll = app.roll(req, res);
                System.out.println(roll);
                if (roll.contains("63")) break vittoria;
            }
        }
    }
}