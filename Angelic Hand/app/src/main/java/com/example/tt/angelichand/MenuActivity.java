package com.example.tt.angelichand;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static String FLAG_MENU;
    String type,type1;
    String user_name,user_id,user_pic;
    ImageView new_post_img;
    Toolbar toolbar;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    DrawerLayout drawer;
    NavigationView navigationView;
    TextView user_name_tv;
    CircleImageView user_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        user_name_tv=headerView.findViewById(R.id.navigation_header_username_textview);
        user_image=(CircleImageView) headerView.findViewById(R.id.navigation_header_user_image_img);
        new_post_img=findViewById(R.id.new_post_imageview);
        receive_data_from_phone();
        if (type.equals("volunteer") || type.equals("donor")) {
            new_post_img.setVisibility(View.VISIBLE);
        }
        else if (type.equals("organization")){

            new_post_img.setVisibility(View.INVISIBLE);


            Menu menu = navigationView.getMenu();
            menu.removeItem(R.id.nav_cloth);
            menu.removeItem(R.id.nav_food);
            menu.removeItem(R.id.nav_charity);

            menu.add(0,1,1, "My Volunteers");
            menu.add(0,2,2, "Request");
            navigationView.invalidate();

        }

//        else {
//            //new_post_img.setVisibility(View.VISIBLE);
//            Intent intent = getIntent();
//            type1 = intent.getStringExtra(FLAG_MENU);
//
//            if (type1.equals("organization")){
//
//                new_post_img.setVisibility(View.INVISIBLE);
//            }
//           else if(type1.equals("volunteer")){
//                new_post_img.setVisibility(View.VISIBLE);
//
//            }
//            else {
//                Toast.makeText(MenuActivity.this,"thek",Toast.LENGTH_LONG).show();
//            }
//        }



        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();






        display_data();


        if(savedInstanceState!=null){


        }
        if (savedInstanceState==null) {

            // toolbar = (Toolbar) findViewById(R.id.toolbar);
            // setSupportActionBar(toolbar);


            getSupportActionBar().setTitle("News Feed");
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.menuactivity_frame, new AllCategoryFragment());
            fragmentTransaction.commit();

            navigationView.setCheckedItem(R.id.nav_all_category);
        }
        user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_to_user_profile();
            }
        });

        new_post_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                send_to_new_post_fragment();

            }
        });







    }




    private void display_data() {

        if (!(user_name.isEmpty() || user_name==null)){
            user_name_tv.setText(user_name);
        }

        if (!(user_pic.isEmpty() || user_pic==null)){

        }String url="http://192.168.43.104:8080/Angelic_Hand/images/";
        url=url+user_pic;
        //Toast.makeText(MenuActivity.this,url,Toast.LENGTH_LONG).show();
        Picasso.get().load(url).fit().centerCrop().placeholder(R.drawable.icon_user_profile).into(user_image);
    }

    private void receive_data_from_phone() {

        sharedPreferences = getSharedPreferences("Angelic_Hand_user_data",MODE_PRIVATE);

        type=   sharedPreferences.getString("type","awe");
        user_id = sharedPreferences.getString("userid","tayy");

        if (type.equals("volunteer") || type.equals("donor")){
            String fname = sharedPreferences.getString("userfname","tayy");
            String lname = sharedPreferences.getString("userlname","tayy");
            user_pic= sharedPreferences.getString("userpicture","tayy");
            user_name=fname+" "+lname;
        }
        else if (type.equals("organization")){

            user_name= sharedPreferences.getString("organization_name","tayy");
            user_pic= sharedPreferences.getString("organization_pic","tayy");
        }





    }

    private void send_to_user_profile() {

        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        new_post_img.setVisibility(View.INVISIBLE);
        toolbar.setVisibility(View.GONE);
        android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.menuactivity_frame,new ProfileFragment())
                .addToBackStack(null);
        fragmentTransaction.commit();


    }

    private void send_to_new_post_fragment() {

        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        new_post_img.setVisibility(View.INVISIBLE);
        toolbar.setVisibility(View.GONE);
        toolbar.getMenu().close();
        android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.menuactivity_frame,new NewPostFragment())
                .addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        //this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_logout) {

            sharedPreferences =getSharedPreferences("Angelic_Hand_user_data",MODE_PRIVATE);
            editor=sharedPreferences.edit();
            editor.putBoolean("islogin",false);
            editor.apply();

            Intent intent=new Intent(MenuActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        else if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_all_category) {


            getSupportActionBar().setTitle("News Feed");
            android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.menuactivity_frame,new AllCategoryFragment())
            .addToBackStack(null);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_food) {
            getSupportActionBar().setTitle("Food");
            android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.menuactivity_frame,new FoodFragment())
                    .addToBackStack(null);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_cloth) {
            getSupportActionBar().setTitle("Cloth");
            android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.menuactivity_frame,new ClothFragment())
                    .addToBackStack(null);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_charity) {

            getSupportActionBar().setTitle("Charity");
            android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.menuactivity_frame,new CharityFragment())
                    .addToBackStack(null);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_organization) {

        } else if (id == R.id.nav_volunteer) {

            Bundle bundle=new Bundle();


            bundle.putString("from","all_vol");




            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

            new_post_img.setVisibility(View.INVISIBLE);
            toolbar.setVisibility(View.GONE);
            toolbar.getMenu().close();

            VolunteerFragment v =new VolunteerFragment();
            v.setArguments(bundle);
            android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.menuactivity_frame,v)
                    .addToBackStack(null);
            fragmentTransaction.commit();
        }

        else if (id == 1) {

            //Toast.makeText(MenuActivity.this,"ma he ho",Toast.LENGTH_LONG).show();

            Bundle bundle=new Bundle();
            if(type.equals("organization")){
                bundle.putString("from","my_vol");
                bundle.putString("name",user_name);
                //Toast.makeText(MenuActivity.this,"ma "+user_name,Toast.LENGTH_LONG).show();

            }
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

            new_post_img.setVisibility(View.INVISIBLE);
            toolbar.setVisibility(View.GONE);
            toolbar.getMenu().close();

            VolunteerFragment v =new VolunteerFragment();
            v.setArguments(bundle);
            android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.menuactivity_frame,v)
                    .addToBackStack(null);
            fragmentTransaction.commit();


        }

        else if (id==2){

            Bundle bundle=new Bundle();
            if(type.equals("organization")){
                bundle.putString("from","req_vol");
                bundle.putString("name",user_name);
                //Toast.makeText(MenuActivity.this,"ma "+user_name,Toast.LENGTH_LONG).show();

            }


            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

            new_post_img.setVisibility(View.INVISIBLE);
            toolbar.setVisibility(View.GONE);
            toolbar.getMenu().close();

            VolunteerFragment v =new VolunteerFragment();
            v.setArguments(bundle);
            android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.menuactivity_frame,v)
                    .addToBackStack(null);
            fragmentTransaction.commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
