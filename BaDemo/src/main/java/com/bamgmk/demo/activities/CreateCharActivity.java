package com.bamgmk.demo.activities;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bamgmk.demo.PlayerCharacter;
import com.bamgmk.demo.R;

import org.w3c.dom.Text;

public class CreateCharActivity extends Activity {
    PlayerCharacter morningStar;
    PlayerCharacter berserker;
    PlayerCharacter anubis;
    PlayerCharacter priestess;
    PlayerCharacter pc;
    TextView damageView;
    TextView hpView;
    TextView rangeView;
    TextView initView;
    TextView speedView;
    ImageButton morningButton;
    ImageButton bersiButton;
    ImageButton anubisButton;
    ImageButton diversityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_char);

        morningStar = PlayerCharacter.createCharacter(0,"",false);
        berserker = PlayerCharacter.createCharacter(1,"",false);
        anubis = PlayerCharacter.createCharacter(2,"",false);
        priestess = PlayerCharacter.createCharacter(3,"",false);
        pc = morningStar;

        morningButton = (ImageButton)findViewById(R.id.morningstarButton);
        bersiButton = (ImageButton) findViewById(R.id.bersiButton);
        anubisButton = (ImageButton) findViewById(R.id.anubisButton);
        diversityButton = (ImageButton) findViewById(R.id.diversityButton);

        damageView = (TextView) findViewById(R.id.damageView2);
        hpView = (TextView) findViewById(R.id.hpView2);
        rangeView = (TextView) findViewById(R.id.rangeView2);
        initView = (TextView) findViewById(R.id.initView2);
        speedView = (TextView) findViewById(R.id.speedView2);

        morningButton.setImageResource(morningStar.getSelectedImageId());
        bersiButton.setImageResource(berserker.getImageId());
        anubisButton.setImageResource(anubis.getImageId());
        diversityButton.setImageResource(priestess.getImageId());

        hpView.setText("Lebenspunkte: "+pc.maxHealth);
        damageView.setText("Schaden: "+pc.mindamage+"-"+pc.maxdamage);
        rangeView.setText("Angriffsreichweite: "+pc.attackRange);
        initView.setText("Initiative: "+pc.initiative);
        speedView.setText("Geschwindigkeit: "+pc.movement);



    }

    public void morningPressed(View view){
        pc = morningStar;

        morningButton.setImageResource(morningStar.getSelectedImageId());
        bersiButton.setImageResource(berserker.getImageId());
        anubisButton.setImageResource(anubis.getImageId());
        diversityButton.setImageResource(priestess.getImageId());

        hpView.setText("Lebenspunkte: "+pc.maxHealth);
        damageView.setText("Schaden: "+pc.mindamage+"-"+pc.maxdamage);
        rangeView.setText("Angriffsreichweite: "+pc.attackRange);
        initView.setText("Initiative: "+pc.initiative);
        speedView.setText("Geschwindigkeit: "+pc.movement);

    }

    public void bersiPressed(View view){
        pc = berserker;

        morningButton.setImageResource(morningStar.getImageId());
        bersiButton.setImageResource(berserker.getSelectedImageId());
        anubisButton.setImageResource(anubis.getImageId());
        diversityButton.setImageResource(priestess.getImageId());

        hpView.setText("Lebenspunkte: "+pc.maxHealth);
        damageView.setText("Schaden: "+pc.mindamage+"-"+pc.maxdamage);
        rangeView.setText("Angriffsreichweite: "+pc.attackRange);
        initView.setText("Initiative: "+pc.initiative);
        speedView.setText("Geschwindigkeit: "+pc.movement);

    }

    public void anubisPressed(View view){
        pc = anubis;

        morningButton.setImageResource(morningStar.getImageId());
        bersiButton.setImageResource(berserker.getImageId());
        anubisButton.setImageResource(anubis.getSelectedImageId());
        diversityButton.setImageResource(priestess.getImageId());

        hpView.setText("Lebenspunkte: "+pc.maxHealth);
        damageView.setText("Schaden: "+pc.mindamage+"-"+pc.maxdamage);
        rangeView.setText("Angriffsreichweite: "+pc.attackRange);
        initView.setText("Initiative: "+pc.initiative);
        speedView.setText("Geschwindigkeit: "+pc.movement);

    }

    public void diversityPressed(View view){
        pc = priestess;

        morningButton.setImageResource(morningStar.getImageId());
        bersiButton.setImageResource(berserker.getImageId());
        anubisButton.setImageResource(anubis.getImageId());
        diversityButton.setImageResource(priestess.getSelectedImageId());

        hpView.setText("Lebenspunkte: "+pc.maxHealth);
        damageView.setText("Schaden: "+pc.mindamage+"-"+pc.maxdamage);
        rangeView.setText("Angriffsreichweite: "+pc.attackRange);
        initView.setText("Initiative: "+pc.initiative);
        speedView.setText("Geschwindigkeit: "+pc.movement);

    }

    public void createChar(View view){


        EditText et = (EditText) findViewById(R.id.editText);
        String s = et.getText().toString();
        if (s.compareTo("")!=0){
            pc.name= s;
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("newCharacter",pc);
            startActivity(intent);
        }
        else
            Toast.makeText(this,"Bitte Namen eingeben",Toast.LENGTH_SHORT);
    }

    @Override
    public void onBackPressed() {
        EditText et = (EditText) findViewById(R.id.editText);
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm.isActive(et)) {
            findViewById(R.id.createCharacterLayout).requestFocus();
        }


        else{
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
    }

}
