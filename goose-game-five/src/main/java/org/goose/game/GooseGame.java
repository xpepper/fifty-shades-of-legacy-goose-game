package org.goose.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GooseGame {
    public static final int finalCell = 63;
    private IDice dice;
    private List<Player> players = new ArrayList<Player>();
    private Map<String , Integer> posizioni = new HashMap<String, Integer>();

    public GooseGame() {
        this.dice = new RandomDice();
    }

    public GooseGame(IDice dice) {
        this.dice = dice;
    }

    public String add(String name) {
        return add(new Player(name));
    }

    private String add(Player player) {

        if (players.contains(player)) {
            return player.getName() + ": already exisiting player";
        }

        players.add(player);
        posizioni.put(player.getName() ,0);
        return getPlayerNames();
    }

    public String userWrite(String comandoUtente) {

        String[] comandi = comandoUtente.split(" |, ");

        if (comandi[0].equals("move")) {
            if (comandi.length == 2)
                comandi = lanciaDadi(comandi);
            String currentPlayer = comandi[1];
            int posizioneCorrente = posizioni.get(currentPlayer);


            int sum = calculateNewPosition(comandi, posizioneCorrente);
            posizioni.replace(currentPlayer,sum);
            String posizionePrecedente = Integer.toString(posizioneCorrente);
            posizionePrecedente = chkStartPosition(posizioneCorrente, posizionePrecedente);


            String output = currentPlayer + " rools " + comandi[2] + ", "+ comandi[3] + ". " + currentPlayer +" moves from " + posizionePrecedente;
            output = chkWinner(currentPlayer , sum, output);
            return output;
        }


        return "Comando Sconosiuto";
    }

    private String[] lanciaDadi(String[] comandi) {
        //define the new array
        String[] nuoviComandi = new String[comandi.length + 2];

        for(int i=0;i < comandi.length;i++)
            nuoviComandi[i] = comandi[i];

        dice.roll();
        nuoviComandi[nuoviComandi.length-2] = dice.getDice1()+"";
        nuoviComandi[nuoviComandi.length-1] = dice.getDice2()+"";

        return nuoviComandi;
    }

    private int calculateNewPosition(String[] comandi, int posizioneCorrente) {
        return posizioneCorrente + Integer.parseInt(comandi[2]) + Integer.parseInt(comandi[3]);
    }

    private String chkStartPosition(int posizioneCorrente, String posizionePrecedente) {
        if (posizioneCorrente == 0)
            posizionePrecedente = "Start";
        return posizionePrecedente;
    }

    private String chkWinner(String currentPlayer ,  int sum, String output) {
        String esito = "";
        if (sum == finalCell) {
            esito = ". " + currentPlayer + " Wins!!";
            return output + " to " + finalCell + esito;
        }
        else if (sum > finalCell){
            Integer overposition = sum - finalCell;
            sum = sum - overposition;

            esito = ". " + currentPlayer + " bounces! " + currentPlayer + " returns to " + (sum - overposition);
            return output + " to " + finalCell + esito;
        }
        return  output + " to " + sum;
    }

    private String getPlayerNames() {
        String listOfPlayers = null;
        for (Player player: players
        ) {
            if (listOfPlayers == null){
                listOfPlayers = "players: " + player.getName();
            }else {
                listOfPlayers += ", " + player.getName();
            }
        }
        return listOfPlayers;
    }
}
