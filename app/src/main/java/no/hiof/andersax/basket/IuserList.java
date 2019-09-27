package no.hiof.andersax.basket;

import java.util.ArrayList;

import no.hiof.andersax.basket.model.List;
import no.hiof.andersax.basket.model.ListItem;
import no.hiof.andersax.basket.model.User;

public interface IuserList {

    public void addSharedList(String uid,String listname , String description, ArrayList<String> userEmails, ArrayList<ListItem> items);
    public void addPrivateList(String uid, String listname , String description, ArrayList<ListItem> items);
    public void AddListItems(int listId, ArrayList<ListItem> item);
    public void UpdateSharedList(int listId, ArrayList<ListItem> list);
    public void updateSingleList(int listId, ArrayList<ListItem> list);
    public void CheckListItem(ListItem item);

}
