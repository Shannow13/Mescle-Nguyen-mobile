package esiea.mescle_nguyen_mobile;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class MainActivity2 extends ListActivity {

    private static final String url = "http://binouze.fabrigli.fr/bieres.json";
    private static final String beer_name = "name";
    private static final String beer_category = "category";
    private static final String beer_buveur = "buveur";
    private static final String beer_note = "note_moyenne";

    ArrayList<HashMap<String, String>> jsonlist = new ArrayList<HashMap<String, String>>();
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        new ProgressTask(MainActivity2.this).execute();
    }

    private class ProgressTask extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog dialog;

        public ProgressTask(ListActivity activity) {
            context = activity;
            dialog = new ProgressDialog(context);
        }

        private Context context;

        protected void onPreExecute() {
            this.dialog.setMessage("Chargement en cours,\nveuillez patienter s'il vous plaît...");
            this.dialog.show();
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            ListAdapter adapter = new SimpleAdapter(context, jsonlist, R.layout.list_activity, new String[]{beer_name, beer_category, beer_buveur, beer_note}, new int[]{R.id.beer_name, R.id.beer_category, R.id.beer_buveur, R.id.beer_note});
            setListAdapter(adapter);
            lv = getListView();
        }

        // Sort the HashMap
        class MapComparator implements Comparator<HashMap<String, String>>
        {
            private final String key;

            public MapComparator(String key)
            {
                this.key = key;
            }

            public int compare(HashMap<String, String> first, HashMap<String, String> second)
            {
                String firstValue = first.get(key);
                String secondValue = second.get(key);
                return firstValue.compareTo(secondValue);
            }
        }

        protected Boolean doInBackground(final String... args) {
            JSONParser jParser = new JSONParser();
            JSONArray json = jParser.getJSONFromUrl(url);

            for (int i = 0; i < json.length(); i++) {
                try {
                    JSONObject c = json.getJSONObject(i);

                    // Get the data from the JSON Array
                    String bname = c.getString(beer_name);
                    String bcategory = c.getString(beer_category);
                    String bbuveur = c.getString(beer_buveur);
                    String bnote = c.getString(beer_note);

                    // "toUpperCase" the data
                    String bnameMaj = bname.replaceFirst(".", (bname.charAt(0) + "").toUpperCase());
                    String bcategoryMaj = bcategory.replaceFirst(".", (bcategory.charAt(0) + "").toUpperCase());
                    String bbuveurMaj = bbuveur.replaceFirst(".", (bbuveur.charAt(0) + "").toUpperCase());
                    String bnoteMaj = bnote.replaceFirst(".",(bnote.charAt(0)+"").toUpperCase());

                    HashMap<String, String> map = new HashMap<String, String>();

                    // Verify if any data equals to null
                    if(bname == "null"){
                        bnameMaj = "Nom inconnu";
                    }

                    if(bcategory == "null"){
                        bcategoryMaj = "Inconnue";
                    }

                    if(bbuveur == "null"){
                        bbuveurMaj = "Inconnu";
                    }

                    if(bnote == "null"){
                        bnoteMaj = "Inconnue";
                    }

                    // Put the data to the HashMap
                    map.put(beer_name, bnameMaj);
                    map.put(beer_category, "Catégorie : " + bcategoryMaj);
                    map.put(beer_buveur, "Buveur : " + bbuveurMaj);
                    map.put(beer_note, "Note moyenne : " + bnoteMaj);

                    // Add the HashMap to the ArrayList
                    jsonlist.add(map);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // Sort the ArrayList by the beer_name
            Collections.sort(jsonlist, new MapComparator(beer_name));

            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Button settings (Action Bar)
        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(), "Et non, c'est un faux bouton désolé ^.^", Toast.LENGTH_SHORT).show();
        }

        // Button contacts (Action Bar)
        if (id == R.id.action_contacts) {
            Toast.makeText(getApplicationContext(), "Et non, c'est un faux bouton désolé ^.^", Toast.LENGTH_SHORT).show();
        }

        return true;
    }
}

