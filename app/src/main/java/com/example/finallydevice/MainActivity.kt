package com.example.finallydevice

import android.Manifest
import android.app.AppOpsManager
import android.content.*
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.example.finallydevice.AppusingStatics.Appusing.BarchartViewModel
import com.example.finallydevice.databinding.ActivityMainBinding
import com.example.finallydevice.ScreenStatics.home.HomeViewModel
import com.example.finallydevice.share.Service_function.ScreenUsingTime
import com.example.finallydevice.share.Factory.MyviewModelFactory
import com.example.finallydevice.shareimage.CreateImage
import com.example.finallydevice.shareimage.Saveimage
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding:ActivityMainBinding
    private lateinit var viewModel:BarchartViewModel
    private lateinit var navController: NavController
    @RequiresApi(Build.VERSION_CODES.M)
    private lateinit var requestlaunch:ActivityResultLauncher<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestlaunch=registerForActivityResult(ActivityResultContracts.RequestPermission()){
            if(it)
                Log.d("permission","Y" )
            else
            {
                val intent1:Intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
                startActivity(intent1)
            }
        }

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)
        val appOps: AppOpsManager =  this.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow("android:get_usage_stats", android.os.Process.myUid(), this.getPackageName());
        if(mode != AppOpsManager.MODE_ALLOWED)
            requestlaunch.launch(Manifest.permission.PACKAGE_USAGE_STATS)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,R.id.settingsFragment,R.id.todolistfragment,R.id.AppStaticsFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        startService(Intent(this, ScreenUsingTime::class.java))

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val viewmodel= ViewModelProvider(
            this,
            MyviewModelFactory(this.application)
        ).get(HomeViewModel::class.java)
        when(item.itemId)
        {
            R.id.share->{
                val shareview=CreateImage(this)
                shareview.setInfo(viewmodel.time.value)
                val image=shareview.createImage()
                val path = Saveimage.saveImage(image,this)
                val share_intent= Intent()
                share_intent.setAction(Intent.ACTION_SEND)
                share_intent.setType("image/*")
                share_intent.putExtra(Intent.EXTRA_STREAM,path)
                val share= Intent.createChooser(share_intent, "share")
                startActivity(share)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
