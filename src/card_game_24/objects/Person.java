/*
 * Copyright (C) 2016 Thomas Kercehval
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package card_game_24.objects;

/**
 * This class represents a Person, with a name.
 * 
 * Project: 24 Card Game
 * Platform: jdk 1.8.0_14; NetBeans IDE 8.1; Windows 10
 * Course: CS 143
 * Created on May 23, 2016, 2:57 PM
 * Revised on May 23, 2016, 3:03 PM
 * 
 * @author Thomas Kercheval
 */
public class Person {
    /** The name of this person */
    private String name;
    
    /**
     * Creates a person with a name.
     * @param namePerson The name of the person we are creating.
     */
    public Person(String namePerson) {
        this.name = namePerson;
    }
    
    /**
     * Copy constructor, copies another instance of a Person
     * @param person Person we are copying.
     */
    public Person(Person person) {
        this.name  = person.getName();
    }
    
    /**
     * @return The name of this Person.
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Sets the name of this person.
     * @param personName The name we want this person to have.
     */
    public void setName(String personName) {
        this.name = personName;
    }
    
    /**
     * @return A string representation of this person, via their name.
     */
    @Override
    public String toString() {
        return this.name;
    }
}
