package com.ssafy.ccd.src.main.mypage

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.rx
import com.ssafy.ccd.R
import com.ssafy.ccd.config.ApplicationClass
import com.ssafy.ccd.config.BaseFragment
import com.ssafy.ccd.databinding.ActivityLoginBinding.inflate
import com.ssafy.ccd.databinding.FragmentMyPageBinding
import com.ssafy.ccd.src.dto.Message
import com.ssafy.ccd.src.dto.Pet
import com.ssafy.ccd.src.login.LoginActivity
import com.ssafy.ccd.src.main.MainActivity
import com.ssafy.ccd.src.network.service.PetService
import com.ssafy.ccd.src.network.service.UserService
import com.ssafy.ccd.src.network.viewmodel.MainViewModels
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Response

private const val TAG = "MyPageFragment_ccd"
class MyPageFragment : BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::bind, R.layout.fragment_my_page) {

    private lateinit var petAdapter: PetListRecyclerviewAdapter
    private lateinit var mainActivity : MainActivity
    private var petId = -1

    // firebase authenticationg
    var mGoogleSignInClient: GoogleSignInClient? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            petId = getInt("petId")
            Log.d(TAG, "onCreate: ${petId}")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.myPageFragmentCvPetImage.visibility = View.INVISIBLE
        binding.myPageFragmentCvUserInfo.visibility = View.VISIBLE

        runBlocking {
            mainViewModel.getMyPetsAllList(ApplicationClass.sharedPreferencesUtil.getUser().id)
        }

        binding.viewModel = mainViewModel
        mainViewModel.loginUserInfo.observe(viewLifecycleOwner) {
            binding.user = it
        }
        if(petId > 0){
            runBlocking {
                mainViewModel.getPetDetailList(petId)
            }
            binding.myPageFragmentCvPetImage.visibility = View.VISIBLE
            binding.myPageFragmentCvUserInfo.visibility = View.INVISIBLE
        }
//        val myPetsList = mainViewModel.myPetsList.value
//        if(myPetsList?.size!! > 0){
//            petId = myPetsList[0].id
//            runBlocking {
//                mainViewModel.myPetsList.value?.get(0)?.let { mainViewModel.getPetDetailList(it.id) }
//            }
//            binding.myPageFragmentCvPetImage.visibility = View.VISIBLE
//            binding.myPageFragmentCvPetEmpty.visibility = View.INVISIBLE
//
//
//        }else{
//            binding.myPageFragmentCvPetImage.visibility = View.INVISIBLE
//            binding.myPageFragmentCvPetEmpty.visibility = View.VISIBLE
//        }


        initPetAdapter()
        initTabAdapter()
        initFirebaseAuth()


        binding.myPageFragmetBtnMore.setOnClickListener {
            val popupMenu = PopupMenu(requireContext(), binding.myPageFragmetBtnMore)
            MenuInflater(requireContext()).inflate(R.menu.popup_menu, popupMenu.menu)
            val listener = PopupMenuListener()
            popupMenu.setOnMenuItemClickListener(listener)
            popupMenu.show()
        }

        binding.myPageFragmentBtnUserMore.setOnClickListener {
            val popupMenu = PopupMenu(requireContext(), binding.myPageFragmentBtnUserMore)
            MenuInflater(requireContext()).inflate(R.menu.popup_user_menu, popupMenu.menu)
            val listener = PopupUserMenuListener()
            popupMenu.setOnMenuItemClickListener(listener)
            popupMenu.show()
        }
    }

    private fun deletePets(petId:Int){
        var response : Response<Message>
        runBlocking {
            response = PetService().petsDeleteService(petId)
        }
        val res = response.body()
        if(response.code() == 200){
            if(res!=null){
                if(res.success){
                    runBlocking {
                        mainViewModel.getMyPetsAllList(ApplicationClass.sharedPreferencesUtil.getUser().id)
                    }
                    petAdapter.notifyDataSetChanged()
                    binding.myPageFragmentCvPetImage.visibility = View.INVISIBLE
                    binding.myPageFragmentCvUserInfo.visibility = View.VISIBLE
//                        val myPetsList = mainViewModel.myPetsList.value
//                        if(myPetsList?.size!! > 0){
//
//                            runBlocking {
//                                mainViewModel.getPetDetailList(petId)
//                            }
//
//                            binding.myPageFragmentCvPetImage.visibility = View.VISIBLE
////                            binding.myPageFragmentCvPetEmpty.visibility = View.INVISIBLE
//                        }else{
//                            binding.myPageFragmentCvPetImage.visibility = View.INVISIBLE
////                            binding.myPageFragmentCvPetEmpty.visibility = View.VISIBLE
//                        }
                }else{
                    Log.d(TAG, "DeletePets: ")
                }
            }
        }else{
            Log.d(TAG, "DeletePets: ")
        }
    }

    private fun initPetAdapter(){
        mainViewModel.myPetsList.observe(viewLifecycleOwner) {
            petAdapter = PetListRecyclerviewAdapter(mainViewModel.loginUserInfo.value!!)
            petAdapter.petList = it

            binding.myPageFragmentRvPetList.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = petAdapter
                adapter!!.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            }

            petAdapter.setAddClickListener(object : PetListRecyclerviewAdapter.AddClickListener {
                override fun onClick(view: View, position: Int) {
                    //AddPetFragment??? ?????????
                    this@MyPageFragment.findNavController().navigate(R.id.action_myPageFragment_to_addPetFragment, bundleOf("postId" to -1))
                }
            })

            petAdapter.setItemClickListener(object : PetListRecyclerviewAdapter.ItemClickListener {
                override fun onClick(view: View, position: Int, pet: Pet) {
                    //?????? ???????????? ?????? ????????????
                    runBlocking {
                        mainViewModel.getPetDetailList(pet.id)
                        petId = pet.id
                        binding.viewModel = mainViewModel
                        Log.d(TAG, "onClick: ${pet.id}")
                    }
                    binding.myPageFragmentCvUserInfo.visibility = View.INVISIBLE
                    binding.myPageFragmentCvPetImage.visibility = View.VISIBLE
                }
            })

            petAdapter.setUserInfoClickListener(object : PetListRecyclerviewAdapter.UserInfoClickListener {
                override fun onClick(view: View) {
                    binding.myPageFragmentCvUserInfo.visibility = View.VISIBLE
                    binding.myPageFragmentCvPetImage.visibility = View.INVISIBLE
                }
            })
        }
    }


    private fun initTabAdapter() {
        val viewPagerAdapter = MyTabPageAdapter(this)
        val tabList = listOf("??? ??????", "?????? ??? ???", "????????????")

        viewPagerAdapter.addFragment(MyScheduleFragment())
        viewPagerAdapter.addFragment(MyPostFragment())
        viewPagerAdapter.addFragment(HistoryFragment())

        binding.myPageFragmentVp.adapter = viewPagerAdapter
        TabLayoutMediator(binding.myPageFragmentTabLayout, binding.myPageFragmentVp) { tab, position ->
            tab.text = tabList[position]
        }.attach()
    }

    /**
     * @author Jiwoo
     * firebase Auth ?????????
     */
    private fun initFirebaseAuth() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.google_login_key))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    }

    /**
     * @author Jiwoo
     * ????????? ????????????
     */
    private fun logout(){

        mainViewModel.loginUserInfo.observe(viewLifecycleOwner) {
            val type = it.socialType
            if (type == "google" || type == "facebook") {
                // google, facebook Logout
                FirebaseAuth.getInstance().signOut()
            } else if (type == "kakao") {
                // kakao Logout
                val disposables = CompositeDisposable()

                UserApiClient.rx.logout()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Log.i(TAG, "???????????? ??????. SDK?????? ?????? ?????? ???")
                    }, { error ->
                        Log.e(TAG, "???????????? ??????. SDK?????? ?????? ?????? ???", error)
                    }).addTo(disposables)
            }
        }

        ApplicationClass.sharedPreferencesUtil.deleteUser()
        ApplicationClass.sharedPreferencesUtil.deleteUserCookie()
        ApplicationClass.sharedPreferencesUtil.deleteAutoLogin()
        //????????????
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }


    /**
     * @author Jiwoo
     * ????????? ???????????? ???????????????
     */
    private fun showDeleteUserDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("?????? ??????")
            .setMessage("????????? ?????????????????????????")
            .setPositiveButton("YES", DialogInterface.OnClickListener{ dialogInterface, id ->
                // ??????????????????
                mainViewModel.loginUserInfo.observe(viewLifecycleOwner) {
                    val type = it.socialType
                    if (type == "kakao") {
                        val disposables = CompositeDisposable()
                        // ?????? ??????
                        UserApiClient.rx.unlink()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                Log.i(TAG, "?????? ?????? ??????. SDK?????? ?????? ?????? ???")
                            }, { error ->
                                Log.e(TAG, "?????? ?????? ??????", error)
                            }).addTo(disposables)
                    } else if (type == "google" || type == "facebook") {
                        FirebaseAuth.getInstance().currentUser?.delete()!!.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                //??????????????????
                                FirebaseAuth.getInstance().signOut()
                                mGoogleSignInClient?.signOut()
                                Log.i(TAG, "firebase auth ????????? ?????? ?????? ??????")
                            } else {
                                Log.i(TAG, "firebase auth ????????? user ?????? ??????")
                            }
                        }
                    }

                    var res: Response<Message>
                    runBlocking {
                        res = UserService().deleteUser(ApplicationClass.sharedPreferencesUtil.getUser().id)
                    }
                    if (res.code() == 200 || res.code() == 500) {
                        val rbody = res.body()
                        if (rbody != null) {
                            if (rbody.data["isDelete"] == true && rbody.message == "?????? ?????? ??????") {
                                showCustomToast("?????? ????????? ?????????????????????.")
                                logout()

                            } else if (rbody.data["isDelete"] == false) {
                                showCustomToast("?????? ?????? ??????")
                            }
                            if (rbody.success == false) {
                                showCustomToast("?????? ?????? ??????")
                                Log.d(TAG, "changePwBtnClickEvent: ${rbody.message}")
                            }
                        }
                    }
                    ApplicationClass.sharedPreferencesUtil.deleteUser()
                    ApplicationClass.sharedPreferencesUtil.deleteUserCookie()
                    ApplicationClass.sharedPreferencesUtil.deleteAutoLogin()
                }
            })
            .setNeutralButton("NO", null)
            .create()

        builder.show()
    }


    inner class PopupMenuListener:PopupMenu.OnMenuItemClickListener {
        override fun onMenuItemClick(item: MenuItem?): Boolean {
            when(item?.itemId){
                R.id.modify->{
                    this@MyPageFragment.findNavController().navigate(R.id.action_myPageFragment_to_addPetFragment, bundleOf("petId" to petId))
//                    mainViewModel.petId = petId
                }
                R.id.delete -> {
                    deletePets(petId)
                }
            }
            return false
        }
    }

    inner class PopupUserMenuListener:PopupMenu.OnMenuItemClickListener {
        override fun onMenuItemClick(item: MenuItem?): Boolean {
            when(item?.itemId){
                R.id.modify ->  {
                    this@MyPageFragment.findNavController().navigate(R.id.action_homeFragment_to_userProfileFragment)
                }
                R.id.logout ->{
                    logout()
                }
                R.id.withdrawal -> {
                    showDeleteUserDialog()
                }
            }
            return false
        }
    }
}