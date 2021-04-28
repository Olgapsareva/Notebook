package ru.geekbrains.notebook.data;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ru.geekbrains.notebook.R;

public class CardSourceImpl implements CardSource {

    private List<CardData> dataSource;
    private Resources resources;

    //карточки храним в листе
    public CardSourceImpl(Resources resources) {
        dataSource = new ArrayList<>(7);
        this.resources = resources;
    }

    //инициализируем карточки данными из arrays, добавляем в лист
    public CardSource init(CardSourceResponse cardSourceResponse) {
        //получаем данные из ресурсов
        String[] titles = resources.getStringArray(R.array.titles);
        String[] descriptions = resources.getStringArray(R.array.descriptions);

        for (int i = 0; i < descriptions.length; i++) {
            dataSource.add(new CardData(titles[i], descriptions[i], Calendar.getInstance().getTime()));
        }

        if (cardSourceResponse != null){
            cardSourceResponse.initialized(this);
        }

        return this;
    }

    public Resources getResources() {
        return resources;
    }


    @Override
    public CardData getCardData(int position) {
        return dataSource.get(position);
    }

    @Override
    public int size() {
        return dataSource.size();
    }

    @Override
    public void deleteCardData(int position) {
        dataSource.remove(position);
    }

    @Override
    public void updateCardData(int position, CardData cardData) {
        dataSource.set(position, cardData);
    }

    @Override
    public void addCardData(CardData cardData) {
        dataSource.add(cardData);
    }

    @Override
    public void clearCardData() {
        dataSource.clear();
    }
}
