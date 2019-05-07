/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Icesi University (Cali - Colombia)
 * Faculty of Engineering (algorithms and programming 2)
 * laboratory 4
 * By: Carlos Andrés Restrepo Marín 
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
 */
package userInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import org.omg.CORBA.Current;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.AirportScreen;
import model.Flight;
import threads.AirportScreenThread;


public class ScreenController {
	
	
	/**
	 * options of the sorting criteria
	 */
	@FXML
    private ComboBox<String> criteria;
    /**
     * options of the search criteria
     */
    @FXML
    private ComboBox<String> search;
    
    /**
     * where the user types the search key
     */
    @FXML
    private TextField searchText;
    
    /**
     * where the user writes the size of the list he wants to generate
     */
    @FXML
    private TextField sizeList;
    
    /**
     * this is the table that represents the flights list
     */
    @FXML
    private TableView<Flight> listFlights;
    /**
     * button that allows sorting the flights according a criteria
     */
    @FXML
    private Button sortingButton;
    
    /**
     * button that allows search a flight according a criteria
     */
    @FXML
    private Button searchButton;
    
    /**
     * label used to warn of errors in the sorting option
     */
    @FXML
    private Label labelSorting;

    /**
     * this is the main node
     */
    @FXML
    private BorderPane panel;
    
    /**
     * label used to warn of errors in the generated list option
     */
    @FXML
    private Label labelGenerate;
    
    /**
     * button that allows you to observe the next page of the flight list
     */
    @FXML
    private Button nextButton;
    /**
     * button that allows you to see the previous page of the flight list
     */
    @FXML
    private Button backButton;
    
    /**
     * label used to warn of errors in the search option
     */
    @FXML
    private Label labelSearch;

    /**
     * relationship with the main class of the model
     */
    
    private AirportScreen screen;
    
    /**
     * attribute used to know the paging of the flight list
     */
    private Flight lastFlight;
    
    /**
     * attribute used to know the paging of the flight list
     */
    private Flight previousFlight;
    
    /**
     * stage used to show the flights found in the search option
     */
    private Stage stage;
    
   
    /**
     * this method performs actions when the application starts, such as assigning the columns to the flight table
     */
	@FXML
    public void initialize() {
		
		searchButton.setDisable(true);
		sortingButton.setDisable(true);
		nextButton.setDisable(true);
		backButton.setDisable(true);
		
    	screen = new AirportScreen();
    	listFlights = new TableView<Flight>();
    	generateColumns(listFlights);
    	panel.setCenter(listFlights);
    	
    	criteria.getItems().addAll("Default (Date and departure time)", "Airline", "Flight number", "Destination", "Gate");
    	search.getItems().addAll("Date", "Departure time", "Airline", "Flight Number", "Destination", "Boarding Gate");
    	
    }
	
	/**
	 * this method is responsible for creating the columns that will be assigned to the flight tables
	 * @param tb, the table to which the columns are added
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void generateColumns(TableView<Flight> tb){
		TableColumn<Flight, String> colDate = new TableColumn<Flight, String>("Date");
    	colDate.setCellValueFactory(new PropertyValueFactory<Flight, String>("date"));
    	colDate.setPrefWidth(colDate.getPrefWidth()+70);
    
    	
    	TableColumn<Flight, String> colTime = new TableColumn<Flight, String>("Time");
    	colTime.setCellValueFactory(new PropertyValueFactory<Flight, String>("departureTime"));
    	colTime.setPrefWidth(colTime.getPrefWidth()+70);
    
    	TableColumn<Flight, String> colAirline = new TableColumn<Flight, String>("Airline");
    	colAirline.setCellValueFactory(new PropertyValueFactory<Flight, String>("airline"));
    	colAirline.setPrefWidth(colAirline.getPrefWidth()+150);
    	
    	TableColumn<Flight, String> colFlight = new TableColumn<Flight, String>("Flight number");
    	colFlight.setCellValueFactory(new PropertyValueFactory<Flight, String>("flightNumber"));
    	colFlight.setPrefWidth(colFlight.getPrefWidth()+150);
    	
    	TableColumn<Flight, String> colDestination = new TableColumn<Flight, String>("To");
    	colDestination.setCellValueFactory(new PropertyValueFactory<Flight, String>("destination"));
    	colDestination.setPrefWidth(colDestination.getPrefWidth() + 150);
    	
    	TableColumn<Flight, String> colGate = new TableColumn<Flight, String>("Gate");
    	colGate.setCellValueFactory(new PropertyValueFactory<Flight, String>("boardingGate"));
    	colGate.setPrefWidth(colGate.getPrefWidth()+70);
    	
    	
    	tb.getColumns().addAll(colDate, colTime, colAirline,  colFlight, colDestination, colGate);
    	listFlights.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}
	
	/**
	 * this method is responsible for ordering the list of flights when the user has chosen the criteria
	 * @param event, the click on the button
	 */
    @FXML
    void sort(ActionEvent event) {
    	
    	long time = System.currentTimeMillis();
    	if(criteria.getValue() == null) {
    		labelSorting.setText("Choose an option, please");
    		labelSorting.setVisible(true);
    	}else {
	    	String value = criteria.getValue();
	    	
	    	if(value.equalsIgnoreCase("gate")) {
	    		screen.sortingByBoardingGate();
	    	}else if(value.equalsIgnoreCase("airline")) {
	    		screen.sortingByAirline();
	    	}else if(value.equalsIgnoreCase("flight number")) {
	    		screen.sortingByFlightNumber();
	    	}
	    	int size = Integer.parseInt(sizeList.getText());
	    	List<Flight> lflights = new ArrayList<Flight>();
	    	showFlights(size);
    	}	
    	time = System.currentTimeMillis()-time;
    //	alertTime(time/1000);
    	
    }
    
   /**
    * this method is responsible for generated the random list when the user has entered a size
    * @param event, the click on the button
    */
	@FXML
    void generateList(ActionEvent event) {
		
		labelSearch.setText("enter each value as you see in the table");
		
		if(sizeList.getText().isEmpty()) {
			labelGenerate.setText("Enter a value");
		}else {
			
			try {
		    	int size = Integer.parseInt(sizeList.getText());
		    	
		    	searchButton.setDisable(false);
				sortingButton.setDisable(false);
		    	try {
					screen.generateList(size);
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
		    	screen.sortingByDateAndTime();
		    	showFlights(size);
			}catch(NumberFormatException e) {
				labelGenerate.setText("Enter a integer number");
			}
		}
    	
    }
	
	public void showFlights(int size) {
		ObservableList<Flight> flights = FXCollections.observableArrayList();
    	
    	if(size > 13) {
    		Flight current = screen.getFirstFlight();
    		flights.add(current);
    		nextButton.setDisable(false);
    		for (int i = 1; i < 13; i++) {
    			current = current.getNextFlight();
    			flights.add(current);
			}
    		lastFlight = current;
    		previousFlight = screen.getFirstFlight();
    	}else {
    		Flight current = screen.getFirstFlight();
    		nextButton.setDisable(true);
    		for (int i = 0; i < size; i++) {
    			flights.add(current);
    			current = current.getNextFlight();
			}
    	}

    	listFlights.setItems(flights);
	}
    
    /**
     * this method allows see the previous page of the flights list
     * @param event, the click on the button
     */
    @FXML
    void previousList(ActionEvent event) {
    	
    	nextButton.setDisable(false);
    	
    	lastFlight = previousFlight;
    	Flight current = previousFlight;
    	int i = 0;
    	while( i<12 && current.getPrevFlight() != null) {
    		current = current.getPrevFlight();
    		i++;
    	}
    	previousFlight = current;
    	
    	ObservableList<Flight> lf = FXCollections.observableArrayList();
    	lf.add(previousFlight);
    	
    	current = previousFlight;
    	 i= 1;
    	while(current.getNextFlight() != null && i<13) {
    		current = current.getNextFlight();
    		lf.add(current);
    		i++;
    	}
    	
    	
    	listFlights.setItems(lf);
    	
    	if(previousFlight == screen.getFirstFlight()) {
    		backButton.setDisable(true);
    	}
    	
    }
    /**
     * this method allows see the next page of the flights list
     * @param event, the click on the button
     */
    @FXML
    void nextList(ActionEvent event) {
    	
    	backButton.setDisable(false);
    	
    	ObservableList<Flight> lf = FXCollections.observableArrayList();
    	int counter = 0;
    	previousFlight = lastFlight;
    
    	Flight current = previousFlight;
    	int i = 0;
    	
    	while(current.getNextFlight()!= null && i < 13){
    		current = current.getNextFlight();
    		lf.add(current);
    		i++;
    	}
    	lastFlight = current;
    	
    	listFlights.setItems(lf);
    	

    	Flight current2 = screen.getFirstFlight();
    	
    	while(current2.getNextFlight()!= null){
    		current2 = current2.getNextFlight();
    	}
    
    	if(lastFlight == current2) {
    		nextButton.setDisable(true);
    	}
    	
    }
    
    
    /**
     * This method is responsible for finding and finding a flight according to a criterion entered 
     * by the user, if there is more than one flight with the same criteria, the first one found is shown
     * @param event, the click on the button
     */
    @FXML
    void searchFlight(ActionEvent event) {
    	/*
    	long time = System.currentTimeMillis();
    	if(search.getValue() == null) {
    		labelSearch.setText("choose an option, please");
    		labelSearch.setVisible(true);
    	}
    	else if(searchText.getText().isEmpty()) {
    		labelSearch.setText("Enter a value");
    		labelSearch.setVisible(true);
    	}else {
	    	
	    	ObservableList<Flight> lf;
	    	
	    	List<Flight> ls = new ArrayList<Flight>(screen.getFlights());
	    	String cr = search.getValue();
	    	try {
		    	if(cr.equalsIgnoreCase("Date")) {
		    		
		    		StringTokenizer st = new StringTokenizer(searchText.getText(),"-" );
		    		int year = Integer.parseInt(st.nextToken());
		    		int month = Integer.parseInt(st.nextToken());
		    		int day = Integer.parseInt(st.nextToken());
		    		lf = FXCollections.observableArrayList(screen.searchByDate(year, month, day));
		    		
		    	}else if(cr.equalsIgnoreCase("Departure time")) {
		    		lf = FXCollections.observableArrayList(screen.searchByTime(searchText.getText()));
		    	}else if(cr.equalsIgnoreCase("Airline")) {
		    		lf = FXCollections.observableArrayList(screen.searchByAirline(searchText.getText()));
		    		
		    	}else if(cr.equalsIgnoreCase("Destination")) {
		    		lf = FXCollections.observableArrayList(screen.searchByDestination(searchText.getText()));
		    	}else if(cr.equalsIgnoreCase("Boarding Gate")) {
		    		lf = FXCollections.observableArrayList(screen.searchByGate(Integer.parseInt(searchText.getText())));
		    	}else if(cr.equalsIgnoreCase("Flight Number")) {
		    		lf = FXCollections.observableArrayList(screen.searchByFlightNumber(searchText.getText()));
		    	}
		    	else {
		    		lf = null;
		    		
		    	}
		    	
		    	screen.setFlights(ls);
		    	
		    	
		    	Stage sta = new Stage();
		    	
		    	sta.initModality(Modality.APPLICATION_MODAL);
		    	sta.initOwner(stage);
		    	BorderPane pan = new BorderPane();
		    	Scene scene = new Scene(pan, 1000, 500);
		    	sta.setScene(scene);
		    	TableView<Flight> tw = new TableView<Flight>();
		    	generateColumns(tw);
		    	
		    	tw.setCenterShape(true);
		    	tw.setMaxSize(600, 200);
		    	tw.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		    	
		    	pan.setCenter(tw);
		    	pan.getCenter().setLayoutY(pan.getCenter().getLayoutY()-500);
		    	if(lf.get(0) == null) {
		    		labelSearch.setText("Flight not found");
		    	}else {
			    	tw.setItems(lf);
			    	sta.show();
			    	time = System.currentTimeMillis()-time;
			    	alertTime(time/100);
		    	}
		    	
		    }catch(NumberFormatException  | NoSuchElementException  | ArrayIndexOutOfBoundsException | NullPointerException e) {
	    		labelSearch.setText("enter the value correctly");
	    	}
    	}
    	*/
    }
    
    /**
     * this method allows show the time of execution of the a method
     * @param time, the time of the execution
     
    public void alertTime(long time) {
    	Alert ale = new Alert(AlertType.INFORMATION);
    	ale.setTitle("Time of excecution");
    	ale.setHeaderText(null);
    	ale.initStyle(StageStyle.UTILITY);
    	ale.setContentText("The time of excecution was: " + time +" " + "seconds");
    	ale.show();
    }
	
    /**
     * this method allows obtain the other stage, used for show the flight found in the search option 
     * @param stage, the other stage
     
     */
	public void setStage(Stage stage) {
		this.stage = stage;
	}



	

}
