package ru.geekbrains.notebook.data;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CardSourceFirebaseImpl implements CardSource {

    private static final String CARDS_COLLECTION = "cards";
    private static final String TAG = "[CardsSourceFirebaseImpl]";
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();              // База данных Firestore
    private CollectionReference collection = firestore.collection(CARDS_COLLECTION);  // Коллекция документов
    private List<CardData> cardsData = new ArrayList<>();               // Загружаемый список карточек

    @Override
    public CardSource init(CardSourceResponse cardsSourceResponse) {
        //получаем отсортированную по дате коллекцию в виде Таска
        collection.orderBy(CardDataMapping.Fields.DATE, Query.Direction.DESCENDING).get()
                //вешаем слушателя на успешную обработку, возвращается тип Task<QuerySnapshot>
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //проверяем, правильный ли результат запроса
                        if (task.isSuccessful()) {
                            cardsData = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> doc = document.getData();
                                String id = document.getId();
                                CardData cardData = CardDataMapping.toCardData(id, doc);
                                cardsData.add(cardData);
                            }
                            Log.i(TAG, "success " + cardsData.size() + " qnt");
                            cardsSourceResponse.initialized(CardSourceFirebaseImpl.this);
                        } else {
                            Log.e(TAG, "get failed with ", task.getException());
                        }
                    }
                })
                //также вешаем слушателя на неуспешную обработку запроса
                .addOnFailureListener(new OnFailureListener() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "get failed with ", e);
                    }
                });
        return this;
    }

    @Override
    public CardData getCardData(int position) {
        return cardsData.get(position);
    }


    @Override
    public int size() {
        return cardsData.size();
    }

    @Override
    public void deleteCardData(int position) {
        collection.document(cardsData.get(position).getId()).delete();
        cardsData.remove(position);

    }

    @Override
    public void updateCardData(int index, CardData cardData) {
        String id = cardData.getId();
        // Изменить документ по идентификатору
        collection.document(id).set(CardDataMapping.toDocument(cardData));
    }

    @Override
    public void addCardData(CardData cardData) {
        // Добавить документ
        collection.add(CardDataMapping.toDocument(cardData)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                cardData.setId(documentReference.getId());
            }
        });

    }

    @Override
    public void clearCardData() {
        for (CardData cardData : cardsData) {
            collection.document(cardData.getId()).delete();
        }
        cardsData = new ArrayList<>();

    }
}
