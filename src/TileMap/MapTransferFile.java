package TileMap;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class MapTransferFile {

	public static File crateFile(String name) {

		System.out.println();
		try {
			File dir = new File("Resources/Maps");
			dir.mkdirs();
			File myObj  = new File(dir,name + ".xml");
			if (myObj.createNewFile()) {
				System.out.println("File created: " + myObj.getName());
				
			} else {
				System.out.println("File already exists.");
			}
			return myObj;
			
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		return null;
	}

	public static void WriteFile(String name, String src) {
		File f=MapTransferFile.crateFile(name);
		boolean finish = false;
		String start="<Area value=\"";
		String end="\"/>\n";
		String linestart="<Line>\n";
		String lineend="</Line>\n";
		String map = "";
		String data;
		try {

			File myObj = new File(src + ".xml");
			Scanner myReader = new Scanner(myObj);

			while (myReader.hasNextLine() && !finish) {
				data = myReader.nextLine();
				if (data.equals("  <data encoding=\"csv\">")) {
					data = myReader.nextLine();
					while (!data.equals("</data>")) {
						if(data.charAt(data.length()-1)==',')
							data = data.substring(0, data.length() - 1);						
						map +=linestart+start+data+end+lineend;
						data = myReader.nextLine();
					}
					finish = true;
				}
			}
			
			map=map.replace(",", end+start);
			FileWriter myWriter = new FileWriter(f);
			myWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n<Map>\r\n" + map+"</Map>");

			myReader.close();
			myWriter.close();
			System.out.println("Successfully wrote to the file.");
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

}
