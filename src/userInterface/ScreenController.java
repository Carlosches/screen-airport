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
    private int lastFlight;
    
    /**
     * attribute used to know the paging of the flight list
     */
    private int previousFlight;
    
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
	 
			
	    	AirportScreenThread tt = new AirportScreenThread(screen, value, 0);
	    	tt.start();
	    	try {
				tt.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    	List<Flight> lflights = new ArrayList<Flight>();
	    	if(screen.getFlights().size() > 13) {
	    		previousFlight = 13;
	    		previousList(null);
	    	
	    	}else {
	    		for (int i = 0; i < screen.getFlights().size(); i++) {
					lflights.add(screen.getFlights().get(i));
				}
	    		ObservableList<Flight> fls = FXCollections.observableArrayList(lflights);
	        	listFlights.setItems(fls);
	
	    	}
    	}	
    	time = System.currentTimeMillis()-time;
    	alertTime(time/1000);
    	
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
		
		    	ObservableList<Flight> flights = FXCollections.observableArrayList();
		    	
		    	for (int i = 0; i < screen.getFlights().size(); i++) {
					flights.add(screen.getFlights().get(i));
				}
		    
		   
		
		    	screen.getFlights().clear();
		    	
		    	AirportScreenThread t = new AirportScreenThread(screen, "generate list", size);
		    	
		    	
		    	t.start();
		    	try {
					t.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		    	List<Flight> lflights = new ArrayList<Flight>();
		    	if(size > 13) {
		    		nextButton.setDisable(false);
		    		for (int i = 0; i < 13; i++) {
						lflights.add(screen.getFlights().get(i));
					}
		    		lastFlight = 13;
		    	}else {
		    		nextButton.setDisable(true);
		    		for (int i = 0; i < size; i++) {
						lflights.add(screen.getFlights().get(i));
					}
		    	}
	
		    	ObservableList<Flight> fls = FXCollections.observableArrayList(lflights);
		    	listFlights.setItems(fls);
			}catch(NumberFormatException e) {
				labelGenerate.setText("Enter a integer number");
			}
		}
    	
    }
    
    /**
     * this method allows see the previous page of the flights list
     * @param event, the click on the button
     */
    @FXML
    void previousList(ActionEvent event) {
    	nextButton.setDisable(false);
    	List<Flight> fli = new ArrayList<Flight>();
    	
    	lastFlight = previousFlight;
    	previousFlight =  previousFlight-13;
 
    	for (int i = previousFlight; i < previousFlight+13 && i<screen.getFlights().size(); i++) {
    			fli.add(screen.getFlights().get(i));
    			
		}
    
    	ObservableList<Flight> lf = FXCollections.observableArrayList(fli);
    	listFlights.setItems(lf);
    	
    	if(previousFlight == 0) {
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
    	List<Flight> fli = new ArrayList<Flight>();
    	
    	int counter = 0;
    	previousFlight = lastFlight;
    	for (int i = previousFlight; i < previousFlight+13 && i<screen.getFlights().size(); i++) {
    			fli.add(screen.getFlights().get(i));
    			counter++;
		}
    	
    	lastFlight += counter;
    	ObservableList<Flight> lf = FXCollections.observableArrayList(fli);
    	listFlights.setItems(lf);
    	
    	if(lastFlight == screen.getFlights().size()) {
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
    	
    }
    
    /**
     * this method allows show the time of execution of the a method
     * @param time, the time of the execution
     */
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
