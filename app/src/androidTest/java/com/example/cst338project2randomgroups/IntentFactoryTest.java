package com.example.cst338project2randomgroups;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.content.Intent;

import androidx.test.espresso.internal.inject.InstrumentationContext;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
//Sarah's Tests

@RunWith(AndroidJUnit4.class)
public class IntentFactoryTest {
    //Sarah W
    @Test
    public void checkAddAdminFactory(){
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = AddAdminActivity.AddAdminActivityIntentFactory(context);

        assertEquals(AddAdminActivity.class.getName(), intent.getComponent().getClassName());
    }

}
