import java.io.IOException;

import com.it.ibm.watson.config.ImageRecognitionConfig;
import com.it.ibm.watson.visualrecframework.WatsonBinaryClassifier;

public class DeleteInstance {

	public static final String label = "helicopter";
	public static final String classifierID = "helicopter_classifier_1689966978";

	public static void main(String[] args) throws IOException {

	

		// Sezione di delete
		 WatsonBinaryClassifier classifier = new WatsonBinaryClassifier(ImageRecognitionConfig.api_key);
		 classifier.setClassifierId(classifierID);
		 classifier.setLabel(label);
		
		 classifier.deleteModel();
	}

}
