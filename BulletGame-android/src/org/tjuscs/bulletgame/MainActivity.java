package org.tjuscs.bulletgame;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import dalvik.system.VMRuntime;


public class MainActivity extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	VMRuntime runtime = VMRuntime.getRuntime();
    	runtime.setMinimumHeapSize(16*1024*1024);
    	runtime.setTargetHeapUtilization(0.75f);
    	
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = false;
        
        initialize(new BulletGame(), cfg);
    }
}