package no.hiof.andersax.basket.model

class sharedList ( val members : MutableList<ListMembers>,
                  listname: String,
                  description: String,
                  Owner: String, items : MutableList<ListItem>, totalPrice : Long
) : ListCollection( listname, description, Owner, items, totalPrice)  {









}