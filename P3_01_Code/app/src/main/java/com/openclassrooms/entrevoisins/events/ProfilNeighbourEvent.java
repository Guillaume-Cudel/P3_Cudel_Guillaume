package com.openclassrooms.entrevoisins.events;

import com.openclassrooms.entrevoisins.model.Neighbour;

public class ProfilNeighbourEvent {

    public Neighbour neighbour;

    public ProfilNeighbourEvent(Neighbour neighbour){
        this.neighbour = neighbour;
    }
}
