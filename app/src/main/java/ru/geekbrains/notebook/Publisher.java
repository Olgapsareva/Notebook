package ru.geekbrains.notebook;

import java.util.ArrayList;
import java.util.List;

public class Publisher {

    List<Observer> observers;

    public Publisher() {
        observers = new ArrayList<>();
    }

    public void subscribe(Observer observer){
        observers.add(observer);
    }

    public void unsubscribe(Observer observer){
        observers.remove(observer);
    }

    public void notifyObservers(CardData cardData){
        for (Observer observer : observers) {
            observer.updateCardData(cardData);
        }
    }
}
