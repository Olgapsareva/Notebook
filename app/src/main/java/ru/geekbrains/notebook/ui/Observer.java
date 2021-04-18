package ru.geekbrains.notebook.ui;

import ru.geekbrains.notebook.data.CardData;

public interface Observer {
    void updateCardData(CardData cardData);

}
