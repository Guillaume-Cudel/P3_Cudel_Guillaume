package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfilActivity extends AppCompatActivity {
    @BindView(R.id.profil_name)
    public TextView mProfilName;
    @BindView(R.id.profil_image_avatar)
    public ImageView mProfilAvatar;
    @BindView(R.id.profil_back_button)
    public ImageButton mProfilBackButton;
    @BindView(R.id.profil_button_addFavorite)
    public FloatingActionButton mAddFavorite;
    @BindView(R.id.profil_space1_name)
    public TextView mProfilName2;
    @BindView(R.id.profil_space1_text_location)
    public TextView mProfilSpace1LocationText;
    @BindView(R.id.profil_space1_text_phone)
    public TextView mProfilSpace1PhoneText;
    @BindView(R.id.profil_space1_text_adress)
    public TextView mProfilSpace1AdressText;
    @BindView(R.id.profil_space2_text_description)
    public TextView mProfilSpace2DescriptionText;

    private String name;
    private String avatar;
    private String phoneNumber;
    private String adress;
    private long id;
    private String aboutMe;

    private NeighbourApiService mApiService;
    private Neighbour neighbour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        ButterKnife.bind(this);
        mApiService = DI.getNeighbourApiService();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            name = bundle.getString("extra_name");
            avatar = bundle.getString("extra_avatar");
            phoneNumber = bundle.getString("extra_phoneNumber");
            adress = bundle.getString("extra_adress");
            id = bundle.getLong("extra_id",0);
            aboutMe = bundle.getString("extra_aboutMe");

            neighbour = new Neighbour(id,name,avatar,adress,phoneNumber,aboutMe);
        }

        mProfilName.setText(name);
        mProfilName2.setText(name);
        Glide.with(mProfilAvatar.getContext())
                .load(avatar)
                .into(mProfilAvatar);
        mProfilSpace1LocationText.setText(adress);
        mProfilSpace2DescriptionText.setText(aboutMe);
        mProfilSpace1PhoneText.setText(phoneNumber);



        mProfilBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        updateFavorite();
    }

    void updateFavorite(){
        boolean isFavorite = false;
        for(Neighbour n : mApiService.getFavorites()){
            long id = n.getId();
            long neighbourId = neighbour.getId();

            //if(n.getId() == neighbour.getId())
            if(id == neighbourId){
                isFavorite = true;
                break;
            }
        }
        if (isFavorite) {
            mAddFavorite.setImageResource(R.drawable.ic_star_yellow);

        } else {
            mAddFavorite.setImageResource(R.drawable.ic_star_border_yellow_24dp);

        }
    }

    @OnClick(R.id.profil_button_addFavorite)
     void updateInFavorite() {
        mApiService.    verifyPresence(neighbour);
        updateFavorite();
    }





}
