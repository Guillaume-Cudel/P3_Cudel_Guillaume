package com.openclassrooms.entrevoisins.service;

import android.content.res.ColorStateList;
import android.graphics.Color;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ProfilActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();
    private List<Neighbour> favorites = new ArrayList<>();


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void createNeighbour(Neighbour neighbour) {
        neighbours.add(neighbour);
    }

    @Override
    public List<Neighbour> getFavorites() {return favorites;}

    @Override
    public void deleteFavorite(Neighbour favorite){favorites.remove(favorite);}

    @Override
    public void updateFavorite(Neighbour favorite){
        favorites.add(favorite);
    }

    @Override
    public void verifyPresence(Neighbour favorite){
        long verifyID = favorite.getId();
        boolean isFavorite = false;
        for(Neighbour n : favorites){
            if(n.getId() == verifyID){
                isFavorite = true;
            }
            else{
                isFavorite = false;
            }

        }
        if(isFavorite)
            deleteFavorite(favorite);

        else
            updateFavorite(favorite);

    }



}
