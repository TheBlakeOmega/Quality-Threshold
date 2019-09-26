package mining;

import data.Data;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * The Quality Threshold miner class.
 */
public class QTMiner implements Serializable {

	/**
	 * The clusterSet that will be computed.
	 */
	private ClusterSet C;
	
	/**
	 * The distance that a cluster can cover.
	 */
	private double radius;

	/**
	 * Public constructor.
	 * @param radius is the distance that a cluster can cover
	 */
	public QTMiner(double radius) {
		C = new ClusterSet();
		this.radius = radius;
	}

	/**
	 * Public constructor, it build a ClusterSet by reading it on a file.
	 * @param fileName is the file's identifier
	 * @throws FileNotFoundException when file doesn't exist
	 * @throws IOException for every error from IO
	 * @throws ClassNotFoundException when there are an error in cast between variable C and Object read by file
	 */
	public QTMiner(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {

		FileInputStream inf = new FileInputStream(fileName);
		ObjectInputStream in = new ObjectInputStream(inf);
		C = (ClusterSet) in.readObject();
		in.close();
	}

	/**
	 * Serialize the content of ClusterSet C in a file in File System.
	 * @param fileName identifier that will be given to file
	 * @throws FileNotFoundException if method doesn't find file
	 * @throws IOException for every error from IO
	 */
	public void salva(String fileName) throws FileNotFoundException, IOException {

		FileOutputStream outf = new FileOutputStream(fileName);
		ObjectOutputStream out = new ObjectOutputStream(outf);
		out.writeObject(C);
		out.close();
	}

	/**
	 * Return the Set of Clusters C.
	 * @return the Set of Clusters C
	 */
	public ClusterSet getC() {
		return C;
	}

	/**
	 * Computes data related to radius:
	 * 1)Builds a cluster for each tuple not yet clustered, including data(not yet clustered
	 * 	 in other clusters) that are in the spherical neighborhood of the tuple with radius in input.
	 * 2)Than saves the most populous cluster and removes all data of this cluster by List of tuple
	 *   not yet clustered.
	 * 3)Returns at phase 1. while there are still tuples to assign to a cluster.
	 * @param data is the table of tuples that will be computed
	 * @return the number of cluster
	 * @throws ClusteringRadiusException if compute made just one cluster
	 */
	public int compute(Data data) throws ClusteringRadiusException {
		int numclusters = 0;
		boolean isClustered[] = new boolean[data.getNumberOfExamples()];

		for (int i = 0; i < isClustered.length; i++) {
			isClustered[i] = false;
		}
		int countClustered = 0;

		while (countClustered != data.getNumberOfExamples()) {
			// Ricerca e salvataggio del cluster più popoloso
			Cluster c = buildCandidateCluster(data, isClustered);
			C.add(c);
			numclusters++;

			// Rimuovo tutti i punti di tale cluster dall'elenco delle tuple da clusterizzare

			for (Integer it : c) {
				isClustered[it] = true;
			}
			countClustered += c.getSize();
			// Ripete il while finchè ci sono ancora tuple da assegnare ad un cluster

		}
		return numclusters;
	}

	/**
	 * Builds a cluster for each tuple not yet clustered and establishes which one is the most populous.
	 * @param data is the table of tuples that will be clustered
	 * @param isClustered is a vector of boolean that models which tuple in data are clustered and which not
	 * @return the most populous cluster found
	 */
	public Cluster buildCandidateCluster(Data data, boolean isClustered[]) {
		Cluster cD = null; // cD cluster vuoto che conterrà il più popoloso
		for (int i = 0; i < isClustered.length; i++) {
			// inizializza il cluster candidato C con tutte le tuple che rientrano in radius
			Cluster C = new Cluster(data.getItemSet(i));
			if (!isClustered[i]) {
				for (int j = 0; j < isClustered.length; j++) {
					if (!isClustered[j]) {
						if (data.getItemSet(i).getDistance(data.getItemSet(j)) <= radius) {
							C.addData(j);
						}
					}
				}
				if (cD == null) {
					cD = C;
				} else {
					if (C.getSize() > cD.getSize()) {	// qui decido quale cluster tra cD e C è più popoloso
						cD = C;
					}
				}
			}
		}
		return cD;
	}

	/**
	 * Overrides Object's toString,
	 * a string with every centroid of the Set C.
	 * @return toString() of a ClusterSet
	 */
	public String toString() {
		return C.toString();
	}
}
