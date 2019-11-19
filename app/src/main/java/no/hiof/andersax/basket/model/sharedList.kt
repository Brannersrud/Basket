package no.hiof.andersax.basket.model

class sharedList (val members : MutableList<ListMembers>,
                  var usernames : MutableList<String>,
                  listname: String ,
                  description: String,
                  Owner: String , items : MutableList<ListItem>, totalPrice : Long, ListItems : ArrayList<ListItem>
) : ListCollection( listname , description , Owner , items, totalPrice, ListItems)  {




constructor() : this(members=ArrayList<ListMembers>(),usernames = ArrayList<String>(), listname = "", description = "",
    Owner = "", items = ArrayList<ListItem>(), totalPrice = 0, ListItems = ArrayList<ListItem>())

}