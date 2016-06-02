/*
 * Copyright (C) 2016 Thomas Kercheval
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

import java.util.ArrayList;

/**
 * A generic implementation of a stack. Can take any type of object.
 *
 * Project: 24 Card Game
 * Platform: jdk 1.8.0_14; NetBeans IDE 8.1; Windows 10
 * Course: CS 143 
 * Created on Jun 1, 2016, 10:25 PM 
 * Revised on Jun 1, 2016, 10:46 PM
 *
 * @author thomas.kercheval
 */
public class GenericStack<E> {
    /**
     * The list which we will use to keep the order of insertion.
     */
    private final ArrayList<E> list = new ArrayList<>();
    
    /**
     * Our default constructor, which doesn't really do anything fancy.
     */
    public GenericStack() {
    }
    
    /**
     * @return The number of elements in our stack.
     */
    public int getSize() {
        return list.size();
    }
    
    /**
     * @return True if our stack is empty.
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }
    
    /**
     * Pushes an element onto the top of our stack for later retrieval.
     * @param element That which we wish to add to the top of our stack.
     * @throws FullStackException Throws an exception when there are over
     * 10,000 items in our stack.
     */
    public void push(E element) throws FullStackException {
        if (this.getSize() > 10000) {
            throw new FullStackException();
        }
        list.add(element);
    }
    
    /**
     * @return The top element of our stack, without removing it.
     * @throws EmptyStackException Thrown if our stack contains no elements.
     */
    public E peek() throws EmptyStackException {
        if (list.isEmpty()) {
            throw new EmptyStackException();
        }
        return list.get(getSize() - 1);
    }
    
    /**
     * @return The top element of our stack, and removes it.
     * @throws EmptyStackException Thrown if our stack contains no elements.
     */
    public E pop() throws EmptyStackException {
        if (list.isEmpty()) {
            throw new EmptyStackException();
        }
        return list.remove(getSize() - 1);
    }
    
    @Override
    /**
     * Prints off the elements in our stack.
     */
    public String toString() {
        return "Stack: " + list.toString();
    }
}
