package automationStepByStep;

public class getLocation {
	
	public static void main(String[] args) {
		String locationHeader =  "http://localhost:3000/usmn/ers///55";
		
		System.out.println(locationHeader.split("/")[locationHeader.split("/").length - 1]);

	}

}
