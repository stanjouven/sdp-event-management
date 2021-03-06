package ch.epfl.sweng.eventmanager.ui.event.interaction;

import androidx.lifecycle.ViewModel;
import ch.epfl.sweng.eventmanager.ui.event.interaction.fragments.*;
import ch.epfl.sweng.eventmanager.ui.event.interaction.fragments.map.EventMapEditionFragment;
import ch.epfl.sweng.eventmanager.ui.event.interaction.fragments.map.EventMapFragment;
import ch.epfl.sweng.eventmanager.ui.event.interaction.fragments.user.EventUserManagementFragment;
import ch.epfl.sweng.eventmanager.ui.event.interaction.models.*;
import ch.epfl.sweng.eventmanager.viewmodel.ViewModelKey;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;

/**
 * @author Louis Vialar
 */
@Module
public abstract class EventInteractionModule {
    @Binds
    @IntoMap
    @ViewModelKey(EventInteractionModel.class)
    abstract ViewModel provideEventListModel(EventInteractionModel eventListModel);

    @Binds
    @IntoMap
    @ViewModelKey(ScheduleViewModel.class)
    abstract ViewModel provideScheduleViewModel(ScheduleViewModel scheduleViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(NewsViewModel.class)
    abstract ViewModel provideNewsViewModel(NewsViewModel newsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SpotsModel.class)
    abstract ViewModel provideSpotsViewModel(SpotsModel spotsModel);

    @Binds
    @IntoMap
    @ViewModelKey(ZoneModel.class)
    abstract ViewModel provideZonesViewModel(ZoneModel zonesModel);

    @Binds
    @IntoMap
    @ViewModelKey(UserModel.class)
    abstract UserModel provideUserViewModel(UserModel userModel);

    @ContributesAndroidInjector
    abstract EventShowcaseActivity contributeEventShowcaseActivityInjector();

    @ContributesAndroidInjector
    abstract EventCreateActivity contributeEventCreateActivityInjector();

    @ContributesAndroidInjector
    abstract SendNewsFragment contributeSendNewsFragmentInjector();

    @ContributesAndroidInjector
    abstract SendNotificationFragment contributeSendNotificationFragmentInjector();

    @ContributesAndroidInjector
    abstract NewsFragment contributeNewsFragmentInjector();

    @ContributesAndroidInjector
    abstract EventMapFragment contributeEventMapFragmentInjector();

    @ContributesAndroidInjector
    abstract EventMapEditionFragment contributeEventMapEditionFragmentInjector();

    @ContributesAndroidInjector
    abstract EventAdministrationActivity contributeEventAdminsitrationActivityInjector();

    @ContributesAndroidInjector
    abstract EventFeedbackFragment contributeEventFeedbackFragmentInjector();

    @ContributesAndroidInjector
    abstract SubmitFeedbackFragment contributeSubmitFeedbackFragmentInjector();

    @ContributesAndroidInjector
    abstract EventMainFragment contributeEventMainFragmentInjector();

    @ContributesAndroidInjector
    abstract EventUserManagementFragment contributeEventUserManagementFragmentInjector();
}
