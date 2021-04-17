package ru.geekbrains.notebook;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class CardFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CARD_DATA = "Param_CardData";

    private CardData cardData;
    private Publisher publisher;
    private EditText title;
    private EditText body;
    private DatePicker datePicker;

    public CardFragment() {
        // Required empty public constructor
    }

    public static CardFragment newInstance(CardData cardData) {
        CardFragment fragment = new CardFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CARD_DATA, cardData);
        fragment.setArguments(args);
        return fragment;
    }

    public static CardFragment newInstance() {
        CardFragment fragment = new CardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cardData = getArguments().getParcelable(ARG_CARD_DATA);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity)context;
        publisher = activity.getPublisher();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = (View) inflater.inflate(R.layout.fragment_note, container, false);

        initView(view);
        // если cardData пустая, то это добавление
        if (cardData != null) {
            populateView();
        }

        return view;
    }

    private void initView(View view) {
        title = view.findViewById(R.id.title1);
        body  = view.findViewById(R.id.body);
        datePicker = view.findViewById(R.id.inputDate);
    }

    private void populateView() {
        title.setText(cardData.getTitle());
        body.setText(cardData.getBody());
        initDatePicker(cardData.getDateOfCreation());
    }

    private void initDatePicker(Date dateOfCreation) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateOfCreation);
        this.datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                null);
    }

    //сохраняем данные в переменную
    @Override
    public void onStop() {
        super.onStop();
        cardData = collectCardData();
    }

    //собираем данные из фрагмента
    private CardData collectCardData() {
        String title = this.title.getText().toString();
        String body = this.body.getText().toString();
        Date date = getDateFromDatePicker();
        return new CardData(title, body, date);
    }

    //получает выбранную пользователем дату
    private Date getDateFromDatePicker() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, this.datePicker.getDayOfMonth());
        calendar.set(Calendar.MONTH, this.datePicker.getMonth());
        calendar.set(Calendar.YEAR, this.datePicker.getYear());
        return  calendar.getTime();
    }

    // при удалении фрагмента передадим данные в паблишер
    @Override
    public void onDestroy() {
        super.onDestroy();
        publisher.notifyObservers(cardData);
    }

    //publisher создается каждый раз при создании фрагмента
    @Override
    public void onDetach() {
        publisher = null;
        super.onDetach();
    }


}