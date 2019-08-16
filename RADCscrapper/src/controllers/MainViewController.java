package controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.AppointmentData;
import models.MakeHTML;
import models.MakeStaffURLS;
import models.ReadScheduleData;
import models.RunPython;
import models.Staff;
import models.StaffTable;
import models.StaffTableData;


/** Controls the main application screen */
public class MainViewController {
  
  @FXML private Button startButton;  
  
  @FXML private MenuBar fileMenuBar;
  @FXML private ScrollPane scrollPane;
  @FXML private DatePicker startDate;
  @FXML private DatePicker endDate;
  @FXML private AnchorPane ringPane;
  
  //table variables
  @FXML private TableView<StaffTableData> staffTable;
  LoginManager login;
  
  //set up the scene  
  public void start(final LoginManager loginManager) {
	  
	  login = loginManager;
	  try {
		StaffTable.populateTableView(staffTable);
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	  startButton.setOnAction((e) -> {
		  List<LocalDate> dates = datePickers();
		  MakeStaffURLS makeURL = new MakeStaffURLS();
		  Map<String,Staff> staffMap = makeURL.Generate(StaffTable.staffBooked, dates);
		  		  
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
  
  @FXML
  public void staffEdit(ActionEvent event) {
	  //select edit staff on the menu bar
	  login.Edit();
	  } //end staffEdit


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