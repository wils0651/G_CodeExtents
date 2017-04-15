import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

//import MyTunesGUIPanel.SongFileButtonListener;

//This is a test change

/**
 * Class that scan GCode and finds the extents of the x, y, and z locations 
 * 
 * @author Tim Wilson
 *
 */

public class GCodeExtents {

	public static final int ERROR_CODE = 1;
	private static final double MAX = 0.0;
	//private static final double MIN = 123456.7;
	private static int numLines = 0;
	File addedFile;

	public static void main(String[] args) {

		double xMax = MAX;
		double xMin = Integer.MAX_VALUE;
		double yMax = MAX;
		double yMin = Integer.MAX_VALUE;
		double zMax = MAX;
		double zMin = Integer.MAX_VALUE;


		//		Scanner kbd = new Scanner(System.in);
		//		
		//		System.out.print("Enter a filename: ");
		//		String filename = kbd.nextLine().trim(); //trim removes extra whitespace
		//		kbd.close(); // we are done reading from user.

		String filename = "EandC.ngc";

		//create a File object
		//Note - this will not throw an Exception if the file doesn't exist, 
		//because you may be wanting to create a new file!

		File theFile = new File(filename);

		//open the file in a Scanner
		//Note - opening a File has the potential to fail (the file might not exist, you might not have permission
		// to read the file, etc.) so Java requires you to deal with the FileNotFoundException that could be thrown
		// if you can't open the File for any reason. We are demonstrating the try-catch way of handling Exceptions
		// here, because it allows you to print a helpful message before exiting rather than letting the program crash.

		try {
			Scanner fileScan = new Scanner(theFile);

			System.out.print("\nCapital first letters:  ");

			//read in each line of the file with fileScan until we run out of lines
			//Note - if our intent is to read whole lines, it is important that we use the hasNextLine() and nextLine()
			//methods rather than the hasNext() and next() methods, // which give us only one token (word) at a time - 
			//mixing these kinds of methods with a single Scanner leads to trouble

			while (fileScan.hasNextLine()) {
				//read one line
				String line = fileScan.nextLine();
				numLines++;

				//create an additional Scanner to break the current line into individual tokens (words) 
				//separated by whitespace

				Scanner lineScan = new Scanner(line);

				//read each token from the line until we run out of tokens

				while (lineScan.hasNext()) {
					//read the next token/word
					String token = lineScan.next();

					//print the token/word to the console
					//System.out.println(token);

					char ch = token.charAt(0);

					double xLoc = 0;
					if(ch == 'X'){
						String xLocStr = "";
						for(int i=1; i<token.length(); i++)
						{
							xLocStr += token.charAt(i);

						}
						xLoc = Double.valueOf(xLocStr);
						System.out.println("X Value: " + xLoc);
					}

					if(xMax < xLoc)
					{
						xMax = xLoc;
					} 
					if(xMin > xLoc)
					{
						xMin = xLoc;
					}

					double yLoc = 0;
					if(ch == 'Y'){
						String yLocStr = "";
						for(int i=1; i<token.length(); i++)
						{
							yLocStr += token.charAt(i);

						}
						yLoc = Double.valueOf(yLocStr);
						System.out.println("Y Value: " + yLoc);
					}

					if(yMax < yLoc)
					{
						yMax = yLoc;
					} 
					if(yMin > yLoc)
					{
						yMin = yLoc;
					}

					double zLoc = 0;
					if(ch == 'Z'){
						String zLocStr = "";
						for(int i=1; i<token.length(); i++)
						{
							zLocStr += token.charAt(i);

						}
						zLoc = Double.valueOf(zLocStr);
						System.out.println("Z Value: " + zLoc);
					}

					if(zMax < zLoc)
					{
						zMax = zLoc;
					} 
					if(zMin > zLoc)
					{
						zMin = zLoc;
					}

				}

				// We are done reading the line, so close the scanner.
				lineScan.close();
			}
			// We are done reading the file, so close the scanner.
			fileScan.close();
			System.out.println();
			System.out.println("xMax: " + xMax);
			System.out.println("xMin: " + xMin);
			System.out.println("yMax: " + yMax);
			System.out.println("yMin: " + yMin);
			System.out.println("zMax: " + zMax);
			System.out.println("zMin: " + zMin);

		} catch (FileNotFoundException errorObject) {

			//print a helpful message before exiting
			//Note - we only get into this block of code if a FileNotFoundException was thrown while trying to open
			// the file in fileScan, above

			System.out.println("File \"" + filename + "\" could not be opened.");
			System.out.println(errorObject.getMessage());
			System.exit(ERROR_CODE); // exit the program with an error status
		}
	}


	private void addFile(){
		System.out.println("Add Remove Listener");
		JPanel popup = new JPanel();
		popup.setLayout(new BoxLayout(popup, BoxLayout.Y_AXIS));
		//if(e.getSource() == addButton){

		JLabel info = new JLabel("Add a Song");
		info.setFont(new Font("Helvetica", Font.BOLD, 18));
		JTextField songTitleField = new JTextField(20);
		JTextField songArtistField = new JTextField(20);
		JTextField songTimeField = new JTextField(20);
		JButton songFileButton = new JButton("Select File");
		songFileButton.addActionListener(new SongFileButtonListener());
		popup.add(info);
		popup.add(new JLabel("Song Title"));
		popup.add(songTitleField);
		popup.add(new JLabel("Artist"));
		popup.add(songArtistField);
		popup.add(new JLabel("Song Time (seconds)"));
		popup.add(songTimeField);
		popup.add(new JLabel("Song File"));
		popup.add(songFileButton);
		int res = JOptionPane.showConfirmDialog(null, popup, "Add Song", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		//System.out.println("Input: " + res);

		if (res == 0) {

			//String title = songTitleField.getText();
			//String artist = songArtistField.getText();
			int playTime = 0;
			try
			{
				playTime = Integer.parseInt(songTimeField.getText());
				if(playTime < 0) {
					JOptionPane.showMessageDialog(null, "Song Time needs to be positive");
				} else {	//Check if file is .wav file
					String fileName = addedFile.getName().trim(); //from SongFileButtonListener
					String fileExtension = fileName.substring((fileName.length()-4), fileName.length());
					if(fileExtension.equals(".wav")) {

						String filePath = addedFile.getPath();
						// Edit file path to match other file paths
						//int truncLocation = filePath.indexOf("sounds");
						//String editedFilePath = filePath.substring(truncLocation, filePath.length());
						//Song theSong = new Song(title, artist, playTime, editedFilePath);
						//playList.addSong(theSong);
						//syncList();
						//int newSongIndex = playList.getNumSongs();
						//songJList.setSelectedIndex(newSongIndex-1);
						//updateLabelSongName();
						//System.out.println("Added song: " + theSong.getTitle());

					} else {
						JOptionPane.showMessageDialog(null, "File needs to be a .wav file");
						System.out.println("Song not added. Wrong File type");
					}
				}
			}
			catch (NumberFormatException exception)
			{
				JOptionPane.showMessageDialog(null, "Song Time needs to be a number");
			}

		} else if (res == 2) {
			System.out.println("Canceled Add");
		} else {
			System.err.println("Add Song Error");
		}

	}

	private class SongFileButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent event)
		{
			JFileChooser songFileChooser = new JFileChooser("."); //Open in current directory
			int status = songFileChooser.showOpenDialog(null);

			if (status != JFileChooser.APPROVE_OPTION) {
				System.out.println("No File Chosen");
			} else {
				addedFile = songFileChooser.getSelectedFile();
			}
		}
	}
}
