package com.Itransition.LaricSecurity.helperСlasses;

import com.Itransition.LaricSecurity.entity.User;

import java.util.List;

public class HelperUser {
    private List<User> checkedItems;

    public List<User> getCheckedItems() {
        return checkedItems;
    }

    public void setCheckedItems(List<User> checkedItems) {
        this.checkedItems = checkedItems;
    }
}