package Main;

import java.security.DrbgParameters.NextBytes;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import Admin.Admin;
import Admin.Song;
import User.User;

public class MainSpotifyEntry {
	static Scanner sc= new Scanner(System.in);
	static Admin admin=new Admin();
	static User user = new User();
	static ArrayList<Song> globalLibrary = new ArrayList<Song>();
	public static void main(String[] args) throws ClassNotFoundException, SQLException, InterruptedException {
		System.out.println("--------------------Welcome to Spotify----------------");
		System.out.println("1.Admin");
		System.out.println("2.User");
		System.out.println("-----------------------------------------------------------");
		int ch=sc.nextInt();
		switch(ch) {
		case 1->LoginAdmin();
		case 2->Loginuser();
		}
	}
	
	public static Connection dbConnect() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student?user=root&password=Pavan2091");

        return connection;
	}
	
	private static void Loginuser() throws ClassNotFoundException, SQLException, InterruptedException {
		 boolean flag = true;

	        while (flag) {

	            System.out.println("--------------------------------------------------");
	            System.out.println("1.Create a Account");
	            System.out.println("2.Login");
	            System.out.println("3.Exit");
	            System.out.println("---------------------------------------------------");
	            System.out.println("Enter Your Choice");

	            int ch = sc.nextInt();

	            switch (ch) {
	                case 1 -> {
	                	Connection connection = dbConnect();
	                	PreparedStatement statement = connection.prepareStatement("insert into user values(?,?,?,?,?)");
	                	System.out.println("Enter name:");
	                	statement.setString(1, sc.next());
	                	System.out.println("Enter email:");//44
	                	statement.setString(2, sc.next());
	                	System.out.println("Enter password:");
	                	statement.setString(3, sc.next());
	                	System.out.println("Enter mobile no:");
	                	statement.setLong(4, sc.nextLong());
	                	System.out.println("Enter location:");
	                	statement.setString(5, sc.next());
	                	int result = statement.executeUpdate();
	                	System.out.println(result==1?"created successfully":"account not created");
	                	statement.close();
	                	connection.close();
	                }
	                case 2 -> {
	                	Connection connection =dbConnect();
	                	PreparedStatement statement = connection.prepareStatement("select * from user where username=? and password=?");
	                	System.out.println("enter username");
	                	String username = sc.next();
	                	statement.setString(1,username);
	                	System.out.println("enter password");
	                	statement.setString(2, sc.next());
	                	ResultSet set = statement.executeQuery();
	                	while(set.next()) {
	                		System.out.println("-----------------------User Menu--------------");
	                		boolean flag1 = true;
	                		while(flag1) {
	                		System.out.println("1.play song");
	                		System.out.println("2.Create a playlist");
	                		System.out.println("3.Add songs to playlist");
	                		System.out.println("4.Remove songs from playlist");
	                		System.out.println("5.show all playlist");
	                		System.out.println("6.Show all Songs");
	                		System.out.println("7.Exit");
	                		System.out.println("-------------------------------------------------");
	                		System.out.println("Enter your choise :");
	                		int n= sc.nextInt();
	                		switch(n) {
	                		case 1->{
	                			showSongs();
	                			System.out.println("Enter song name:");
	                			String song =sc.next();
	                			System.out.println("playing s song ("+song+")........");
	                			Thread.sleep(5000);
	                			
	                		}
	                		case 2->{
	                			PreparedStatement statement2 = connection.prepareStatement("insert into playlist values(?,?,?)");
	                            System.out.println("Enter playlist name:");
	                            statement2.setString(1, sc.next());
	                            statement2.setString(2, username);
	                            showSongs();
	                            System.out.println("Enter a song: ");
	                            statement2.setString(3, sc.next());
	                            int result = statement2.executeUpdate();
	                            System.out.println(result==1?"playlist created":"playlist not created");
	                		}
	                		case 3->{
	                			PreparedStatement statement2 = connection.prepareStatement("insert into playlist values(?,?,?)");
	                            System.out.println("Enter playlist name:");
	                            statement2.setString(1, sc.next());
	                            statement2.setString(2, username);
	                            showSongs();
	                            System.out.println("Enter a song: ");
	                            statement2.setString(3, sc.next());
	                            int result = statement2.executeUpdate();
	                            System.out.println(result==1?"song is added":"song is not added");
	                			
	                		}
	                		case 4->{
	                			PreparedStatement statement2 = connection.prepareStatement("delete from playlist where pname=? and song=?");
	                            System.out.println("Enter playlist name:");
	                            statement2.setString(1, sc.next());
	                            System.out.println("Enter song name:");
	                            statement2.setString(2, sc.next());
	                            int result = statement2.executeUpdate();
	                            System.out.println(result==1?"song removed":"song is not removed");
	                			
	                		}
	                		case 5->{
	                			PreparedStatement statement2 = connection.prepareStatement("select * from playlist");
	                			ResultSet set3= statement2.executeQuery();
	                			System.out.println("-----------------playlist----------------------");
	                			while(set3.next()) {
	                				System.out.println(set3.getString(1));
	                			}
	                			System.out.println("------------------------------------------------");
	                			
	                			
	                		}
	                		case 6->{
	                			showSongs();
	                			
	                		}
	                		case 7-> flag1=false;
	                		
	                		}
	                		}
	                	}
	                	
	                	
	                }
	                case 3 -> flag = false;
	            }
	            
	        }
	    }
	public static void showSongs() throws ClassNotFoundException, SQLException {
		Connection connection =dbConnect();
    	PreparedStatement statement1 = connection.prepareStatement("select * from song");
    	ResultSet set1=statement1.executeQuery();
    	System.out.println("--------------song list----------------------");
    	System.out.println("--------------------------------------------------------------------------------------2");
    	while(set1.next()) {
    		
    		System.out.println(set1.getString(1)+"||"+set1.getString(2)+"||"+set1.getDouble(3)+"||"+set1.getString(4));
    		System.out.println("-----------------------------------------------------------------------------");
    	}	
	}

	private static void LoginAdmin() throws ClassNotFoundException, SQLException {
		System.out.println("Enter Username");
		String User=sc.next();
		System.out.println("Enter Password");
		String Pass=sc.next();
		if(Admin.isAuthenticate(User,Pass)) {
			boolean flag=true;
			while(true) {
			System.out.println("------------------------Admin Menu----------------------");
			System.out.println("1.Add songs");
			System.out.println("2.Update Song Details");
			System.out.println("3.delete a song");
			System.out.println("4.Show all songs");
			System.out.println("5.Exit");
			System.out.println("Enter your choise");
			int ch=sc.nextInt();
			switch(ch) {
			case 1->{
				System.out.println("Enter Song Title");
				String title =sc.next();
				System.out.println("Enter song artist");
				String artist =sc.next();
				System.out.println("Enter song Duration");
				double duration =sc.nextDouble();
				System.out.println("Enter song genre");
				String genre =sc.next();
				Song song = new Song(title,artist,duration,genre);
				//globalLibrary.add(song);
				Connection connection = dbConnect();
				PreparedStatement statement = connection.prepareStatement("insert into song values(?,?,?,?)");
				statement.setString(1, title);
				statement.setString(2, artist);
				statement.setDouble(3, duration);
				statement.setString(4, genre);
				statement.executeUpdate();
				statement.close();
				connection.close();
				
				System.out.println("sond added");
			}
			case 2->{
			    System.out.println("Enter Song Name");
			    String title = sc.next();
			    System.out.println("1.title");
			    System.out.println("2.artist");
			    System.out.println("3.duration");
			    System.out.println("4.genre");
			    
			    int ch1=sc.nextInt();
			    String Query = "";
			    switch(ch1) {
			    case 1->{
			    	System.out.println("Enter new title");
			    	Query = "update song set title='"+sc.next()+"' where title =?";
			    }
			    case 2->{
			    	System.out.println("Enter new artist name");
			    	Query = "update song set artist='"+sc.next()+"' where title =?";
			    	}
			    case 3->{
			    	System.out.println("Enter new duration");
			    	Query = "update song set duration="+sc.nextDouble()+" where title =?";
			    }
			    case 4->{
			    	System.out.println("Enter new genre");
			    	Query = "update song set genre='"+sc.next()+"' where title =?";
			    }
			    }
			    Connection connection =dbConnect();
			    PreparedStatement statement = connection.prepareStatement(Query);
			    statement.setString(1, title);
			    int result = statement.executeUpdate();
			    if(result==1) {
			    	System.out.println("song detail updated");
			    }
			    else {
			    	System.out.println("song does not found");
			    }
			    statement.close();
			    connection.close();
			    

			    
			}
			case 3->{
				System.out.println("Enter the song Name which you want to delete");
				String title = sc.next();
				Connection connection = dbConnect();
				PreparedStatement statement = connection.prepareStatement("delete from song where title=?");
				statement.setString(1, title);
				int result = statement.executeUpdate();
				System.out.println(result==1?"song deleted":"song not deleted");
			}
			case 4->{
				
				System.out.println("--------------------song list-------------------");
				//globalLibrary.forEach(song->System.out.println(song.getTitle()+" "+song.getArtist()));
				Connection connection =dbConnect();
				PreparedStatement statement = connection.prepareStatement("select * from song;");
				ResultSet set = statement.executeQuery();
				while(set.next()) {
				System.out.println(set.getString(1)+"||"+set.getString(2)+"||"+set.getDouble(3)+"||"+set.getString(4));
				}
				statement.close();
				connection.close();
						
				
			}
			}
			
			}
			
		}
		else {
			System.out.println("Inavlid Username or Password");
		}
	}

}
