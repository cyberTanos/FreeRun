package com.example.maps

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.maps.databinding.ActivityMainBinding
import com.example.maps.other.ACTION_SHOW_TRACKING_FRAGMENT
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navigationToTrackingFragmentIfNeeded(intent)
        setupBottomNav()
    }

    private fun setupBottomNav() {
        binding.bottomNav.setupWithNavController(findNavController(R.id.nav_host_fragment_container))
        binding.bottomNav.setOnNavigationItemReselectedListener { /* NO-OP*/ }
        findNavController(R.id.nav_host_fragment_container).addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNav.isVisible = destination.id != R.id.setupFragment && destination.id != R.id.trackingFragment
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        navigationToTrackingFragmentIfNeeded(intent)
    }

    private fun navigationToTrackingFragmentIfNeeded(intent: Intent?) {
        if (intent?.action == ACTION_SHOW_TRACKING_FRAGMENT) {
            findNavController(R.id.nav_host_fragment_container).navigate(R.id.action_global_trackingFragment)
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
