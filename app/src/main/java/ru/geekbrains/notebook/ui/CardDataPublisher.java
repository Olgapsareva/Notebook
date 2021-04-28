package ru.geekbrains.notebook.ui;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.notebook.data.CardData;

public class CardDataPublisher {

    List<CardDataObserver> cardDataObservers;

    public CardDataPublisher() {
        cardDataObservers = new ArrayList<>();
    }

    public void subscribe(CardDataObserver cardDataObserver){
        cardDataObservers.add(cardDataObserver);
    }

    public void unsubscribe(CardDataObserver cardDataObserver){
        cardDataObservers.remove(cardDataObserver);
    }

    public void notifyObservers(CardData cardData){
        for (CardDataObserver cardDataObserver : cardDataObservers) {
            cardDataObserver.updateCardData(cardData);
        }
    }
}
