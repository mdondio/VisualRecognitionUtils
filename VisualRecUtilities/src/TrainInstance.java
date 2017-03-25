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

	public static final int STOP_TRAINING = 300; // num of pos OR neg elems (total size = x2)
	public static final int CLASSIFYMAXIMAGES = 20; // max number of images per

	public static final String BASEFOLDER = "visualrecframework/dataset/";
	public static final String label = "mushroom";

	public static void main(String[] args) throws IOException {

//		 Sezione di training
		 HashMap<Long, Boolean> trainingSet = loadSet(BASEFOLDER + label +
		 "/training", STOP_TRAINING);
		
		 // // Prepare positive class for training set
		 byte[] positiveClassZip = buildZipStream(BASEFOLDER + label +
		 "/training/positive/", new
		 ArrayList<Long>(trainingSet.keySet()).subList(0, STOP_TRAINING));
		
		 // Prepare negative class for training set
		 byte[] negativeClassZip = buildZipStream(BASEFOLDER + label +
		 "/training/negative/", new
		 ArrayList<Long>(trainingSet.keySet()).subList(STOP_TRAINING,
		 trainingSet.size()));
		
		 // Train my classifier
		  new WatsonBinaryClassifier(ImageRecognitionConfig.api_key, label,
		 positiveClassZip, negativeClassZip);
	}

	private static HashMap<Long, Boolean> loadSet(String path, int stop) {

		HashMap<Long, Boolean> set = new LinkedHashMap<Long, Boolean>();

		// Load positive samples
		int i = 0;
		File dir = new File(path + "/positive");
		for (File img : dir.listFiles()) {
			long imageID = Long.parseUnsignedLong(img.getName().replaceAll(".jpg", ""));
			set.put(imageID, true);

			// System.out.println(i);
			if ((++i) >= stop)
				break;
		}

		// Load negative samples
		i = 0;
		dir = new File(path + "/negative");
		for (File img : dir.listFiles()) {
			long imageID = Long.parseUnsignedLong(img.getName().replaceAll(".jpg", ""));
			set.put(imageID, false);

			if ((++i) >= stop)
				break;
		}

		return set;
	}

	private static byte[] buildZipStream(String path, List<Long> imageList) throws IOException {

		// First, build a list containing all files indicated by this set

		List<File> files = new ArrayList<File>();

		for (long imageID : imageList) {
			files.add(new File(path + Long.toUnsignedString(imageID) + ".jpg"));
			System.out.println(path + Long.toUnsignedString(imageID) + ".jpg");
		}

		return Utils.getCompressedStream(files);
	}

}
