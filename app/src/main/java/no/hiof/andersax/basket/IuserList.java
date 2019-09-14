package no.hiof.andersax.basket;

import no.hiof.andersax.basket.model.List;
import no.hiof.andersax.basket.model.User;

public interface IuserList {

    public void addSharedList(List list);
    public void addPrivateList(List list);

}
