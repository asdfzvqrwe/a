package com.example.qwer0511

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*




class MainActivity : AppCompatActivity() {
    //private var mChatService: BluetoothChatService? = null
    private var mOutEditText: EditText? = null
    private var mOutStringBuffer: StringBuffer? = null
    private var mConversationArrayAdapter: ArrayAdapter<String>? = null
    private var mConversationView: ListView? = null
    private var mConnectedDeviceName: String? = null
    private var mBluetoothAdapter: BluetoothAdapter? = null


    private var mLogShown = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //supportActionBar?.setHomeAsUpIndicator(R.drawable.new_sejin_logo_8)

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        val fragmentList = listOf(FragmentD(), FragmentA(), FragmentB(), FragmentC())
        val adapter = FragmentAdapter(this)
        adapter.fragmentList = fragmentList
        viewPager.adapter = adapter

        val tabTitles = listOf<String>("COMMON","RADIO", "CDP", "USB")
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.bluetooth_chat, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onStart() {
        super.onStart()
        if (mBluetoothAdapter == null) {
            return
        }
        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter!!.isEnabled) {
            val enableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT)
            // Otherwise, setup the chat session
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mChatService != null) {
            mChatService!!.stop()
        }
    }

    override fun onResume() {
        super.onResume()

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mChatService?.getState() == BluetoothChatService.Companion.STATE_NONE) {
                // Start the Bluetooth chat services
                mChatService!!.start()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CONNECT_DEVICE_SECURE ->                 // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, true)
                }
            REQUEST_CONNECT_DEVICE_INSECURE ->                 // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, false)
                }
            REQUEST_ENABLE_BT ->                 // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    //setupChat()
                } /**else {
                    // User did not enable Bluetooth or an error occurred
                    Log.d(TAG, "BT not enabled")
                    val activity = activity
                    if (activity != null) {
                        Toast.makeText(activity, R.string.bt_not_enabled_leaving,
                                Toast.LENGTH_SHORT).show()
                        activity.finish()
                    }
                }**/
        }
    }

    private fun connectDevice(data: Intent?, secure: Boolean) {
        // Get the device MAC address
        val extras = data!!.extras ?: return
        val address = extras.getString(DeviceListActivity.Companion.EXTRA_DEVICE_ADDRESS)
        // Get the BluetoothDevice object
        val device = mBluetoothAdapter!!.getRemoteDevice(address)
        // Attempt to connect to the device

        mChatService = BluetoothChatService(this, mHandler)

        mChatService!!.connect(device, secure)
    }

    /**override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.bluetooth_chat, menu)
    }**/

    private fun ensureDiscoverable() {
        if (mBluetoothAdapter!!.scanMode !=
                BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            val discoverableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300)
            startActivity(discoverableIntent)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.secure_connect_scan -> {

                // Launch the DeviceListActivity to see devices and do scan
                val serverIntent = Intent(this, DeviceListActivity::class.java)
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE)
                return true
            }
            R.id.insecure_connect_scan -> {

                // Launch the DeviceListActivity to see devices and do scan
                val serverIntent = Intent(this, DeviceListActivity::class.java)
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_INSECURE)
                return true
            }
            R.id.discoverable -> {

                // Ensure this device is discoverable by others
                ensureDiscoverable()
                return true
            }
        }
        return false
    }



    private val mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            //val activity = activity
            when (msg.what) {
                Constants.Companion.MESSAGE_STATE_CHANGE -> when (msg.arg1) {
                    BluetoothChatService.Companion.STATE_CONNECTED -> {
                        //setStatus(getString(R.string.title_connected_to, mConnectedDeviceName))
                        //mConversationArrayAdapter!!.clear()
                    }
                    //BluetoothChatService.Companion.STATE_CONNECTING -> setStatus(R.string.title_connecting)
                    //BluetoothChatService.Companion.STATE_LISTEN, BluetoothChatService.Companion.STATE_NONE -> setStatus(R.string.title_not_connected)
                }
                Constants.Companion.MESSAGE_WRITE -> {
                    val writeBuf = msg.obj as ByteArray
                    // construct a string from the buffer/
                    val writeMessage = String(writeBuf)
                    //mConversationArrayAdapter!!.add("Me:  $writeMessage")
                }
                Constants.Companion.MESSAGE_READ -> {
                    val readBuf = msg.obj as ByteArray
                    // construct a string from the valid bytes in the buffer
                    val readMessage = String(readBuf, 0, msg.arg1)
                    //mConversationArrayAdapter!!.add("$mConnectedDeviceName:  $readMessage")
                }
                Constants.Companion.MESSAGE_DEVICE_NAME -> {
                    // save the connected device's name
                    //mConnectedDeviceName = msg.data.getString(Constants.Companion.DEVICE_NAME)
                    if (null != this) {
                        //Toast.makeText(this, "Connected to "
                                // +mConnectedDeviceName, Toast.LENGTH_SHORT).show()
                    }
                }
                Constants.Companion.MESSAGE_TOAST -> if (null != this) {
                    //Toast.makeText(this, msg.data.getString(Constants.Companion.TOAST),
                            //Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        var mChatService: BluetoothChatService? = null

        private const val REQUEST_CONNECT_DEVICE_SECURE = 1
        private const val REQUEST_CONNECT_DEVICE_INSECURE = 2
        private const val REQUEST_ENABLE_BT = 3
    }


}