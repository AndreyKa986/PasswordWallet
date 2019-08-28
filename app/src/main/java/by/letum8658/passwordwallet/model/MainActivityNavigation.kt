package by.letum8658.passwordwallet.model

import by.letum8658.passwordwallet.view.fragments.LogInFragment
import by.letum8658.passwordwallet.view.fragments.CreateAccountFragment
import by.letum8658.passwordwallet.view.fragments.RecyclerViewFragment
import by.letum8658.passwordwallet.view.fragments.CreateItemFragment
import by.letum8658.passwordwallet.view.fragments.InformationFragment
import by.letum8658.passwordwallet.view.fragments.ChangePasswordFragment
import by.letum8658.passwordwallet.view.fragments.DeleteItemFragment
import by.letum8658.passwordwallet.view.fragments.CreatePasswordFragment

interface MainActivityNavigation :
    LogInFragment.Listener,
    CreateAccountFragment.Listener,
    RecyclerViewFragment.Listener,
    CreateItemFragment.Listener,
    InformationFragment.Listener,
    ChangePasswordFragment.Listener,
    DeleteItemFragment.Listener,
    CreatePasswordFragment.Listener