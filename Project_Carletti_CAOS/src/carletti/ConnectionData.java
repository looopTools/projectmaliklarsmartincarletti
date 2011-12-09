package carletti;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectionData {
	public ConnectionData(){
		String reguest = "SELECT regnr, ktonr  " +
				"FROM KundeHarKonto khk, Kunde k " +
				"WHERE k.cprNr=" + "'" + "kid" + "' " +
				" AND k.cprNr=khk.cprNr";
		System.out.println(reguest);
		try {
			Connection myConnection;
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			myConnection = DriverManager.getConnection(
					"jdbc:jtds:sqlserver://10.37.129.3:1433/AndbyBank", "sa",
					"lnc20020"); // "jdbc:jtds:sqlserver://10.37.129.3:1433/Bank"
			Statement stmt = myConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = stmt.executeQuery(reguest);
			while (res.next()) {
				int regNr = res.getInt(1);
				long ktonr = res.getLong(2);
				System.out.println("RegNr: " + regNr  + " Konto nr: " + ktonr);
//				String navn = res.getString(2);
//				String adresse = res.getString(3);
//				String tlf = res.getString(4);
//				System.out.println("RegNr: " + regNr + " Navn: " + navn + " Adresse: " + adresse + " Tlf: " + tlf);
			}
			if (res != null)
				res.close();
			if (stmt != null)
				stmt.close();
			if (myConnection != null)
				myConnection.close();
		} catch (Exception e) {
			System.out.println("error:  " + e.getMessage());
		}
	}
}
