package controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gluonhq.charm.glisten.control.ProgressBar;

import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.AppointmentData;
import models.MakeHTML;
import models.MakeStaffURLS;
import models.ProgressBarModel;
import models.ReadScheduleData;
import models.RunPython;
import models.Staff;
import models.StaffTableData;


/** Controls the main application screen */
public class MainViewController {
  
  @FXML private Button startButton;  
  
  @FXML private MenuBar fileMenuBar;
  @FXML private ScrollPane scrollPane;
  @FXML private DatePicker startDate;
  @FXML private DatePicker endDate;
  @FXML private ProgressBar progressBar;
  public static ProgressBarModel model = new ProgressBarModel();
  
  //table variables
  @FXML private TableView<StaffTableData> staffTable;

  private Property<StaffTableData> staffProperty;
  private ChangeListener<StaffTableData> staffListener;
  private ArrayList<Staff> staffBooked = new ArrayList<Staff>();
  //set up the scene
	  // (1.0F = 100%)
	  // (0.25 = 25%) etc.
  public void setProgress(ProgressBarModel model){
      progressBar.progressProperty().bind(ProgressBarModel.workToDoProperty().divide(100d));
  }
  
  public void start(final LoginManager loginManager) {
	  try {
		populateTableView();
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	  startButton.setOnAction((e) -> {
		  List<LocalDate> dates = datePickers();
		  MakeStaffURLS makeURL = new MakeStaffURLS();
		  Map<String,Staff> staffMap = makeURL.Generate(staffBooked, dates);
		  		  
		  System.out.println("READING SCHEDULE\n\n\n");
		  ReadScheduleData readSchedule = new ReadScheduleData();
		  try {
			List<AppointmentData> data = readSchedule.readData(staffMap);
			System.out.println("SENDING EMAIL\n\n\n");
			MakeHTML email = new MakeHTML();
			String emailBodyFile = email.makeFile(dates.get(0).toString(), data);
			RunPython python = new RunPython();
			python.runPython(emailBodyFile);
			
			
		} catch (FailingHttpStatusCodeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		  
	}); // end button
  } // end start
         	  //file chooser

  
  //About menu item on menu bar
  @FXML
  public void aboutHelp(ActionEvent event) {
	  // select file from the menu bar
	  Alert alert = new Alert(AlertType.INFORMATION,
 	           "MRI EMAILER TOOL\n\n\n"
 	         + "Generate an Email for the car service\n "
 	         + " CREATED BY TIM TRUTY\n"
 	         + " Summer 2019 \n"
 	         + " RUSH ALZHEIMER'S DISEASE CENTER\n"
 	         + " CHICAGO IL\n"
 	         + "\n"
 	         + "\n"
 	         + "\n"
 	         + "CURRENT BUILD IS BETA:\n"
 	         + "Functionality missing\n"
 	         + "- Editing staff list\n"
 	         + "- - - if staff additions are needed manual "
 	         + "nediting of staff.txt in dir is needed\n"
 	         + "- No couple checking to combine travel\n"
 	         + "- - - do this manually on outlook email\n"
 	         + "");


 	 alert.setTitle("About info");
 	 alert.showAndWait();
  } //end aboutmenu
  
  
  @FXML
  public void closeApp(ActionEvent event) {
	  //select close on the menu bar
	  Platform.exit();
	  System.exit(0);
	  } //end closeApp

  public void populateTableView() throws IOException {
	  staffTable.getItems().clear();
	  staffTable.refresh();
	  
	  ObservableList<StaffTableData> staffList = null;
	  staffList = FXCollections.observableArrayList();
	  
	  File file = new File("resources/staff.txt"); 
	  
	  BufferedReader br = new BufferedReader(new FileReader(file)); 
	  
	  //table
	  TableColumn<StaffTableData, String> idCol = new TableColumn("ID");
      idCol.setMinWidth(30);
      idCol.setMaxWidth(50);
      idCol.setCellValueFactory(new PropertyValueFactory<StaffTableData, String>("staffIdProperty"));

      TableColumn<StaffTableData, String> nameCol = new TableColumn("Name");
      nameCol.setCellValueFactory(new PropertyValueFactory<StaffTableData, String>("staffNameProperty"));

      TableColumn<StaffTableData, String> numCol = new TableColumn("Number");
      numCol.setCellValueFactory(new PropertyValueFactory<StaffTableData, String>("staffNumberProperty"));

      TableColumn<StaffTableData, String> emailCol = new TableColumn("Email");
      emailCol.setCellValueFactory(new PropertyValueFactory<StaffTableData, String>("staffEmailProperty"));

      staffTable.getColumns().addAll(idCol, nameCol, numCol, emailCol);
      staffTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	  
	  String st; 
	  while ((st = br.readLine()) != null) 
	  {
	    //System.out.println(st); 
	  	String[] dataLine = st.split(",");
	  	int count = 0;
	  	for (String line : dataLine) {
	  		String[] staffData = line.split(":");
	  		Staff staff = new Staff(staffData[1], staffData[0], staffData[2], staffData[3]);
	  		staffBooked.add(staff);
//	  		System.out.println(staff.toString());
	  		staffList.add(new StaffTableData(staff.getID(), staff.getFirstName(), staff.getPhoneNumber(), staff.getEmail()));
	  		count++;
	  	}	  	
	  }
	  staffTable.setItems(staffList);
	  //staffTable.setItems(staffList);
	  
	  
	  
	  staffTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
		    if (newValue != null) {
		        System.out.println("Selected Staff: "
		            + newValue.getStaffIdProperty() + " | "
		            + newValue.getStaffNameProperty()
		        );
		   }
		});
  }

  public List<LocalDate> datePickers() {
	  List<LocalDate> dates = new ArrayList<LocalDate>();
	  if (startDate.getValue() == null || endDate.getValue() == null 
			  || endDate.getValue().isBefore(startDate.getValue()) //validate date picker
			  || startDate.getValue().isAfter(endDate.getValue()))
	  {
		  Alert alert = new Alert(AlertType.WARNING,
	    	         "Please Select a \n"
	    	         + "START and END date \n ");	
	    	 alert.setTitle("Date Picking Error");
	    	 alert.showAndWait();
	    	 return null;
	  }
	  else {
		  LocalDate start = startDate.getValue();
		  LocalDate end = endDate.getValue();
		  //System.out.println("Start = " + start.toString());
		  //System.out.println("End = " + end.toString());
		  
		  dates = datesBetween(start, end);
	//	  for (LocalDate i : dates) {
	//		  System.out.println(i.toString());	  
	//	  }
	  }
	  return dates;
  }
  
  private List<LocalDate> datesBetween(LocalDate start, LocalDate end){
	  List<LocalDate> totalDates = new ArrayList<>();
	  while (!start.isAfter(end)) {
	      totalDates.add(start);
	      start = start.plusDays(1);
	  }
	  return totalDates;
  }
  
  
}