package com.openclassrooms.entrevoisins.events;

import com.openclassrooms.entrevoisins.model.Neighbour;

public class DeleteFavoriteEvent {

    public Neighbour favorite;

    public DeleteFavoriteEvent(Neighbour favorite){
        this.favorite = favorite;
    }
}
