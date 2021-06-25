package com.example.qwer0511

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.EditorInfo
import android.widget.*

import com.example.android.common.activities.SampleActivityBase
import com.example.android.common.logger.Log

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentC.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentC : Fragment() {
    private var mSendButton19: Button? = null
    private var mSendButton20: Button? = null
    private var mSendButton21: Button? = null
    private var mSendButton22: Button? = null
    private var mSendButton23: Button? = null
    private var mSendButton24: Button? = null
    private var mSendButton25: Button? = null
    private var mSendButton26: Button? = null
    private var mSendButton27: Button? = null

    private var mSendButton38: Button? = null
    private var mSendButton39: Button? = null
    private var mSendButton40: Button? = null
    private var mSendButton41: Button? = null

    private var mChatService: BluetoothChatService? = null
    private var mOutEditText: EditText? = null
    private var mOutStringBuffer: StringBuffer? = null
    private var mConversationArrayAdapter: ArrayAdapter<String>? = null
    private var mConversationView: ListView? = null

    private var mConnectedDeviceName: String? = null
    private var mBluetoothAdapter: BluetoothAdapter? = null

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        setHasOptionsMenu(true)
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        val activity = activity
        if (mBluetoothAdapter == null && activity != null) {
            Toast.makeText(activity, "Bluetooth is not available", Toast.LENGTH_LONG).show()
            activity.finish()
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mSendButton19 = view.findViewById(R.id.button19)
        mSendButton20 = view.findViewById(R.id.button20)
        mSendButton21 = view.findViewById(R.id.button21)
        mSendButton22 = view.findViewById(R.id.button22)
        mSendButton23 = view.findViewById(R.id.button23)
        mSendButton24 = view.findViewById(R.id.button24)
        mSendButton25 = view.findViewById(R.id.button25)
        mSendButton26 = view.findViewById(R.id.button26)
        mSendButton27 = view.findViewById(R.id.button27)

        mSendButton38 = view.findViewById(R.id.button38)
        mSendButton39 = view.findViewById(R.id.button39)
        mSendButton40 = view.findViewById(R.id.button40)
        mSendButton41 = view.findViewById(R.id.button41)

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
        } else if (mChatService == null) {
            setupChat()
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_c, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentC.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentC().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
        private const val REQUEST_CONNECT_DEVICE_SECURE = 1
        private const val REQUEST_CONNECT_DEVICE_INSECURE = 2
        private const val REQUEST_ENABLE_BT = 3
    }

    private fun setupChat() {
        Log.d(SampleActivityBase.TAG, "setupChat()")

        val activity = activity ?: return
        mConversationArrayAdapter = ArrayAdapter(activity, R.layout.message)
        mConversationView?.adapter = mConversationArrayAdapter

        mOutEditText?.setOnEditorActionListener(mWriteListener)

        mSendButton19!!.setOnClickListener { // Send a message using content of the edit text widget
            val view = view
            if (null != view) {
                //val textView = view.findViewById<TextView>(R.id.edit_text_out)
                //val message = textView.text.toString()
                val message = "\$UST"
                sendMessage(message)

            }
        }

        mSendButton20!!.setOnClickListener { // Send a message using content of the edit text widget
            val view = view
            if (null != view) {
                //val textView = view.findViewById<TextView>(R.id.edit_text_out)
                //val message = textView.text.toString()
                val message = "\$UPL"
                sendMessage(message)

            }
        }

        mSendButton21!!.setOnClickListener { // Send a message using content of the edit text widget
            val view = view
            if (null != view) {
                //val textView = view.findViewById<TextView>(R.id.edit_text_out)
                //val message = textView.text.toString()
                val message = "\$UPA"
                sendMessage(message)

            }
        }

        mSendButton22!!.setOnClickListener { // Send a message using content of the edit text widget
            val view = view
            if (null != view) {
                //val textView = view.findViewById<TextView>(R.id.edit_text_out)
                //val message = textView.text.toString()
                val message = "\$UPR"
                sendMessage(message)

            }
        }

        mSendButton23!!.setOnClickListener { // Send a message using content of the edit text widget
            val view = view
            if (null != view) {
                //val textView = view.findViewById<TextView>(R.id.edit_text_out)
                //val message = textView.text.toString()
                val message = "\$UNE"
                sendMessage(message)

            }
        }

        mSendButton24!!.setOnClickListener { // Send a message using content of the edit text widget
            val view = view
            if (null != view) {
                //val textView = view.findViewById<TextView>(R.id.edit_text_out)
                //val message = textView.text.toString()
                val message = "\$UPD"
                sendMessage(message)

            }
        }

        mSendButton25!!.setOnClickListener { // Send a message using content of the edit text widget
            val view = view
            if (null != view) {
                //val textView = view.findViewById<TextView>(R.id.edit_text_out)
                //val message = textView.text.toString()
                val message = "\$UND"
                sendMessage(message)

            }
        }

        mSendButton26!!.setOnClickListener { // Send a message using content of the edit text widget
            val view = view
            if (null != view) {
                //val textView = view.findViewById<TextView>(R.id.edit_text_out)
                //val message = textView.text.toString()
                val message = "\$UMF"
                sendMessage(message)

            }
        }

        mSendButton27!!.setOnClickListener { // Send a message using content of the edit text widget
            val view = view
            if (null != view) {
                //val textView = view.findViewById<TextView>(R.id.edit_text_out)
                //val message = textView.text.toString()
                val message = "\$UIT"
                sendMessage(message)

            }
        }
        mSendButton38!!.setOnClickListener { // Send a message using content of the edit text widget
            val view = view
            if (null != view) {
                //val textView = view.findViewById<TextView>(R.id.edit_text_out)
                //val message = textView.text.toString()
                val message = "\$URM"
                sendMessage(message)

            }
        }

        mSendButton39!!.setOnClickListener { // Send a message using content of the edit text widget
            val view = view
            if (null != view) {
                //val textView = view.findViewById<TextView>(R.id.edit_text_out)
                //val message = textView.text.toString()
                val message = "\$UR1"
                sendMessage(message)

            }
        }

        mSendButton40!!.setOnClickListener { // Send a message using content of the edit text widget
            val view = view
            if (null != view) {
                //val textView = view.findViewById<TextView>(R.id.edit_text_out)
                //val message = textView.text.toString()
                val message = "\$URD"
                sendMessage(message)

            }
        }

        mSendButton41!!.setOnClickListener { // Send a message using content of the edit text widget
            val view = view
            if (null != view) {
                //val textView = view.findViewById<TextView>(R.id.edit_text_out)
                //val message = textView.text.toString()
                val message = "\$URA"
                sendMessage(message)

            }
        }

        //mChatService = MainActivity.mChatService
        mOutStringBuffer = StringBuffer()
    }

    private fun sendMessage(message: String) {
        // Check that we're actually connected before trying anything
        if (MainActivity.mChatService != null){
            if(mChatService==null){
                mChatService = MainActivity.mChatService
            }

        }
        if (mChatService?.getState() != BluetoothChatService.Companion.STATE_CONNECTED) {
            Toast.makeText(activity, R.string.not_connected, Toast.LENGTH_SHORT).show()
            return
        }

        // Check that there's actually something to send
        if (message.length > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            val send = message.toByteArray()
            mChatService!!.write(send)

            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer!!.setLength(0)
            mOutEditText?.setText(mOutStringBuffer)
        }
    }

    private val mWriteListener =
        TextView.OnEditorActionListener { view, actionId, event -> // If the action is a key-up event on the return key, send the message
            if (actionId == EditorInfo.IME_NULL && event.action == KeyEvent.ACTION_UP) {
                val message = view.text.toString()
                sendMessage(message)
            }
            true
        }

    private fun setStatus(resId: Int) {
        val activity = activity ?: return
        val actionBar = activity.actionBar ?: return
        actionBar.setSubtitle(resId)
    }

    private fun setStatus(subTitle: CharSequence) {
        val activity = activity ?: return
        val actionBar = activity.actionBar ?: return
        actionBar.subtitle = subTitle
    }


    private val mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            val activity = activity
            when (msg.what) {
                Constants.Companion.MESSAGE_STATE_CHANGE -> when (msg.arg1) {
                    BluetoothChatService.Companion.STATE_CONNECTED -> {
                        setStatus(getString(R.string.title_connected_to, mConnectedDeviceName))
                        mConversationArrayAdapter!!.clear()
                    }
                    BluetoothChatService.Companion.STATE_CONNECTING -> setStatus(R.string.title_connecting)
                    BluetoothChatService.Companion.STATE_LISTEN, BluetoothChatService.Companion.STATE_NONE -> setStatus(R.string.title_not_connected)
                }
                Constants.Companion.MESSAGE_WRITE -> {
                    val writeBuf = msg.obj as ByteArray
                    // construct a string from the buffer
                    val writeMessage = String(writeBuf)
                    mConversationArrayAdapter!!.add("Me:  $writeMessage")
                }
                Constants.Companion.MESSAGE_READ -> {
                    val readBuf = msg.obj as ByteArray
                    // construct a string from the valid bytes in the buffer
                    val readMessage = String(readBuf, 0, msg.arg1)
                    mConversationArrayAdapter!!.add("$mConnectedDeviceName:  $readMessage")
                }
                Constants.Companion.MESSAGE_DEVICE_NAME -> {
                    // save the connected device's name
                    mConnectedDeviceName = msg.data.getString(Constants.Companion.DEVICE_NAME)
                    if (null != activity) {
                        Toast.makeText(activity, "Connected to "
                                + mConnectedDeviceName, Toast.LENGTH_SHORT).show()
                    }
                }
                Constants.Companion.MESSAGE_TOAST -> if (null != activity) {
                    Toast.makeText(activity, msg.data.getString(Constants.Companion.TOAST),
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /**override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
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
                    setupChat()
                } else {
                    // User did not enable Bluetooth or an error occurred
                    Log.d(SampleActivityBase.TAG, "BT not enabled")
                    val activity = activity
                    if (activity != null) {
                        Toast.makeText(activity, R.string.bt_not_enabled_leaving,
                            Toast.LENGTH_SHORT).show()
                        activity.finish()
                    }
                }
        }
    }

    private fun connectDevice(data: Intent?, secure: Boolean) {
        // Get the device MAC address
        val extras = data!!.extras ?: return
        val address = extras.getString(DeviceListActivity.Companion.EXTRA_DEVICE_ADDRESS)
        // Get the BluetoothDevice object
        val device = mBluetoothAdapter!!.getRemoteDevice(address)
        // Attempt to connect to the device
        mChatService!!.connect(device, secure)
    }

   /** override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
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
                val serverIntent = Intent(activity, DeviceListActivity::class.java)
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE)
                return true
            }
            R.id.insecure_connect_scan -> {

                // Launch the DeviceListActivity to see devices and do scan
                val serverIntent = Intent(activity, DeviceListActivity::class.java)
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
    }**/
}