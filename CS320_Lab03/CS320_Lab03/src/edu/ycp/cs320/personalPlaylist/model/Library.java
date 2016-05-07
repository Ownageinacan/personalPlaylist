//THIS CLASS MIGHT BE USELESS BUT WE'RE KEEPING IT HERE JUST IN CASE

package edu.ycp.cs320.personalPlaylist.model;

public class Library
{
	private int numberSongs;
	
	Library(int numberSongs)
	{
		this.setNumberSongs(numberSongs);
	}

	//getter and setter
	public int getNumberSongs() {
			return numberSongs;		
	}

	public void setNumberSongs(int numberSongs) {
		if(numberSongs >= 0)//Check to make sure number of songs is more than 0 before assigning it
		{
			this.numberSongs = numberSongs;
		}else{
			this.numberSongs = 0;
		}
	}
}
