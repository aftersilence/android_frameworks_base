package com.android.systemui.quicksettings;
 
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.UserHandle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
 
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.QuickSettingsContainerView;
import com.android.systemui.statusbar.phone.QuickSettingsController;
 
public class HaloTile extends QuickSettingsTile {
 
    private boolean mEnabled;
 
    public HaloTile(Context context, QuickSettingsController qsc) {
        super(context, qsc);
 
        mOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Settings.System.putInt(mContext.getContentResolver(), Settings.System.HALO_ENABLED,
                        mEnabled ? 0 : 1);
                               
            }
        };
        mOnLongClick = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setClassName("com.android.settings", "com.android.settings.Settings$HaloSettingsActivity");
                startSettingsActivity(intent);
                return true;
            }
        };
        qsc.registerObservedContent(Settings.System.getUriFor(Settings.System.HALO_ENABLED), this);
    }
 
    @Override
    void onPostCreate() {
        updateTile();
        super.onPostCreate();
    }
 
    @Override
    public void updateResources() {
        updateTile();
        super.updateResources();
    }
 
    private synchronized void updateTile() {
        mEnabled = Settings.System.getInt(mContext.getContentResolver(),
                Settings.System.HALO_ENABLED, 0) == 1;
        if (mEnabled) {
            mDrawable = R.drawable.ic_notify_halo_pressed ;
            mLabel = mContext.getString(R.string.quick_settings_halo_on_label);
        } else {
            mDrawable = R.drawable.ic_notify_halo_normal;
            mLabel = mContext.getString(R.string.quick_settings_halo_off_label);
        }
    }
 
    @Override
    public void onChangeUri(ContentResolver resolver, Uri uri) {
        updateResources();
    }
 
}