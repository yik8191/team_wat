package matachi.mapeditor.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import matachi.mapeditor.grid.Camera;
import matachi.mapeditor.grid.Grid;
import matachi.mapeditor.grid.GridCamera;
import matachi.mapeditor.grid.GridModel;
import matachi.mapeditor.grid.GridView;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
//import org.jdom.Attribute;
//import org.jdom.Document;
//import org.jdom.Element;
//import org.jdom.input.SAXBuilder;
//import org.jdom.output.Format;
//import org.jdom.output.XMLOutputter;

/**
 * Controller of the application.
 *
 * @author Daniel "MaTachi" Jonsson
 * @version 1
 * @since v0.0.5
 *
 */
public class Controller implements ActionListener, GUIInformation {

	/**
	 * The model of the map editor.
	 */
	private Grid model;

	private Tile selectedTile;
	private Camera camera;

	private List<Tile> tiles;

	private GridView grid;
	private View view;

	private int gridWith = Constants.MAP_WIDTH;
	private int gridHeight = Constants.MAP_HEIGHT;

	/**
	 * Construct the controller.
	 */
	public Controller() {
		init(Constants.MAP_WIDTH, Constants.MAP_HEIGHT);

	}

	public void init(int width, int height) {
		this.tiles = TileManager.getTilesFromFolder("data/");
		this.model = new GridModel(width, height, tiles.get(0).getCharacter());
		this.camera = new GridCamera(model, Constants.GRID_WIDTH,
				Constants.GRID_HEIGHT);

		grid = new GridView(this, camera, tiles); // Every tile is
													// 30x30 pixels

		this.view = new View(this, camera, grid, tiles);
	}

	/**
	 * Different commands that comes from the view.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		for (Tile t : tiles) {
			if (e.getActionCommand().equals(
					Character.toString(t.getCharacter()))) {
				selectedTile = t;
				break;
			}
		}
		if (e.getActionCommand().equals("flipGrid")) {
			// view.flipGrid();
		} else if (e.getActionCommand().equals("save")) {
			saveFile();
		} else if (e.getActionCommand().equals("load")) {
			loadFile();
		} else if (e.getActionCommand().equals("update")) {
			updateGrid(gridWith, gridHeight);
		}
	}

	public void updateGrid(int width, int height) {
		view.close();
		init(width, height);
		view.setSize(width, height);
	}

	DocumentListener updateSizeFields = new DocumentListener() {

		public void changedUpdate(DocumentEvent e) {
		}

		public void removeUpdate(DocumentEvent e) {
			gridWith = view.getWidth();
			gridHeight = view.getHeight();
		}

		public void insertUpdate(DocumentEvent e) {
			gridWith = view.getWidth();
			gridHeight = view.getHeight();
		}
	};

	// OLD
	private void saveTEXTFile() {
		do {
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"Text/Java files", "txt", "java");
			chooser.setFileFilter(filter);
			int returnVal = chooser.showSaveDialog(null);
			try {
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					PrintStream p;
					p = new PrintStream(chooser.getSelectedFile());
					p.print(model.getMapAsString());
					break;
				} else {
					break;
				}
			} catch (FileNotFoundException e1) {
				JOptionPane.showMessageDialog(null, "Invalid file!", "error",
						JOptionPane.ERROR_MESSAGE);
			}
		} while (true);
	}

	private void saveFile() {

		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"json files", "json");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showSaveDialog(null);
		try {
			if (returnVal == JFileChooser.APPROVE_OPTION) {

                JSONObject level = new JSONObject();

                int height = model.getHeight();
                int width = model.getWidth();

                level.put("width", width);
                level.put("height", height);

                JSONArray tiles = new JSONArray();
                JSONArray objects = new JSONArray();

                boolean hasStart = false;
                boolean hasGoal  = false;

				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						char tileChar = model.getTile(x,y);
						String tileType = "";
                        String object = "";

						if (tileChar == '1')      //start
							tileType = "start";
						else if (tileChar == '2') //goal
							tileType = "goal";
						else if (tileChar == '3') //obstacle
							tileType = "obstacle";
						else if (tileChar == '4') //platform
							object = "platform";
						else if (tileChar == '5') //slime
							object = "slime";
						else if (tileChar == '6') //skeleton
							object = "skeleton";


                        JSONObject curObj = new JSONObject();

                        if (!tileType.equals("")) {
                            curObj.put("x", x);
                            curObj.put("y", y);
                            curObj.put("type", tileType);
                            if (tileType.equals("start") && !hasStart) {
                                hasStart = true;
                                JSONObject player = new JSONObject();
                                player.put("x", x);
                                player.put("y", y);
                                level.put("player", player);
                            } else if (tileType.equals("start") && hasStart) {
                                System.out.println("Error saving file! You can only have one start tile");
                                return;
                            }

                            if (tileType.equals("goal") && !hasGoal) {
                                hasGoal = true;
                            } else if (tileType.equals("goal") && hasGoal) {
                                System.out.println("Error saving file! You can only have one goal tile");
                                return;
                            }
                            tiles.add(curObj);
                        } else if (!object.equals("")){
                            curObj.put("type", object);
                            curObj.put("x", x);
                            curObj.put("y", y);
                            //TODO: Correctly implement this so objects have paths
                            curObj.put("path", "NNNN");

                            objects.add(curObj);
                        }
					}
                }

                if (!hasGoal){
                    System.out.println("Error saving! You need to have a goal!");
                    return;
                }
                if (!hasStart){
                    System.out.println("Error saving! You need to have a start!");
                    return;
                }

                level.put("tiles", tiles);
                level.put("objects", objects);

                //finished adding objects, now to save the file
                FileWriter file = new FileWriter(chooser.getSelectedFile());
                file.write(level.toJSONString());
                file.flush();
                file.close();
			}
		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(null, "Invalid file!", "error",
					JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {

		}
	}

	public void loadFile() {
		try {
			JFileChooser chooser = new JFileChooser();
			File selectedFile;
			BufferedReader in;
			FileReader reader = null;

			int returnVal = chooser.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				selectedFile = chooser.getSelectedFile();
				if (selectedFile.canRead() && selectedFile.exists()) {

                    JSONParser parser = new JSONParser();
                    Object o;
                    try {
                        FileReader f = new FileReader(selectedFile.getAbsoluteFile());
                        o = parser.parse(f);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        o = null;
                        System.out.println("File not found!");
                    } catch (ParseException e) {
                        e.printStackTrace();
                        o = null;
                        System.out.println("ParseException");
                    } catch (IOException e) {
                        e.printStackTrace();
                        o = null;
                        System.out.println("IOException");
                    }
                    JSONObject level = (JSONObject) o;//new JSONObject(filePath);

                    int height = ((Long) level.get("height")).intValue();
                    int width  = ((Long) level.get("width")).intValue();

                    updateGrid(width, height);

                    //initialize all tiles to be empty
                    for (int y=0; y < height; y++){
                        for (int x=0; x < width; x++){
                            model.setTile(x, y, '0');
                        }
                    }

                    //populate the tile types
                    JSONArray tiles = (JSONArray) level.get("tiles");
                    if (tiles != null){
                        Iterator<JSONObject> iterator = tiles.iterator();
                        int i = 0;
                        while (iterator.hasNext()) {

                            JSONObject curTile = iterator.next();
                            i += 1;

                            //get coordinates of tile
                            int x = ((Long) curTile.get("x")).intValue();
                            int y = ((Long) curTile.get("y")).intValue();

                            char tileNr;

                            String s = (String) curTile.get("type");
                             //find the type of this tile
                            if (s.equals("obstacle")) {
                                tileNr = '3';
                            } else if (s.equals("start")) {
                                tileNr = '1';
                            } else if (s.equals("goal")) {
                                tileNr = '2';
                            } else if (s.equals("normal")) {
                                tileNr = '0';
                            } else {
                                tileNr = '0';
                            }

                            //got everything, set the tile
                            model.setTile(x, y, tileNr);
                            grid.redrawGrid();
                        }
                    }

                    //now to populate objects (enemies/dynamic tiles)
                    JSONArray objects = (JSONArray) level.get("objects");
                    if (objects != null) {
                        for (int j = 1; j < objects.size()+1; j++) {
                            JSONObject curObj = (JSONObject) objects.get(j-1);
                            //get where enemy starts
                            int x = ((Long) curObj.get("x")).intValue();
                            int y = ((Long) curObj.get("y")).intValue();

                            char tileNr;

                            //get path for object

                            //TODO: add path parsing once Austin finishes
//                            String pathString = ((String) curObj.get("path")).toUpperCase();
//                            int[] path = new int[pathString.length()];
//                            for (int i=0; i<pathString.length(); i++){
//                                char c = pathString.charAt(i);
//                                switch (c){
//                                    case 'U': path[i] = InputController.CONTROL_MOVE_UP;    break;
//                                    case 'D': path[i] = InputController.CONTROL_MOVE_DOWN;  break;
//                                    case 'L': path[i] = InputController.CONTROL_MOVE_LEFT;  break;
//                                    case 'R': path[i] = InputController.CONTROL_MOVE_RIGHT; break;
//                                    case 'N': path[i] = InputController.CONTROL_NO_ACTION;  break;
//                                    case 'S': path[i] = InputController.CONTROL_SHOOT;      break;
//                                    default: System.out.println("unrecognized character '"+c+"'");
//                                }
//                            }

                            String type = (String) curObj.get("type");
                            if (type.equals("skeleton")){
                                tileNr = '6';
                            } else if (type.equals("slime")){
                                tileNr = '5';
                            } else if (type.equals("platform")){
                                tileNr = '4';
                            } else {
                                System.out.println("Invalid type '"+type+"' of object #"+(j+1));
                                tileNr = '0';
                            }

                            model.setTile(x, y, tileNr);
                            grid.redrawGrid();
                        }
                    }

				}
			}
		} catch (Exception e) {
		}
	}

	// OLD
	private void loadTEXTFile() {
		try {
			JFileChooser chooser = new JFileChooser();
			File selectedFile;
			BufferedReader in;
			FileReader reader = null;

			int returnVal = chooser.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				selectedFile = chooser.getSelectedFile();
				if (selectedFile.canRead() && selectedFile.exists()) {
					reader = new FileReader(selectedFile);
				}
			}

			in = new BufferedReader(reader);
			String line;
			int y = 0;
			while ((line = in.readLine()) != null) {
				for (int x = 0; x < line.length(); x++) {
					char c = line.charAt(x);
					model.setTile(x, y, c);
				}
				y++;
			}
			in.close();
			grid.redrawGrid();
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}

		/**
		 * //catches input/output exceptions and all subclasses exceptions
		 * catch(IOException ex) { jTxtAMain.append("Error Processing File:\n" +
		 * ex.getMessage()+"\n"); } //catches nullpointer exception, file not
		 * found catch(NullPointerException ex) {
		 * jTxtAMain.append("Open File Cancelled:\n" + ex.getMessage()+"\n"); }
		 */
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Tile getSelectedTile() {
		return selectedTile;
	}
}
