package ru.geekbrains.notebook;

public interface CardSource {

    CardData getCardData(int position);
    int size();
}
