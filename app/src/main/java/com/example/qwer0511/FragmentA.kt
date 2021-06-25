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

import com.example.android.common.activities.SampleActivityBase.Companion.TAG
import com.example.android.common.logger.Log





// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentA.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentA : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private var mChatService: BluetoothChatService? = null
    private var mOutEditText: EditText? = null
    private var mOutStringBuffer: StringBuffer? = null
    private var mConversationArrayAdapter: ArrayAdapter<String>? = null
    private var mConversationView: ListView? = null

    private var mSendButton4: Button? = null
    private var mSendButton5: Button? = null
    private var mSendButton6: Button? = null
    private var mSendButton7: Button? = null
    private var mSendButton8: Button? = null
    private var mSendButton9: Button? = null
    private var mSendButton10: Button? = null
    private var mSendButton11: Button? = null
    private var mSendButton12: Button? = null
    private var mSendButton46: Button? = null
    private var mSendButton47: Button? = null
    private var mSendButton48: Button? = null
    private var mSendButton49: Button? = null
    private var mSendButton50: Button? = null
    private var mSendButton51: Button? = null
    private var mSendButton52: Button? = null
    private var mSendButton53: Button? = null
    private var mSendButton54: Button? = null

    private var mConnectedDeviceName: String? = null
    private var mBluetoothAdapter: BluetoothAdapter? = null

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
        return inflater.inflate(R.layout.fragment_a, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mSendButton4 = view.findViewById(R.id.button4)
        mSendButton5 = view.findViewById(R.id.button5)
        mSendButton6 = view.findViewById(R.id.button6)
        mSendButton7 = view.findViewById(R.id.button7)
        mSendButton8 = view.findViewById(R.id.button8)
        mSendButton9 = view.findViewById(R.id.button9)
        mSendButton10 = view.findViewById(R.id.button10)
        mSendButton11 = view.findViewById(R.id.button11)
        mSendButton12 = view.findViewById(R.id.button12)

        mSendButton46 = view.findViewById(R.id.button46)
        mSendButton47 = view.findViewById(R.id.button47)
        mSendButton48 = view.findViewById(R.id.button48)
        mSendButton49 = view.findViewById(R.id.button49)
        mSendButton50 = view.findViewById(R.id.button50)
        mSendButton51 = view.findViewById(R.id.button51)
        mSendButton52 = view.findViewById(R.id.button52)
        mSendButton53 = view.findViewById(R.id.button53)
        mSendButton54 = view.findViewById(R.id.button54)


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentA.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentA().apply {
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
        Log.d(TAG, "setupChat()")


        val activity = activity ?: return
        mConversationArrayAdapter = ArrayAdapter(activity, R.layout.message)
        mConversationView?.adapter = mConversationArrayAdapter

        mOutEditText?.setOnEditorActionListener(mWriteListener)

        mSendButton4!!.setOnClickListener { // Send a message using content of the edit text widget
            val view = view
            if (null != view) {
                //val textView = view.findViewById<TextView>(R.id.edit_text_out)
                //val message = textView.text.toString()
                val message = "\$RB1"
                sendMessage(message)

            }
        }

        mSendButton5!!.setOnClickListener { // Send a message using content of the edit text widget
            val view = view
            if (null != view) {
                //val textView = view.findViewById<TextView>(R.id.edit_text_out)
                //val message = textView.text.toString()
                val message = "\$RB2"
                sendMessage(message)

            }
        }

        mSendButton6!!.setOnClickListener { // Send a message using content of the edit text widget
            val view = view
            if (null != view) {
                //val textView = view.findViewById<TextView>(R.id.edit_text_out)
                //val message = textView.text.toString()
                val message = "\$RB3"
                sendMessage(message)

            }
        }

        mSendButton7!!.setOnClickListener { // Send a message using content of the edit text widget
            val view = view
            if (null != view) {
                //val textView = view.findViewById<TextView>(R.id.edit_text_out)
                //val message = textView.text.toString()
                val message = "\$RB4"
                sendMessage(message)

            }
        }

        mSendButton8!!.setOnClickListener { // Send a message using content of the edit text widget
            val view = view
            if (null != view) {
                //val textView = view.findViewById<TextView>(R.id.edit_text_out)
                //val message = textView.text.toString()
                val message = "\$RB5"
                sendMessage(message)

            }
        }

        mSendButton9!!.setOnClickListener { // Send a message using content of the edit text widget
            val view = view
            if (null != view) {
                //val textView = view.findViewById<TextView>(R.id.edit_text_out)
                //val message = textView.text.toString()
                val message = "\$RP1"
                sendMessage(message)

            }
        }

        mSendButton10!!.setOnClickListener { // Send a message using content of the edit text widget
            val view = view
            if (null != view) {
                //val textView = view.findViewById<TextView>(R.id.edit_text_out)
                //val message = textView.text.toString()
                val message = "\$RP2"
                sendMessage(message)

            }
        }

        mSendButton11!!.setOnClickListener { // Send a message using content of the edit text widget
            val view = view
            if (null != view) {
                //val textView = view.findViewById<TextView>(R.id.edit_text_out)
                //val message = textView.text.toString()
                val message = "\$RP3"
                sendMessage(message)

            }
        }

        mSendButton12!!.setOnClickListener { // Send a message using content of the edit text widget
            val view = view
            if (null != view) {
                //val textView = view.findViewById<TextView>(R.id.edit_text_out)
                //val message = textView.text.toString()
                val message = "\$RP4"
                sendMessage(message)

            }
        }

        mSendButton46!!.setOnClickListener { // Send a message using content of the edit text widget
            val view = view
            if (null != view) {
                //val textView = view.findViewById<TextView>(R.id.edit_text_out)
                //val message = textView.text.toString()
                val message = "\$RP5"
                sendMessage(message)

            }
        }

        mSendButton47!!.setOnClickListener { // Send a message using content of the edit text widget
            val view = view
            if (null != view) {
                //val textView = view.findViewById<TextView>(R.id.edit_text_out)
                //val message = textView.text.toString()
                val message = "\$RP6"
                sendMessage(message)

            }
        }

        mSendButton48!!.setOnClickListener { // Send a message using content of the edit text widget
            val view = view
            if (null != view) {
                //val textView = view.findViewById<TextView>(R.id.edit_text_out)
                //val message = textView.text.toString()
                val message = "\$RTD"
                sendMessage(message)

            }
        }

        mSendButton49!!.setOnClickListener { // Send a message using content of the edit text widget
            val view = view
            if (null != view) {
                //val textView = view.findViewById<TextView>(R.id.edit_text_out)
                //val message = textView.text.toString()
                val message = "\$RTU"
                sendMessage(message)

            }
        }

        mSendButton50!!.setOnClickListener { // Send a message using content of the edit text widget
            val view = view
            if (null != view) {
                //val textView = view.findViewById<TextView>(R.id.edit_text_out)
                //val message = textView.text.toString()
                val message = "\$RSD"
                sendMessage(message)

            }
        }

        mSendButton51!!.setOnClickListener { // Send a message using content of the edit text widget
            val view = view
            if (null != view) {
                //val textView = view.findViewById<TextView>(R.id.edit_text_out)
                //val message = textView.text.toString()
                val message = "\$RSU"
                sendMessage(message)

            }
        }

        mSendButton52!!.setOnClickListener { // Send a message using content of the edit text widget
            val view = view
            if (null != view) {
                //val textView = view.findViewById<TextView>(R.id.edit_text_out)
                //val message = textView.text.toString()
                val message = "\$RAS"
                sendMessage(message)

            }
        }

        mSendButton53!!.setOnClickListener { // Send a message using content of the edit text widget
            val view = view
            if (null != view) {
                //val textView = view.findViewById<TextView>(R.id.edit_text_out)
                //val message = textView.text.toString()
                val message = "\$RPS"
                sendMessage(message)

            }
        }

        mSendButton54!!.setOnClickListener { // Send a message using content of the edit text widget
            val view = view
            if (null != view) {
                //val textView = view.findViewById<TextView>(R.id.edit_text_out)
                //val message = textView.text.toString()
                val message = "\$RFR"
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
                    Log.d(TAG, "BT not enabled")
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