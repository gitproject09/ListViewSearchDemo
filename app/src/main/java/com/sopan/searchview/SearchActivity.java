package com.sopan.searchview;

import android.content.Context;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {

    private ListView listView;
    private MyAppAdapter myAppAdapter;
    private ArrayList<Post> postArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        listView= (ListView) findViewById(R.id.listView);
        postArrayList=new ArrayList<>();
        postArrayList.add(new Post("Sopan Ahmed", "Aditional Mohtamim Taleem"));
        postArrayList.add(new Post("Munadil Fahad", "Sadr from majalis khuddamul Ahmadiyya Bangladesh"));
        postArrayList.add(new Post("Shahin Ahmed", "Kaid, Majalis Khuddamul Ahmadiyya, Tarua"));
        postArrayList.add(new Post("Kawsar Ahmed", "Mohaseb, MKA Bangladesh"));
        postArrayList.add(new Post("Mahmood Ahmed Buet", "Mohatamim Tajneed, MKA Bangladesh"));
        postArrayList.add(new Post("Abdur Rouf", "Regional Qaid, Rangpur"));
        postArrayList.add(new Post("Sabbir Ahmmed", "Jela Qaid, Brahmanbaria"));
        postArrayList.add(new Post("Mishkatul Haque Shakil", "Asst. Mohtamim Atfal MKA Bangladesh"));
        postArrayList.add(new Post("Mahmud Ahmed", "Batgaon, Dinajpur"));
        postArrayList.add(new Post("Al Amin Tushar", "Purulia Majlish"));
        postArrayList.add(new Post("SH Mishu", "DQ, Sylhet"));
        postArrayList.add(new Post("Mahmood Ahmed Pallab", "DQ, Mymensingh"));
        postArrayList.add(new Post("Shimran Mirza", "LQ, Chittagong"));

        myAppAdapter=new MyAppAdapter(postArrayList,SearchActivity.this);
        listView.setAdapter(myAppAdapter);
    }

    public class MyAppAdapter extends BaseAdapter {

        public class ViewHolder {
            TextView txtTitle,txtSubTitle;


        }

        public List<Post> parkingList;

        public Context context;
        ArrayList<Post> arraylist;

        private MyAppAdapter(List<Post> apps, Context context) {
            this.parkingList = apps;
            this.context = context;
            arraylist = new ArrayList<Post>();
            arraylist.addAll(parkingList);

        }

        @Override
        public int getCount() {
            return parkingList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View rowView = convertView;
            ViewHolder viewHolder;

            if (rowView == null) {
                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.item_post, null);
                // configure view holder
                viewHolder = new ViewHolder();
                viewHolder.txtTitle = (TextView) rowView.findViewById(R.id.title);
                viewHolder.txtSubTitle = (TextView) rowView.findViewById(R.id.subtitle);
                rowView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.txtTitle.setText(parkingList.get(position).getPostTitle() + "");
            viewHolder.txtSubTitle.setText(parkingList.get(position).getPostSubTitle() + "");
            return rowView;


        }

        public void filter(String charText) {

            charText = charText.toLowerCase(Locale.getDefault());

            parkingList.clear();
            if (charText.length() == 0) {
                parkingList.addAll(arraylist);

            } else {
                for (Post postDetail : arraylist) {
                    if (charText.length() != 0 && postDetail.getPostTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                        parkingList.add(postDetail);
                    }

                    else if (charText.length() != 0 && postDetail.getPostSubTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                        parkingList.add(postDetail);
                    }
                }
            }
            notifyDataSetChanged();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        //*** setOnQueryTextFocusChangeListener ***
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchQuery) {
                myAppAdapter.filter(searchQuery.toString().trim());
                listView.invalidate();
                return true;
            }
        });

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when collapsed
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                return true;  // Return true to expand action view
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
