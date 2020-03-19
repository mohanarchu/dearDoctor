package com.example.deardoctor.Weeks;

public interface ActionCompletionContract {
    void onViewMoved(int oldPosition, int newPosition);
    void onViewSwiped(int position);
}