/**
 * Skeleton for 'Borders.fxml' Controller Class
 */

package it.polito.tdp.borders;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class BordersController 
{

	Model model;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="txtAnno"
	private TextField txtAnno; // Value injected by FXMLLoader

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader
	


	@FXML
	void doCalcolaConfini(ActionEvent event) 
	{
		try
		{
			int anno = Integer.parseInt(txtAnno.getText());
			if (anno >= 1816 && anno <= 2016)
			{
				txtResult.setText(model.calcolaConfini(anno));
				btnTrovaVicini.setDisable(false);
			}
			else
				txtResult.setText("Inserire un anno compreso fra 1816 e 2016");
			
		}
		catch(NumberFormatException e)
		{
			txtResult.setText("ERRORE nell'anno");
		}
		
	}
	
	// Esercizio 2
	@FXML
    private Button btnTrovaVicini;
	
	@FXML
    private ComboBox<Country> cmbStato;
	
    @FXML
    void doTrovaVicini(ActionEvent event)
    {
    	Country country = cmbStato.getSelectionModel().getSelectedItem();
    	if (country != null)
    		txtResult.appendText("\n"+model.displayNeighbours(country));
    	else
    		txtResult.appendText("\nSelezionare uno stato dal combobox");
    }

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize()
	{
		assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Borders.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Borders.fxml'.";
	
		btnTrovaVicini.setDisable(true);
	}

	public void setModel(Model model) 
	{
		this.model = model;
		
		List<Country> countries = model.loadCountries();
		cmbStato.getItems().addAll(countries);
	}
}
