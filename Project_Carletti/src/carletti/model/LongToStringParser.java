package carletti.model;

public class LongToStringParser {
	public static String parseLongToString(long time){
		boolean negative = false;
		int days, hours, minutes, seconds;
		
		if (time < 0){
			negative = true;
			time = - time;
		}
		
		days = (int) (time / (1000 * 60 * 60 * 24));
		time = time - days * 1000 * 60 * 60 * 24;
		
		hours = (int) (time / (1000 * 60 * 60));
		time = time - hours * 1000 * 60 * 60;
		
        minutes = (int) (time / (1000 * 60));
		time = time - minutes * 1000 * 60;
		
		seconds = (int) (time / (1000));
		
		StringBuffer buffer = new StringBuffer();
		if (negative){
			buffer.append("+");
		} else {
			buffer.append("-");
		}
		buffer.append(padNumber(days) + "d");
		buffer.append(padNumber(hours) + "h");
		buffer.append(padNumber(minutes) + "m");
		buffer.append(padNumber(seconds) + "s");
		
		return buffer.toString();
	}
	
	private static String padNumber(int number){
		if (number < 10){
			return "0" + number;
		} else {
			return number + "";
		}
	}
}
