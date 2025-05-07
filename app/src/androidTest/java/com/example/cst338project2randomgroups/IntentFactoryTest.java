package com.example.cst338project2randomgroups;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.content.Intent;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;


import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class IntentFactoryTest {
    //Sarah W
    @Test
    public void checkAddAdminFactory() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = AddAdminActivity.AddAdminActivityIntentFactory(context);

        assertEquals(AddAdminActivity.class.getName(), intent.getComponent().getClassName());
    }

    //Sarah w
    @Test
    public void checkEditGroupSizeFactory() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = EditGroupSizeActivity.EditGroupSizeActivityIntentFactory(context);

        assertEquals(EditGroupSizeActivity.class.getName(), intent.getComponent().getClassName());
    }

    //Ann J
    @Test
    public void checkJoinClassroomIntentFactory(){
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = JoinAClassActivity.joinAClassIntentFactory(context);

        assertEquals(JoinAClassActivity.class.getName(), intent.getComponent().getClassName());
    }

    @Test
    public void checkStudentViewALLIntentFactory(){
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = StudentViewALLClassesActivity.studentViewALLClassesIntentFactory(context);

        assertEquals(StudentViewALLClassesActivity.class.getName(), intent.getComponent().getClassName());
    }


}
