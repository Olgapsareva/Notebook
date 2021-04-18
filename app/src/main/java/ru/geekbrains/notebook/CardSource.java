package ru.geekbrains.notebook;

public interface CardSource {

    CardData getCardData(int position);
    int size();
    void deleteCardData(int position);
    void updateCardData(int index, CardData cardData);
    void addCardData(CardData cardData);
    void clearCardData();


}
