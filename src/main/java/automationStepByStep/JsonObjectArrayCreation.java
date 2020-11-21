package automationStepByStep;

import org.json.JSONArray;
import org.json.simple.JSONObject;

public class JsonObjectArrayCreation {

//	{
//	    "employees": [
//	        {"firstName": "John", "lastName": "Doe"}, 
//	        {"firstName": "Anna", "lastName": "Smith"}, 
//	        {"firstName": "Peter", "lastName": "Jones"}
//	    ],
//	    "manager": [
//	        {"firstName": "John", "lastName": "Doe"}, 
//	        {"firstName": "Anna", "lastName": "Smith"}, 
//	        {"firstName": "Peter", "lastName": "Jones"}
//	    ]
//	}

	@SuppressWarnings("unchecked")
	public static JSONObject getperson(String firstName, String lastName) {

		JSONObject person = new JSONObject();
		person.put("firstName", firstName);
		person.put("lastName", lastName);

		return person;

	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		JSONObject request = new JSONObject();

		JSONArray employees = new JSONArray();

//		JSONObject person1 = new JSONObject();
//		person1.put("firstName", "John");
//		person1.put("lastName", "Doe");
//		
//		JSONObject person2 = new JSONObject();
//		person2.put("firstName", "Anna");
//		person2.put("lastName", "Smith");

		employees.put(getperson("John", "Doe"));
		employees.put(getperson("Anna", "Smith"));
		employees.put(getperson("Peter", "Jones"));

		request.put("employees", employees);

		JSONArray managers = new JSONArray();

		managers.put(getperson("John", "Doe"));
		managers.put(getperson("Anna", "Smith"));
		managers.put(getperson("Peter", "Jones"));

		request.put("managers", managers);

		System.out.println(request.toJSONString());
	}

}
