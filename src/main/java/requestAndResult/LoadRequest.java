package requestAndResult;

import model.Event;
import model.Person;
import model.User;

import java.util.ArrayList;

public class LoadRequest {
    //objects created from json
    private ArrayList<User> users;
    private ArrayList<Person> persons;
    private ArrayList<Event> events;

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
}
