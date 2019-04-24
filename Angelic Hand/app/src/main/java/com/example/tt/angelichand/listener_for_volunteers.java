package com.example.tt.angelichand;

public interface listener_for_volunteers {
    public void onSuccess(String code,volunteer object);
    public void onSuccess(String code,requested_volunteer object);
    public void onFailure(Exception e);
}
