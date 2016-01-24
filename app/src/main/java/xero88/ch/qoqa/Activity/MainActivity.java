package xero88.ch.qoqa.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.ParseUser;

import butterknife.Bind;
import butterknife.ButterKnife;
import xero88.ch.qoqa.Fragment.CouponFragment;
import xero88.ch.qoqa.Fragment.GiftFragment;
import xero88.ch.qoqa.Fragment.OfferListFragment;
import xero88.ch.qoqa.Model.User;
import xero88.ch.qoqa.R;
import xero88.ch.qoqa.Service.Callback.OrderCallback;
import xero88.ch.qoqa.Service.OrderService;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LogOutCallback {

    @Bind(R.id.main_container) FrameLayout mainContainer;

    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Parse : subscribe in channel by the name of user
        ParseUser user = ParseUser.getCurrentUser();
        ParsePush.subscribeInBackground("QoQa_" + user.getObjectId());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headView = navigationView.getHeaderView(0);

        // set current user email and name on drawer
        ((TextView) headView.findViewById(R.id.emailUser)).setText(user.getUsername());
        ((TextView) headView.findViewById(R.id.firstNameAndLastName)).setText((String)user.get(User.FIRSTNAME) + " " + (String)user.get(User.LASTNAME));

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (mainContainer != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            selectFragment(getIntent());
        }

    }

    private void selectFragment(Intent intent) {

        // Selected Fragment
        Fragment selectedFragment = selectOfferListFragment(intent);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, selectedFragment).commit();

    }

    private Fragment selectOfferListFragment(Intent intent) {

        Fragment selectedFragment;
        selectedFragment = new OfferListFragment();
        selectedFragment.setArguments(intent.getExtras());
        navigationView.getMenu().getItem(0).setChecked(true);

        return selectedFragment;
    }

    private Fragment selectCouponFragment(Intent intent) {

        Fragment selectedFragment;
        selectedFragment = new CouponFragment();
        selectedFragment.setArguments(intent.getExtras());
        //navigationView.getMenu().getItem(1).setChecked(true);

        return selectedFragment;
    }

    private Fragment selectGiftFragment(Intent intent) {
        Fragment selectedFragment;
        selectedFragment = new GiftFragment();
        selectedFragment.setArguments(intent.getExtras());
        navigationView.getMenu().getItem(1).setChecked(true);

        return selectedFragment;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_offers) {

            Fragment selectedFragment = selectOfferListFragment(getIntent());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, selectedFragment).commit();

        } else if (id == R.id.nav_gift_of_month) {

            Fragment selectedFragment = selectGiftFragment(getIntent());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, selectedFragment).commit();


        } else if (id == R.id.nav_my_coupons) {

            Fragment selectedFragment = selectCouponFragment(getIntent());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, selectedFragment).commit();

        } else if (id == R.id.nav_logout) {

            ParseUser.logOutInBackground(this);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void iWantItClickButton(View view) {

        OrderService orderService = new OrderService();
        orderService.sendOrder(ParseUser.getCurrentUser(), new OrderCallback(this));

    }

    @Override
    public void done(ParseException e) {

        // after logout
        if (e == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();

        } else {
            Log.e("Error", e.getMessage());
            Toast.makeText(this, getString(R.string.main_activity_error_while_loggout), Toast.LENGTH_LONG).show();
        }


    }
}
