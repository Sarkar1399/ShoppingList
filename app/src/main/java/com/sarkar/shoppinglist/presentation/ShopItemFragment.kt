package com.sarkar.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import com.sarkar.shoppinglist.R
import com.sarkar.shoppinglist.domain.ShopItem

class ShopItemFragment(
    private var screenMode: String = MODE_UNKNOWN,
    private var shopItemId: Int = ShopItem.UNDEFINED_ID
) : Fragment() {

    companion object {
        private const val SCREEN_MODE = "extra_mode"
        private const val SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newInstanceAddItem(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(shopItemId: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(MODE_EDIT, shopItemId)
                }
            }
        }

    }

    private lateinit var viewModel: ShopItemViewModel
    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        initViews(view)
        addTextChangeListeners()
        launchRightMode()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            tilName.error = if (it) getString(R.string.error_input_name) else null
        }

        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            tilCount.error = if (it) getString(R.string.error_input_count) else null
        }

        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
        }
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_ADD -> launchAddMode()
            MODE_EDIT -> launchEditMode(shopItemId)
        }
    }

    private fun addTextChangeListeners() {
        etName.doOnTextChanged { _, _, _, _ ->
            viewModel.resetErrorInputName()
        }

        etCount.doOnTextChanged { _, _, _, _ ->
            viewModel.resetErrorInputCount()
        }
    }

    private fun launchAddMode() {
        btnSave.setOnClickListener {
            viewModel.addShopItem(etName.text?.toString(), etCount.text?.toString())
        }
    }

    private fun launchEditMode(shopItemId: Int) {
        viewModel.getShopItemFromId(shopItemId)

        viewModel.shopItemFromId.observe(viewLifecycleOwner) {
            etName.setText(it.name)
            etCount.setText(it.count.toString())
        }

        btnSave.setOnClickListener {
            viewModel.changeShopItem(etName.text?.toString(), etCount.text?.toString())
        }
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = args.getString(SCREEN_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw RuntimeException("Unknown screen mode:$mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(SHOP_ITEM_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            shopItemId = args.getInt(SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    private fun initViews(view: View) {
        tilName = view.findViewById(R.id.tilName)
        tilCount = view.findViewById(R.id.tilCount)
        etName = view.findViewById(R.id.etName)
        etCount = view.findViewById(R.id.etCount)
        btnSave = view.findViewById(R.id.btnSave)
    }


}