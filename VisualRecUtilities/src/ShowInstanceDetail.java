import java.io.IOException;

import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.VisualClassifier;
import com.it.ibm.watson.config.ImageRecognitionConfig;

/**
 * Questa classe permette di recuperare velocemente informazioni relative ad un 
 * certa istanza Visual recognition esistente
 * 
 * @author Marco Dondio
 *
 */
public class ShowInstanceDetail {

	public static void main(String[] args) throws IOException {

		
		// Instantiate service
		VisualRecognition service = new VisualRecognition(VisualRecognition.VERSION_DATE_2016_05_20);
		service.setApiKey(ImageRecognitionConfig.api_key);

		// List all classifiers
		for(VisualClassifier vc : service.getClassifiers().execute())
			System.out.println(vc);

		


	
	}

}
