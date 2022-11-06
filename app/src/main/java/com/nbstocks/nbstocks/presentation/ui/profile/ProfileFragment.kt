package com.nbstocks.nbstocks.presentation.ui.profile

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.nbstocks.nbstocks.R
import com.nbstocks.nbstocks.common.custom_views.CustomDialog
import com.nbstocks.nbstocks.common.extensions.asynchronously
import com.nbstocks.nbstocks.common.extensions.makeSnackbar
import com.nbstocks.nbstocks.common.extensions.obtainViewModel
import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.databinding.FragmentProfileBinding
import com.nbstocks.nbstocks.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private var imageUri: Uri? = null

    private val viewModel: ProfileViewModel by lazy {
        obtainViewModel(
            requireActivity(),
            ProfileViewModel::class.java,
            defaultViewModelProviderFactory
        )
    }

    override fun start() {
        observe()
        listeners()
        viewModel.getUserImage()
    }

    private fun observe() {
        asynchronously {
            viewModel.getShownBalanceState(requireContext()).collect {
                binding.swBalanceVisible.isChecked = it
                Log.wtf("TAGGGG", it.toString())
            }
        }
        asynchronously {
            viewModel.profilePic.collect{
                when(it){
                    is Resource.Success ->{
                        binding.civProfileImage.setImageBitmap(it.data)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun listeners() {
        binding.apply {


            binding.btnUploadPhoto.setOnClickListener {
                choosePhoto()
            }

            swBalanceVisible.setOnCheckedChangeListener { buttonView, isChecked ->
                viewModel.setShownBalance(requireContext(), isChecked)
            }
            tvAbout.setOnClickListener {

            }
            tvChangePassword.setOnClickListener {
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToPasswordChangeFragment())
            }
            tvLogOut.setOnClickListener {
                signOut()
            }
        }
    }


    private fun choosePhoto() {
        ImagePicker.Companion.with(this)
            .crop(150f, 150f)
            .createIntent { intent ->
                startForProfileImageResult.launch(intent)
            }
    }

    private fun uploadProfilePic() {
        viewModel.uploadProfilePic(imageUri)
        asynchronously {
            viewModel.uploadProfilePicResponse.collect {
                when (it) {
                    is Resource.Success<*> -> {
                        binding.root.makeSnackbar("Upload successfully",false)
                    }
                    is Resource.Error -> {
                        binding.root.makeSnackbar("Upload failed",true)
                    }
                    else -> {}
                }
            }
        }
    }


    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            when (resultCode) {
                Activity.RESULT_OK -> {

                    imageUri = data?.data!!

                    if(imageUri != null){
                        uploadProfilePic()
                    }

                    Glide.with(this)
                        .load(imageUri)
                        .into((binding.civProfileImage) as ImageView)

                }

                ImagePicker.RESULT_ERROR -> {
                    binding.root.makeSnackbar(ImagePicker.getError(data), true)
                }
                else -> {
                    binding.root.makeSnackbar("Task Cancelled", true)
                }
            }
        }




    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToLogInFragment())
    }
}