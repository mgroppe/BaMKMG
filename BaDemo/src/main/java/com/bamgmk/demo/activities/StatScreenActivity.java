package com.bamgmk.demo.activities;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bamgmk.demo.GameItem;
import com.bamgmk.demo.PlayerCharacter;
import com.bamgmk.demo.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class StatScreenActivity extends Activity {
    public List<GameItem> weapons;
    public List<GameItem> armors;
    public List<GameItem> shoes;
    public List<String> currentList;
    public PlayerCharacter pc;
    public TextView nameView;
    public TextView damageView;
    public TextView hpView;
    public TextView initView;
    public TextView rangeView;
    public TextView speedView;
    public TextView itemView;
    public TextView lvlView;
    public ImageButton goBackButton;
    public ImageButton weaponsButton;
    public ImageButton armorButton;
    public ImageButton shoeButton;
    public int position = 0;
    public ArrayList<GameItem> itemList;
    private ArrayAdapter<String> myAdapter;
    private int mode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat_screen);
        pc = (PlayerCharacter) getIntent().getExtras().get("Character");
        itemList = (ArrayList<GameItem>) getIntent().getExtras().get("Items");
        position = getIntent().getIntExtra("Position",0);
        nameView = (TextView) findViewById(R.id.nameView);
        lvlView = (TextView) findViewById(R.id.lvlView);
        hpView = (TextView) findViewById(R.id.hpView);
        damageView = (TextView) findViewById(R.id.damageView);
        initView = (TextView) findViewById(R.id.initView);
        rangeView = (TextView) findViewById(R.id.rangeView);
        speedView = (TextView) findViewById(R.id.speedView);
        goBackButton = (ImageButton) findViewById(R.id.goBackButton);
        weaponsButton = (ImageButton) findViewById(R.id.imageButton);
        armorButton = (ImageButton) findViewById(R.id.imageButton2);
        shoeButton = (ImageButton) findViewById(R.id.imageButton3);
        updateStats();
        lvlView.setText("lvl "+pc.lvl);


        itemView =(TextView) findViewById(R.id.selectedItemView);
        if (pc.weapon != null){
            String s = "";
            s+= "lvl "+pc.weapon.lvl+" "+pc.weapon.name;
            if(pc.weapon.mindmg!= 0){
                s+="\n";
                s+="Schaden: "+pc.weapon.mindmg+"-"+pc.weapon.maxdmg;
            }
            if(pc.weapon.hp != 0){
                s+="\n";
                s+="Lebenspunkte: "+pc.weapon.hp;
            }
            if (pc.weapon.attackrange !=0){
                s+="\n";
                s+="Angriffsreichweite: "+pc.weapon.attackrange;
            }
            if(pc.weapon.initiative!=0){
                s+="\n";
                s+="Initiative: "+pc.weapon.initiative;
            }
            if (pc.weapon.movement!= 0){
                s+="\n";
                s+="Geschwindigkeit: "+pc.weapon.movement;
            }
            itemView.setText(s);
        }
        else {
            itemView.setText("keine Waffe ausgerüstet");
        }

        ImageView iv = (ImageView) findViewById(R.id.imageView2);
        iv.setImageResource(pc.getImageId());
        weaponsButton.setImageResource(pc.getWeaponImageId());
        armorButton.setImageResource(R.drawable.armor);
        shoeButton.setImageResource(R.drawable.boots);
        goBackButton.setImageResource(R.drawable.arrow_back);
        weapons = new ArrayList<GameItem>();
        armors = new ArrayList<GameItem>();
        shoes = new ArrayList<GameItem>();

        for (GameItem gi : itemList){
            switch (gi.type){
                case GameItem.weapon:
                    if (gi.weaponType == pc.model)
                        weapons.add(gi);
                    break;
                case GameItem.armor:
                        armors.add(gi);
                    break;
                case GameItem.shoes:
                        shoes.add(gi);
                    break;
                default:
                    break;
            }
        }


        currentList = createWeaponslist();
        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,currentList);
        ListView lv =(ListView) findViewById(R.id.itemListView);
        lv.setAdapter(myAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (mode){
                    case 0:
                        if(pc.weapon != null)
                            weapons.add(pc.weapon);
                        pc.equip(weapons.get(i),itemList);
                        weapons.remove(i);
                        clickedWeapon(null);
                        updateStats();
                        break;
                    case 1:
                        if(pc.armor != null)
                            armors.add(pc.armor);
                        pc.equip(armors.get(i),itemList);
                        armors.remove(i);
                        clickedArmor(null);
                        updateStats();
                        break;
                    case 2:
                        if(pc.shoes != null)
                            shoes.add(pc.shoes);
                        pc.equip(shoes.get(i),itemList);
                        shoes.remove(i);
                        clickedShoe(null);
                        updateStats();
                        break;
                    default:
                        break;
                }

            }
        });
    }

    private void updateStats() {
        speedView.setText("Geschwindigkeit: "+pc.movement);
        rangeView.setText("Angriffsreichweite: "+pc.attackRange);
        initView.setText("Initiative: "+pc.initiative);
        hpView.setText("Lebenspunkte: "+pc.maxHealth);
        damageView.setText("Schaden: "+pc.mindamage+"-"+pc.maxdamage);
        nameView.setText(pc.name);
    }

    private ArrayList<String> createWeaponslist(){
        ArrayList<String>result = new ArrayList<String>();
        for (GameItem weapon : weapons){
            String s = "";
            s+= "lvl "+weapon.lvl+" "+weapon.name;
            if(weapon.mindmg!= 0){
                s+="\n";
                s+="Schaden: "+weapon.mindmg+"-"+weapon.maxdmg;
            }
            if(weapon.hp != 0){
                s+="\n";
                s+="Lebenspunkte: "+weapon.hp;
            }
            if (weapon.attackrange !=0){
                s+="\n";
                s+="Angriffsreichweite: "+weapon.attackrange;
            }
            if(weapon.initiative!=0){
                s+="\n";
                s+="Initiative: "+weapon.initiative;
            }
            if (weapon.movement!= 0){
                s+="\n";
                s+="Geschwindigkeit: "+weapon.movement;
            }
            result.add(s);
        }
        return result;
    }

    private ArrayList<String> createArmorlist(){
        ArrayList<String>result = new ArrayList<String>();
        for (GameItem armor : armors){
            String s = "";
            s+="lvl "+ armor.lvl+" "+armor.name;
            if(armor.mindmg!= 0){
                s+="\n";
                s+="Schaden: "+armor.mindmg+"-"+armor.maxdmg;
            }
            if(armor.hp != 0){
                s+="\n";
                s+="Lebenspunkte: "+armor.hp;
            }
            if (armor.attackrange !=0){
                s+="\n";
                s+="Angriffsreichweite: "+armor.attackrange;
            }
            if(armor.initiative!=0){
                s+="\n";
                s+="Initiative: "+armor.initiative;
            }
            if (armor.movement!= 0){
                s+="\n";
                s+="Geschwindigkeit: "+armor.movement;
            }
            result.add(s);
        }
        return result;
    }

    private ArrayList<String> createShoeList(){
        ArrayList<String>result = new ArrayList<String>();
        for (GameItem shoe : shoes){
            String s = "";
            s+= "lvl "+shoe.lvl+" "+shoe.name;
            if(shoe.mindmg!= 0){
                s+="\n";
                s+="Schaden: "+shoe.mindmg+"-"+shoe.maxdmg;
            }
            if(shoe.hp != 0){
                s+="\n";
                s+="Lebenspunkte: "+shoe.hp;
            }
            if (shoe.attackrange !=0){
                s+="\n";
                s+="Angriffsreichweite: "+shoe.attackrange;
            }
            if(shoe.initiative!=0){
                s+="\n";
                s+="Initiative: "+shoe.initiative;
            }
            if (shoe.movement!= 0){
                s+="\n";
                s+="Geschwindigkeit: "+shoe.movement;
            }
            result.add(s);
        }
        return result;
    }

    public void goBack(View view){
        Intent i = new Intent(this,MainActivity.class);
        i.putExtra("Items",itemList);
        i.putExtra("Character",pc);
        i.putExtra("Position",position);
        startActivity(i);
    }

    public void clickedWeapon(View view){
        if (pc.weapon != null){
            String s = "";
            s+= "lvl "+pc.weapon.lvl+" "+pc.weapon.name;
            if(pc.weapon.mindmg!= 0){
                s+="\n";
                s+="Schaden: "+pc.weapon.mindmg+"-"+pc.weapon.maxdmg;
            }
            if(pc.weapon.hp != 0){
                s+="\n";
                s+="Lebenspunkte: "+pc.weapon.hp;
            }
            if (pc.weapon.attackrange !=0){
                s+="\n";
                s+="Angriffsreichweite: "+pc.weapon.attackrange;
            }
            if(pc.weapon.initiative!=0){
                s+="\n";
                s+="Initiative: "+pc.weapon.initiative;
            }
            if (pc.weapon.movement!= 0){
                s+="\n";
                s+="Geschwindigkeit: "+pc.weapon.movement;
            }
            itemView.setText(s);
        }
        else {
            itemView.setText("keine Waffe ausgerüstet");
        }
        mode = 0;
        currentList.removeAll(currentList);
        currentList.addAll(createWeaponslist());
        myAdapter.notifyDataSetChanged();
    }
    public void clickedArmor(View view){
        if (pc.armor != null){
            String s = "";
            s+="lvl "+ pc.armor.lvl+" "+pc.armor.name;
            if(pc.armor.mindmg!= 0){
                s+="\n";
                s+="Schaden: "+pc.armor.mindmg+"-"+pc.armor.maxdmg;
            }
            if(pc.armor.hp != 0){
                s+="\n";
                s+="Lebenspunkte: "+pc.armor.hp;
            }
            if (pc.armor.attackrange !=0){
                s+="\n";
                s+="Angriffsreichweite: "+pc.armor.attackrange;
            }
            if(pc.armor.initiative!=0){
                s+="\n";
                s+="Initiative: "+pc.armor.initiative;
            }
            if (pc.armor.movement!= 0){
                s+="\n";
                s+="Geschwindigkeit: "+pc.armor.movement;
            }
            itemView.setText(s);
        }
        else {
            itemView.setText("keine Rüstung ausgerüstet");
        }
        currentList.removeAll(currentList);
        currentList.addAll(createArmorlist());
        myAdapter.notifyDataSetChanged();
        mode = 1;
    }
    public void clickedShoe(View view){
        if (pc.shoes != null){
            String s = "";
            s+="lvl "+ pc.shoes.lvl+" "+pc.shoes.name;
            if(pc.shoes.mindmg!= 0){
                s+="\n";
                s+="Schaden: "+pc.shoes.mindmg+"-"+pc.shoes.maxdmg;
            }
            if(pc.shoes.hp != 0){
                s+="\n";
                s+="Lebenspunkte: "+pc.shoes.hp;
            }
            if (pc.shoes.attackrange !=0){
                s+="\n";
                s+="Angriffsreichweite: "+pc.shoes.attackrange;
            }
            if(pc.shoes.initiative!=0){
                s+="\n";
                s+="Initiative: "+pc.shoes.initiative;
            }
            if (pc.shoes.movement!= 0){
                s+="\n";
                s+="Geschwindigkeit: "+pc.shoes.movement;
            }
            itemView.setText(s);
        }
        else {
            itemView.setText("keine Schuhe ausgerüstet");
        }
        currentList.removeAll(currentList);
        currentList.addAll(createShoeList());
        for (String s : currentList){
            Log.d("test",s);
        }
        myAdapter.notifyDataSetChanged();
        mode = 2;
    }
    @Override
    public void onBackPressed() {
        goBack(null);
    }

}
