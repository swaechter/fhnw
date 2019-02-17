package com.gluonapplication;

import com.gluonapplication.countrycantonmap.model.CantonUtils;
import com.gluonapplication.countrycantonmap.view.CountryCantonMap;
import com.gluonapplication.toolbar.HydroToolBar;
import com.gluonhq.charm.down.Platform;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;

public class BasicView extends View {

    public BasicView() {
        boolean showSearch = Platform.getCurrent().name().equals("DESKTOP");
        HydroToolBar hydroToolBar = new HydroToolBar(showSearch);

        CountryCantonMap countryCantonMap = new CountryCantonMap(CantonUtils.getAllCantons());
        countryCantonMap.setCantonClickCallback(canton -> canton.setIsActive(!canton.isIsActive()));
        countryCantonMap.setActiveCantonColor(Color.valueOf("#0F5E9C"));
        countryCantonMap.setInactiveCantonColor(Color.valueOf("#1CA3EC"));
        countryCantonMap.setPadding(new Insets(10, 10, 10, 10));

        setTop(hydroToolBar);
        setCenter(countryCantonMap);
    }

    @Override
    protected void updateAppBar(AppBar appBar) {
        appBar.setVisible(false);
    }
}
