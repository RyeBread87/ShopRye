package com.application.shoprye;

import android.view.View;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import org.hamcrest.Matcher;

import java.util.ArrayList;
import java.util.List;

public class MyViewAction {

    public static ViewAction clickChildViewWithId(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on a child view with specified id.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                ArrayList<View> viewList = new ArrayList<>();
                //View v =
                view.findViewsWithText(viewList, "whatever", 0);
                viewList.get(0).performClick();
            }
        };
    }

}