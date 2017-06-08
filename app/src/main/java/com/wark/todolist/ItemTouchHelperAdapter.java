package com.wark.todolist;

/**
 * Created by pc on 2017-06-07.
 */

interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);

}
