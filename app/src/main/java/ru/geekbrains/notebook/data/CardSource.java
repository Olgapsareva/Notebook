package ru.geekbrains.notebook.data;

public interface CardSource {
    CardSource init(CardSourceResponse cardsSourceResponse);
    CardData getCardData(int position);
    int size();
    void deleteCardData(int position);
    void updateCardData(int index, CardData cardData);
    void addCardData(CardData cardData);
    void clearCardData();


}
