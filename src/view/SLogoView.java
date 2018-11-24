package view;

import commands.CommandInitializer;
import controller.Controller;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import model.CommandList;
import model.Loader;
import model.Saver;
import model.VariableMap;
import view.view_component.*;

import java.io.File;
import java.util.ResourceBundle;

/**
 * SLogoView
 *
 * main UI class that connects all elements
 *
 * @author duytrieu
 * @author brookekeene
 */
public class SLogoView extends HBox implements SLogoViewAPI {
    private static final double FRAMES_PER_SECOND = 1;
    private static final double MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 100.0/ FRAMES_PER_SECOND;
    private static final String RESOURCE_PACKAGE = "/text/view";

    private Timeline animation = new Timeline();
    private LogoScreen logoScreen;
    private DropDownButtons dropDownButtons;
    private ScriptEditor scriptView;
    private Console consoleView;
    private ResourceBundle myResources;
    private Controller myController;
    private CommandList myHistory;
    private int numOfTurtle = 1;
    private VariableMap myVariables;
    private CommandInitializer myCommands;

    public SLogoView() {
        myController = new Controller(this);
        sceneInit();
    }
    public Controller getMyController () {
        return myController;
    }

    private void sceneInit() {
        myResources = ResourceBundle.getBundle(RESOURCE_PACKAGE);
        logoScreen = new LogoScreen(Color.WHITE, myController, numOfTurtle);
        myHistory = new CommandList(myController);
        myVariables = myController.getVariableSupplier();
        myCommands = myController.getInitializerSupplier();
        initVariable();
        VBox scriptView = addScriptView();
        VBox logoView = addLogoView();
        BorderPane myBP = new BorderPane();
        myBP.setPadding(new Insets(Integer.parseInt(myResources.getString("Padding"))));
        myBP.setLeft(addButton());
        myBP.setRight(scriptView);
        myBP.setCenter(logoView);
        this.getChildren().add(myBP);
    }
    private void initVariable () {
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                e -> this.step());
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
    }
    private void step() {
        logoScreen.updateTurtle();
        dropDownButtons.editCurrentState();
    }
    private VBox addButton () {
        dropDownButtons = new DropDownButtons(logoScreen, myController);
        dropDownButtons.makeTabs();
        VBox buttonPane = new VBox();
        ScrollPane sp = new ScrollPane();
        int width = Integer.parseInt(myResources.getString("Scroller_Width"));
        int height = Integer.parseInt(myResources.getString("Dropdown_Height"));
        sp.setMaxSize(width, height);
        sp.setContent(dropDownButtons);
        buttonPane.getChildren().add(sp);
        return buttonPane;
    }
    private VBox addScriptView () {
        VBox scriptViewBox = new VBox();
        HBox buttonBox = new HBox();
        scriptView = new ScriptEditor();
        consoleView = new Console();
        buttonBox.getChildren().add(new LogoButton(myResources.getString("Run"), event -> runScript()));
        buttonBox.getChildren().add(new LogoButton(myResources.getString("Clear"), event -> clearScript()));
        buttonBox.getChildren().add(new LogoButton(myResources.getString("Load"), event -> loadScript()));
        buttonBox.getChildren().add(new LogoButton(myResources.getString("Save"), event -> saveScript()));
        scriptViewBox.getChildren().add(scriptView);
        scriptViewBox.getChildren().add(buttonBox);
        scriptViewBox.getChildren().add(consoleView);
        buttonBox.setAlignment(Pos.BOTTOM_CENTER);
        return scriptViewBox;
    }
    private VBox addLogoView () {
        VBox logoBox = new VBox();
        HBox buttonBox = new HBox();
        buttonBox.getChildren().add(new LogoButton(myResources.getString("Play"), event -> startButtonHandler()));
        buttonBox.getChildren().add(new LogoButton(myResources.getString("Stop"), event -> stopButtonHandler()));
        buttonBox.getChildren().add(new LogoButton(myResources.getString("Step"), event -> stepButtonHandler()));
        buttonBox.getChildren().add(new LogoButton(myResources.getString("Add"), event -> addTurtle()));
        HBox allignmentBox = new HBox();
        allignmentBox.getChildren().add(buttonBox);
        TurtleButtonControl turtleButtonControl = new TurtleButtonControl(myController);
        allignmentBox.getChildren().add(turtleButtonControl);
        logoBox.getChildren().addAll(logoScreen, allignmentBox);
        buttonBox.setAlignment(Pos.TOP_LEFT);
        turtleButtonControl.setAlignment(Pos.BASELINE_RIGHT);
        return logoBox;
    }

    public void clearDisplay() {
        logoScreen.clear();
    }

    public void showMessage(String text) {
        consoleView.addText(new Text(text));
    }

    private void startButtonHandler () {
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.playFromStart();
    }

    private void stopButtonHandler () {
        animation.pause();
    }
    private void stepButtonHandler () {
        stopButtonHandler();
        logoScreen.updateTurtle();
    }
    private void addTurtle () {
        numOfTurtle++;
        clearDisplay();
    }

    /**
     * processes the user input
     */
    private void runScript() {
        String command = scriptView.getUserInput();
        myController.setParseConsumer(command);
        myHistory.addCommand(command);
        updateGUI(command);
    }

    private void updateGUI(String command) {
        dropDownButtons.editPaletteTab();
        dropDownButtons.editHistoryTab(command);
        dropDownButtons.editVariableTab();
        dropDownButtons.editUserCommandTab();
        myController.getMessageConsumer(myController.setOutputSupplier());
        logoScreen.clear();
    }

    /**
     * clears the user input
     */
    private void clearScript() {
        consoleView.getConsole().getChildren().clear();
        scriptView.clearEditor();
    }
    private void loadScript () {
        myVariables = myController.getVariableSupplier();
        myCommands = myController.getInitializerSupplier();
        Loader loader = new Loader(myVariables, myCommands, chooseFile());
        loader.load();

    }
    private void saveScript () {
        myVariables = myController.getVariableSupplier();
        myCommands = myController.getInitializerSupplier();
        Saver saver = new Saver(myVariables, myCommands, chooseFile());
        saver.save();
    }

    private File chooseFile() {
        FileChooser myFileChooser = new FileChooser();
        myFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt"));
        myFileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        File chosen = null;
        while(chosen == null){
            chosen = myFileChooser.showOpenDialog(getScene().getWindow());
        }
        return chosen;
    }
}
