package com.bamgmk.demo.activities;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bamgmk.demo.CharData;
import com.bamgmk.demo.GameCharacter;
import com.bamgmk.demo.GameItem;
import com.bamgmk.demo.Quest;
import com.bamgmk.demo.QuestSave;
import com.bamgmk.demo.VicData;
import com.bamgmk.demo.fragments.CharFrag;
import com.bamgmk.demo.Fight;
import com.bamgmk.demo.fragments.MapFrag;
import com.bamgmk.demo.MapReadyListener;
import com.bamgmk.demo.PlayerCharacter;
import com.bamgmk.demo.R;
import com.bamgmk.demo.fragments.QuestFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MainActivity extends FragmentActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,MapReadyListener, CharFrag.OnCharFragInteractionListener ,QuestFragment.OnQuestFragmentInteractionListener{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private GoogleMap map = null;
    private Gson gson;
    public List<PlayerCharacter> heroTeam = new ArrayList<PlayerCharacter>();
    public ArrayList<GameItem> gameItems = new ArrayList<GameItem>();
    public HashMap<Marker,Fight> currentFights = new HashMap<Marker,Fight>();
    private Location currentLocation;
    private Marker player;
    public QuestFragment questFragment;
    private boolean questsUpdated = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(1);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        gson = new GsonBuilder().create();
        if (!new File(getFilesDir().toString()+"/heroes").exists()){
            createHeroTeam();
        }

        loadHeroes();
        for (PlayerCharacter p : heroTeam){
        }


        loadItems();
        Intent intent = getIntent();
        if (intent!= null && intent.getExtras() != null && intent.getExtras().containsKey("newCharacter")){
            PlayerCharacter pc = (PlayerCharacter) intent.getExtras().get("newCharacter");
            heroTeam.add(pc);
            saveHeroes();
            mViewPager.setCurrentItem(0);
        }
        if (intent != null && intent.getExtras()!=null && intent.getExtras().containsKey("Character") ){
            PlayerCharacter pc = (PlayerCharacter) intent.getExtras().get("Character");
            int position = intent.getIntExtra("Position",-1);
            gameItems = (ArrayList) intent.getExtras().get("Items");
            heroTeam.remove(position -3);
            heroTeam.add(position -3,pc);
            saveHeroes();
            saveItems();
            mViewPager.setCurrentItem(0);
        }
        if (intent!= null && intent.getExtras()!=null && intent.getExtras().containsKey("vicData")){
            VicData vd = gson.fromJson(intent.getExtras().getString("vicData"),VicData.class);
            gameItems.add(new GameItem(vd));
            saveItems();
            for (PlayerCharacter pc : heroTeam){
                if (pc.isActive)
                    pc.addxp(vd.xpReward);
            }
            saveHeroes();
        }
    }

    private void loadItems() {
        if(new File(getFilesDir().toString()+"/items").exists()){
            try {
                FileInputStream inputStream = openFileInput("items");
                InputStreamReader streamReader = new InputStreamReader(inputStream);
                BufferedReader Br = new BufferedReader(streamReader);
                String s = Br.readLine();
                gameItems = new ArrayList<GameItem>();
                gameItems.addAll(Arrays.asList(gson.fromJson(s,GameItem[].class)));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void saveItems(){
        String filename = "items";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(gson.toJson(gameItems).getBytes());
            Log.d("test","items = "+gson.toJson(gameItems));
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buttonClicked(View view){
        Intent intent = new Intent(this, CreateCharActivity.class);
        startActivity(intent);
    }


    private void loadHeroes() {
        try {

            FileInputStream inputStream = openFileInput("heroes");
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader Br = new BufferedReader(streamReader);
            String s = Br.readLine();
            Log.d("test","heroteam: "+s);
            heroTeam = new ArrayList<PlayerCharacter>();
            heroTeam.addAll(Arrays.asList(gson.fromJson(s,PlayerCharacter[].class)));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void createHeroTeam() {
        heroTeam.add( PlayerCharacter.createCharacter(0,"Waterloo",true));
        heroTeam.add (  PlayerCharacter.createCharacter(0,"Leipzig",true));
        heroTeam.add ( PlayerCharacter.createCharacter(0,"Aspern",true));
   //     heroTeam.add ( PlayerCharacter.createCharacter(1,"Essling",false));

        saveHeroes();

    }

    public void saveHeroes() {
        String filename = "heroes";
        FileOutputStream outputStream;


        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(gson.toJson(heroTeam).getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Connect the client.
        mGoogleApiClient.connect();
    }
    @Override
    protected void onStop() {
        // Disconnecting the client invalidates it.
        mGoogleApiClient.disconnect();
        super.onStop();
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds gameItems to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("test","location requested");
        mLocationRequest = LocationRequest.create();

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000); // Update location every second
        try{
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
        }
        catch (SecurityException e){
            Log.d("Error","no permission for location");
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        if (map != null){
            currentLocation = location;
            LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
            if (player == null)
                player = map.addMarker(new MarkerOptions().position(ll).icon(BitmapDescriptorFactory.fromResource(R.drawable.viking)).title("Essling").snippet(""));
            else
                player.setPosition(ll);
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(ll,18f));
            long time = Calendar.getInstance().getTime().getTime();
            List<Marker> temp = new ArrayList<>();
            temp.addAll(currentFights.keySet());
            for (Marker m : temp)
            {

                if (currentFights.get(m).timeCreated.getTime() +600000 <= time){
                    currentFights.remove(m);
                    m.remove();
                }

            }


            if (currentFights.size() <= 6 && Math.random()<1){

                int i =0;
                for (PlayerCharacter pc : heroTeam){
                    if (pc.isActive)
                        i+=pc.lvl;

                }


                Fight fight = new Fight(i/3,ll);
                String s = "";
                for (int j =0; j<fight.enemies.size();j++){
                    if (j!= 0)
                        s+="\n";
                    s+= "lvl "+fight.fightlvl+" "+fight.enemies.get(j).getTypeString();
                }


                Marker newMarker = map.addMarker(new MarkerOptions().position(randLoc(ll,100)).title(s).snippet(fight.difficulty));
                newMarker.setIcon(BitmapDescriptorFactory.fromResource(fight.enemies.get(0).getMapImageId()));

                currentFights.put(newMarker,fight);
            //    saveFightList();
            }
            //map.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitude())).title());
        }
    }

    /**
     * generates a random location
     * @param ll
     * @param radius in meter
     * @return random location in a circle of radius radius around coordinate ll
     */

    public  LatLng randLoc(LatLng ll, int radius) {
        double y0 = ll.latitude, x0 = ll.longitude;
        Random random = new Random();

        // Convert radius from meters to degrees
        double radiusInDegrees = radius / 111000f;

        double u = random.nextDouble();
        double v = random.nextDouble();
        double w = radiusInDegrees * Math.sqrt(u);
        double t = 2 * Math.PI * v;
        double x = w * Math.cos(t);
        double y = w * Math.sin(t);

        // Adjust the x-coordinate for the shrinking of the east-west distances
        double new_x = x / Math.cos(y0);

        double foundLongitude = new_x + x0;
        double foundLatitude = y + y0;
        LatLng res = new LatLng(foundLatitude, foundLongitude);
        return res;
    }

    @Override
    public void mapReady(GoogleMap map) {
        this.map = map;
        loadFightList();
    }

    @Override
    public void fightIfCloseEnough(Marker marker) {
        Location loc = new Location("me");

        loc.setLatitude(marker.getPosition().latitude);
        loc.setLongitude(marker.getPosition().longitude);

        if (loc.distanceTo(currentLocation)<=20){
            if (currentFights.containsKey(marker)){
                CharData cd = new CharData();
                VicData vd = new VicData();
                for (PlayerCharacter pc : heroTeam){
                    if (pc.isActive){
                        cd.addChar(pc);
                        vd.addChar(pc);
                    }
                }
                ArrayList<Integer> enemyTypes = new ArrayList<>();
                for(GameCharacter gc : currentFights.get(marker).enemies){
                    cd.addChar(gc);
                    enemyTypes.add(gc.model);
                }


                vd.addReward(currentFights.get(marker));
                currentFights.remove(marker);
                saveFightList();

                Log.d("test","starting unity?");
                Log.d("test",gson.toJson(vd));
                Intent intent = new Intent(this,UnityPlayerActivity.class);
                intent.putExtra("enemyTypes",enemyTypes);
                intent.putExtra("charData",gson.toJson(cd));
                intent.putExtra("vicData",gson.toJson(vd));
                Log.d("test",gson.toJson(vd));
                Log.d("test",gson.toJson(cd));
                startActivity(intent);
            }
            else{
                Toast.makeText(this,"stop hitting yourself", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this,"Nicht nah genug dran",Toast.LENGTH_SHORT).show();
        }



    }

    @Override
    public void onBackPressed() {
    }

    private void loadFightList(){
        long time = Calendar.getInstance().getTime().getTime();
        Log.d("test","lFl");
        if(new File(getFilesDir().toString()+"/fights").exists()){
            Log.d("test","file exists");
            try {
                FileInputStream inputStream = openFileInput("fights");
                InputStreamReader streamReader = new InputStreamReader(inputStream);
                BufferedReader Br = new BufferedReader(streamReader);
                String s = Br.readLine();
                Log.d("test","saved location: "+s);
                Fight[] fights = gson.fromJson(s,Fight[].class);
                Log.d("test","length: "+fights.length);
                for (Fight fight : fights) {
                    if (fight.timeCreated.getTime()+60000 <= time && map!= null){
                        String s2 = "";
                        for (int j =0; j<fight.enemies.size();j++){
                            if (j!= 0)
                                s2+="\n";
                            s2+= "lvl "+fight.fightlvl+" "+fight.enemies.get(j).getTypeString();
                        }
                        Marker newMarker = map.addMarker(new MarkerOptions().position(randLoc(fight.position,100)).title(s2).snippet(fight.difficulty));
                        newMarker.setIcon(BitmapDescriptorFactory.fromResource(fight.enemies.get(0).getMapImageId()));
                        currentFights.put(newMarker,fight);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void saveFightList() {
        String filename = "fights";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(gson.toJson(currentFights.values()).getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void questFragmentReady(QuestFragment qf) {
        questFragment = qf;
        if (!new File(getFilesDir().toString()+"/quests").exists()){
            questFragment.createMinorQuest();
            questFragment.createMajorQuest();
            saveQuests();
        }
        loadQuests();
        if (this.getIntent().getExtras() != null && this.getIntent().getExtras().containsKey("enemyTypes")&& !questsUpdated){
            ArrayList<Integer> types = getIntent().getIntegerArrayListExtra("enemyTypes");
            for (int i : types){
                if(i<=4)
                questFragment.increaseCounters(i);
                if (i>=4)
                    questFragment.increaseCounters(i-4);
            }
            if (types.size() == 1)
                questFragment.increaseCounters(Quest.killSolo);
            saveQuests();
            questsUpdated = true;
        }
    }

    private void saveQuests() {
        QuestSave qs = new QuestSave(questFragment.major,questFragment.minor);
        String filename = "quests";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(gson.toJson(qs).getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void generateReward(boolean major) {
        saveQuests();
        int lvl =0;
        for (PlayerCharacter pc : heroTeam){
            if (pc.isActive)
                lvl+=pc.lvl;
        }
        int type = (int)(Math.random()*3);
        int charType = (int)(Math.random()*4);
        GameItem item;
        if (major)
            item = (GameItem.createItem(type,1,lvl/3,charType));
        else
            item =(GameItem.createItem(type,0,lvl/3,charType));
        gameItems.add(item);
        Toast.makeText(this,item.name+" erhalten.",Toast.LENGTH_SHORT).show();
    }

    public void turnInQuests(View view){
        Log.d("test","minor finished: "+questFragment.minor.isFinished);
        if (questFragment.minor.isFinished){
            questFragment.createMinorQuest();
            questFragment.increaseCounters(Quest.completeQuests);
            generateReward(false);
        }
        if (questFragment.major.isFinished){
            questFragment.createMajorQuest();
            generateReward(true);
        }
    }


    private void loadQuests() {
        try {
            FileInputStream inputStream = openFileInput("quests");
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader Br = new BufferedReader(streamReader);
            String s = Br.readLine();
            QuestSave qs = gson.fromJson(s,QuestSave.class);
            Log.d("test",s);
            questFragment.loadSave(qs);
        }catch (Exception e){
            e.printStackTrace();
        }


    }
/*
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
*/
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            //    TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //   textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if (position == 1) return new MapFrag();
            if (position==0)return  CharFrag.newInstance(heroTeam,gameItems);
            if (position == 2) return QuestFragment.newInstance();

            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
