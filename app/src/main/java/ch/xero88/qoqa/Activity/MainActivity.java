package ch.xero88.qoqa.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.ParseUser;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import ch.xero88.qoqa.Fragment.CouponFragment;
import ch.xero88.qoqa.Fragment.ErrorFragment;
import ch.xero88.qoqa.Fragment.GiftFragment;
import ch.xero88.qoqa.Fragment.HomeFragment;
import ch.xero88.qoqa.Fragment.OfferListFragment;
import ch.xero88.qoqa.Fragment.WinnerFragment;
import ch.xero88.qoqa.Model.User;
import ch.xero88.qoqa.R;
import ch.xero88.qoqa.Service.Callback.OrderCallback;
import ch.xero88.qoqa.Service.OrderService;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LogOutCallback {

    @Bind(R.id.main_container) FrameLayout mainContainer;

    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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

        ParseUser user = ParseUser.getCurrentUser();
        if(user == null){
            // login / signUp
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return;
        }

        // Parse : subscribe in channel by the name of user
        ParsePush.subscribeInBackground("QoQa_" + user.getObjectId());

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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void selectFragment(Intent intent) {

        Fragment selectedFragment = null;

        Bundle extras = intent.getExtras();
        if(extras != null && intent.hasExtra("giftId")) {
            selectedFragment = selectWinnerFragment(intent);
        }
        else {
            selectedFragment = selectHomeFragment(intent);
        }


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, selectedFragment).commit();

    }

    private Fragment selectHomeFragment(Intent intent) {

        Fragment selectedFragment;
        selectedFragment = new HomeFragment();
        selectedFragment.setArguments(intent.getExtras());
        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.getMenu().getItem(4).getSubMenu().getItem(0).setChecked(false);
        return selectedFragment;
    }

    private Fragment selectWinnerFragment(Intent intent) {
        Fragment selectedFragment;
        selectedFragment = new WinnerFragment();
        selectedFragment.setArguments(intent.getExtras());
        navigationView.getMenu().getItem(4).getSubMenu().getItem(0).setChecked(false);
        return selectedFragment;
    }

    private Fragment selectOfferListFragment(Intent intent) {

        Fragment selectedFragment;
        selectedFragment = new OfferListFragment();
        selectedFragment.setArguments(intent.getExtras());
        navigationView.getMenu().getItem(1).setChecked(true);
        navigationView.getMenu().getItem(4).getSubMenu().getItem(0).setChecked(false);

        return selectedFragment;
    }

    private Fragment selectCouponFragment(Intent intent) {

        Fragment selectedFragment;
        selectedFragment = new CouponFragment();
        selectedFragment.setArguments(intent.getExtras());
        navigationView.getMenu().getItem(0).setChecked(false);
        navigationView.getMenu().getItem(1).setChecked(false);
        navigationView.getMenu().getItem(2).setChecked(false);
        navigationView.getMenu().getItem(3).setChecked(false);
        navigationView.getMenu().getItem(4).getSubMenu().getItem(0).setChecked(true);

        return selectedFragment;
    }

    private Fragment selectGiftFragment(Intent intent) {
        Fragment selectedFragment;
        selectedFragment = new GiftFragment();
        intent.putExtra(GiftFragment.SHOW_WINNING_GIFTS, false);
        selectedFragment.setArguments(intent.getExtras());
        navigationView.getMenu().getItem(2).setChecked(true);
        navigationView.getMenu().getItem(4).getSubMenu().getItem(0).setChecked(false);
        return selectedFragment;
    }

    private Fragment selectWinningGiftFragment(Intent intent) {
        Fragment selectedFragment;
        selectedFragment = new GiftFragment();
        intent.putExtra(GiftFragment.SHOW_WINNING_GIFTS, true);
        selectedFragment.setArguments(intent.getExtras());
        navigationView.getMenu().getItem(3).setChecked(true);
        navigationView.getMenu().getItem(4).getSubMenu().getItem(0).setChecked(false);
        return selectedFragment;
    }

    private Fragment selectErrorFragment(Intent intent, String errorMessage) {

        Fragment selectedFragment = new ErrorFragment();
        if(errorMessage != null && errorMessage != "") {
            intent.putExtra(ErrorFragment.ERROR_MESSAGE, errorMessage);
            selectedFragment.setArguments(intent.getExtras());
        }
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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_offers) {

            Fragment selectedFragment = selectOfferListFragment(getIntent());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, selectedFragment).commit();

        }
        else if (id == R.id.nav_home) {

            Fragment selectedFragment = selectHomeFragment(getIntent());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, selectedFragment).commit();

        }
        else if (id == R.id.nav_gift_of_month) {

            Fragment selectedFragment = selectGiftFragment(getIntent());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, selectedFragment).commit();


        }
        else if (id == R.id.nav_gift_winning) {

            Fragment selectedFragment = selectWinningGiftFragment(getIntent());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, selectedFragment).commit();


        }
        else if (id == R.id.nav_my_coupons) {

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

    /* External navigation */

    public void goToCouponFragment(){

        Fragment selectedFragment = selectCouponFragment(getIntent());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, selectedFragment).commit();

    }

    public void openErrorFragment(String errorMessage) {

        Fragment selectedFragment = selectErrorFragment(getIntent(), errorMessage);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, selectedFragment).commit();

    }

    /* Callbacks */

    @Override
    public void done(ParseException e) {

        // after logout
        if (e == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        } else {
            Log.e("Error", e.getMessage());
            this.openErrorFragment("");
        }

    }

    /* Click handlers */

    public void iWantItClickButton(View view) {

        OrderService orderService = new OrderService();
        orderService.sendOrder(ParseUser.getCurrentUser(), new OrderCallback(this));

    }

    public void buyOfferNowButtonClick(View view) {

        Fragment selectedFragment = selectOfferListFragment(getIntent());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, selectedFragment).commit();

    }

}
