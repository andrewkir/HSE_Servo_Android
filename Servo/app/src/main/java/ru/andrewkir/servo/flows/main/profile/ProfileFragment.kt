package ru.andrewkir.servo.flows.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.andrewkir.servo.common.BaseFragment
import ru.andrewkir.servo.databinding.EmptyFragmentBinding

class ProfileFragment :
    BaseFragment<ProfileViewModel, ProfileRepository, EmptyFragmentBinding>() {

//    private lateinit var recyclerFavoritesAdapter: CoursePreviewRecyclerViewAdapter
//    private lateinit var linearFavoritesLayoutManager: LinearLayoutManager
//
//    private lateinit var recyclerViewedAdapter: CoursePreviewRecyclerViewAdapter
//    private lateinit var linearViewedLayoutManager: LinearLayoutManager


    override fun provideViewModelClass() = ProfileViewModel::class.java

    override fun provideRepository(): ProfileRepository {
        return ProfileRepository(
//            apiProvider.provideApi(
//                CoursesApi::class.java,
//                requireContext(),
//                userPrefsManager.accessToken
//            )
        )
    }

    override fun provideBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = EmptyFragmentBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//
//        setupRecyclerViews()
//        setupExpandButtons()
//        setupSwipeToRefresh()
//        setupLogout()
//        subscribeToFavorites()
//        subscribeToViewed()
//        subscribeToError()
//        subscribeToLoading()
//
//        bind.profileEmail.text = userPrefsManager.email
//        bind.profileUsername.text = userPrefsManager.username
    }
//
//    private fun setupRecyclerViews() {
//        recyclerFavoritesAdapter = CoursePreviewRecyclerViewAdapter(requireContext()) {
//            val intent = Intent(requireContext(), CourseActivity::class.java)
//            intent.putExtra("COURSE_ID", it.id)
//            startActivity(intent)
//        }
//        linearFavoritesLayoutManager = LinearLayoutManager(requireContext())
//        bind.profileFavoritesRecyclerView.run {
//            overScrollMode = View.OVER_SCROLL_NEVER
//            layoutManager = linearFavoritesLayoutManager
//            adapter = recyclerFavoritesAdapter
//        }
//
//        recyclerViewedAdapter = CoursePreviewRecyclerViewAdapter(requireContext()) {
//            val intent = Intent(requireContext(), CourseActivity::class.java)
//            intent.putExtra("COURSE_ID", it.id)
//            startActivity(intent)
//        }
//        linearViewedLayoutManager = LinearLayoutManager(requireContext())
//        bind.profileViewedRecyclerView.run {
//            overScrollMode = View.OVER_SCROLL_NEVER
//            layoutManager = linearViewedLayoutManager
//            adapter = recyclerViewedAdapter
//        }
//    }
//
//    private fun setupExpandButtons() {
//        bind.expandFavorites.setOnClickListener {
//            if (bind.expandFavorites.rotation == 0f) {
//                viewModel.getFavorites()
//
//            } else {
//                viewModel.favoritesCourses.value = null
//            }
//        }
//
//        bind.expandViewed.setOnClickListener {
//            if (bind.expandViewed.rotation == 0f) {
//                viewModel.getViewed()
//            } else {
//                viewModel.viewedCourses.value = null
//            }
//        }
//    }
//
//    private fun setupSwipeToRefresh() {
//        bind.profileSwipeRefresh.setOnRefreshListener {
//            updateVisibleCourses()
//        }
//    }
//
//    private fun setupLogout() {
//        bind.profileLogoutButton.setOnClickListener {
//            AlertDialog.Builder(requireContext())
//                .setTitle("Выход из приложения")
//                .setMessage("Вы действительно хотите выйти из приложения?")
//                .setNeutralButton("Отмена") { dialog, _ ->
//                    dialog.dismiss()
//                }
//                .setPositiveButton("Да") { _, _ ->
//                    userLogout()
//                }
//                .show()
//        }
//    }
//
//    private fun updateVisibleCourses() {
//        if (bind.expandFavorites.rotation == 0f && bind.expandViewed.rotation == 0f) {
//            bind.profileSwipeRefresh.isRefreshing = false
//            return
//        }
//        if (bind.expandFavorites.rotation == 90f) {
//            viewModel.getFavorites()
//        }
//        if (bind.expandViewed.rotation == 90f) {
//            viewModel.getViewed()
//        }
//    }
//
//    private fun subscribeToViewed() {
//        viewModel.viewedCourses.observe(viewLifecycleOwner, Observer {
//            if (it == null) {
//                bind.expandViewed.animate().rotation(0f).interpolator =
//                    AccelerateDecelerateInterpolator()
//                bind.profileViewedRecyclerView.visibility = View.GONE
//            } else {
//                recyclerViewedAdapter.data = it
//                bind.expandViewed.animate()
//                    .rotation(90f).interpolator =
//                    AccelerateDecelerateInterpolator()
//                bind.profileViewedRecyclerView.visibility = View.VISIBLE
//            }
//        })
//    }
//
//    private fun subscribeToFavorites() {
//        viewModel.favoritesCourses.observe(viewLifecycleOwner, Observer {
//            if (it == null) {
//                bind.expandFavorites.animate().rotation(0f).interpolator =
//                    AccelerateDecelerateInterpolator()
//                bind.profileFavoritesRecyclerView.visibility = View.GONE
//            } else {
//                recyclerFavoritesAdapter.data = it
//                bind.expandFavorites.animate()
//                    .rotation(90f).interpolator =
//                    AccelerateDecelerateInterpolator()
//                bind.profileFavoritesRecyclerView.visibility = View.VISIBLE
//            }
//        })
//    }
//
//    private fun subscribeToLoading() {
//        viewModel.loading.observe(viewLifecycleOwner, Observer {
//            bind.profileSwipeRefresh.isRefreshing = it
//        })
//    }
//
//    private fun subscribeToError() {
//        viewModel.errorResponse.observe(viewLifecycleOwner, Observer {
//            handleApiError(it) {
//                updateVisibleCourses()
//            }
//        })
//    }

    override fun onResume() {
        super.onResume()
//        updateVisibleCourses()
    }
}