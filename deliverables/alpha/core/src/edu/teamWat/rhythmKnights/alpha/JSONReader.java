package edu.teamWat.rhythmKnights.alpha;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import edu.teamWat.rhythmKnights.alpha.controllers.*;
import edu.teamWat.rhythmKnights.alpha.models.*;
import edu.teamWat.rhythmKnights.alpha.models.gameObjects.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by Kylar on 4/1/2015.
 */
public class JSONReader {

    private static JSONObject level = null;


    public static Board parseFile(String string) {
        //get the stuff from the file
        JSONParser parser = new JSONParser();

        Object o;
        try {
			o = parser.parse(string);
		} catch (ParseException e) {
			o = null;
			e.printStackTrace();	
		}
//        try {
//            FileReader f = new FileReader(string);
//            o = parser.parse(f);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            o = null;
//            try {
//                o = parser.parse("{\n" +
//                        "    \"tiles\": [\n" +
//                        "\t{\"x\":4,\"y\":0,\"type\":\"obstacle\"},\n" +
//                        "\t{\"x\":11,\"y\":0,\"type\":\"goal\"},\n" +
//                        "\t{\"x\":2,\"y\":1,\"type\":\"obstacle\"},\n" +
//                        "\t{\"x\":5,\"y\":1,\"type\":\"obstacle\"},\n" +
//                        "\t{\"x\":9,\"y\":1,\"type\":\"obstacle\"},\n" +
//                        "\t{\"x\":2,\"y\":2,\"type\":\"obstacle\"},\n" +
//                        "\t{\"x\":6,\"y\":2,\"type\":\"obstacle\"},\n" +
//                        "\t{\"x\":10,\"y\":2,\"type\":\"obstacle\"},\n" +
//                        "\t{\"x\":2,\"y\":3,\"type\":\"obstacle\"},\n" +
//                        "\t{\"x\":7,\"y\":3,\"type\":\"obstacle\"},\n" +
//                        "\t{\"x\":11,\"y\":3,\"type\":\"obstacle\"},\n" +
//                        "\t{\"x\":2,\"y\":4,\"type\":\"obstacle\"},\n" +
//                        "\t{\"x\":8,\"y\":4,\"type\":\"obstacle\"},\n" +
//                        "\t{\"x\":0,\"y\":5,\"type\":\"start\"},\n" +
//                        "\t{\"x\":2,\"y\":5,\"type\":\"obstacle\"}\n" +
//                        "    ],\n" +
//                        "    \"objects\":[\n" +
//                        "\t{\"path\":\"UDLR\",\"x\":5,\"y\":0,\"type\":\"skeleton\"},\n" +
//                        "\t{\"path\":\"RUDL\",\"x\":1,\"y\":1,\"type\":\"slime\"},\n" +
//                        "\t{\"path\":\"UDLR\",\"x\":5,\"y\":2,\"type\":\"skeleton\"},\n" +
//                        "\t{\"path\":\"UDLR\",\"x\":7,\"y\":4,\"type\":\"skeleton\"},\n" +
//                        "\t{\"path\":\"RUDL\",\"x\":3,\"y\":5,\"type\":\"slime\"}\n" +
//                        "    ],\n" +
//                        "    \"width\":12,\n" +
//                        "    \"height\":6,\n" +
//                        "    \"player\":{\"x\":0,\"y\":5}\n" +
//                        "    \"ticker\": \"MMMM\"\n" +
//                        "}\n");
//            } catch (ParseException e1) {
//                e1.printStackTrace();
//            }
//            System.out.println("File not found!");
//        } catch (ParseException e) {
//            e.printStackTrace();
//            o = null;
//            System.out.println("ParseException");
//        } catch (IOException e) {
//            e.printStackTrace();
//            o = null;
//            System.out.println("IOException");
//        }
        level = (JSONObject)o;

        //get base info and create board
        //also look at this stupid conversion bullshit because you can't cast directly to int
        int w = ((Long)level.get("width")).intValue();
        int h = ((Long)level.get("height")).intValue();
        Board b = new Board(w, h);

        //populate the tiles of the board
        JSONArray tiles = (JSONArray)level.get("tiles");
        if (tiles != null) {
            Iterator<JSONObject> iterator = tiles.iterator();
            int i = 0;
            while (iterator.hasNext()) {
                JSONObject curTile = iterator.next();
                i += 1;
                //get coordinates of tile
                int x = ((Long)curTile.get("x")).intValue();
                int y = ((Long)curTile.get("y")).intValue();

                String s = (String)curTile.get("type");
                Board.Tile.tileType t;
                s = s.toLowerCase();
                //find the type of this tile
                if (s.equals("obstacle")) {
                    t = Board.Tile.tileType.OBSTACLE;
                } else if (s.equals("start")) {
                    t = Board.Tile.tileType.START;
                } else if (s.equals("goal")) {
                    t = Board.Tile.tileType.GOAL;
                } else if (s.equals("normal")) {
                    t = Board.Tile.tileType.NORMAL;
                } else {
                    System.out.println("Invalid type '" + s + "' for tile #" + (i - 1));
                    t = Board.Tile.tileType.NORMAL;
                }

                //got everything, set the tile
                b.setTile(x, y, t);
            }
        } else {
            System.out.println("No tiles found! that's bad! you at least need a start/goal tile!");
        }

        return b;
    }

    public static void getObjects() {
        //TODO: Add things to check for null objects
        JSONArray objects = (JSONArray)level.get("objects");
        GameObjectList gameObjects;
        InputController[] controls;
        //initialize with enough space to hold objects plus player
        gameObjects = new GameObjectList(objects.size() + 1);
        controls = new InputController[objects.size() + 1];

        JSONObject player = (JSONObject)level.get("player");
        int x = ((Long)player.get("x")).intValue();
        int y = ((Long)player.get("y")).intValue();
        gameObjects.add(new Knight(0, x, y));
        controls[0] = GameplayController.playerController;


        //populate the list of game objects (enemies/dynamic tiles)

        if (objects != null) {
            for (int j = 1; j < objects.size() + 1; j++) {
                JSONObject curObj = (JSONObject)objects.get(j - 1);
                //get where enemy starts
                x = ((Long)curObj.get("x")).intValue();
                y = ((Long)curObj.get("y")).intValue();

                //get path for object
                String pathString = ((String)curObj.get("path")).toUpperCase();
                int[] path = new int[pathString.length()];
                for (int i = 0; i < pathString.length(); i++) {
                    char c = pathString.charAt(i);
                    switch (c) {
                        case 'U':
                            path[i] = InputController.CONTROL_MOVE_UP;
                            break;
                        case 'D':
                            path[i] = InputController.CONTROL_MOVE_DOWN;
                            break;
                        case 'L':
                            path[i] = InputController.CONTROL_MOVE_LEFT;
                            break;
                        case 'R':
                            path[i] = InputController.CONTROL_MOVE_RIGHT;
                            break;
                        case 'N':
                            path[i] = InputController.CONTROL_NO_ACTION;
                            break;
                        case 'S':
                            path[i] = InputController.CONTROL_SHOOT;
                            break;
                        default:
                            System.out.println("unrecognized character '" + c + "'");
                    }
                }
                String type = (String)curObj.get("type");
                if (type.equals("skeleton")) {
                    gameObjects.add(new Enemy(j, x, y, Enemy.EnemyType.SKELETON));
                } else if (type.equals("slime")) {
                    gameObjects.add(new Enemy(j, x, y, Enemy.EnemyType.SLIME));
                } else if (type.equals("platform")) {
                    gameObjects.add(new DynamicTile(j, x, y));
                } else {
                    System.out.println("Invalid type '" + type + "' of object #" + (j + 1));
                }
                controls[j] = new EnemyController(j, gameObjects, path);
            }
        } else {
            System.out.println("No objects found! did you mean to do that?");
        }

        GameplayController.gameObjects = gameObjects;
        GameplayController.controls = controls;
        return;
    }

    public static Ticker initializeTicker() {

        String tickerActions = (String)level.get("ticker");
        Ticker.TickerAction[] actionArray = new Ticker.TickerAction[tickerActions.length()];
        for (int i = 0; i < tickerActions.length(); i++) {
            char c = tickerActions.charAt(i);
            switch (c) {
                case 'M':
                    actionArray[i] = Ticker.TickerAction.MOVE;
                    break;
                case 'D':
                    actionArray[i] = Ticker.TickerAction.DASH;
                    break;
                case 'F':
                    actionArray[i] = Ticker.TickerAction.FIREBALL;
                    break;
                case 'R':
                    actionArray[i] = Ticker.TickerAction.FREEZE;
                    break;
                default:
                    System.out.println("Unrecognized character '" + c + "' for ticker at location " + i + ", setting it to MOVE");
                    actionArray[i] = Ticker.TickerAction.MOVE;
                    break;
            }
        }
        return new Ticker(actionArray);
    }

    public static FileHandle getAudioHandle() {
        String audio = (String)level.get("audio");
        return Gdx.files.internal("music/" + audio);
    }

    public static int getBackground(){
        int n = ((Long)level.get("backNum")).intValue();
        return n;
    }


}