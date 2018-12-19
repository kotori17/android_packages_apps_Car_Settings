/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.car.settings.wifi.details;

import android.content.Context;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.android.car.settings.common.BasePreferenceController;
import com.android.car.settings.common.FragmentController;
import com.android.car.settings.wifi.WifiUtil;
import com.android.settingslib.wifi.AccessPoint;

/**
 * Controller for logic pertaining to displaying Wifi information.
 */
public abstract class WifiControllerBase extends BasePreferenceController
        implements WifiInfoProvider.Listener, LifecycleObserver {

    protected AccessPoint mAccessPoint;
    protected WifiInfoProvider mWifiInfoProvider;

    public WifiControllerBase(
            Context context, String preferenceKey, FragmentController fragmentController) {
        super(context, preferenceKey, fragmentController);
    }

    /**
     * Sets all parameters for the controller to run, need to get called as early as possible.
     */
    public WifiControllerBase init(
            AccessPoint accessPoint, WifiInfoProvider wifiInfoProvider) {
        mAccessPoint = accessPoint;
        mWifiInfoProvider = wifiInfoProvider;
        return this;
    }

    /**
     * Start listening for wifi updates.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        mWifiInfoProvider.addListener(this);
    }

    /**
     * Stop listening for wifi updates.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        mWifiInfoProvider.removeListener(this);
    }

    @Override
    public void onWifiConfigurationChanged(WifiConfiguration wifiConfiguration,
            NetworkInfo networkInfo, WifiInfo wifiInfo) {
    }

    @Override
    public void onLinkPropertiesChanged(Network network, LinkProperties lp) {
    }

    @Override
    public void onCapabilitiesChanged(Network network, NetworkCapabilities nc) {
    }

    @Override
    public void onWifiChanged(NetworkInfo networkInfo, WifiInfo wifiInfo) {
    }

    @Override
    public void onLost(Network network) {
    }

    @Override
    public int getAvailabilityStatus() {
        return WifiUtil.isWifiAvailable(mContext) ? AVAILABLE : UNSUPPORTED_ON_DEVICE;
    }
}