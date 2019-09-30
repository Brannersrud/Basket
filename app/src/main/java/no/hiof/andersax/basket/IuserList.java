package no.hiof.andersax.basket;

import java.util.ArrayList;
import java.util.List;

import no.hiof.andersax.basket.model.ListItem;
import no.hiof.andersax.basket.model.User;

public interface IuserList {

    public void addSharedList(String listname , String description, ArrayList<String> userEmails, List<ListItem> items);
    public void addPrivateList( String listname , String description, String owner, List<ListItem> items);
    public void AddListItems(int listId, List<ListItem> item);
    public void UpdateSharedList(int listId, List<ListItem> list);
    public void updateSingleList(int listId, List<ListItem> list);
    public void CheckListItem(ListItem item);

}
