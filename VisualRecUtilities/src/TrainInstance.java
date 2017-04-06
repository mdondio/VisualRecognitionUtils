import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.it.ibm.watson.config.ImageRecognitionConfig;
import com.it.ibm.watson.utils.Utils;
import com.it.ibm.watson.visualrecframework.WatsonBinaryClassifier;


// TODO cose da fare.. rifare tutta questa classe
// 1 - creare dataset con tutto quello che c'è nelle cartelle
// 2 - salvare anche il dataset come zip


/**
 * Classe che permette il training di nuovi classificatori
 * 
 * @author Marco Dondio
 *
 */
public class TrainInstance {

	public static final String BASEFOLDER = "visualrecframework/trainingset/";
	public static final String label = "helicopter";
	public static final String datasetName = "/helicopter_training_1900";


	public static void main(String[] args) throws IOException {

//		 Sezione di training
		 HashMap<Long, Boolean> trainingSet = loadSet(BASEFOLDER + label +
				 datasetName);
		
		 // // Prepare positive class for training set
		 byte[] positiveClassZip = buildZipStream(BASEFOLDER + label + datasetName + "/positive/", 
				 trainingSet, true);
		
		 // Prepare negative class for training set
		 byte[] negativeClassZip = buildZipStream(BASEFOLDER + label + datasetName + "/negative/", trainingSet, false);
		
		 // Train my classifier
		 new WatsonBinaryClassifier(ImageRecognitionConfig.api_key, label,
		 positiveClassZip, negativeClassZip);
	}

	private static HashMap<Long, Boolean> loadSet(String path) {

		HashMap<Long, Boolean> set = new LinkedHashMap<Long, Boolean>();

		// Load positive samples
		File dir = new File(path + "/positive");
		for (File img : dir.listFiles()) {
			long imageID = Long.parseUnsignedLong(img.getName().replaceAll(".jpg", ""));
			set.put(imageID, true);
		}

		// Load negative samples
		dir = new File(path + "/negative");
		for (File img : dir.listFiles()) {
			long imageID = Long.parseUnsignedLong(img.getName().replaceAll(".jpg", ""));
			set.put(imageID, false);
		}

		return set;
	}

	private static byte[] buildZipStream(String path, HashMap<Long, Boolean> trainingSet, boolean targetClass) throws IOException {

		// First, build a list containing all files indicated by this set
		List<File> files = new ArrayList<File>();

		for (long imageID : trainingSet.keySet()) {
			
			if(trainingSet.get(imageID) == targetClass)
			
			files.add(new File(path + Long.toUnsignedString(imageID) + ".jpg"));
			System.out.println(path + Long.toUnsignedString(imageID) + ".jpg");
		}

		return Utils.getCompressedStream(files);
	}

}
